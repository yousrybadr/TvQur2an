package com.pentavalue.tvquran.network.response;


import com.pentavalue.tvquran.data.exception.AppException;

/**
 * @author Mahmoud Turki
 */
public class BaseResponse {
    /* Response Status Reference */
    /**
     * Parameter is missing or not entered correctly
     */
    public static final int STATUS_WEBSERVICE_PARAM_ERROR = 2;
    /**
     * An exception occurred on the server side
     */
    public static final int STATUS_WEBSERVICE_SERVER_EXCEPTION = 4;
    /**
     * A validation rule has been violated, server should return a list_item_shopping_list of those rules
     */
    public static final int STATUS_WEBSERVICE_VALIDATION_RULE_ERROR = 3;
    /**
     * The request is a success with data is available at server returned
     */
    public static final int STATUS_WEBSERVICE_SUCCES_WITH_DATA = 200;
    /**
     * The request is a success with data is available at server returned
     */
    public static final int STATUS_WEBSERVICE_SUCCES_WITH_DATA_EMPTY = 204;
    /**
     * The request is a success with data is available at server returned
     */
    public static final int STATUS_WEBSERVICE_NO_AUTH = 401;
    /**
     * The request is a success with data is available at server returned
     */
    public static final int STATUS_WEBSERVICE_MISSING_PARAMETERS = 422;
    public static final int STATUS_WEBSERVICE_BAD_REQUEST= 400;
    public static final int STATUS_WEBSERVICE_CONFLICT= 409;
    public static final int CHECK_OLD_PASSWORD= 4011;
    public static final int PASSWORD_MUST_BE_AT_LEAST= 4012;
    public static final int CHECK_THE_NEW_PASSWORD_CONFORMITION= 4013;
    public static final int EMAIL_IS_NOT_REGISTERED= 4014;
    public static final int RESET_PASSWORD_CODE_IS_WRONG= 4015;
    public static final int SAVE_ERROR_MASSAGE= 1;
    /**
     * The request is a not succeeded beacuse invalid access token
     */
    public static final int STATUS_WEBSERVICE_INVALID_TOKEN = 0;

    private int status;
    private String data;
    private String message;

    public BaseResponse(int status, String data, String message) {
        this(status);
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int status) {
        this.status = status;
    }


   public BaseResponse(String data) {
        this.data = data;
    }

    /**
     * @return One of the following values, {@linkplain BaseResponse#STATUS_WEBSERVICE_PARAM_ERROR}, {@linkplain BaseResponse#STATUS_WEBSERVICE_SERVER_EXCEPTION}
     * , {@linkplain BaseResponse#STATUS_WEBSERVICE_SUCCES_VOID}, {@linkplain BaseResponse#STATUS_WEBSERVICE_VALIDATION_RULE_ERROR},
     * {@linkplain BaseResponse#STATUS_SUCCESS}
     */
    public int getStatus() {
        return status;
    }

    /**
     * Message is returned only in case of {@linkplain BaseResponse#STATUS_SUCCESS or BasResponse#STATUS_ERROR}
     *
     * @return A string message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Data is returned only in case of {@linkplain BaseResponse#STATUS_SUCCESS}
     *
     * @return A JSON formatted string.
     */
    public String getData() {
        return data;
    }

    public static int getExceptionType(int status) {
        int type = AppException.UNKNOWN_EXCEPTION;
        switch (status) {
            case BaseResponse.STATUS_WEBSERVICE_PARAM_ERROR:
                type = AppException.WB_PARAM_EXCEPTION;
                break;
            case BaseResponse.STATUS_WEBSERVICE_SERVER_EXCEPTION:
                type = AppException.WB_SERVER_EXCEPTION;
                break;
            case BaseResponse.STATUS_WEBSERVICE_VALIDATION_RULE_ERROR:
                type = AppException.WB_VALIDATION_RULE_EXCEPTION;
                break;
        }
        return type;
    }
}
