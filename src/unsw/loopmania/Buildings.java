package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Buildings that can be placed on the map.
 */
public class Buildings extends StaticEntity {
    /**
     * building type
     */
    protected String type; 

    /**
     * constructor for building
     * @param x
     * @param y
     */
    public Buildings(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for building
     * @return String
     */
    public String getType() {
        return type;
    }

    
}