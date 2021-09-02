package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {

    /**
     * cardType
     */
    protected String cardType;

    /**
     * constructor for Card
     * @param x
     * @param y
     */
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for card type
     * @return String
     */
    public String getCardType() {
        return cardType;
    }

    
}
