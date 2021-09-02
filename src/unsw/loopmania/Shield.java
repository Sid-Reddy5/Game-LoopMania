package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped shield in the backend world
 */
public class Shield extends EquipItems {
    // TODO = add more weapon/item types
    
    
    private int price;

    private String type;

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

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.price = 200;
        this.type = "protect";
    }     
    public String getItemType() {
        return "Shield";
    } 
    public int getDamageType(String enemyType) {
        //return this.damage;
        return 0;
    }
}
