/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jms.queue;
import com.administered.chat.app.MessageHandler;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author Raj Kumar Rb
 */
public class MessageSenderQueue {
    private QueueConnectionFactory queueConnectionFactory;
    private QueueSession queueSession;
    private String queueName = "spring_jms_queue_destination";
    private String queueConnectionFactoryName = "queue_connection_factory";
    private Queue queue;
    private QueueConnection queueConnection;
    private QueueSender queueSender;
         
    public MessageSenderQueue()  throws NamingException,JMSException{
        InitialContext ctx = new InitialContext();

       // Step1: Lookup the Connection Factory and the Topic
       queueConnectionFactory = (QueueConnectionFactory)
                        ctx.lookup(queueConnectionFactoryName);
       queue = (Queue)ctx.lookup(queueName);

       // Step2: Create a connection using the Factory
       queueConnection = queueConnectionFactory.createQueueConnection();

       // Step3: Create Topic Sessions using the connection
       queueSession = queueConnection.createQueueSession
                         (false,Session.AUTO_ACKNOWLEDGE);

       // Step4: Create TopicPublisher
       queueSender = queueSession.createSender(queue);

       queueConnection.start();
    }

    public void close() throws JMSException{
	queueConnection.stop();
    }
    
    public void sendMessage(String msg) throws JMSException {
	       //  Creating a Text Message with the String object
	TextMessage txtMsg = queueSession.createTextMessage(msg);
        //  Publishing the message object to the Topic
	queueSender.send(txtMsg);
    }
}
