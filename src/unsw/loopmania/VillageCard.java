package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a vampire castle card in the backend game world
 */
public class VillageCard extends Card {
    // TODO = add more types of card
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "VillageCard";
    }    
}
