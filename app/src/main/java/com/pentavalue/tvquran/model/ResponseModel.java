package com.pentavalue.tvquran.model;

import com.pentavalue.tvquran.network.managers.BusinessObject;

/**
 * Created by Passant on 6/7/2017.
 */

public class ResponseModel extends BusinessObject{
    /**
     * The server CRUD methods should be provided by the entity sub-classing {@linkplain BusinessObject}
     *
     * @param saveMethod
     * @param deleteMethod
     * @param getMethod
     */
    public ResponseModel(String saveMethod, String deleteMethod, String getMethod) {
        super(saveMethod, deleteMethod, getMethod);
    }


}
