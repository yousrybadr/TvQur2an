package com.pentavalue.tvquran.data.parsing;




import com.pentavalue.tvquran.data.constants.Params;
import com.pentavalue.tvquran.network.response.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mahmoud Turki
 */
public class ParsingManager {

    public static BaseResponse parseServerResponse(String result)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        int status = jsonObject.optInt(Params.STATUS);
        String message = jsonObject.optString(Params.MESSAGE);
        String data = jsonObject.optString(Params.DATA);
        return new BaseResponse(status, data, message);
    }
    public static BaseResponse forTestOnly(String result)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        int status = jsonObject.optInt("Status");
        String message = jsonObject.optString(Params.MESSAGE);
        String data = jsonObject.optString(Params.DATA);
        return new BaseResponse(status, data, message);
    }
    public static BaseResponse parseSpecificServerResponse(String result)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
       /* int status = jsonObject.optInt(Params.Login.EXPIRES_IN);
        String accessToken = jsonObject.optString(Params.Login.ACCESS_TOKEN);
        String tokenType = jsonObject.optString(Params.Login.TOKEN_TYPE);
        String username = jsonObject.optString(Params.Login.USER_NAME);
        String issued = jsonObject.optString(Params.Login.ISSUED);
        String expires = jsonObject.optString(Params.Login.EXPIRES);
        String error = jsonObject.optString(Params.Login.ERROR);
        String errorMsg = jsonObject.optString(Params.Login.ERROR_MSG);*/

        return new BaseResponse(jsonObject.toString());
    }

}
