package unsw.loopmania;

import java.util.Random;

/**
 *  a basic form of enemy in the world
 */
public class BasicEnemy extends MovingEntity {
    private String type;
    private int health;
    private int speed;
    private int damage;
    private int battleRadius;
    private int supportRadius;
    
    public BasicEnemy(PathPosition position) {
        super(position);
        this.health = 100;
    }
    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getBattleRadius() {
        return this.battleRadius;
    }

    public void setBattleRadius(int battleRadius) {
        this.battleRadius = battleRadius;
    }

    public int getSupportRadius() {
        return this.supportRadius;
    }

    public void setSupportRadius(int supportRadius) {
        this.supportRadius = supportRadius;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    /**
     * move the enemy
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        // int directionChoice = (new Random()).nextInt(2);
        // if (directionChoice == 0){
        //     moveUpPath();
        // }
        // else if (directionChoice == 1){
        //     moveDownPath();
        // }
        moveUpPath();
    }
}
