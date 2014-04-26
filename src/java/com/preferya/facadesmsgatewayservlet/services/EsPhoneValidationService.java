/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayservlet.services;

import com.preferya.facadesmsgatewayservlet.models.MessageEntity;
import com.preferya.facadesmsgatewayservlet.utils.RabbitMQUtils;
import com.preferya.facadesmsgatewayservlet.utils.TokenUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sergio
 */
public class EsPhoneValidationService implements IPhoneValidationService {
    
    private RabbitMQUtils internalQueue;
    
    public boolean checkIntegrity(MessageEntity message, String token) {
        //TODO: this method checks the integrity of message recived. Complete and comment.
        
        boolean _ret = false;
        
        //First, we go to check token with pattern.
        Pattern _patToken = Pattern.compile("[a-f0-9]{40}");
        Matcher _matToken = _patToken.matcher(token);
        
        if(_matToken.matches()){
            String _sha1 = TokenUtil.sha1(message);
            if(token.equals(_sha1))
                _ret = true;
        }
        
        return _ret;
    }
    
    //This method validates phone number according to pattern.
    public boolean dataValidation(MessageEntity message) {
        
        boolean _resPatt1 = false;
        boolean _resPatt2 = false;
        boolean _resPatt3 = false;
        
        //First, we go to check phone from message according to pattern.
        Pattern _patPhone = Pattern.compile("34[6|7][0-9]{8}");
        Matcher _matPhone = _patPhone.matcher(message.getPhone());
        
        if(_matPhone.matches())
            _resPatt1 = true;
        
        //Second, we go to check validationCode from message according to pattern.
        //Pattern _patValidationCode = Pattern.compile("/\\d{5}/");
        Pattern _patValidationCode = Pattern.compile("\\d{5}");
        Matcher _matValidationCode = _patValidationCode.matcher(message.getValidactionCode());
        
        if(_matValidationCode.matches())
            _resPatt2 = true;
        
        //Finally, we go to check isoLang from message according to pattern.
        Pattern _patIsoLang = Pattern.compile("[a-z]{2}(_[a-z]{2})");
        Matcher _matIsoLang = _patIsoLang.matcher(message.getIsoLang());
        
        if(_matIsoLang.matches())
            _resPatt3 = true;
        
        return _resPatt1 && _resPatt2 && _resPatt3;
    }
    
    //This method call to auxiliary class to send message with RabbitMQ.
    public void senQueue(MessageEntity message) {
        try {
            this.internalQueue = new RabbitMQUtils();
            this.internalQueue.sendMessage(message);
            this.internalQueue.closeConnection();
        } catch (IOException ex) {
            Logger.getLogger(EsPhoneValidationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
