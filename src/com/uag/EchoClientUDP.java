package com.uag;

import java.io.IOException;
import java.net.*;

public class EchoClientUDP {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public EchoClientUDP() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
        // address = InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 168, 100, 32});
        System.out.println("Initializing socket... " + address);
    }

    public String sendEcho(String msg)throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }
}