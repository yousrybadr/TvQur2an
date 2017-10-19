package com.pentavalue.tvquran.network.helper;

import android.net.ParseException;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = -111949272938833145L;

	public static final int UNKNOWN_EXCEPTION = 0x00;
	public static final int NETWORK_EXCEPTION = 0x01;
	public static final int JSON_PARSING_EXCEPTION = 0x02;
	public static final int WB_PARAM_EXCEPTION = 0x03;
	public static final int WB_SERVER_EXCEPTION = 0x04;
	public static final int LOGIN_PARAM_REQUIRED = 0x05;
	public static final int OBJECT_INSTANTIATION_EXCEPTION = 0x06;
	public static final int DATE_PARSING_EXCEPTION = 0x07;
	public static final int NO_DATA_EXCEPTION = 0x08;
	public static final int CANCEL_REASON_ERROR= 0x09;
	public static final int SENDING_SUCCESSFUL = 0x10;
	public static final int WB_REGISTRATION_VALIDATION_EXCEPTION = 0x11;
	public static final int WB_LOGIN_INVALID_GRANT_EXCEPTION = 0x12;
	public static final int WB_CHANGE_PASSWORD_VALIDATION_EXCEPTION = 0x13;
	public static final int WB_PASSWORD_VALIDATION_EXCEPTION = 0x14;
	public static final int WB_TOKEN_EXPIRED = 0x15;
	
	private int type;
	
	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(int type) {
		super(getExceptionMsg(type));
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	private static String getExceptionMsg(int type) {
		String msg;
		switch (type) {
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
			case CANCEL_REASON_ERROR:
				msg = "canceling reason not correct!";
				break;
			case SENDING_SUCCESSFUL:
				msg = "Your request send successfully";
				break;
			case LOGIN_PARAM_REQUIRED:
				msg = "missing required login parameters";
				break;
			case WB_REGISTRATION_VALIDATION_EXCEPTION:
				msg = "This email is already taken";
				break;
			case WB_LOGIN_INVALID_GRANT_EXCEPTION:
				msg = "The user name or password is incorrect.";
				break;
			case WB_CHANGE_PASSWORD_VALIDATION_EXCEPTION:
				msg = "Incorrect password.";
				break;
			case WB_TOKEN_EXPIRED:
				msg = "You session is expired and you have to sign in one more time";
				break;
			case WB_PASSWORD_VALIDATION_EXCEPTION:
				msg = "Passwords must have at least one non letter or digit character. Passwords must have at least one lowercase ('a'-'z'). Passwords must have at least one uppercase ('A'-'Z').\\n";
				break;
			default:
				msg = "Unkwown error";
				break;
		}
		return msg;
	}
	
	public static ApplicationException getAppException(Exception e) {
		if (e instanceof ApplicationException) {
			return (ApplicationException) e;
		} else if (e instanceof InstantiationException || e instanceof IllegalAccessException) {
			return new ApplicationException(ApplicationException.OBJECT_INSTANTIATION_EXCEPTION);
		} else if (e instanceof IOException) {
			return new ApplicationException(ApplicationException.NETWORK_EXCEPTION);
		} else if (e instanceof JSONException || e instanceof JsonSyntaxException) {
			return new ApplicationException(ApplicationException.JSON_PARSING_EXCEPTION);
		} else if (e instanceof ParseException) {
			return new ApplicationException(ApplicationException.DATE_PARSING_EXCEPTION);
		} else {
			return new ApplicationException(ApplicationException.UNKNOWN_EXCEPTION);
		}
	}
	
}
