package com.palprotech.ensyfi.serviceinterfaces;

import org.json.JSONObject;

/**
 * Created by Admin on 27-06-2017.
 */

public interface IServiceListener {

    void onResponse(JSONObject response);

    void onError(String error);
}
