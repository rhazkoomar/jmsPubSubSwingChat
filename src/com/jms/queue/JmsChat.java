/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jms.queue;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.JFrame;

/**
 *
 * @author Raj Kumar Rb
 */
public class JmsChat {
    public static void main(String[] args) {
       try{
        String user="Anonymous";
        if(args.length>0){
            user=args[0];
        }
        new JmsChat().init(user);
       }catch(NamingException e){
           e.printStackTrace();
           System.exit(0);
       }catch(JMSException e){
           e.printStackTrace();
           System.exit(0);
       }
    }
    
    private void init(String user) throws NamingException,JMSException{
        JFrame frame= new JFrame("Chat: "+user );
        MessagePanel mp=new MessagePanel(new MessageSenderQueue(), new MessageReceiverQueue(), user);
        frame.getContentPane().add(mp);
        frame.setSize(300,300);
        frame.pack();
        frame.show();
    }
}
