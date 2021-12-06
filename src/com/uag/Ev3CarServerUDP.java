package com.uag;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Ev3CarServerUDP extends Thread {

    private DatagramSocket socket;
    private Route route;
    private boolean running;
    private byte[] bufferReceived = new byte[1];
    private byte[] bufferSent;

    public Ev3CarServerUDP() throws SocketException {
        socket = new DatagramSocket(4445);
        route = new Route("right");
    }

    public void run() {
        running = true;
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(bufferReceived, bufferReceived.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.println("Server listening at address: "+ address+ " port: " + port);
                packet = new DatagramPacket(bufferReceived, bufferReceived.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server received: "+ received);
                String command = route.chooseRouteByColor(Integer.parseInt(received));
                bufferSent = command.getBytes();
                packet = new DatagramPacket(bufferSent, bufferSent.length, address, port);
                System.out.println("command: " + command);
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}