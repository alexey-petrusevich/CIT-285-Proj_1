/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Aliaksei
 */
public class CustomException extends Exception{
    
    protected String message; // holds the text of the message passed as an error (invalid length, for instance)
    
    public CustomException(String message){
        this.message = message;
    }
    public String getErrorMessage(){
        return message;
    }
}
