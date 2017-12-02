/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guru.springframework.jms;

/**
 *
 * @author I.T.W764
 */
import guru.springframework.JmsConfig;
import javax.jms.JMSException;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

//    static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
//    private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";
    @JmsListener(destination = JmsConfig.TEXT_MESSAGE_QUEUE)
//    public void receiveMessage(final Message<InventoryResponse> message) throws JMSException {
    public void receiveMessage(final Message<String> message) throws JMSException {
        System.out.println("++++++++++++++++Message Reciev+++++++++++++++++++++++");
        MessageHeaders headers = message.getHeaders();
        System.out.println("Message : " + message.getPayload());
        System.out.println("Application : headers received : " + headers);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
