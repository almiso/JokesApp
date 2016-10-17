package org.almiso.jokesapp.network.calback;

import org.almiso.jokesapp.model.base.JokeError;
import org.almiso.jokesapp.model.base.JokeObject;

/**
 * Extend listeners for requests from that class.
 */
public abstract class JokeRequestListener {
    /**
     * Called if there were no HTTP or API errors, returns execution result.
     *
     * @param response response from BaseRequest.
     */
    public void onComplete(JokeObject response) {
    }

    /**
     * Called immediately if there was API error, or after <b>attempts</b> tries if there was an HTTP error
     *
     * @param error error for VKRequest
     */
    public void onError(JokeError error) {
    }
}