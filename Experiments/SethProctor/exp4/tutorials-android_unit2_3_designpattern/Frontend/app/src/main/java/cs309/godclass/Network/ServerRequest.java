package cs309.godclass.Network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import cs309.godclass.AppController;
import cs309.godclass.Logic.IVolleyListener;
import cs309.godclass.Logic.RegistrationLogic;

public class ServerRequest implements IServerRequest {

    private String tag_json_obj = "json_obj_req";
    private IVolleyListener l;

    @Override
    public void sendToServer(String url, JSONObject newUserObj, String methodType) {

        int method = Request.Method.GET;

        if (methodType.equals("POST")) {
            method = Request.Method.POST;
        }
        JsonObjectRequest registerUserRequest = new JsonObjectRequest(method, url, newUserObj,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                            if (response != null ) {
                               // l.onSuccess(response.toString());
                                try {
                                    l.onSuccess(response);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println(response.toString());
                            } else {
                                l.onError("Null Response object received");
                            }
                    }},

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        l.onError(error.getMessage());
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(registerUserRequest, tag_json_obj);
    }

    @Override
    public void addVolleyListener(IVolleyListener logic) {
        l = logic;
    }
}
