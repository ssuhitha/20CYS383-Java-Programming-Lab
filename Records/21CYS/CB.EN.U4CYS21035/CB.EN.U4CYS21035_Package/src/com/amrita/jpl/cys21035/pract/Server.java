package com.amrita.jpl.cys21035.pract;

import java.io.*;
import java.net.*;

/**
 * The UDPServer class represents the server side of the UDP client-server application.
 * It listens for client messages and sends responses.
 */
public class Server {

    private static final int PORT = 4424;
    private static final int BUFFER_SIZE = 1024;

    /**
     * The main method is the entry point of the server application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            // Create a UDP socket
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            byte[] receiveBuffer = new byte[BUFFER_SIZE];

            while (true) {
                // Receive client message
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                // Extract client message
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + clientMessage);

                // Read user input
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter a message: ");
                String response = reader.readLine();

                String flag = "exit";
                if(response.equals(flag)){
                    break;
                }

                // Send response to client
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                byte[] sendBuffer = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);


            }
            // Close the socket
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes the client message and generates a response.
     * Modify this method to implement your specific server logic.
     *
     * @param message the client message
     * @return the response to the client
     */
    private static String processMessage(String message) {
        // Placeholder implementation
        return "Server response: " + message.toUpperCase();
    }
}
