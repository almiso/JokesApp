package org.almiso.jokesapp.model;


import com.google.gson.annotations.SerializedName;

import org.almiso.jokesapp.model.base.JokeObject;

import java.util.List;

public class FewJokesResponse extends JokeObject {

    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private List<Joke> jokes;

    public List<Joke> getJokes() {
        return jokes;
    }
}