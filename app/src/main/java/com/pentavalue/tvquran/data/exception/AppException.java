package com.pentavalue.tvquran.data.exception;


import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Mahmoud Turki
 */
public class AppException extends Exception {

    private static final long serialVersionUID = 6605821659085061135L;

    /* Error Codes */
    public static final int UNKNOWN_EXCEPTION = 0x00;
    public static final int NETWORK_EXCEPTION = 0x01;
    public static final int JSON_PARSING_EXCEPTION = 0x02;
    public static final int WB_PARAM_EXCEPTION = 0x03;
    public static final int WB_SERVER_EXCEPTION = 0x04;
    public static final int WB_VALIDATION_RULE_EXCEPTION = 0x05;
    public static final int OBJECT_INSTANTIATION_EXCEPTION = 0x06;
    public static final int DATE_PARSING_EXCEPTION = 0x07;
    public static final int NO_DATA_EXCEPTION = 0x08;
    public static final int UPDATE_STATUS_FORCE_EXCEPTION = 0x09;
    public static final int SENDING_SUCCESSFUL = 0x10;
    public static final int WB_REGISTRATION_VALIDATION_EXCEPTION = 0x11;
    public static final int WB_LOGIN_INVALID_GRANT_EXCEPTION = 0x12;
    public static final int WB_CHANGE_PASSWORD_VALIDATION_EXCEPTION = 0x13;
    public static final int WB_PASSWORD_VALIDATION_EXCEPTION = 0x14;
    public static final int WB_TOKEN_EXPIRED = 0x15;
    public static final int WB_OLD_PASSWORD_ERROR = 0x16;
    private int errorCode = -1;

    public AppException(String errorMsg) {
        super(errorMsg);
    }

    public AppException(int errorCode) {
        super(getErrorMsg(errorCode));
        this.errorCode = errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    private static String getErrorMsg(int errorCode) {
        String msg;
        switch (errorCode) {
            case UNKNOWN_EXCEPTION:
                msg = "Unknown exception has occurred";
                //	msg = "An error has occurred, Send error to TawasolIT company support";
                break;
            case NETWORK_EXCEPTION:
                msg = "No internet connection right now, try it later";
                break;
            case JSON_PARSING_EXCEPTION:
                //msg = "An exception has occurred while parsing data from server";
                msg = "An error has occurred, Send error to PentaValue company support";
                break;
            case WB_PARAM_EXCEPTION:
                msg = "A WS parameter is missing or incorrect";
                break;
            case WB_SERVER_EXCEPTION:
                msg = "An exception has occurred at the server side";
                break;
            case OBJECT_INSTANTIATION_EXCEPTION:
                msg = "An exception has occurred when trying to instantiate object of generic type [BusniessManager]";
                break;
            case DATE_PARSING_EXCEPTION:
                msg = "An exception has occurred while parsing date";
                break;
            case NO_DATA_EXCEPTION:
                msg = "No data found on server";
                break;
            case UPDATE_STATUS_FORCE_EXCEPTION:
                msg = "Your version is outdated please download the latest version";
                break;
            case SENDING_SUCCESSFUL:
                msg = "Your request send successfully";
                break;
            case WB_VALIDATION_RULE_EXCEPTION:
                msg = "You have broke the validation rule";
                break;
            case WB_REGISTRATION_VALIDATION_EXCEPTION:
                msg = "This email is already taken";
                break;
            case WB_LOGIN_INVALID_GRANT_EXCEPTION:
                msg = "The user name or password is incorrect.";
                break;
            case WB_CHANGE_PASSWORD_VALIDATION_EXCEPTION:
                msg = "Password is wrong";
                break;
            case WB_TOKEN_EXPIRED:
                msg = "You session is expired and you have to sign in one more time";
                break;
            case WB_PASSWORD_VALIDATION_EXCEPTION:
                msg = "Passwords must have at least one non letter or digit character. Passwords must have at least one lowercase ('a'-'z'). Passwords must have at least one uppercase ('A'-'Z').\\n";
                break;
            case WB_OLD_PASSWORD_ERROR:
                msg = "Check your old password";
                break;
            default:
                msg = "Unkwown error";
                break;
        }
        return msg;
    }

    public int getType() {
        return errorCode;
    }

    public static AppException getAppException(Exception e) {
        if (e instanceof AppException) {
            return (AppException) e;
        } else if (e instanceof InstantiationException || e instanceof IllegalAccessException) {
            return new AppException(AppException.OBJECT_INSTANTIATION_EXCEPTION);
        } else if (e instanceof IOException) {
            return new AppException(AppException.NETWORK_EXCEPTION);
        } else if (e instanceof JSONException || e instanceof JsonSyntaxException) {
            return new AppException(AppException.JSON_PARSING_EXCEPTION);
        } else if (e instanceof ParseException) {
            return new AppException(AppException.DATE_PARSING_EXCEPTION);
        } else {
            return new AppException(AppException.UNKNOWN_EXCEPTION);
        }
    }
}
