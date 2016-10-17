package org.almiso.jokesapp.network.request;

import android.os.Handler;

import org.almiso.jokesapp.model.base.JokeError;
import org.almiso.jokesapp.model.base.JokeResponse;
import org.almiso.jokesapp.network.calback.JokeRequestListener;
import org.almiso.jokesapp.network.calback.ProgressInterface;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.os.Looper.getMainLooper;

/**
 * Base request class.
 */
public abstract class BaseRequest<T extends JokeResponse> {

    /* Constants */

    String TAG;

    /* Data */

    private ProgressInterface progressInterface;

    private JokeRequestListener requestListener;

    private int retryCount;

    /* Protected methods */

    protected abstract Observable<T> createRequest();

    /* Constructors */

    BaseRequest() {
        this.TAG = this.getClass().getSimpleName();
        this.retryCount = 1;
    }

    /* Public methods */

    public void setProgressInterface(ProgressInterface progressInterface) {
        this.progressInterface = progressInterface;
    }

    public void setRequestListener(JokeRequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void execute() {
        createRequest()
                .retry(retryCount)
                .doOnSubscribe(this::showProgress)
                .doAfterTerminate(this::hideProgress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onComplete, this::onError);
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

    private void onComplete(JokeResponse response) {
        if (requestListener != null) {
            requestListener.onComplete(response);
        }
    }

    private void onError(Throwable throwable) {
        JokeError error = (JokeError) throwable;
        error.setRequest(this);
        if (requestListener != null) {
            requestListener.onError(error);
        }
    }

    /* Override methods */

    @Override
    public String toString() {
        return "Request{" + TAG + "}";
    }
}