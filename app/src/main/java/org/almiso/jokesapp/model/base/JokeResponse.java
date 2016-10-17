package org.almiso.jokesapp.model.base;


import com.google.gson.annotations.SerializedName;

import org.almiso.jokesapp.network.request.BaseRequest;

/**
 * Base response class.
 * Contains common data about request response,
 * such as status_code or ect.
 * In current project response has only "type".
 */
public class JokeResponse extends JokeObject {

    /* Serializable values */

    @SerializedName("type")
    private String type;

    /* Data */

    private BaseRequest request;

    /* Setters */

    public void setRequest(BaseRequest request) {
        this.request = request;
    }

    /* Getters */

    public String getType() {
        return type;
    }

    public BaseRequest getRequest() {
        return request;
    }
}