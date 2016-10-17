package org.almiso.jokesapp.network;

import org.almiso.jokesapp.network.api.JokeCommon;

/**
 * Provides access for API parts.
 */
public class JokesApi {

    /**
     * Returns object for preparing requests to common part of API
     */
    public static JokeCommon common() {
        return new JokeCommon();
    }
}