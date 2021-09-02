package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.util.EnumMap;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import javafx.beans.binding.Bindings;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEMSWORD,
    ITEMSTAKE,
    ITEMSTAFF,
    ITEMARMOUR,
    ITEMSHIELD,
    ITEMHELMET,
    ITEMGOLD,
    ITEMHEALTHPOTION
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private Label health = new Label();
    private Label cylesLabel = new Label();

    @FXML
    private Label gameOverScreen = new Label();
    @FXML
    private Label gameSuccessScreen = new Label();


    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;





    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;
    private Character character;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    private Image vampireCastleCardImage;
    private Image barracksCardImage;
    private Image campFireCardImage;
    private Image towerCardImage;
    private Image trapCardImage;
    private Image villageCardImage;
    private Image zombiePitCardImage;

    private Image vampireBuildingImage;
    private Image barracksBuildingImage;
    private Image herosCastleBuildingImage;
    private Image campFireBuildingImage;
    private Image towerBuildingImage;
    private Image trapBuildingImage;
    private Image villageBuildingImage;
    private Image zombiePitBuildingImage;

    private Image basicEnemyImage;
    private Image basicItemImage;
    private Image vampireImage;
    private Image zombieImage;
    private Image swordImage;
    private Image basicBuildingImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image goldImage;
    private Image healthPotionImage;

    List<Image> allImages;
    List<Image> buildingCardsImage;
    List<Image> buildingImages;
    List<Image> allItemImages;
    List<Image> allEnemyImages;

    
    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the building/item which should be dropped
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    /**
     * object handling switching to the shop menu
     */
    private MenuSwitcher shopMenuSwitcher;
    private MenuSwitcher gameOverSwitcher;
    private MenuSwitcher gameWonSwitcher;

    private MenuSwitcher shopMusicSwitcher;

    /**
     * object handling switching to the help menu
     */
    private MenuSwitcher helpMenuSwitcher;

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        this.character = world.getCharacter();
        entityImages = new ArrayList<>(initialEntities);

        vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        barracksCardImage = new Image((new File("src/images/barracks_card.png")).toURI().toString()); 
        campFireCardImage = new Image((new File("src/images/campfire_card.png")).toURI().toString()); 
        towerCardImage = new Image((new File("src/images/tower_card.png")).toURI().toString()); 
        trapCardImage = new Image((new File("src/images/trap_card.png")).toURI().toString()); 
        villageCardImage = new Image((new File("src/images/village_card.png")).toURI().toString()); 
        zombiePitCardImage = new Image((new File("src/images/zombie_pit_card.png")).toURI().toString()); 
        basicEnemyImage = new Image((new File("src/images/slug.png")).toURI().toString());
        basicItemImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        vampireImage = new Image((new File("src/images/vampire.png")).toURI().toString());
        zombieImage = new Image((new File("src/images/zombie.png")).toURI().toString());
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        goldImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        healthPotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        basicBuildingImage = new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
        vampireBuildingImage = new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
        barracksBuildingImage = new Image((new File("src/images/barracks.png")).toURI().toString()); 
        herosCastleBuildingImage = new Image((new File("src/images/heros_castle.png")).toURI().toString()); 
        campFireBuildingImage = new Image((new File("src/images/campfire.png")).toURI().toString());
        towerBuildingImage = new Image((new File("src/images/tower.png")).toURI().toString()); 
        trapBuildingImage = new Image((new File("src/images/trap.png")).toURI().toString()); 
        villageBuildingImage = new Image((new File("src/images/village.png")).toURI().toString()); 
        zombiePitBuildingImage = new Image((new File("src/images/zombie_pit.png")).toURI().toString());

        buildingCardsImage = Arrays.asList(vampireCastleCardImage, zombiePitCardImage, towerCardImage, villageCardImage, barracksCardImage, trapCardImage, campFireCardImage);
        buildingImages = Arrays.asList(vampireBuildingImage, zombiePitBuildingImage, towerBuildingImage, villageBuildingImage, barracksBuildingImage, trapBuildingImage, campFireBuildingImage, herosCastleBuildingImage);
        
        
        currentlyDraggedImage = null;
        currentlyDraggedType = null;
        

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() {
        // TODO = load more images/entities during initialization
        
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // Load the Hero's Castle
        HerosCastleBuilding herosCastle = new HerosCastleBuilding(new SimpleIntegerProperty(world.getCastleX()), new SimpleIntegerProperty(world.getCastleY()));
        onLoadHerosCastle(herosCastle);
        world.getBuildingEntities().add(herosCastle);

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        gameOverScreen.setVisible(false);
        gameSuccessScreen.setVisible(false);

        health.textProperty().bind(Bindings.convert(character.HealthValueProperty()));
        //System.out.println(character.HealthValueProperty());

        //Cycles
        //cylesLabel.textProperty().bind(Bindings.convert(world.CyclesValueProperty()));
    }

    /**
     * create and run the timer
     */
    public void startTimer(){
        // TODO = handle more aspects of the behaviour required by the specification
        System.out.println("starting timer");
        //System.out.println(character.getHealth());
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            world.runTickMoves();
            loadCard();
            List<BasicEnemy> defeatedEnemies = world.runBattles();
            if (world.characterInShop()) {
                switchToShopMenu();

                if (!world.getShop().charcterAtTheShop()) {
                    world.setCharacterShop(false);
                }
            }
            if (world.isGameOver()) {
                switchToGameOver();
                //System.out.println("GAME OVER");
            }
            if (world.isGameWon()) {
                switchToGameWon();
               // System.out.println("CONGRATULATIONS - GAME WON");
            }
            if (defeatedEnemies == null ) {
                switchToGameOver();
                //stop();
                //mainController.terminate();
                
            } else {
                for (BasicEnemy e: defeatedEnemies){
                    reactToEnemyDefeat(e);
                }
            }
           

            

            List<BasicItem> defeatedItems = world.runItemBattles();
            for (BasicItem f: defeatedItems){
                reactToItemDefeat(f);
            }
            List<BasicEnemy> newEnemies = world.possiblySpawnEnemies();
            
            for (BasicEnemy newEnemy: newEnemies){
                //if (newEnemy.getType().equals("slug")) {
                onLoad(newEnemy);
                //}
            }
            List<BasicItem> newItems = world.possiblySpawnItems();
            for (BasicItem newItem: newItems){
                onLoad(newItem);
            }
            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate(){
        pause();
    }

    /**
     * pair the entity anentity view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    private void loadCard() {
        List<Card> cards = world.getCardEntities();
        for (Card c: cards) {
            if (c != null) {
                onLoadCard(c);
            }
        }
    }

    /**
     * load a sword from the world, and pair it with an image in the GUI
     */
    private void loadSword(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Sword sword = world.addUnequippedSword();
        onLoad(sword);
    }

    /**
     * load a stake from the world, and pair it with an image in the GUI
     */
    private void loadStake(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Stake stake = world.addUnequippedStake();
        onLoad(stake);
    }

    private void loadStaff(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Staff staff = world.addUnequippedStaff();
        onLoad(staff);
    }

    private void loadArmour(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Armour armour = world.addUnequippedArmour();
        onLoad(armour);
    }

    private void loadShield(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Shield shield = world.addUnequippedShield();
        onLoad(shield);
    }

    private void loadHelmet(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Helmet helmet = world.addUnequippedHelmet();
        onLoad(helmet);
    }

    private void loadGold(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Gold gold = world.addUnequippedGold();
        onLoad(gold);
    }
    
    private void loadHealthPotion(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        HealthPotion healthPotion = world.addUnequippedHealthPotion();
        onLoad(healthPotion);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    // private void reactToEnemyDefeat(BasicEnemy enemy){
    //     // react to character defeating an enemy
    //     // in starter code, spawning extra card/weapon...
    //     // TODO = provide different benefits to defeating the enemy based on the type of enemy
        
    //     loadSword();
    //     loadStake();
    //     loadStaff();
    //     world.loadCard();
    //     loadArmour();
    //     loadShield();
    //     loadHelmet();
    //     //loadGold();
    //     //loadHealthPotion();
    //     //loadVampireCard();
    // }
    private void reactToEnemyDefeat(BasicEnemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        // TODO = provide different benefits to defeating the enemy based on the type of enemy
        world.loadCard();
        Random rngIII = new Random();
        int minIII = 1;
        int maxIII = 3;
        int upperBoundIII = maxIII - minIII + 1;
        int itemDrops = minIII + rngIII.nextInt(upperBoundIII);

        while (itemDrops > 0) {

            Random rng = new Random();
            int min = 0;
            int max = 7;
            int upperBound = max - min + 1;
            int itemDropNumber = min + rng.nextInt(upperBound);
            //System.out.println(itemDropNumber);
            
            switch(itemDropNumber) {
                case(0):
                    loadSword();
                    break;
                case(1):
                    loadStake();
                    break;
                case(2):
                    loadStaff();
                    break;
                case(3):
                    loadArmour();
                    break;
                case(4):
                    loadShield();
                    break;
                case(5):
                    loadHelmet();
                    break;
                case(6):
                    loadGold();
                    break;
                case(7):
                    loadHealthPotion();
                    break;
            }

            itemDrops = itemDrops - 1;

        }
    }

    /**
     * run GUI events after an item is defeated, such as spawning items/experience/gold
     * @param item defeated item for which we should react to the death of
     */
    private void reactToItemDefeat(BasicItem item){
        // react to character defeating an item
        // in starter code, spawning extra card/weapon...
        // TODO = provide different benefits to defeating the item based on the type of item
        //loadArmour();
        //loadShield();
        //loadHelmet();
        //loadGold();
        loadHealthPotion();
      
        //loadVampireCard();
        //loadBarracksCard();
        //loadCampFireCard();
        //loadTowerCard();
        //loadTrapCard();
        //loadVillageCard();
        //loadZombiePitCard();

    }

  

    private void onLoadCard(Card card) {
        Image image = getCardImage(card, buildingCardsImage);
        ImageView view = new ImageView(image);
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);
        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load a sword into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param sword
     */
    private void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMSWORD, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }
    
    private void onLoad(Stake stake) {
        ImageView view = new ImageView(stakeImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMSTAKE, unequippedInventory, equippedItems);
        addEntity(stake, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Staff staff) {
        ImageView view = new ImageView(staffImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMSTAFF, unequippedInventory, equippedItems);
        addEntity(staff, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Armour armour) {
        ImageView view = new ImageView(armourImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMARMOUR, unequippedInventory, equippedItems);
        addEntity(armour, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Shield shield) {
        ImageView view = new ImageView(shieldImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMSHIELD, unequippedInventory, equippedItems);
        addEntity(shield, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Helmet helmet) {
        ImageView view = new ImageView(helmetImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMHELMET, unequippedInventory, equippedItems);
        addEntity(helmet, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Gold gold) {
        ImageView view = new ImageView(goldImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMGOLD, unequippedInventory, equippedItems);
        addEntity(gold, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(HealthPotion healthPotion) {
        ImageView view = new ImageView(healthPotionImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEMHEALTHPOTION, unequippedInventory, equippedItems);
        addEntity(healthPotion, view);
        unequippedInventory.getChildren().add(view);
    }
    /**
     * load an enemy into the GUI
     * @param enemy
     */
    private void onLoad(BasicEnemy enemy) {
        if (enemy.getType().equals("slug")) {
            ImageView view = new ImageView(basicEnemyImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        } else if (enemy.getType().equals("zombie")) {
            ImageView view = new ImageView(zombieImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        } else if (enemy.getType().equals("vampire")) {
            ImageView view = new ImageView(vampireImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        }
        //ImageView view = new ImageView(basicEnemyImage);
        
    }

    /**
     * load an item into the GUI
     * @param item
     */
    private void onLoad(BasicItem item) {
        ImageView view = new ImageView(basicItemImage);
        addEntity(item, view);
        squares.getChildren().add(view);
    }

    private void onLoadBuilding(Buildings building) {
        Image image = getBuildingImage(building, buildingImages);
        ImageView view = new ImageView(image);
        addEntity(building, view);
        squares.getChildren().add(view);
    }
    
    
    
    
    

    private void onLoadHerosCastle(Buildings building) {
        ImageView view = new ImageView(herosCastleBuildingImage);
        addEntity(building, view);
        squares.getChildren().add(view);
    }


    
    private Sword convertSwordtoItemByCoordinates(int cardNodeX, int cardNodeY, int x, int y) {
        return world.convertSwordtoItemByCoordinates(cardNodeX, cardNodeY, x, y);
    }
    private Armour convertArmourtoItemByCoordinates(int cardNodeX, int cardNodeY, int x, int y) {
        return world.convertArmourtoItemByCoordinates(cardNodeX, cardNodeY, x, y);
    }

    private Staff convertStafftoItemByCoordinates(int cardNodeX, int cardNodeY, int x, int y) {
        return world.convertStafftoItemByCoordinates(cardNodeX, cardNodeY, x, y);
    }
    private Stake convertStaketoItemByCoordinates(int cardNodeX, int cardNodeY, int x, int y) {
        return world.convertStaketoItemByCoordinates(cardNodeX, cardNodeY, x, y);
    }
    private Helmet convertHelmettoItemByCoordinates(int cardNodeX, int cardNodeY, int x, int y) {
        return world.convertHelmettoItemByCoordinates(cardNodeX, cardNodeY, x, y);
    }
    private Shield convertShieldtoItemByCoordinates(int cardNodeX, int cardNodeY, int x, int y) {
        return world.convertShieldtoItemByCoordinates(cardNodeX, cardNodeY, x, y);
    }
    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * add drag event handlers for dropping into gridpanes, dragging over the
     * background, dropping over the background. These are not attached to invidual
     * items such as swords/cards.
     * 
     * @param draggableType  the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to
     *                       (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane) {
        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != targetGridPane && db.hasImage()) {

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());
                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType) {
                            case CARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Buildings newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if (newBuilding != null) {
                                    onLoadBuilding(newBuilding);
                                } else {
                                    // Removes transparent image upon placing the building
                                    node.setOpacity(1);
                                    Card card = world.getCard(nodeX, nodeY);
                                    onLoadCard(card);
                                }
                                break;
                            case ITEMSWORD:
                                Sword sword = convertSwordtoItemByCoordinates(nodeX, nodeY, x, y);
                                
                                if (sword != null) {
                                    removeItemByCoordinates(nodeX, nodeY);
                                    targetGridPane.add(image, x, y, 1, 1);
                                } else {
                                    node.setOpacity(1);
                                    onLoad(sword);
                                }
                                break;
                            case ITEMSTAKE:
                                Stake stake = convertStaketoItemByCoordinates(nodeX, nodeY, x, y);
                                    
                                if (stake != null) {
                                    removeItemByCoordinates(nodeX, nodeY);
                                    targetGridPane.add(image, x, y, 1, 1);
                                } else {
                                    node.setOpacity(1);
                                    onLoad(stake);
                                }
                                break;
                            case ITEMSTAFF:
                                Staff staff = convertStafftoItemByCoordinates(nodeX, nodeY, x, y);
                                
                                if (staff != null) {
                                    removeItemByCoordinates(nodeX, nodeY);
                                    targetGridPane.add(image, x, y, 1, 1);
                                } else {
                                    node.setOpacity(1);
                                    onLoad(staff);
                                }
                                break;
                            case ITEMARMOUR:
                                Armour armour = convertArmourtoItemByCoordinates(nodeX, nodeY, x, y);
                                    
                                if (armour != null) {
                                    removeItemByCoordinates(nodeX, nodeY);
                                    targetGridPane.add(image, x, y, 1, 1);
                                } else {
                                    node.setOpacity(1);
                                    onLoad(armour);
                                }
                                break;
                            case ITEMSHIELD:
                                Shield shield = convertShieldtoItemByCoordinates(nodeX, nodeY, x, y);
                                    
                                if (shield != null) {
                                    removeItemByCoordinates(nodeX, nodeY);
                                    targetGridPane.add(image, x, y, 1, 1);
                                } else {
                                    node.setOpacity(1);
                                    onLoad(shield);
                                }
                                break;
                            case ITEMHELMET:
                                Helmet helmet = convertHelmettoItemByCoordinates(nodeX, nodeY, x, y);
                                    
                                if (helmet != null) {
                                    removeItemByCoordinates(nodeX, nodeY);
                                    targetGridPane.add(image, x, y, 1, 1);
                                } else {
                                    node.setOpacity(1);
                                    onLoad(helmet);
                                }
                                break;
                            case ITEMGOLD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;
                            case ITEMHEALTHPOTION:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;
                            default:
                                break;
                        }

                        // The transparent image is set to false
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a
                // sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read
                // https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for
        // dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>() {
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    if (event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null) {
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for
        // dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != anchorPaneRoot && db.hasImage()) {
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);

                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                // let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }


    private Buildings convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }
    private Image getCardImage(Card card, List<Image> allImages) {
        Image image;
        String cardType = card.getCardType();
        switch(cardType) {
            case "VampireCastleCard":
                image = allImages.get(0);
                return image;
            case "ZombiePitCard":
                image = allImages.get(1);
                return image;
            case "TowerCard":
                image = allImages.get(2);
                return image;
            case "VillageCard":
                image = allImages.get(3);
                return image;
            case "BarracksCard":
                image = allImages.get(4);
                return image;
            case "TrapCard":
                image = allImages.get(5);
                return image;
            case "CampFireCard":
                image = allImages.get(6);
                return image;
        }
        return null;
    }



    private Image getBuildingImage(Buildings building, List<Image> allImages) {
        Image image;
        String buildingType = building.getType();
        switch(buildingType) {
            case "VampireCastleBuilding":
                image = allImages.get(0);
                return image;
            case "ZombiePitBuilding":
                image = allImages.get(1);
                return image;
            case "TowerBuilding":
                image = allImages.get(2);
                return image;
            case "VillageBuilding":
                image = allImages.get(3);
                return image;
            case "BarracksBuilding":
                image = allImages.get(4);
                return image;
            case "TrapBuilding":
                image = allImages.get(5);
                return image;
            case "CampFireBuilding":
                image = allImages.get(6);
                return image;
        }
        return null;
    }



    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);
                Node node = event.getPickResult().getIntersectedNode();
                Integer cIndex = GridPane.getColumnIndex(node);
                Integer rIndex = GridPane.getRowIndex(node);
                int x = cIndex == null ? 0 : cIndex;
                int y = rIndex == null ? 0 : rIndex;
                //ImageSelector imageSelector = new ImageSelector();
                Image image = null;
                int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                int nodeY = GridPane.getRowIndex(currentlyDraggedImage);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case CARD:
                        Card card = world.getCard(x, y);
                        image = getCardImage(card, buildingCardsImage);
                        draggedEntity.setImage(image);
                        break;
                    case ITEMSWORD:
                        draggedEntity.setImage(swordImage);
                        break;
                    case ITEMSTAKE:
                        draggedEntity.setImage(stakeImage);
                        break;
                    case ITEMSTAFF:
                        draggedEntity.setImage(staffImage);
                        break;
                    case ITEMARMOUR:
                        draggedEntity.setImage(armourImage);
                        break;
                    case ITEMSHIELD:
                        draggedEntity.setImage(shieldImage);
                        break;
                    case ITEMHELMET:
                        draggedEntity.setImage(helmetImage);
                        break;
                    case ITEMGOLD:
                        draggedEntity.setImage(goldImage);
                        break;
                    case ITEMHEALTHPOTION:
                        draggedEntity.setImage(healthPotionImage);
                        break;

                    default:
                        break;
                }
                
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                if(event.getGestureSource() != n && event.getDragboard().hasImage()){
                                    n.setOpacity(0.7);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, you could program the game so if the new highlight location is invalid the highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                startTimer();
            }
            else{
                pause();
            }
            break;
        default:
            break;
        }
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    public void setShopMenuSwitcher(MenuSwitcher shopMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.shopMenuSwitcher = shopMenuSwitcher;
    }

    public void setGameOverSwitcher(MenuSwitcher gameOverSwitcher) {
        this.gameOverSwitcher = gameOverSwitcher;
    }

    public void setGameWonSwitcher(MenuSwitcher gameWonSwitcher) {
        this.gameWonSwitcher = gameWonSwitcher;
    }
    public void setMusicSwitcher(MenuSwitcher shopMusicSwitcher){
        // TODO = possibly set other menu switchers
        this.shopMusicSwitcher = shopMusicSwitcher;
    }

    public void setHelpMenuSwitcher(MenuSwitcher helpMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.helpMenuSwitcher = helpMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        mainMenuSwitcher.switchMenu();
    }

    /**
     * Goes to the shop
     * 
     */
    @FXML
    private void switchToShopMenu() {
        // TODO = possibly set other menu switchers
        pause();
        shopMenuSwitcher.switchMenu();
    }

    /**
     * Game Over 
     * 
     */
    @FXML
    private void switchToGameOver() {
        //pause();
        gameOverSwitcher.switchMenu();
        terminate();
    }


    /**
     * Game Won 
     * 
     */
    @FXML
    private void switchToGameWon() {
  
        pause();
        gameWonSwitcher.switchMenu();
        //terminate();
    }
    /**
     * this method is triggered when click button to go to shop in FXML
     * @throws IOException
     */
    @FXML
    private void switchToHelpMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        helpMenuSwitcher.switchMenu();
    }

    

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }
    
    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
        System.out.println("Number of Cycles completed is: " + world.getCycles());
    }

    
}
