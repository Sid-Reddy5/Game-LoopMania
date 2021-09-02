package unsw.loopmania;

import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Zombie extends BasicEnemy {
    public Zombie(PathPosition position) {
        super(position);
        setType("zombie");
        setDamage(20);
        setBattleRadius(3);
        setSupportRadius(3);
        setSpeed(1);
    }
}