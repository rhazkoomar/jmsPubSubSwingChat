/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administered.chat.app;

/**
 *
 * @author Raj Kumar Rb
 */
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIService {
    
    private static Properties prop;
    
    public static void init(String contextFactory,String providerURL){
        prop= new Properties();
        if(contextFactory!=null){
            prop.put(Context.INITIAL_CONTEXT_FACTORY,contextFactory);            
        }
        
        if(providerURL!=null){
            prop.put(Context.INITIAL_CONTEXT_FACTORY, providerURL);
        } 
    }
    
    public static Object lookup(String jndiName) throws NamingException{
        Context ctx= new InitialContext(prop);
        Object obj=ctx.lookup(jndiName);
        ctx.close();
        return obj;
    }
    
}
