package brickBracker.network;

import brickBracker.Gameplay;

import java.io.IOException;
import java.net.*;

public class UDPServer extends Thread{

    private Gameplay gameplay;

    public UDPServer(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    public void run() {
        try {
            DatagramSocket client = new DatagramSocket();

            InetAddress ipAddress = InetAddress.getLocalHost();

            String str = "Hello World";

            byte[] encodeByteArray = str.getBytes();

            DatagramPacket packet = new DatagramPacket(encodeByteArray,encodeByteArray.length,ipAddress,4222);

            client.send(packet);

            client.receive(packet);

            String message = new String(packet.getData());

            System.out.println(message.trim());

            for (int i = 0; i < 10; i++) {
                gameplay.rightKey();
                Thread.sleep(1000);
            }
            client.close();

        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }



    }

}