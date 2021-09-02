package unsw.loopmania;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private int health;
    private int speed;
    private int damage;
    private SimpleIntegerProperty healthValue = new SimpleIntegerProperty(this, "healthValue");

    public int getHealthValue(){
        return healthValue.get();
    }
    public void setHealthValue(int health){ 
        this.healthValue.set(health);
    }
    private int gold;

   
   
    // TODO = potentially implement relationships between this class and other classes
    public Character(PathPosition position) {
        super(position);
        this.health = 700;
        this.damage = 20;
        this.gold = 100;
        //this.healthValue.set(health);
        this.healthValue.set(health);
    }
    public IntegerProperty HealthValueProperty(){
        return healthValue;
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

    public int getGold() {
        return this.gold;
    }
}
