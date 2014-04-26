/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayservlet.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sergio
 */
public class MessageEntity implements Serializable {
    
    private String phone;
    private String validactionCode;
    private String isoLang;
    
    public MessageEntity() {
        
    }
    
    public MessageEntity(String phone, String validationCode, String isoLang) {
        this.phone = phone;
        this.validactionCode = validationCode;
        this.isoLang = isoLang;
    }
    
    public String toString() {
        String _ret = "";
        
        _ret += this.phone + ",";
        _ret += this.validactionCode + ",";
        _ret += this.isoLang;
        
        return _ret;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the validactionCode
     */
    public String getValidactionCode() {
        return validactionCode;
    }

    /**
     * @return the isoLang
     */
    public String getIsoLang() {
        return isoLang;
    }
    
}
