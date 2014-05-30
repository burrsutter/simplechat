/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.websocket_simplechat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/chat")
public class ChatEndpoint {

	private int x = 0;
	private static final Set<Session> sessions = new HashSet<Session>();
	
    @OnMessage
    public void messageHandler(String msg, Session session) {
        System.out.println("Say hello to '" + msg + "'" + x++);
        for (Session s : sessions) {
        	System.out.println(s.getId());
        	try {
        		s.getBasicRemote().sendText(msg);
        	} catch (IOException e) {
        		System.err.println(e);
        	}
        }
    }

    @OnOpen
    public void myOnOpen(Session session) {
    	
        System.out.println("WebSocket opened: " + session.getId());
        String sessionID = session.getId();
        sessions.add(session);
    }
    
    @OnClose
    public void myOnClose(CloseReason reason) {
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }
}
