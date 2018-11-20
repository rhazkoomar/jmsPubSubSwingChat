/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jms.chat.privpub;

import com.administered.chat.app.*;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 *
 * @author Raj Kumar Rb
 */
public class MessageReceiver implements MessageListener{
    private TopicConnectionFactory topicConnectionFactory;
    private TopicSession topicSession;
    private String topicName = "spring_jms_topic_destination";
    private String topicConnectionFactoryName = "topic_connection_factory";
    private Topic topic;
    private TopicConnection topicConnection;
    private TopicSubscriber topicSubscriber;
    private MessageHandler messageHandler;
    
    public MessageReceiver(String user)  throws NamingException,JMSException{
        InitialContext ctx = new InitialContext();
        // Step1: Lookup the Connection Factory and the Topic
        topicConnectionFactory = (TopicConnectionFactory)
                         ctx.lookup(topicConnectionFactoryName);
        topic = (Topic)ctx.lookup(topicName);
        // Step2: Create a connection using the Factory
        topicConnection = topicConnectionFactory.createTopicConnection();
        // Step3: Create Topic Sessions using the connection
        topicSession = topicConnection.createTopicSession
                          (false,Session.AUTO_ACKNOWLEDGE);
        String selector="addressedTo in ('all','"+user+"')";
        System.out.println("selector==>"+selector);
        // Step4: Create TopicPublisher
        topicSubscriber = topicSession.createSubscriber(topic,selector,false);
        topicSubscriber.setMessageListener(this);
        topicConnection.start();
    }
    public void close() throws JMSException{
	topicConnection.stop();
    }
    
    @Override
    public synchronized void onMessage(Message message) {
//        System.out.println("message==>"+message);
        try{
            TextMessage msg=(TextMessage)message;
            if(messageHandler!=null){
                messageHandler.handleMessage(msg.getText());
            }
        }catch(JMSException ex){
            ex.printStackTrace();
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
    
}
