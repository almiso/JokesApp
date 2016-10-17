package org.almiso.jokesapp.network.request;


import org.almiso.jokesapp.model.RandomJokeResponse;
import org.almiso.jokesapp.network.JokeSdk;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public class JokeForName extends BaseRequest<RandomJokeResponse> {

    private String firstName;
    private String lastName;

    public JokeForName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private interface IJokeForName {
        @GET("jokes/random")
        Observable<RandomJokeResponse> prepareRequest(@Query("firstName") String firstName, @Query("lastName") String lastName);
    }

    @Override
    protected Observable prepareRequest() {
        return JokeSdk.getRetrofit().create(IJokeForName.class).prepareRequest(firstName, lastName);
    }
}