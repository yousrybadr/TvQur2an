package com.pentavalue.tvquran.network.managers;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pentavalue.tvquran.network.helper.DataHelper;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by passant on 28/12/2015.
 */
public class GsonRequestWithParam<T> extends Request {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
 private  final Map<String,String> mParam;
    String string;
    /**
     * Make a POST request and return a parsed object from JSON.
     *  @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     * @param mParam
     */
    public GsonRequestWithParam(String url, Class<T> clazz, Map<String, String> headers,
                                Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> mParam) {
        super(Method.POST, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.mParam = mParam;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParam;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

             Log.i("JSN", json);
            if(json.contains("message")) {


                return  Response.success(DataHelper.deserialize(json, clazz),
                        //  gson.fromJson(json, clazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
                else
            return Response.success(DataHelper.deserialize(json, clazz),
                    //  gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        listener.onResponse((T)response);

    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }


}

