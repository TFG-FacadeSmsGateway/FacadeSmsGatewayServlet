/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayservlet.services;

import com.preferya.facadesmsgatewayservlet.models.MessageEntity;

/**
 *
 * @author Sergio
 */
public interface IPhoneValidationService {
    
    public boolean checkIntegrity(MessageEntity message, String token);
    
    public boolean dataValidation(MessageEntity message);
    
    public void senQueue(MessageEntity message);
    
}
