package unsw.loopmania;

import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Vampire extends BasicEnemy {
    public Vampire(PathPosition position) {
        super(position);
        setType("vampire");
        setDamage(30);
        setBattleRadius(4);
        setSupportRadius(5);
        setSpeed(1);
    }
}