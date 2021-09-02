package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Hero's Castle building in the backend game world
 */
public class HerosCastleBuilding extends Buildings {
    public HerosCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "HerosCastleBuilding";
    }    


}
