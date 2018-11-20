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
public class MessageReceiverQueue implements MessageListener{
	 private QueueConnectionFactory queueConnectionFactory;
	 private QueueSession queueSession;
	 private final String queueName = "spring_jms_queue_destination";
	 private final String queueConnectionFactoryName = "queue_connection_factory";
	 private Queue queue;
	 private QueueConnection queueConnection;
	 private QueueReceiver queueReceiver;
         private MessageHandler messageHandler;
    
    public MessageReceiverQueue()  throws NamingException,JMSException{
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
		       queueReceiver = queueSession.createReceiver(queue);
		       queueReceiver.setMessageListener(this);

		       queueConnection.start();
    }
    public void close() throws JMSException{
		 queueConnection.stop();
	 }
    
    @Override
    public synchronized void onMessage(Message message) {
        boolean bool=message instanceof TextMessage;
        System.out.println("bool==>"+bool);
        try{
            if(message instanceof TextMessage) {
                TextMessage msg=(TextMessage)message;
                if(messageHandler!=null){
                    messageHandler.handleMessage(msg.getText());
                }
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
