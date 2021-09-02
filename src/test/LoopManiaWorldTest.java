package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.lang.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.*;

import unsw.loopmania.*;
import unsw.loopmania.Character;

public class LoopManiaWorldTest {
    @Test
    public void basicBattleWithSlug() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Character character = new Character(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        Slug slug = new Slug(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        world.setCharacter(character);
        world.addEnemy(slug);
        List<BasicEnemy> enemies = world.runBattles();
        assertEquals(1, enemies.size());
        assertEquals(enemies.get(0).getType(), "slug");
        world.runTickMoves();
    } 
    @Test
    public void basicBattleWithVampire() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Character character = new Character(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        Vampire vampire = new Vampire(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        world.setCharacter(character);
        world.addEnemy(vampire);
        List<BasicEnemy> enemies = world.runBattles();
        assertEquals(world.getEnemies().size(), 1);
        assertEquals(enemies, null);
        //assertEquals(true, enemies.equals(null));
        //assertEquals(enemies.get(0).getType(), "vampire");
        world.runTickMoves();
    } 
    @Test
    public void basicBattleWithZombie() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Character character = new Character(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        Zombie zombie = new Zombie(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        world.setCharacter(character);
        world.addEnemy(zombie);
        List<BasicEnemy> enemies = world.runBattles();
        //assertEquals(0, enemies.size());
        assertEquals(world.getEnemies().size(), 1);
        assertEquals(enemies, null);
        //assertEquals(enemies.get(0).getType(), "zombie");
        world.runTickMoves();
    } 

    @Test
    public void convertSwordToCoordTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Sword sword = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Entity> unequip = world.getUnequippedInventoryItems();
        unequip.add(sword);
        assertEquals(unequip.size(), 1);
        world.setUnequippedInventoryItems(unequip);
        Sword test = world.convertSwordtoItemByCoordinates(0, 0, 0, 0);
        List<EquipItems> equip = world.getEquippedInv();
        assertEquals(equip.size(), 3);
       // Character character = new Character(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        // Zombie zombie = new Zombie(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        // world.setCharacter(character);
        // world.addEnemy(zombie);
        // List<BasicEnemy> enemies = world.runBattles();
        // //assertEquals(0, enemies.size());
        // assertEquals(world.getEnemies().size(), 1);
        // assertEquals(enemies, null);
        // //assertEquals(enemies.get(0).getType(), "zombie");
        //world.runTickMoves();
    } 
    @Test
    public void convertStakeToCoordTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Entity> unequip = world.getUnequippedInventoryItems();
        unequip.add(stake);
        assertEquals(unequip.size(), 1);
        world.setUnequippedInventoryItems(unequip);
        Stake test = world.convertStaketoItemByCoordinates(0, 0, 0, 0);
        List<EquipItems> equip = world.getEquippedInv();
        assertEquals(equip.size(), 3);
       // Character character = new Character(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        // Zombie zombie = new Zombie(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        // world.setCharacter(character);
        // world.addEnemy(zombie);
        // List<BasicEnemy> enemies = world.runBattles();
        // //assertEquals(0, enemies.size());
        // assertEquals(world.getEnemies().size(), 1);
        // assertEquals(enemies, null);
        // //assertEquals(enemies.get(0).getType(), "zombie");
        //world.runTickMoves();
    } 
    @Test
    public void convertStaffToCoordTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Staff staff = new Staff(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Entity> unequip = world.getUnequippedInventoryItems();
        unequip.add(staff);
        assertEquals(unequip.size(), 1);
        world.setUnequippedInventoryItems(unequip);
        Staff s = world.convertStafftoItemByCoordinates(0, 0, 0, 0);
        List<EquipItems> equip = world.getEquippedInv();
        assertEquals(equip.size(), 3);
    } 
    @Test
    public void convertArmourToCoordTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Entity> unequip = world.getUnequippedInventoryItems();
        unequip.add(armour);
        assertEquals(unequip.size(), 1);
        world.setUnequippedInventoryItems(unequip);
        Armour a = world.convertArmourtoItemByCoordinates(0, 0, 0, 0);
        List<EquipItems> equip = world.getEquippedInv();
        assertEquals(equip.size(), 3);
    } 

    @Test
    public void convertShieldToCoordTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Entity> unequip = world.getUnequippedInventoryItems();
        unequip.add(shield);
        assertEquals(unequip.size(), 1);
        world.setUnequippedInventoryItems(unequip);
        Shield s = world.convertShieldtoItemByCoordinates(0, 0, 0, 0);
        List<EquipItems> equip = world.getEquippedInv();
        assertEquals(equip.size(), 3);
    } 
    @Test
    public void convertHelmetToCoordTest() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(new Pair<>(0,0));
        orderedPath.add(new Pair<>(0,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Helmet helmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        List<Entity> unequip = world.getUnequippedInventoryItems();
        unequip.add(helmet);
        assertEquals(unequip.size(), 1);
        world.setUnequippedInventoryItems(unequip);
        Helmet h = world.convertHelmettoItemByCoordinates(0, 0, 0, 0);
        List<EquipItems> equip = world.getEquippedInv();
        assertEquals(equip.size(), 3);
    } 
}
