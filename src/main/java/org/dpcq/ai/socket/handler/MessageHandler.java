package org.dpcq.ai.socket.handler;

import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.WebSocketConnectionManager;

public interface MessageHandler {

    String getHandlerType(String ops);

    void handle(String userId, String msg, SessionHandler sessionHandler);
}
