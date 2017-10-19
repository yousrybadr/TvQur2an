package com.pentavalue.tvquran.network.managers;

import java.io.Serializable;

/**
 * @author Mahmoud Turki
 *
 */
public abstract class BusinessObject implements Serializable{

	private static final long serialVersionUID = 7241428545629872341L;

	public static final class State {
		public static final int NEW = 0x00;
		public static final int LOADED = 0x01;
		public static final int CACHED = 0x02;
	}
	
	public final String SAVE_METHOD;
	public final String DELETE_METHOD;
	public final String GET_METHOD;

	private int state;
	private boolean valid;

	
	/**
	 * The server CRUD methods should be provided by the entity sub-classing {@linkplain BusinessObject} 
	 * @param saveMethod
	 * @param deleteMethod
	 * @param getMethod
	 */
	public BusinessObject(String saveMethod, String deleteMethod, String getMethod) {
		SAVE_METHOD = saveMethod;
		DELETE_METHOD = deleteMethod;
		GET_METHOD = getMethod;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@SuppressWarnings("unused")
	private String getValidationErrors() {
		// TODO: To be implemented ...
		return null;
	}
	
}




