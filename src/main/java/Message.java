import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {
  private Header header = new Header();

  private List<String[]> domainName; // Domain name, multiple questions = multiple domains
  private short[] recordType; // The type of record
  private short[] classType; // Usually set to 1 (internet)
  private int questionSize; // in byte

  private int[] timeToLive;
  private short[] rdLength;
  private List<Byte[]> rdata; // each record has length specified by rdLength

  private byte[] packet = new byte[512];
  private int packetLength = 0;

  public void build() {
    byte[] headerMsg = header.buildHeader();
    System.arraycopy(headerMsg, 0, packet, 0, headerMsg.length);
    packetLength = headerMsg.length;
    buildQuestion();
    buildAnswer();
  }

  public void buildQuestion() {
    List<Byte> allQuestions = new ArrayList<>();
    for (int quesCnt = 0; quesCnt < header.getQuestionNum(); quesCnt++) {
      for (int i = 0; i < domainName.get(quesCnt).length; i++) {
        String part = domainName.get(quesCnt)[i];

        allQuestions.add((byte) part.length());
        for (byte b : part.getBytes()) {
          allQuestions.add(b);
        }
      }
      allQuestions.add((byte) 0x00);
      allQuestions.add((byte) (recordType[quesCnt] >> 8));
      allQuestions.add((byte) (recordType[quesCnt]));
      allQuestions.add((byte) (classType[quesCnt] >> 8));
      allQuestions.add((byte) (classType[quesCnt]));
    }
    byte[] bytes = new byte[allQuestions.size()];
    for (int i = 0; i < allQuestions.size(); i++) {
      bytes[i] = allQuestions.get(i);
    }
    questionSize = bytes.length;

    System.arraycopy(bytes, 0, packet, 12, bytes.length);
    packetLength += bytes.length;
  }

  public void buildAnswer() {
    List<Byte> allAnswers = new ArrayList<>();
    for (int ansCnt = 0; ansCnt < header.getAnswerRecordNum(); ansCnt++) {
      for (int i = 0; i < domainName.get(ansCnt).length; i++) {
        String part = domainName.get(ansCnt)[i];

        allAnswers.add((byte) part.length());
        for (byte b : part.getBytes()) {
          allAnswers.add(b);
        }
      }
      allAnswers.add((byte) 0x00);
      allAnswers.add((byte) (recordType[ansCnt] >> 8));
      allAnswers.add((byte) (recordType[ansCnt]));
      allAnswers.add((byte) (classType[ansCnt] >> 8));
      allAnswers.add((byte) (classType[ansCnt]));

      allAnswers.add((byte) (timeToLive[ansCnt] >> 24));
      allAnswers.add((byte) (timeToLive[ansCnt] >> 16));
      allAnswers.add((byte) (timeToLive[ansCnt] >> 8));
      allAnswers.add((byte) (timeToLive[ansCnt]));

      allAnswers.add((byte) (rdLength[ansCnt] >> 8));
      allAnswers.add((byte) (rdLength[ansCnt]));

      allAnswers.addAll(Arrays.asList(rdata.get(ansCnt)));
    }
    byte[] bytes = new byte[allAnswers.size()];
    for (int i = 0; i < allAnswers.size(); i++) {
      bytes[i] = allAnswers.get(i);
    }

    System.arraycopy(bytes, 0, packet, packetLength, bytes.length);
    packetLength += bytes.length;
  }

  public int[] getTimeToLive() {
    return timeToLive;
  }

  public void setTimeToLive(int[] timeToLive) {
    this.timeToLive = timeToLive;
  }

  public short[] getRdLength() {
    return rdLength;
  }

  public void setRdLength(short[] rdLength) {
    this.rdLength = rdLength;
  }

  public List<Byte[]> getRdata() {
    return rdata;
  }

  public void setRdata(List<Byte[]> rdata) {
    this.rdata = rdata;
  }

  public byte[] getPacket() {
    return packet;
  }

  public int getQuestionSize() {
    return questionSize;
  }

  public void setQuestionSize(int questionSize) {
    this.questionSize = questionSize;
  }

  public List<String[]> getDomainName() {
    return domainName;
  }

  public void setDomainName(List<String[]> domainName) {
    this.domainName = domainName;
  }

  public short[] getRecordType() {
    return recordType;
  }

  public void setRecordType(short[] recordType) {
    this.recordType = recordType;
  }

  public short[] getClassType() {
    return classType;
  }

  public void setClassType(short[] classType) {
    this.classType = classType;
  }

  public int getPacketLength() {
    return packetLength;
  }

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }
}
