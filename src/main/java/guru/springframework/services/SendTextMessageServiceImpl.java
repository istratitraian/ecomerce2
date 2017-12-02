package guru.springframework.services;

import javax.jms.ObjectMessage;
import javax.jms.Queue;

import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 4/17/16.
 */
@Component
public class SendTextMessageServiceImpl implements SendMessageService<String> {

    @Autowired
    private Queue textMessageQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    public SendTextMessageServiceImpl() {
    
//    BrokerService broker = new BrokerService();
// 
//        try {
//            TransportConnector connector = new TransportConnector();
//            connector.setUri(new URI(JmsConfig.DEFAULT_BROKER_URL));
//            broker.addConnector(connector);
//            broker.start();
//        } catch (Exception exception) {
//            System.err.println("  ERROR SendTextMessageServiceImpl() broker ");
//            
//        }
    }

    
    
    
    
    
    @Override
    public void sendTextMessage(String msg) {
        System.out.println("SendTextMessageServiceImpl.sendTextMessage(" + msg + ")");
        this.jmsTemplate.convertAndSend(this.textMessageQueue, msg);
    }

    @Override
    public void sendMessage(String msg) {
        System.out.println("SendTextMessageServiceImpl.sendMessage(" + msg + ")");

        jmsTemplate.send((Session session) -> {
            ObjectMessage objectMessage = session.createObjectMessage(msg);
            return objectMessage;
        });
    }
}
