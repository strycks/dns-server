import java.util.ArrayList;
import java.util.List;

public class Parser {
  private static Parser instance = null;

  private Parser() {

  }

  public static Parser getInstance() {
    if (instance == null) {
      return instance = new Parser();
    }
    return instance;
  }

  public Message makeMessage(byte[] buffer) {
    // 0 - 11 is header
    Message msg = new Message();
    msg.setIdentifier(combine(buffer[0], buffer[1]));

    msg.setIsResponse(getBit(buffer[2], 7));
    msg.setOpCode(getChunk(buffer[2], 3, 4));
    msg.setDomainOwned(getBit(buffer[2], 2));
    msg.setIsTruncated(getBit(buffer[2], 1));
    msg.setRecursionDesired(getBit(buffer[2], 0));

    msg.setRecursionAvailable(getBit(buffer[3], 7));
    msg.setReserved(getChunk(buffer[3], 4, 3));
    msg.setResponseCode(getChunk(buffer[3], 0, 4));

    msg.setQuestionNum(combine(buffer[4], buffer[5]));
    msg.setAnswerRecordNum(combine(buffer[6], buffer[7]));
    msg.setAuthRecordNum(combine(buffer[8], buffer[9]));
    msg.setAdditionalRecordNum(combine(buffer[10], buffer[11]));

    int idx = 12;
    for (int i = 0; i < msg.getQuestionNum(); i++, idx++) {
      List<String> domains = new ArrayList<>();
      StringBuilder cur = new StringBuilder();
      int byteRemaining = 0;
      for (int j = idx; j < buffer.length && !(buffer[j] == 0 && byteRemaining == 0); j++, idx++) {
        if (byteRemaining == 0) {
          if (!cur.isEmpty()) {
            domains.add(String.valueOf(cur));
            cur = new StringBuilder();
          }
          byteRemaining = buffer[j];
        } else {
          byteRemaining--;
          cur.append((char) buffer[j]);
        }
      }
      if (!cur.isEmpty()) {
        domains.add(String.valueOf(cur));
        cur = new StringBuilder();
      }
      System.out.println(domains);
    }

    return msg;
  }

  /**
   * Since right shift will perform sign extension, we have to cast to larger type first.
   */
  public byte getBit(byte num, int pos) {
    return (byte) ((((short) num >> pos) & 1) > 0 ? 1 : 0);
  }

  public byte getChunk(byte num, int pos, int len) {
    return (byte) ((num >> pos) & ((1 << len) - 1));
  }

  private short combine(byte hi, byte lo) {
    return (short) (Integer.toUnsignedLong(hi << 8) + Byte.toUnsignedInt(lo));
  }
}
