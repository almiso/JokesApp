package org.almiso.jokesapp.model;


import com.google.gson.annotations.SerializedName;

import org.almiso.jokesapp.model.base.JokeObject;

public class RandomJokeResponse extends JokeObject {

    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private Joke joke;

    public Joke getJoke() {
        return joke;
    }
}