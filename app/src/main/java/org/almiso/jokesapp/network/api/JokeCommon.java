package org.almiso.jokesapp.network.api;


import org.almiso.jokesapp.network.request.BaseRequest;
import org.almiso.jokesapp.network.request.FewJokes;
import org.almiso.jokesapp.network.request.JokeForName;
import org.almiso.jokesapp.network.request.RandomJoke;

public class JokeCommon {

    public BaseRequest getRandomJoke() {
        RandomJoke request = new RandomJoke();
        return request;
    }

    public BaseRequest getJokeForName(String firstName, String lastName) {
        JokeForName request = new JokeForName(firstName, lastName);
        return request;
    }

    public BaseRequest getFewJokes(int count) {
        FewJokes request = new FewJokes(count);
        return request;
    }
}