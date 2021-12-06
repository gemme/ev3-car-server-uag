package com.uag;

import java.io.IOException;
import java.net.SocketException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            System.out.println("Starting server ...");
            // new Ev3CarServerUDP().start();
            new Ev3CarServerTCP().start(4445);
            // 192.168.100.32
//            EchoClientTCP client = new EchoClientTCP("localhost", 4546);
//            String response = client.sendMessage("hello gemme!");
//            System.out.println("response from server: " + response);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
