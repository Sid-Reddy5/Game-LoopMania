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

public class CardsTest {
    

    // @Test
    // public void loadVampireCard() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     VampireCastleCard vampireCastleCard = world.getCard(0, 0);
    //     Boolean added = false;
    //     for (Card card : world.getCardEntities())
    //     //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
    //         if (card instanceof VampireCastleCard) added = true;
    //     //assertEquals(stake.getPrice(), 350);
    //     assertEquals(added, true);



    // }

    // @Test
    // public void loadBarracksCard() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     BarracksCard BarracksCard = world.loadBarracksCard();
    //     Boolean added = false;
    //     for (Card card : world.getCardEntities())
    //     //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
    //         if (card instanceof BarracksCard) added = true;
    //     //assertEquals(stake.getPrice(), 350);
    //     assertEquals(added, true);
    // }


    // @Test
    // public void loadCampFireCard() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     CampFireCard CampFireCard = world.loadCampFireCard();
    //     Boolean added = false;
    //     for (Card card : world.getCardEntities())
    //     //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
    //         if (card instanceof CampFireCard) added = true;
    //     //assertEquals(stake.getPrice(), 350);
    //     assertEquals(added, true);
    // }

    // @Test
    // public void loadTowerCard() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     TowerCard TowerCard = world.loadTowerCard();
    //     Boolean added = false;
    //     for (Card card : world.getCardEntities())
    //     //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
    //         if (card instanceof TowerCard) added = true;
    //     //assertEquals(stake.getPrice(), 350);
    //     assertEquals(added, true);
    // }


    // @Test
    // public void loadTrapCard() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     TrapCard TrapCard = world.loadTrapCard();
    //     Boolean added = false;
    //     for (Card card : world.getCardEntities())
    //     //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
    //         if (card instanceof TrapCard) added = true;
    //     //assertEquals(stake.getPrice(), 350);
    //     assertEquals(added, true);
    // }

    // @Test
    // public void loadVillageCard() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     VillageCard VillageCard = world.loadVillageCard();
    //     Boolean added = false;
    //     for (Card card : world.getCardEntities())
    //     //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
    //         if (card instanceof VillageCard) added = true;
    //     //assertEquals(stake.getPrice(), 350);
    //     assertEquals(added, true);
    // }

    // @Test
    // public void loadZombiePitCard() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     ZombiePitCard ZombiePitCard = world.loadZombiePitCard();
    //     Boolean added = false;
    //     for (Card card : world.getCardEntities())
    //     //Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
    //         if (card instanceof ZombiePitCard) added = true;
    //     //assertEquals(stake.getPrice(), 350);
    //     assertEquals(added, true);
    // }

    // @Test
    // public void convertBarracksCardToBuildingByCoordinates() {
    //     List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    //     orderedPath.add(new Pair<>(0,0));
    //     orderedPath.add(new Pair<>(0,1));
    //     orderedPath.add(new Pair<>(0,2));
    //     orderedPath.add(new Pair<>(0,3));
    //     orderedPath.add(new Pair<>(1,0));
    //     orderedPath.add(new Pair<>(1,1));
    //     orderedPath.add(new Pair<>(1,2));
    //     orderedPath.add(new Pair<>(1,3));
    //     LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
    //     BarracksBuilding barracks = world.convertBarracksCardToBuildingByCoordinates(0, 0, 0, 1);
    //     Boolean added = false;
    //     for (Buildings building: world.getBuildingEntities())
    //         if (building instanceof BarracksBuilding) added = true;
    //     assertEquals(added, true);
    // }

}
