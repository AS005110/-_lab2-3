package com.example.lexus23.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lexus23.Model.Photo;

@Database(entities = {Photo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PhotoDao photoDao();
}
