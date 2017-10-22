package com.pentavalue.tvquran.network.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.data.exception.AppException;
import com.pentavalue.tvquran.data.parsing.ParsingManager;
import com.pentavalue.tvquran.datasorage.AppSharedPrefs;
import com.pentavalue.tvquran.datasorage.PrefsConstant;
import com.pentavalue.tvquran.model.AboutUsModel;
import com.pentavalue.tvquran.model.AlphabeticalHolyQuran;
import com.pentavalue.tvquran.model.CollectionWrapper;
import com.pentavalue.tvquran.model.Home;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.model.RectrictsWrapperModel;
import com.pentavalue.tvquran.model.SearchName;
import com.pentavalue.tvquran.model.SearchResultModel;
import com.pentavalue.tvquran.model.VidioAndLiveBroadcast;
import com.pentavalue.tvquran.network.NetworkChecker;
import com.pentavalue.tvquran.network.helper.DataHelper;
import com.pentavalue.tvquran.network.helper.VolleyNetworkHelper;
import com.pentavalue.tvquran.network.listeneres.OnAboutUsRecitersListener;
import com.pentavalue.tvquran.network.listeneres.OnBrawseCatogry;
import com.pentavalue.tvquran.network.listeneres.OnBrowseHome;
import com.pentavalue.tvquran.network.listeneres.OnBrowseProfile;
import com.pentavalue.tvquran.network.listeneres.OnHolyQuranListener;
import com.pentavalue.tvquran.network.listeneres.OnLiveBroadcastListener;
import com.pentavalue.tvquran.network.listeneres.OnSearchRecitersListener;
import com.pentavalue.tvquran.network.listeneres.OnSearchResultListener;
import com.pentavalue.tvquran.network.response.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by OmniaGamil on 5/16/2017.
 */

public class RecitersMangers extends BusinessManager<Reciters> {
    private static RecitersMangers sInstance;
    CollectionWrapper collectionWrapper;
    AlphabeticalHolyQuran alphabeticalHolyQuran;
    RectrictsWrapperModel reciterses;
    private Context context;

    private RecitersMangers(Context context) {
        super(Reciters.class);
        this.context = context;
    }

    public static RecitersMangers getInstance(Context context) {
        if (sInstance == null)
            sInstance = new RecitersMangers(context);
        return sInstance;
    }

