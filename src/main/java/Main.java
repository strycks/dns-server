import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args){
    try(DatagramSocket serverSocket = new DatagramSocket(2053)) {
      while (true) {
        final byte[] buf = new byte[512];
        final DatagramPacket packet = new DatagramPacket(buf, buf.length);
        serverSocket.receive(packet);
        System.out.println("Received data");

        Message response = Parser.getInstance().makeMessage(buf);
        makeHeaderSection(response);
        makeResponseSection(response);

        response.build();

        final byte[] bufResponse = response.getPacket();

        final DatagramPacket packetResponse = new DatagramPacket(bufResponse, response.getPacketLength(), packet.getSocketAddress());
        serverSocket.send(packetResponse);
      }
    } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
    }
  }

  private static void makeResponseSection(Message response) {
    response.getHeader().setAnswerRecordNum(response.getHeader().getQuestionNum());
    int[] ttl = new int[response.getHeader().getAnswerRecordNum()];
    Arrays.fill(ttl, 60); // random value at this stage
    response.setTimeToLive(ttl);

    short[] rdLength = new short[response.getHeader().getAnswerRecordNum()];
    Arrays.fill(rdLength, (short) 4); // 4 byte ipv4 address
    response.setRdLength(rdLength);

    Byte[] ip = new Byte[4];
    Arrays.fill(ip, (byte) 8); // 8.8.8.8
    List<Byte[]> ipList = new ArrayList<>();
    for (int i = 0; i < response.getHeader().getAnswerRecordNum(); i++) {
      ipList.add(ip);
    }
    response.setRdata(ipList);
  }

  private static void makeHeaderSection(Message response) {
    response.getHeader().setIsResponse((byte) 1);
    response.getHeader().setDomainOwned((byte) 0);
    response.getHeader().setIsTruncated((byte) 0);
    response.getHeader().setRecursionAvailable((byte) 0);
    response.getHeader().setReserved((byte) 0);
    if (response.getHeader().getOpCode() == 0) {
      response.getHeader().setResponseCode((byte) 0);
    } else {
      response.getHeader().setResponseCode((byte) 4); // has not implemented yet
    }
  }
}
