/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgateway.controllers;

import com.preferya.facadesmsgateway.models.MessageEntity;
import com.preferya.facadesmsgateway.services.EsPhoneValidationService;
import com.preferya.facadesmsgateway.services.IPhoneValidationService;
import com.preferya.facadesmsgateway.services.PhoneValidationFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio
 */
@WebServlet(name = "FacadeSmsGateway", urlPatterns = {"/FacadeSmsGateway"})
public class FacadeSmsGateway extends HttpServlet {
    
    /*private String id_metamodel;
    private Object metamodel_link;*/
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        //Firstly, we get the parameters.
        String _phone = request.getParameter("phone");
        String _validation_code = request.getParameter("validation_code");
        String _iso_lang = request.getParameter("iso_lang");
        String _token = request.getParameter("token");
        
        //Secondly, we check the integrity with token.
        IPhoneValidationService _validationService = PhoneValidationFactory.getServiceByIsoLang(_iso_lang);
        
        MessageEntity _message = new MessageEntity(_phone, _validation_code, _iso_lang);
        if(_validationService.checkIntegrity(_message, _token) && _validationService.dataValidation(_message)){
            _validationService.senQueue(_message);
            out.print("OK");
        }else {
            out.print("FAIL! Token doesn't match or not valid data");
        }
        
        /*try {
            /* TODO output your page here. You may use following sample code. *//*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PhoneValidationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>Servlet PhoneValidationServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>Sergio</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }*/
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FacadeSmsGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FacadeSmsGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
