import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {
  public static void main(String[] args){
    try(DatagramSocket serverSocket = new DatagramSocket(2053)) {
      while (true) {
        final byte[] buf = new byte[512];
        final DatagramPacket packet = new DatagramPacket(buf, buf.length);
        serverSocket.receive(packet);
        System.out.println("Received data");

        final byte[] bufResponse = new byte[512];

        // Message msg1 = new Message();
        // byte[] bufTest1 = new byte[512];
        // System.arraycopy(msg1.buildHeader(), 0, bufTest1, 0, 12);
        // Message msg2 = Parser.getInstance().makeMessage(bufTest1);
        //
        // System.out.println(msg1 + "\n" + msg2);

        Message msg = Parser.getInstance().makeMessage(buf);
        byte[] header = new Message().buildHeader();
        System.arraycopy(header, 0, bufResponse, 0, 12);
        final DatagramPacket packetResponse = new DatagramPacket(bufResponse, bufResponse.length, packet.getSocketAddress());
        serverSocket.send(packetResponse);
      }
    } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
    }
  }
}
