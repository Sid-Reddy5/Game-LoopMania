package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a vampire castle card in the backend game world
 */
public class CampFireCard extends Card {
    // TODO = add more types of card
    public CampFireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "CampFireCard";
    }    
}
