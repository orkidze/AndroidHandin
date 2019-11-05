package com.adrcourseassignment.dataModels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_joke")
public class FavoriteJoke {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;

    public FavoriteJoke(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
