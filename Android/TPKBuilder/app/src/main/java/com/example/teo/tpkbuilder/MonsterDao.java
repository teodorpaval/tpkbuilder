package com.example.teo.tpkbuilder;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by teo on 16-Dec-17.
 */

@Dao
public interface MonsterDao {
    @Query("select * from Monster")
    List<Monster> getEntries();

    @Insert
    void insert(Monster monster);

    @Query("delete from monster where id=:id")
    void delete(int id);

    @Delete
    void remove(Monster monster);

    @Update
    void update(Monster monster);

    @Query("DELETE FROM Monster")
    void nukeAll();
}
