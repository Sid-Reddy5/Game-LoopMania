package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.lang.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.*;

import unsw.loopmania.*;
import unsw.loopmania.Character;

public class ItemsTest {
    @Test
    public void SwordTest() {
        Sword sword = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(sword.getPrice(), 50);
    }
    @Test
    public void HelmetTest() {
        Helmet helmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(helmet.getPrice(), 70);
    }
    @Test
    public void ArmourTest() {
        Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(armour.getPrice(), 100);
    }
    @Test
    public void SheildTest() {
        Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(shield.getPrice(), 200);
    }
    @Test
    public void StaffTest() {
        Staff staff = new Staff(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(staff.getPrice(), 150);
    }
    @Test
    public void StakeTest() {
        Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(stake.getPrice(), 350);
    }

    @Test
    public void addedStakeTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Stake stake = world.addUnequippedStake();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Stake) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }



    @Test
    public void addedStaffTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Staff staff = world.addUnequippedStaff();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Staff) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    @Test
    public void addedShieldTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Shield shield = world.addUnequippedShield();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Shield) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    @Test
    public void addedSwordTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Sword sword = world.addUnequippedSword();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Sword) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    @Test
    public void addedHelmetTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Helmet helmet = world.addUnequippedHelmet();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Helmet) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    @Test
    public void addedGoldTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Gold gold = world.addUnequippedGold();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Gold) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    @Test
    public void addedHealthPotionTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        HealthPotion healthPotion = world.addUnequippedHealthPotion();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof HealthPotion) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    @Test
    public void addedArmourTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Armour armour = world.addUnequippedArmour();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Armour) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    /*@Test
    public void getUnequippedInventoryItemEntityByCoordinates() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Armour armour = world.addUnequippedArmour();
        Boolean added = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            if (unequiped instanceof Armour) added = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(added, true);
    }

    @Test
    public void removeUnequippedInventoryItem() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Armour armour = world.addUnequippedArmour();
        Boolean remove = false;
        for (Entity unequiped : world.getUnequippedInventoryItems())
        //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
            world.removeUnequippedInventoryItemByCoordinates(0, 0); 
            //if (unequiped == null) remove = true;
        //assertEquals(stake.getPrice(), 350);
        assertEquals(remove, true);
    }*/
}
