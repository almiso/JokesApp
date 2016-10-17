package org.almiso.jokesapp.model;

import com.google.gson.annotations.SerializedName;

import org.almiso.jokesapp.model.base.JokeResponse;

public class RandomJokeResponse extends JokeResponse {

    @SerializedName("value")
    private Joke joke;

    public Joke getJoke() {
        return joke;
    }
}