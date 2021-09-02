package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Tower building in the backend game world
 */
public class TowerBuilding extends Buildings {
    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "TowerBuilding";
    }    

}
