/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmschat;
import java.util.logging.Logger;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
//import org.jboss.naming.remote.client.InitialContextFactory;

/**
 *
 * @author Raj Kumar Rb
 */
public class JmsTest {
    
    private static final Logger log = Logger.getLogger(HelloWorldJMSClient.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/test";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String DEFAULT_USERNAME = "user";
    private static final String DEFAULT_PASSWORD = "123456!";
//    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";

    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

     public static void main(String[] args) {
        System.out.println("1==>");
        Context namingContext = null;
        System.out.println("2==>");
        try {
            String userName = System.getProperty("username", DEFAULT_USERNAME);
            String password = System.getProperty("password", DEFAULT_PASSWORD);
            System.out.println("3==>");
            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            System.out.println("4==>");
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
//            System.out.println("5==>");
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            System.out.println("6==>");
            env.put(Context.SECURITY_PRINCIPAL, userName);
            System.out.println("7==>");
            env.put(Context.SECURITY_CREDENTIALS, password);
            System.out.println("8==>");
           
            namingContext = new InitialContext(env);
            System.out.println("9==>");
            // Perform the JNDI lookups

            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            System.out.println("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            System.out.println("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
            System.out.println("Attempting to acquire destination \"" + destinationString + "\"");
            Destination destination = (Destination) namingContext.lookup(destinationString);
            System.out.println("Found destination \"" + destinationString + "\" in JNDI");

            int count = Integer.parseInt(System.getProperty("message.count", DEFAULT_MESSAGE_COUNT));
            String content = System.getProperty("message.content", DEFAULT_MESSAGE);
//            connectionFactory.createConnection(userName, password)
            try (JMSContext context = connectionFactory.createContext(userName, password)) {
                System.out.println("Sending " + count + " messages with content: " + content);
                // Send the specified number of messages
                for (int i = 0; i < count; i++) {
                    context.createProducer().send(destination, content);
                }

                // Create the JMS consumer
//                JMSConsumer consumer = context.createConsumer(destination);
//                // Then receive the same number of messages that were sent
//                for (int i = 0; i < count; i++) {
//                    String text = consumer.receiveBody(String.class, 5000);
//                    System.out.println("Received message with content " + text);
//                }
            }
        } catch (NamingException e) {
            log.severe(e.getMessage());
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException e) {
                    log.severe(e.getMessage());
                }
            }
        }
    }

}
