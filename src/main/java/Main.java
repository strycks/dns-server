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

        // Message msg1 = new Message();
        // byte[] bufTest1 = new byte[512];
        // System.arraycopy(msg1.buildHeader(), 0, bufTest1, 0, 12);
        // Message msg2 = Parser.getInstance().makeMessage(bufTest1);
        //
        // System.out.println(msg1 + "\n" + msg2);

        Message response = Parser.getInstance().makeMessage(buf);
        response.setIsResponse((byte) 1);
        response.setReserved((byte) 0);
        response.buildHeader();
        response.buildQuestion();
        final byte[] bufResponse = response.getPacket();

        final DatagramPacket packetResponse = new DatagramPacket(bufResponse, response.getPacketLength(), packet.getSocketAddress());
        serverSocket.send(packetResponse);
      }
    } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
    }
  }
}
