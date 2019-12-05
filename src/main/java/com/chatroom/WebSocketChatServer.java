package com.chatroom;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.alibaba.fastjson.JSON;
import com.chatroom.model.Message;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String jsonStr, String type) {
        Message message;
        if(jsonStr != null)
            message = JSON.parseObject(jsonStr,Message.class);
        else
            message = new Message("","");
        message.setType(type);
        message.setOnlineCount(onlineSessions.size());

        for(Session s : onlineSessions.values()){
            try {
                s.getBasicRemote().sendText(JSON.toJSONString(message));
                System.out.println(String.format("Sending message '%1s' to session %2s", jsonStr,s.getId()));
            }
            catch (IOException ex) {
                System.out.println("Error while sending message to session "+s.getId()+". Exception is: "+ex.toString());
            }
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connection opened with ID: "+session.getId());
        onlineSessions.put(session.getId(),session);
        sendMessageToAll(null,"STATUS");
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        System.out.println("Message received: " + jsonStr);
        sendMessageToAll(jsonStr,"SPEAK");
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed with ID: "+session.getId());
        onlineSessions.remove(session.getId());
        sendMessageToAll(null,"STATUS");
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}