package com.example.lexus23.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lexus23.Model.Photo;

import java.util.List;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM photos")
    List<Photo> getAll();

    @Query("SELECT * FROM photos WHERE id=:id")
    List<Photo> getCurrent(Integer id);

    @Insert
    void insertAll(Photo... movies);

    @Delete
    void delete(Photo movie);
}

