package com.pentavalue.tvquran.network.managers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pentavalue.tvquran.data.exception.AppException;
import com.pentavalue.tvquran.data.parsing.ParsingManager;
import com.pentavalue.tvquran.datasorage.AppSharedPrefs;
import com.pentavalue.tvquran.datasorage.PrefsConstant;
import com.pentavalue.tvquran.network.response.BaseResponse;
import com.pentavalue.tvquran.network.listeneres.OnContactListener;
import com.pentavalue.tvquran.model.ResponseModel;
import com.pentavalue.tvquran.network.NetworkChecker;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.network.helper.VolleyNetworkHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Passant on 6/7/2017.
 */

public class SettingManager extends BusinessManager<ResponseModel> {
    /**
     * Acts as a default constructor which should be called in all constructors
     * of children managers.
     *
     * @param clazz Class instance of the entity upon which the manager is based
     *              on.
     */

    private static SettingManager sInstance;
    private Context context;
    protected SettingManager(Class<ResponseModel> clazz) {
        super(clazz);
    }

    public SettingManager(Context context) {
        super(null);
        this.context = context;
    }

    public static SettingManager getInstance(Context context) {
        if (sInstance == null)
            sInstance = new SettingManager(context);
        return sInstance;
    }

    public void contactUs(String name,String email,String content){
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestContactUs( name, email, content);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnContactListener.class);
    }

    private void asyncRequestContactUs( String userName, String email,String massage) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("username", userName);
            obj.put("email", email);
            obj.put("message", massage);
            obj.put("locale", AppSharedPrefs.getStringVal(PrefsConstant.LANG));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.GET_CONTACTUS_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        notifyVoidSuccess(OnContactListener.class);
                    }
                    else if(statusResponse==BaseResponse.STATUS_WEBSERVICE_MISSING_PARAMETERS)
                    {
                        notifyRetrievalException(new AppException(AppException.WB_PARAM_EXCEPTION), OnContactListener.class);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    notifyRetrievalException(e, OnContactListener.class);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(error, OnContactListener.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

}
