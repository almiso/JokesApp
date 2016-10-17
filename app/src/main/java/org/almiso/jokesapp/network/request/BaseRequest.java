package org.almiso.jokesapp.network.request;

import android.os.Handler;

import org.almiso.jokesapp.model.base.JokeObject;
import org.almiso.jokesapp.network.calback.JokeRequestListener;
import org.almiso.jokesapp.network.calback.ProgressInterface;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.os.Looper.getMainLooper;

/**
 * Base request class.
 */
public abstract class BaseRequest<T extends JokeObject> {

    /* Data */

    private ProgressInterface progressInterface;

    private JokeRequestListener requestListener;

    protected abstract Observable<T> prepareRequest();

    /* Public methods */

    public void setProgressInterface(ProgressInterface progressInterface) {
        this.progressInterface = progressInterface;
    }

    public void setRequestListener(JokeRequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public void execute() {
        prepareRequest()
                .doOnSubscribe(this::showProgress)
                .doAfterTerminate(this::hideProgress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onComplete, throwable -> onError());

        // TODO: 17.10.16 other params can configure on concrete request
//              .map(response::getMovies)
//                .flatMap(movies -> {
//                    Realm.getDefaultInstance().executeTransaction(realm -> {
//                        realm.delete(Movie.class);
//                        realm.insert(movies);
//                    });
//                    return Observable.just(movies);
//                })
//                .onErrorResumeNext(throwable -> {
//                    Realm realm = Realm.getDefaultInstance();
//                    RealmResults<Movie> results = realm.where(Movie.class).findAll();
//                    return Observable.just(realm.copyFromRealm(results));
//                })
    }

    /* private methods */

    private void showProgress() {
        if (progressInterface != null) {
            new Handler(getMainLooper()).post(() -> progressInterface.showProgress());
        }
    }

    private void hideProgress() {
        if (progressInterface != null) {
            new Handler(getMainLooper()).post(() -> progressInterface.hideProgress());
        }
    }

    private void onComplete(JokeObject response) {
        if (requestListener != null) {
            requestListener.onComplete(response);
        }
    }

    private void onError() {
        if (requestListener != null) {
            requestListener.onError(null);
        }
    }
}