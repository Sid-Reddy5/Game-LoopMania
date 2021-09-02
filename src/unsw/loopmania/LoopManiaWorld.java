package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    // TODO = add additional backend functionality

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;
    public static final int equippedInventoryLength = 3;
    public static final int equippedInvLength = 3;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    /**
     * number of the character has completed
     */
    static int cycles;

    private Character character;
    //private HerosCastleBuilding herosCastle;
    private int count = 0;
    private int shopsCount = 1;
    private boolean gameOver = false; 
    private boolean gameWon = false; 

    // TODO = add more lists for other entities, for equipped inventory items, etc...

    // TODO = expand the range of enemies
    private List<BasicEnemy> enemies;

    private List<BasicItem> items;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // TODO = expand the range of items
    private List<Entity> unequippedInventoryItems;
    private List<Entity> equippedInventoryItems;
    private List<EquipItems> equippedInv;

    public List<EquipItems> getEquippedInv() {
        return this.equippedInv;
    }

    public void setEquippedInv(List<EquipItems> equippedInv) {
        this.equippedInv = equippedInv;
    }

    public List<BasicItem> getItems() {
        return this.items;
    }

    public void setItems(List<BasicItem> items) {
        this.items = items;
    }

    public List<Entity> getEquippedInventoryItems() {
        return this.equippedInventoryItems;
    }

    public void setEquippedInventoryItems(List<Entity> equippedInventoryItems) {
        this.equippedInventoryItems = equippedInventoryItems;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public List<Entity> getNonSpecifiedEntities() {
        return this.nonSpecifiedEntities;
    }

    public void setNonSpecifiedEntities(List<Entity> nonSpecifiedEntities) {
        this.nonSpecifiedEntities = nonSpecifiedEntities;
    }

    public Character getCharacter() {
        return this.character;
    }


    public List<BasicEnemy> getEnemies() {
        return this.enemies;
    }

    public void setEnemies(List<BasicEnemy> enemies) {
        this.enemies = enemies;
    }

    public List<Card> getCardEntities() {
        return this.cardEntities;
    }

    public void setCardEntities(List<Card> cardEntities) {
        this.cardEntities = cardEntities;
    }

    public List<Entity> getUnequippedInventoryItems() {
        return this.unequippedInventoryItems;
    }

    public void setUnequippedInventoryItems(List<Entity> unequippedInventoryItems) {
        this.unequippedInventoryItems = unequippedInventoryItems;
    }

    public List<Buildings> getBuildingEntities() {
        return this.buildingEntities;
    }

    public void setBuildingEntities(List<Buildings> buildingEntities) {
        this.buildingEntities = buildingEntities;
    }

    public List<Pair<Integer,Integer>> getOrderedPath() {
        return this.orderedPath;
    }

    public void setOrderedPath(List<Pair<Integer,Integer>> orderedPath) {
        this.orderedPath = orderedPath;
    }

   
   
  
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }




    private int castleX;
    private int castleY;
    // TODO = expand the range of buildings
    private List<Buildings> buildingEntities;
   
    /*
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        //check = false;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        //herosCastle = null;
        cycles = 0;
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        equippedInventoryItems = new ArrayList<>();
        equippedInv = new ArrayList<>();

        // for (int i = 0; i < equippedInventoryLength; i++) {
        //     equippedInventoryItems.add(null);
        // }
        for (int i = 0; i < equippedInvLength; i++) {
            equippedInv.add(null);
        }

        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void setCastleCoordinates(int x, int y) {
        castleX = x;
        castleY = y;
    }

    public int getCastleX() {
        return castleX;
    }

    public int getCastleY() {
        return castleY;
    }

    public void incrementCycles() {
        cycles++;
        incrementCyclesProperty();
    }


    public int getCycles() {
        return cycles;
    }


    private SimpleIntegerProperty cyclesValue = new SimpleIntegerProperty(this, "cyclesValue");


    public IntegerProperty CyclesValueProperty(){
        return cyclesValue;
    }

    public int getCyclesProperty(){
        return cyclesValue.get();
    }

    public void incrementCyclesProperty(){ 
        this.cyclesValue.set(getCyclesProperty()+1);
    }



    private ShopMenu shop = new ShopMenu(); 


    public ShopMenu getShop() {
        return shop;
    }

    

    private boolean characterAtShop = false; 

    public boolean characterInShop() {
        return characterAtShop;
    }

    public void setCharacterShop(boolean characterAtShop) {
        this.characterAtShop = characterAtShop;
    }
   
    public void addShopCount() {
        count += shopsCount;
        shopsCount += 1;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    
    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnEnemies(){
        // TODO = expand this very basic version
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            Slug enemy = new Slug(new PathPosition(indexInPath, orderedPath));
            //Zombie enemy2 = new Zombie(new PathPosition(indexInPath, orderedPath));
            //BasicEnemy enemy = new BasicEnemy(new PathPosition(indexInPath, orderedPath));
            //enemy.
            
            enemies.add(enemy);
            //enemies.add(enemy2);
            spawningEnemies.add(enemy);
            //spawningEnemies.add(enemy2);
        }
        return spawningEnemies;
    }

    /*Conditions warrant it, adds to world
     * @return list of the items to be displayed on screen
     */
    public List<BasicItem> possiblySpawnItems(){
        // TODO = expand this very basic version
        Pair<Integer, Integer> pos = possiblyGetBasicItemSpawnPosition();
        List<BasicItem> spawningItems = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            BasicItem item = new BasicItem(new PathPosition(indexInPath, orderedPath));
            items.add(item);
            spawningItems.add(item);
        }
        return spawningItems;
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    
    private void killEnemy(BasicEnemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * kill an item
     * @param item item to be killed
     */
    private void killItem(BasicItem item){
        item.destroy();
        items.remove(item);
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {

        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        for (BasicEnemy e: enemies){
            int radii = e.getBattleRadius();
            //if (e.getType().equals("slug")) {
                if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < radii*radii){
                    // fight...
                    int healthEnemy = e.getHealth();
                    int healthCharacter = character.getHealth();
                    //put the equip items here
                    for (EquipItems i : equippedInv) {
                        if (i != null) {
                            if (i.getItemType().equals("Armour")) {
                                int halfEnemyDamage = e.getDamage()/2;
                                e.setDamage(halfEnemyDamage);
                            } else if (i.getItemType().equals("Helmet")) {
                                int decreaseEnemyAttack = e.getDamage()-2;
                                e.setDamage(decreaseEnemyAttack);
                            } else if (i.getItemType().equals("Shield")) {
                                int decreaseEnemyAttack = e.getDamage()-4;
                                e.setDamage(decreaseEnemyAttack);
                            }
                            int extraDamage = character.getDamage()+i.getDamageType(e.getType());
                            character.setDamage(extraDamage);
                        }
                    }

                    while (healthEnemy > 0 || healthCharacter > 0){
                        
                        if (healthEnemy <= 0) {
                            defeatedEnemies.add(e);
                            
                            character.setDamage(20);
                            break;
                        } else if (healthCharacter <= 0) {
                            //defeatedEnemies.add(character);
                            //check = "true";
                            //check = true;
                            character.setHealthValue(0);
                            return null;

                        }
                        healthEnemy = healthEnemy-character.getDamage();

                        System.out.println("Health enemy is "+healthEnemy);
                        e.setHealth(healthEnemy);
                        healthCharacter = healthCharacter-e.getDamage();
                        
                        // if (character.getHealthValue() < 0){
                        //     character.setHealthValue(0);
                        // }
                        //e.setHealth(healthCharacter);
                        System.out.println("Health Character is "+healthCharacter);
                        character.setHealth(healthCharacter);
                        character.setHealthValue(healthCharacter);
                        //System.out.println("Health Character is "+character.HealthValueProperty());
                    }
                    if (healthEnemy<=0 && healthCharacter <=0) {
                        return null;
                    }
                    
                }
         
        }
        for (BasicEnemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }
        return defeatedEnemies;


    }
    public void addEnemy(BasicEnemy enemy) {
        enemies.add(enemy);
    }
    /**
     * run the expected battles in the world, based on current world state
     * @return list of items which have been killed
     */
    public List<BasicItem> runItemBattles() {
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<BasicItem> defeatedItems = new ArrayList<BasicItem>();
        for (BasicItem e: items){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < 4){
                // fight...
                defeatedItems.add(e);
            }
        }
        for (BasicItem e: defeatedItems){
            // IMPORTANT = we kill items here, because killItem removes the item from the items list
            // if we killItems in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killItem(e);
        }
        return defeatedItems;
    }

    public void loadCard() {
        if (cardEntities.size() >= getWidth()) {
            removeCard(0);
        }
        Random randomCard = new Random();
        int rand = randomCard.nextInt(10);
        Card card = getCardsByType(rand, cardEntities.size());
        if(card != null) {
            cardEntities.add(card);
        }
    }

    private Card getCardsByType(int cardSelected, int size) {
        Card card;
        switch(cardSelected) {
            case 0:
                card = new CampFireCard(new SimpleIntegerProperty(size), new SimpleIntegerProperty(0));
                return card;
            case 1:
                card = new TrapCard(new SimpleIntegerProperty(size), new SimpleIntegerProperty(0));
                return card;
            case 2:
                card = new VillageCard(new SimpleIntegerProperty(size), new SimpleIntegerProperty(0));
                return card;
            case 3:
                card = new ZombiePitCard(new SimpleIntegerProperty(size), new SimpleIntegerProperty(0));
                return card;
            case 4:
                card = new VampireCastleCard(new SimpleIntegerProperty(size), new SimpleIntegerProperty(0));
                return card;
            case 5:
                card = new TowerCard(new SimpleIntegerProperty(size), new SimpleIntegerProperty(0));
                return card;
            case 6:
                card = new BarracksCard(new SimpleIntegerProperty(size), new SimpleIntegerProperty(0));
                return card;
        }
        return null;
    }

    public Card getCard(int cardNodeX, int cardNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }
        return card;
    }
    
    
    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * spawn a sword in the world and return the sword entity
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Sword addUnequippedSword(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    /** 
     * spawn a stake in the world and return the stake entity
     * @return a stake to be spawned in the controller as a JavaFX node
     */
    public Stake addUnequippedStake(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Stake stake = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(stake);
        return stake;
    }

    //Staff
    public Staff addUnequippedStaff(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Staff staff = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(staff);
        return staff;
    }
    //Armour
    public Armour addUnequippedArmour(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Armour armour = new Armour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(armour);
        return armour;
    }
    //Shield
    public Shield addUnequippedShield(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Shield shield = new Shield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(shield);
        return shield;
    }
    //Helmet
    public Helmet addUnequippedHelmet(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Helmet helmet = new Helmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(helmet);
        return helmet;
    }
    //Gold
    public Gold addUnequippedGold(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Gold gold = new Gold(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(gold);
        return gold;
    }
    //Health potion
    public HealthPotion addUnequippedHealthPotion(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(healthPotion);
        return healthPotion;
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    // public Sword convertSwordtoItemByCoordinates(int nodeX, int nodeY, int x, int y) {
    //     Entity entity = null;
    //     for (Entity i : unequippedInventoryItems) {
    //         if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
    //             entity = i;
    //             break;
    //         }
    //     }
    //     Sword sword = new Sword(new SimpleIntegerProperty(nodeX), new SimpleIntegerProperty(nodeY));
    //     if (entity != null) {
    //         switch (x) {
    //             case 0:
    //                 //if (item.getItemType().equals("Weapon") ) {
    //                     //equippedInventoryItems.set(x, item);
    //                     equippedInventoryItems.set(x, sword);
                        
    //                     System.out.println("Weapon: " + "sword" + " has been added" + equippedInventoryItems.get(0));
    //                 //} else {
    //                  //   entity = null;
    //                 //}
    //                 break;
    //             case 1:
    //             //if (item.getItemType().equals("Weapon") ) {
    //                 //equippedInventoryItems.set(x, item);
    //                 equippedInventoryItems.set(x, sword);
    //                 Entity en = equippedInventoryItems.get(0);
                    
                    
    //                 System.out.println("Weapon: " + "sword2" + " has been added" + equippedInventoryItems.get(0));
    //             //} else {
    //                 //   entity = null;
    //             //}
    //             break;
    //             case 2:
    //             //if (item.getItemType().equals("Weapon") ) {
    //                 //equippedInventoryItems.set(x, item);
    //                 equippedInventoryItems.set(x, sword);
                    
    //                 System.out.println("Weapon: " + "sword3" + " has been added" + equippedInventoryItems.get(0));
    //             //} else {
    //                 //   entity = null;
    //             //}
    //             break;
    //         }
    //     }
    //     return sword;

    // }
    public boolean checkInEquippedInventory(String itemType) {
        for (EquipItems i : equippedInv) {
            if (i != null && i.getItemType().equals(itemType)) {
                return true;
            }
        }
        return false;
    }

    public Sword convertSwordtoItemByCoordinates(int nodeX, int nodeY, int x, int y) {
        Entity entity = null;
        for (Entity i : unequippedInventoryItems) {
            if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
                entity = i;
                break;
            }
        }
        Sword sword = new Sword(new SimpleIntegerProperty(nodeX), new SimpleIntegerProperty(nodeY));
        if (entity != null) {
            switch (x) {
                case 0:
                    if (!checkInEquippedInventory("Sword")) {
                        equippedInv.set(x, sword);
                        //equippedInv.add(sword);
                        System.out.println("Slot 1: Sword has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 1:
                    if (!checkInEquippedInventory("Sword")) {
                        equippedInv.set(x, sword);
                        System.out.println("Slot 2: Sword has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 2:
                    if (!checkInEquippedInventory("Sword")) {
                        equippedInv.set(x, sword);
                        System.out.println("Slot 3: Sword has been added");
                    } else {
                        entity = null;
                    }
                    break;

            }
        }
        return sword;

    }

    public Armour convertArmourtoItemByCoordinates(int nodeX, int nodeY, int x, int y) {
        Entity entity = null;
        for (Entity i : unequippedInventoryItems) {
            if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
                entity = i;
                break;
            }
        }
        Armour armour = new Armour(new SimpleIntegerProperty(nodeX), new SimpleIntegerProperty(nodeY));
        if (entity != null) {
            switch (x) {
                case 0:
                    if (!checkInEquippedInventory("Armour")) {
                        equippedInv.set(x, armour);
                        //equippedInv.add(sword);
                        System.out.println("Slot 1: Armour has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 1:
                    if (!checkInEquippedInventory("Armour")) {
                        equippedInv.set(x, armour);
                        System.out.println("Slot 2: Armour has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 2:
                    if (!checkInEquippedInventory("Armour")) {
                        equippedInv.set(x, armour);
                        System.out.println("Slot 3: Armour has been added");
                    } else {
                        entity = null;
                    }
                    break;

            }
        }
        return armour;

    }

    public Staff convertStafftoItemByCoordinates(int nodeX, int nodeY, int x, int y) {
        Entity entity = null;
        for (Entity i : unequippedInventoryItems) {
            if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
                entity = i;
                break;
            }
        }
        Staff staff = new Staff(new SimpleIntegerProperty(nodeX), new SimpleIntegerProperty(nodeY));
        if (entity != null) {
            switch (x) {
                case 0:
                    if (!checkInEquippedInventory("Staff")) {
                        equippedInv.set(x, staff);
                        //equippedInv.add(sword);
                        System.out.println("Slot 1: Staff has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 1:
                    if (!checkInEquippedInventory("Staff")) {
                        equippedInv.set(x, staff);
                        System.out.println("Slot 2: Staff has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 2:
                    if (!checkInEquippedInventory("Staff")) {
                        equippedInv.set(x, staff);
                        System.out.println("Slot 3: Staff has been added");
                    } else {
                        entity = null;
                    }
                    break;

            }
        }
        return staff;

    }

    public Stake convertStaketoItemByCoordinates(int nodeX, int nodeY, int x, int y) {
        Entity entity = null;
        for (Entity i : unequippedInventoryItems) {
            if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
                entity = i;
                break;
            }
        }
        Stake stake = new Stake(new SimpleIntegerProperty(nodeX), new SimpleIntegerProperty(nodeY));
        if (entity != null) {
            switch (x) {
                case 0:
                    if (!checkInEquippedInventory("Stake")) {
                        equippedInv.set(x, stake);
                        //equippedInv.add(sword);
                        System.out.println("Slot 1: Stake has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 1:
                    if (!checkInEquippedInventory("Stake")) {
                        equippedInv.set(x, stake);
                        System.out.println("Slot 2: Stake has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 2:
                    if (!checkInEquippedInventory("Staff")) {
                        equippedInv.set(x, stake);
                        System.out.println("Slot 3: Stake has been added");
                    } else {
                        entity = null;
                    }
                    break;

            }
        }
        return stake;

    }

    public Helmet convertHelmettoItemByCoordinates(int nodeX, int nodeY, int x, int y) {
        Entity entity = null;
        for (Entity i : unequippedInventoryItems) {
            if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
                entity = i;
                break;
            }
        }
        Helmet helmet = new Helmet(new SimpleIntegerProperty(nodeX), new SimpleIntegerProperty(nodeY));
        if (entity != null) {
            switch (x) {
                case 0:
                    if (!checkInEquippedInventory("Helmet")) {
                        equippedInv.set(x, helmet);
                        //equippedInv.add(sword);
                        System.out.println("Slot 1: Helmet has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 1:
                    if (!checkInEquippedInventory("Helmet")) {
                        equippedInv.set(x, helmet);
                        System.out.println("Slot 2: Helmet has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 2:
                    if (!checkInEquippedInventory("Helmet")) {
                        equippedInv.set(x, helmet);
                        System.out.println("Slot 3: Helmet has been added");
                    } else {
                        entity = null;
                    }
                    break;

            }
        }
        return helmet;

    }

    public Shield convertShieldtoItemByCoordinates(int nodeX, int nodeY, int x, int y) {
        Entity entity = null;
        for (Entity i : unequippedInventoryItems) {
            if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
                entity = i;
                break;
            }
        }
        Shield shield = new Shield(new SimpleIntegerProperty(nodeX), new SimpleIntegerProperty(nodeY));
        if (entity != null) {
            switch (x) {
                case 0:
                    if (!checkInEquippedInventory("Shield")) {
                        equippedInv.set(x, shield);
                        //equippedInv.add(sword);
                        System.out.println("Slot 1: Shield has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 1:
                    if (!checkInEquippedInventory("Shield")) {
                        equippedInv.set(x, shield);
                        System.out.println("Slot 2: Shield has been added");
                    } else {
                        entity = null;
                    }
                    break;
                case 2:
                    if (!checkInEquippedInventory("Shield")) {
                        equippedInv.set(x, shield);
                        System.out.println("Slot 3: Shield has been added");
                    } else {
                        entity = null;
                    }
                    break;

            }
        }
        return shield;

    }

    
    

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        character.moveDownPath();
        moveBasicEnemies();
        detectCharOnTile();
        moveBasicItems(); //NOTE TO SELF - DELETE THIS LATER
        if (cycles == 5 && character.getHealth() >= 50) {
            setGameWon(true);
        } else if (cycles == 5 && character.getGold() >= 100 && character.getHealth() >= 10 ) {
            setGameWon(true);
        } else if (cycles == 15 && character.getHealth() >= 30 ) {
            setGameWon(true);
        } else if (cycles == 20 ) {
            setGameWon(true);
        }
        else if (character.getHealth() <= 0) {
            setGameOver(true);
        }
    }

     // Go through buildings and check if character on path update cycles.
     public void detectCharOnTile() {
        for (Buildings buildings : buildingEntities) {
            if (buildings.getX() == (character.getX()) && buildings.getY() == (character.getY())) {
                if (buildings instanceof HerosCastleBuilding) {
                    if (cycles == count) {
                        System.out.println("Character in Shop");
                        addShopCount();
                        setCharacterShop(true); 
                    }
                    incrementCycles();
                }
            }
        }
        
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to che VampireCastleBuildingck by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        // TODO = expand to more types of enemy
        for (BasicEnemy e: enemies){
            e.move();
        }
    }

    /**
     * move all items
     */
    private void moveBasicItems() {
        // TODO = expand to more types of item
        for (BasicItem e: items){
            e.move();
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition(){
        // TODO = modify this
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (enemies.size() < 2)){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    private Pair<Integer, Integer> possiblyGetBasicItemSpawnPosition(){
        // TODO = modify this
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (items.size() < 2)){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * remove a card by its x, y coordinates
     * 
     * @param cardNodeX     x index from 0 to width-1 of card to be removed
     * @param cardNodeY     y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Buildings convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }
        Buildings building = null;
        building = getBuildings(card.getCardType(), new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), 
                                                checkBuildIsPath(buildingNodeX, buildingNodeY), checkBuildAdjPath(buildingNodeX, buildingNodeY));
        if (building != null) {
            buildingEntities.add(building);
            card.destroy();
            cardEntities.remove(card);
            shiftCardsDownFromXCoordinate(cardNodeX);
        } 
        return building;

    }

    public boolean checkBuildIsPath(int x, int y) {
        for (int i = 0; i < orderedPath.size(); i++) {
            Pair<Integer,Integer> cell = orderedPath.get(i);
            if (cell.getValue0() == x && cell.getValue1() == y) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBuildAdjPath(int x, int y) {
        for (int i = 0; i < orderedPath.size(); i++) {
            Pair<Integer,Integer> cell = orderedPath.get(i);
            if ((cell.getValue0() == x + 1 && cell.getValue1() == y) || (cell.getValue0() == x - 1 && cell.getValue1() == y) ||
                (cell.getValue0() == x && cell.getValue1() == y + 1) || (cell.getValue0() == x && cell.getValue1() == y - 1)) {
                return true;
            }
        }
        return false;
    }


    public void addBuilding(Buildings building) {
        buildingEntities.add(building);
    }

    //get the buiildings by type

    public Buildings getBuildings(String buildingSelector, SimpleIntegerProperty x, SimpleIntegerProperty y, boolean buildIsPath, boolean buildAdjPath) {
        Buildings building;
        switch(buildingSelector) {
            case "VampireCastleCard":
                if (buildAdjPath && !buildIsPath) {
                    building = new VampireCastleBuilding(x, y);
                    return building;
                }
                break;
            case "ZombiePitCard":
                if (buildAdjPath && !buildIsPath) {
                    building = new ZombiePitBuilding(x, y);
                    return building;
                }
                break;
            case "TowerCard":
                if (buildAdjPath && !buildIsPath) {
                    building = new TowerBuilding(x, y);
                    return building;
                }
                break;
            case "VillageCard":
                if (buildIsPath) {
                    building = new VillageBuilding(x, y);
                    return building;
                }
                break;
            case "BarracksCard":
                if (buildIsPath) {
                    building = new BarracksBuilding(x, y);
                    return building;
                }
                break;
            case "TrapCard":
                if (buildIsPath) {
                    building = new TrapBuilding(x, y);
                    return building;
                }
                break;
            case "CampFireCard":
                if (!buildIsPath) {
                    building = new CampFireBuilding(x, y);
                    return building;
                }
                break;
        }
        return null;
    }



    
   
}
