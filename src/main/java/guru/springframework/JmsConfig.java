/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guru.springframework;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author I.T.W764
 */
@Configuration
public class JmsConfig {//  extends ExternalResource {

    public static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
//    public static final String DEFAULT_BROKER_URL = "vm://localhost";

    public static final String TEXT_MESSAGE_QUEUE = "text.messagequeue";
////    public String brokerUrl = "failover:(tcp://localhost:61616)";
////    public String brokerUrl = "tcp://localhost:61616";
////    public String brokerUrl = "istrati.traian@yahoo.com";
//    public String brokerUrl = "vm://localhost";
//

//    private static final int STORAGE_LIMIT = 1024 * 1024 * 8; // 8mb
    @Bean
    public Queue textMessageQueue() {
        return new ActiveMQQueue(TEXT_MESSAGE_QUEUE);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
//        connectionFactory.setTrustedPackages(Arrays.asList("guru.springframework"));
        System.out.println(" --- JmsConfig.connectionFactory()");
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestination(textMessageQueue());
//        template.setDefaultDestinationName(ORDER_QUEUE);

        System.out.println(" --- JmsConfig.jmsTemplate()");
        return template;
    }

//    @Override
//	protected void before() throws Throwable {
////    public BrokerService brokerService() throws Exception {
//        try {
//            BrokerService broker = new BrokerService();
//            broker.addConnector(connector());
//            broker.setUseJmx(true);
//            broker.setPersistent(false);
//
////        SystemUsage systemUsage = broker.getSystemUsage();
////        systemUsage.getStoreUsage().setLimit(STORAGE_LIMIT);
////        systemUsage.getTempUsage().setLimit(STORAGE_LIMIT);
//            broker.start();
//        } catch (Exception exception) {
//             System.out.println("  ERROR JmsConfig.brokerService()");
//        }
//        System.out.println(" --- JmsConfig.brokerService()");
////        return broker;
//    }
//        	@Override
//	protected void after() {
//		if (broker != null) {
//			try {
//				broker.stop();
//			} catch (Exception e) {
//				throw new IllegalStateException(e);
//			}
//		}
//	}
//    private TransportConnector connector() throws URISyntaxException {
//        TransportConnector connector = new TransportConnector();
//        connector.setUri(new URI(DEFAULT_BROKER_URL)); // or tcp://localhost:0
//        return connector;
//    }
}
