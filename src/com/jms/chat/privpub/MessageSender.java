/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jms.chat.privpub;
import com.administered.chat.app.*;
import javax.jms.*;
import javax.naming.*;
/**
 *
 * @author Raj Kumar Rb
 */
public class MessageSender  {
    private TopicConnectionFactory topicConnectionFactory;
    private TopicSession topicSession;
    private final String topicName = "spring_jms_topic_destination";
    private final String topicConnectionFactoryName = "topic_connection_factory";
    private Topic topic;
    private TopicConnection topicConnection;
    private TopicPublisher topicPublisher;
    
    public MessageSender() throws NamingException,JMSException{
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

		       // Step4: Create TopicPublisher
		       topicPublisher = topicSession.createPublisher(topic);

		       topicConnection.start();
		}
	 
	 public void close() throws JMSException{
		 topicConnection.stop();
	 }
         
     public void sendMessage(String message,String recpAddr) throws JMSException{        
          TextMessage txtMsg = topicSession.createTextMessage(message);
	       //  Publishing the message object to the Topic
               txtMsg.setStringProperty("addressedTo", recpAddr);
               txtMsg.setText(message);
              System.out.println("sendMessage==>"+txtMsg);
	       topicPublisher.publish(txtMsg);
                 
     }
}
