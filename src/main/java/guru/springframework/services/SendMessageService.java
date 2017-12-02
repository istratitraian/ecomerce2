/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guru.springframework.services;

import java.io.Serializable;

/**
 *
 * @author I.T.W764
 * @param <T>
 */
public interface SendMessageService<T extends Serializable> {

    void sendTextMessage(String msg);

    void sendMessage(T msg);
}
