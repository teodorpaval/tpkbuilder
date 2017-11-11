package com.example.teo.tpkbuilder;

import java.util.ArrayList;

/**
 * Created by teo on 08-Nov-17.
 */

public class MonsterController {
    private ArrayList<Monster> monsterArrayList;

    public MonsterController(){
        monsterArrayList = new ArrayList<Monster>();
        monsterArrayList.add(new Monster("Goblin", 2));
        monsterArrayList.add(new Monster("Hobgoblin", 5));
        monsterArrayList.add(new Monster("Black Dragon", 10));
        monsterArrayList.add(new Monster("Tarrasque", 20));
    }

    public ArrayList<Monster> getMonsterArrayList(){
        return monsterArrayList;
    }

    public void addMonster(String name, Integer cr){
        monsterArrayList.add(new Monster(name, cr));
    }
    public void updateMonster(Integer index, String newName){
        monsterArrayList.get(index).setName(newName);
    }
}
