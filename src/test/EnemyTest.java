package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.lang.*;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.javatuples.*;

import unsw.loopmania.*;
import unsw.loopmania.Character;


/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */

public class EnemyTest {
    @Test
    public void SlugPropertyTest() {
        Slug slug = new Slug(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        assertEquals(slug.getDamage(), 10);
        assertEquals(slug.getBattleRadius(), 2);
        assertEquals(slug.getSupportRadius(), 2);
        assertEquals(slug.getHealth(), 100);
    }
    @Test
    public void ZombiePropertyTest() {
        Zombie zombie = new Zombie(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        assertEquals(zombie.getDamage(), 20);
        assertEquals(zombie.getBattleRadius(), 3);
        assertEquals(zombie.getSupportRadius(), 3);
        assertEquals(zombie.getHealth(), 100);
    }
    @Test
    public void VampirePropertyTest() {
        Vampire vampire = new Vampire(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        assertEquals(vampire.getDamage(), 30);
        assertEquals(vampire.getBattleRadius(), 4);
        assertEquals(vampire.getSupportRadius(), 5);
        assertEquals(vampire.getHealth(), 100);
    }
    @Test
    public void CharacterHealthTest() {
        Character character = new Character(new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2))));
        assertEquals(character.getHealth(), 100);
        assertEquals(character.getDamage(), 20);
    }
}
