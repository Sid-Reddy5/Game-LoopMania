package unsw.loopmania;

import java.util.Random;

/**
 * a basic form of item in the world - movingentity because debug for now
 */
public class BasicItem extends MovingEntity {
    // TODO = modify this, and add additional forms of item
    public BasicItem(PathPosition position) {
        super(position);
    }

    /**
     * move the item
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected item behaviour
        // this basic item moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            //moveUpPath();
        }
        else if (directionChoice == 1){
            //moveDownPath();
        }
    }
}
