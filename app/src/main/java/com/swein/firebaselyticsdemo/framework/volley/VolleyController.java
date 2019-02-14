package com.swein.firebaselyticsdemo.framework.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seokho on 01/02/2018.
 */

public class VolleyController {

    private final static String STATE = "state";
    private final static String OBJ = "obj";
    private final static String ACCESS_TOKEN = "accessToken";

    public final static String SUCCESS = "success";
    public final static String OK = "OK";

    public interface SHVolleyDelegate {
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
    }

    private RequestQueue queue;

    public VolleyController(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void requestUrlGet(String url, final SHVolleyDelegate shVolleyDelegate) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shVolleyDelegate.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shVolleyDelegate.onErrorResponse(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Accept","application/json");

                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void requestUrlPost(String url, final SHVolleyDelegate shVolleyDelegate) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shVolleyDelegate.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shVolleyDelegate.onErrorResponse(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Accept","application/json");

                return params;

            }
        };

        queue.add(stringRequest);
    }

    public String parserState(String response) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
            return jsonObject.getString(STATE);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String parserObj(String response) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
            return jsonObject.getString(OBJ);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String parserAccessToken(String response) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
            return jsonObject.getString(ACCESS_TOKEN);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

}