    /**
     * @URL: ?supermarketId=1002&pageIndex=0&pageSize=10&lang=en
     */
    public void performBrowseHome(int id) {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseHome(id);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnBrowseHome.class);

    }

    public void performBrowseProfile(int id) {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseProfile(id);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnBrowseProfile.class);

    }

    public void performBrowseCategoryDetails(int id, int sort) {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseCategoryDetails(id, sort);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnBrawseCatogry.class);

    }

    public void performBrowseAlphabeticalHolyQuran() {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseAlphabeticalHolyQuran();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnHolyQuranListener.class);

    }

    public void performBrowseMostListenedHolyQuran() {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseMostListened();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnHolyQuranListener.class);

    }

    public void performBrowseRewayatOrder() {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseRewayatOrder();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnHolyQuranListener.class);

    }

    public void performBrowseRecentlyModifiedHolyQuran() {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseRecentlyModifiedHolyQuran();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnHolyQuranListener.class);

    }

    public void performBrowseLiveBroadcast() {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseLiveBroadcast();
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnLiveBroadcastListener.class);

    }

    public void performBrowseSearchResult(String recitersName, String language) {
        if (NetworkChecker.isNetworkAvailable(context))
            asyncRequestBrowseSearchResult(recitersName, language);
        else
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnSearchRecitersListener.class);
    }

    public void performAboutUsResult(String lang, String state) {
        if (NetworkChecker.isNetworkAvailable(context)) {
            asyncRequestAboutUsResult(lang, state);
        } else {
            notifyRetrievalException(new AppException(AppException.NETWORK_EXCEPTION), OnAboutUsRecitersListener.class);
        }
    }

    private void asyncRequestBrowseHome(int id) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("sort", id);
            obj.put("locale", AppSharedPrefs.getStringVal(PrefsConstant.LANG));
            Log.i("LOLO", AppSharedPrefs.getStringVal(PrefsConstant.LANG));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.HOME_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Home reciters = DataHelper.deserialize(baseResponse.getData(), Home.class);
                        //List<Reciters> list = handleObjectListServerResponseReciters(baseResponse, Reciters.class);
                        // List<Entries> list2 = handleObjectListServerResponseEntries(baseResponse, Entries.class);

                        notifyEntityReceviedSuccess(reciters, OnBrowseHome.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(error, OnBrowseHome.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseProfile(int id) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("reciter_id", id);
            obj.put("locale", AppSharedPrefs.getStringVal(PrefsConstant.LANG));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.PROFILE_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Reciters reciters = DataHelper.deserialize(baseResponse.getData(), Reciters.class);
                        //List<Reciters> list = handleObjectListServerResponseReciters(baseResponse, Reciters.class);
                        // List<Entries> list2 = handleObjectListServerResponseEntries(baseResponse, Entries.class);

                        notifyEntityReceviedSuccess(reciters, OnBrowseProfile.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnBrowseProfile.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseCategoryDetails(int id, int sort) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("category_id", id);
            obj.put("sort", sort);
            obj.put("locale", AppSharedPrefs.getStringVal(PrefsConstant.LANG));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // pass third argument as "null" for GET requests

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.GET_CATOGRY_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Home home = DataHelper.deserialize(baseResponse.getData(), Home.class);

                        notifyEntityReceviedSuccess(home, OnBrawseCatogry.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnBrawseCatogry.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseAlphabeticalHolyQuran() {

        // pass third argument as "null" for GET requests
        String url = NetworkConstants.GET_AlphabeticalHolyQuran_URL + "?locale=" + AppSharedPrefs.getStringVal(PrefsConstant.LANG);
        Log.i("SERV", url);
        /*GsonRequest<AlphapiticQuranWrapperModel> request=new GsonRequest<>(url, AlphapiticQuranWrapperModel.class, null, new Response.Listener<AlphapiticQuranWrapperModel>() {
            @Override
            public void onResponse(AlphapiticQuranWrapperModel response) {
                Log.i("LOLO",response.toString());
                if (response.getCode()==BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA){


                    notifyVoidSuccess(OnHolyQuranListener.class);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Gson gson = new Gson();
                        AlphabeticalHolyQuran home = gson.fromJson(response.toString(), AlphabeticalHolyQuran.class);
                        setAlphabeticalHolyQuran(home);

                        notifyVoidSuccess(OnHolyQuranListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnHolyQuranListener.class);
            }
        });
        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseRewayatOrder() {

        // pass third argument as "null" for GET requests
        String url = NetworkConstants.GET_CollectionHolyQuran_URL + "?locale=" + AppSharedPrefs.getStringVal(PrefsConstant.LANG);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        CollectionWrapper home = DataHelper.deserialize(baseResponse.getData(), CollectionWrapper.class);
                        setCollectionWrapper(home);
                        notifyVoidSuccess(OnHolyQuranListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnHolyQuranListener.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseMostListened() {

        // pass third argument as "null" for GET requests
        String url = NetworkConstants.GET_MostListenedReciters_URL + "?locale=" + AppSharedPrefs.getStringVal(PrefsConstant.LANG);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, NetworkConstants.GET_MostListenedReciters_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Gson gson = new Gson();
                        Type rectrictModel = new TypeToken<List<Reciters>>() {
                        }.getType();
                        reciterses = gson.fromJson(baseResponse.getData(), RectrictsWrapperModel.class); /*(List<Reciters>) DataHelper.deserialize(baseResponse.getData(), Reciters.class);*/
                        setReciterses(reciterses);
                        notifyVoidSuccess(OnHolyQuranListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnHolyQuranListener.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseRecentlyModifiedHolyQuran() {

        // pass third argument as "null" for GET requests
        String url = NetworkConstants.GET_RecentlyModifiedHolyQuran_URL + "?locale=" + AppSharedPrefs.getStringVal(PrefsConstant.LANG);
        Log.i("ZOZO", url);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Gson gson = new Gson();
                        reciterses = gson.fromJson(baseResponse.getData(), RectrictsWrapperModel.class);
                        //setCollectionWrapper(home);
                        setReciterses(reciterses);
                        notifyVoidSuccess(OnHolyQuranListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnHolyQuranListener.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseLiveBroadcast() {

        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, NetworkConstants.GET_VideosAndLiveBroadcast_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        VidioAndLiveBroadcast vidioAndLiveBroadcast = DataHelper.deserialize(baseResponse.getData(), VidioAndLiveBroadcast.class);
                        notifyEntityReceviedSuccess(vidioAndLiveBroadcast, OnLiveBroadcastListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnLiveBroadcastListener.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestBrowseSearchResult(String recitersName, String language) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("name", recitersName);
            obj.put("locale", language);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.GET_AutoCompleteReciterName_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        SearchName searchName = DataHelper.deserialize(baseResponse.getData(), SearchName.class);
                        notifyEntityReceviedSuccess(searchName, OnSearchRecitersListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnSearchRecitersListener.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }

    private void asyncRequestAboutUsResult(String language, String state) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("lang", "ar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, state, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                Log.v("yoyoAbout", response.toString());

                System.out.println("Response: " + response.toString());
                try {
                    //BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    // int statusResponse = baseResponse.getStatus();
                    AboutUsModel aboutUsModel = DataHelper.deserialize(response.toString(), AboutUsModel.class);
                    Log.v("yoyoAbout", aboutUsModel.getEDesc());

                    notifyEntityReceviedSuccess(aboutUsModel, OnAboutUsRecitersListener.class);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(new AppException(AppException.JSON_PARSING_EXCEPTION), OnSearchRecitersListener.class);
            }
        });

        // add the request object to the queue to be executed
        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }



    public void getSearchResult(String text, String local) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("text", text);
            obj.put("locale", local);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // pass third argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.GET_SEARCH_RESULT, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                System.out.println("Response: " + response.toString());
                try {
                    BaseResponse baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        SearchResultModel searchName = DataHelper.deserialize(baseResponse.getData(), SearchResultModel.class);
                        notifyEntityReceviedSuccess(searchName, OnSearchResultListener.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: " + e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error);
                System.out.println("Error: " + error.getMessage());
                notifyRetrievalException(error, OnSearchResultListener.class);
            }
        });

        // add the request object to the queue to be executed
        if (NetworkChecker.isNetworkAvailable(context)) {
            req.setRetryPolicy(new DefaultRetryPolicy(
                    7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public CollectionWrapper getCollectionWrapper() {
        return collectionWrapper;
    }

    public void setCollectionWrapper(CollectionWrapper collectionWrapper) {
        this.collectionWrapper = collectionWrapper;
    }

    public AlphabeticalHolyQuran getAlphabeticalHolyQuran() {
        return alphabeticalHolyQuran;
    }

    public void setAlphabeticalHolyQuran(AlphabeticalHolyQuran alphabeticalHolyQuran) {
        this.alphabeticalHolyQuran = alphabeticalHolyQuran;
    }

    public RectrictsWrapperModel getReciterses() {
        return reciterses;
    }

    public void setReciterses(RectrictsWrapperModel reciterses) {
        this.reciterses = reciterses;
    }
}
