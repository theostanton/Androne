package com.theostanton.monitor;

import com.jakewharton.rxrelay2.BehaviorRelay;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

import io.reactivex.Observable;

public class SocketServer extends WebSocketServer {

  private WebSocket socket;
  private BehaviorRelay<String> relay = BehaviorRelay.create();

  public SocketServer(InetSocketAddress address) {
    super(address);
  }

  public Observable<String> observe() {
    return relay;
  }

  public void send(String message) {
    socket.send(message);
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    socket = conn;
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {

  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    relay.accept(message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {

  }
}