package com.jilquero.project2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insert(Phone item);

    @Query("SELECT * FROM phones ORDER BY manufacturer ASC")
    LiveData<List<Phone>> getAllPhones();

    @Update
    void update(Phone phone);

    @Query("DELETE FROM phones")
    void deleteAll();
    @Query("DELETE FROM phones WHERE id = :phoneId")
    void deleteById(long phoneId);
}
