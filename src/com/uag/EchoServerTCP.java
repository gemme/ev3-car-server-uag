package com.uag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerTCP {
    private ServerSocket serverSocket;

    public void start (int port) throws IOException{

        serverSocket = new ServerSocket(port);

        while(true){
            new ClientHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException{
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        public void run(){
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                );
                String inputLine;
                while((inputLine = in.readLine() )!= null){
                    if(".".equals(inputLine)){
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // write your code here
        try {
            System.out.println("Starting server ...");
            // new Ev3CarServerUDP().start();
            EchoServerTCP server = new EchoServerTCP();
            server.start(4546);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
