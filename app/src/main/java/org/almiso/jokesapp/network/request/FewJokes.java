package org.almiso.jokesapp.network.request;


import org.almiso.jokesapp.model.FewJokesResponse;
import org.almiso.jokesapp.model.Joke;
import org.almiso.jokesapp.network.JokeSdk;
import org.almiso.jokesapp.util.Logger;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public class FewJokes extends BaseRequest<FewJokesResponse> {

    private int count;

    public FewJokes(int count) {
        this.count = count;
    }

    private interface IFewJokes {

        @GET("jokes/random/{count}")
        Observable<FewJokesResponse> createRequest(@Path("count") int count);
    }

    @Override
    protected Observable createRequest() {
        return JokeSdk.getRetrofit().create(IFewJokes.class).createRequest(count)
                .flatMap(response -> {
                    for (Joke joke : response.getJokes()) {
                        Logger.d(TAG, joke.toString());
                    }
                    return Observable.just(response);
                });
    }
}