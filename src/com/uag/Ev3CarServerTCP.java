package com.uag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Ev3CarServerTCP {
        private ServerSocket serverSocket;

        public void start (int port) throws IOException {

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
            private Route route;

            public ClientHandler(Socket socket){
                System.out.println("connection from client: ");
                this.clientSocket = socket;
                route = new Route("right");
            }

            public void run(){
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream())
                    );
                    String inputLine;
                    while((inputLine = in.readLine() )!= null){
                        System.out.println("Server tcp received:  "+ inputLine);
                        String command = route.chooseRouteByColor(Integer.parseInt(inputLine));
                        System.out.println("command: " + command);
                        if(".".equals(inputLine)){
                            out.println("bye");
                            break;
                        }
                        out.println(command);
                    }
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
