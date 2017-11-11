package com.example.teo.tpkbuilder;

/**
 * Created by teo on 08-Nov-17.
 */

public class Monster {
    private String name;
    private int cr;

    public Monster(){}

    public Monster(String name, int cr){
        this.name = name;
        this.cr = cr;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCr() {
        return cr;
    }

    public void setCr(int cr) {
        this.cr = cr;
    }

    @Override
    public String toString() {
        return name + " CR=" + cr;
    }
}
