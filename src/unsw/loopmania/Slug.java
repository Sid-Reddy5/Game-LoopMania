package unsw.loopmania;

import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Slug extends BasicEnemy {
    public Slug(PathPosition position) {
        super(position);
        setType("slug");
        setDamage(10);
        setBattleRadius(2);
        setSupportRadius(2);
        setSpeed(1);
    }
}