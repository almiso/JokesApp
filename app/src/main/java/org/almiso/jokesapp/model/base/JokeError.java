package org.almiso.jokesapp.model.base;

import org.almiso.jokesapp.network.request.BaseRequest;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Base class for all errors.
 */
public class JokeError extends Throwable {

    /**
     * Identifies the event kind which triggered a {@link JokeError}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    /* Data */

    private Kind kind;
    private String errorMessage;
    private String url;
    private BaseRequest request;
    private Response response;
    private Retrofit retrofit;

    /* Constructors */

    /**
     * Base public constructor.
     * Converts {@link Throwable throwable} to custom {@link JokeError error}.
     */
    public static JokeError prepareError(Throwable throwable, Retrofit retrofit) {

        /* We had non-200 http error */
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();
            return JokeError.httpError(response.raw().request().url().toString(), response, retrofit);
        }

        /* A network error happened */
        if (throwable instanceof IOException) {
            return JokeError.networkError((IOException) throwable, retrofit);
        }

        /* We don't know what happened. We need to simply convert to an unknown error */
        return JokeError.unexpectedError(throwable, retrofit);
    }

    private static JokeError httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new JokeError(message, url, response, Kind.HTTP, null, retrofit);
    }

    private static JokeError networkError(IOException exception, Retrofit retrofit) {
        return new JokeError(exception.getMessage(), null, null, Kind.NETWORK, exception, retrofit);
    }

    private static JokeError unexpectedError(Throwable exception, Retrofit retrofit) {
        return new JokeError(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, retrofit);
    }

    private JokeError(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
        this.errorMessage = message;
    }

    /* Public methods */

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setRequest(BaseRequest request) {
        this.request = request;
    }

    /* Override methods */

    @Override
    public String toString() {
        return "JokeError{" +
                "kind=" + kind +
                ", errorMessage='" + errorMessage + '\'' +
                ", url='" + url + '\'' +
                ", request=" + request +
                ", response=" + response +
                ", retrofit=" + retrofit +
                '}';
    }
}