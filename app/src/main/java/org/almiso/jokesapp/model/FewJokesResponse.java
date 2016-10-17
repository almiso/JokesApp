package org.almiso.jokesapp.model;


import com.google.gson.annotations.SerializedName;

import org.almiso.jokesapp.model.base.JokeResponse;

import java.util.ArrayList;
import java.util.List;

public class FewJokesResponse extends JokeResponse {

    @SerializedName("value")
    private List<Joke> jokes;

    public FewJokesResponse(){
        jokes = new ArrayList<>();
    }

    public List<Joke> getJokes() {
        return jokes;
    }
}