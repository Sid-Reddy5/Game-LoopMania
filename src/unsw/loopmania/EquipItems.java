package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;


public abstract class EquipItems extends StaticEntity {
    private String type;
    private int damage;
    public String getType() {
        return type;
    }
    public int getDamage() {
        return damage;
    }
    
    public EquipItems(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    public abstract String getItemType();
    public abstract int getDamageType(String enemyType);
}
