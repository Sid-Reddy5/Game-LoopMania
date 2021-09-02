package unsw.loopmania;

public class ShopMenu {

    private LoopManiaWorld world;  

    private boolean charcterShopping; 



    public ShopMenu(LoopManiaWorld world){
        this.world = world;
    }

    public ShopMenu() { 
        this.charcterShopping = false;

    }


    public boolean charcterAtTheShop() {
        return charcterShopping;
    }


    

    public LoopManiaWorld getWorld() {
        return world;
    }

    public void setWorld(LoopManiaWorld world) {
        this.world = world;
    }



}
