package org.almiso.jokesapp.model;


import com.google.gson.annotations.SerializedName;

import org.almiso.jokesapp.model.base.JokeObject;

import java.util.List;

public class Joke extends JokeObject {

    @SerializedName("id")
    private int id;
    @SerializedName("joke")
    private String value;
    @SerializedName("categories")
    private List<String> categories;

    public String getValue() {
        return value;
    }
}