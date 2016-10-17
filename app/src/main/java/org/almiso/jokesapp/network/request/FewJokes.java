package org.almiso.jokesapp.network.request;


import org.almiso.jokesapp.model.FewJokesResponse;
import org.almiso.jokesapp.network.JokeSdk;

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
        Observable<FewJokesResponse> prepareRequest(@Path("count") int count);
    }

    @Override
    protected Observable prepareRequest() {
        return JokeSdk.getRetrofit().create(IFewJokes.class).prepareRequest(count);
    }
}