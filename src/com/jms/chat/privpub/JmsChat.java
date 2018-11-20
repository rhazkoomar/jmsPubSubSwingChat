/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jms.chat.privpub;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.JFrame;
import java.lang.Math;

/**
 *
 * @author Raj Kumar Rb
 */
public class JmsChat {
//    public static final String CTX_FACT="com.sun.jndi.fscontext.RefFSContextFactory";
//    public static final String CTX_FACT="org.jboss.naming.remote.client.InitialContextFactory";
//    public static final String PROV_URL="http-remoting://127.0.0.1:8080";
//    public static final String TOPIC_NAME="myTopic";
//    public static final String TCF_NAME="TCFactory";
//    private TopicConnection conn;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] name=new String[]{"RajKumar","Ramesh","Shiva","Sita","Rubina","Parvati","Dharmesh","Nara","Jackie","Barak"};
        int no=(int)Math.floor(Math.random()*10);
       try{
        String user=name[no];
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
        MessagePanel mp=new MessagePanel(new MessageSender(), new MessageReceiver(user), user);
        frame.getContentPane().add(mp);
        frame.setSize(300,300);
        frame.pack();
        frame.show();
    }
}
