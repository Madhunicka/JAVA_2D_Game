package brickBracker.network;

public class UDPCLientHandler  {
    public static void main(String[] args) {

            UDPClient client = new UDPClient(0);
            client.start();

    }
}
