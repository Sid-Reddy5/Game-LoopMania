package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends EquipItems {
    private int price;
    // TODO = add more weapon/item types
    private String type;
    // private int damage;

    // public int getDamage() {
    //     return this.damage;
    // }

    // public void setDamage(int damage) {
    //     this.damage = damage;
    // }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.price = 50;
        this.type = "protect";
        //this.damage = 10;
    }    
    public String getItemType() {
        return "Sword";
    }
    public int getDamageType(String enemyType) {
        return 10;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
