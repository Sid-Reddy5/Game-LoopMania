package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped stake in the backend world
 */
public class Stake extends EquipItems {
    // TODO = add more weapon/item types
    private int price;

    private String type;
    //private int damage;

    public int getDamageType(String enemyType) {
        //return this.damage;
        if (enemyType.equals("vampire")) {
            return 15;
        } else {
            return 5;
        }
    }

    

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.price = 350;
        this.type = "protect";
    }     
    public String getItemType() {
        return "Stake";
    } 
}
