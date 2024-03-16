package brickBracker.network;

import brickBracker.Gameplay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends Thread {
//    Gameplay gameplay;
    int ClientNumber;

    public UDPClient(int ClientNumber) {
        this.ClientNumber = ClientNumber;
    }


    public void run() {
        try {
            DatagramSocket server = new DatagramSocket(
                    4222
            );
            byte[] recievingBytes = new byte[256];

            DatagramPacket packet = new DatagramPacket(recievingBytes,recievingBytes.length);

            server.receive(packet);

            String message = new String(packet.getData());

            System.out.println(message.trim());

            //input

            server.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
}

