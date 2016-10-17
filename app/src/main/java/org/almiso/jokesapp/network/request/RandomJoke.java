package org.almiso.jokesapp.network.request;


import org.almiso.jokesapp.model.RandomJokeResponse;
import org.almiso.jokesapp.network.JokeSdk;

import retrofit2.http.GET;
import rx.Observable;

public class RandomJoke extends BaseRequest<RandomJokeResponse> {

    private interface IRandomJoke {
        @GET("jokes/random")
        Observable<RandomJokeResponse> createRequest();
    }

    @Override
    protected Observable createRequest() {
        return JokeSdk.getRetrofit().create(IRandomJoke.class).createRequest();
    }
}