package com.example.teo.tpkbuilder;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by teo on 08-Nov-17.
 */

@Entity(indices = {@Index("id")})
public class Monster {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int cr;

    public Monster(){}

    public Monster(String name, int cr){
        this.name = name;
        this.cr = cr;
    }

    public  int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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
