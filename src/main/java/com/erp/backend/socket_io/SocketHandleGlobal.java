package com.erp.backend.socket_io;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.erp.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component

public class SocketHandleGlobal {
    private static final Logger log = LoggerFactory.getLogger(SocketHandleGlobal.class);

    private  SocketIONamespace namespace;

    @Autowired
    public SocketHandleGlobal(SocketIOServer server) {
        // muốn nhận tất cả thì server.addNamespace("")
        this.namespace = server.addNamespace("/chat");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("join", String.class, onJoin());
    }

    public void sendEvent(Long roomId, String event, Object data) {
        this.namespace.getRoomOperations(String.valueOf(roomId)).sendEvent(event, data);
    }

    private DataListener<String> onJoin() {
        return (client, data, ackSender) -> {
            System.out.println("Client[{}] - Joined chat module");
            //join room
            client.joinRoom(data);
//            this.namespace.getRoomOperations(data).sendEvent("message", "Hello " + data);
        };
    }


    private ConnectListener onConnected() {
        return client -> {
            System.out.println("Client[{}] - Connected to chat module through '{}'");
            HandshakeData handshakeData = client.getHandshakeData();
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            System.out.println("Client[{}] - Disconnected from chat module.");
        };
    }

}
