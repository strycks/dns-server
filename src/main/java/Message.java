import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {
  private short identifier = 1234; // Packet Identifier (ID)
  private byte isResponse = 1; // Query/Response Indicator (QR)
  private byte opCode = 0; // Operation Code (OPCODE)
  private byte domainOwned = 0; // Authoritative Answer (AA)
  private byte isTruncated = 0; // Truncation (TC)
  private byte recursionDesired = 0; // Recursion Desired (RD)
  private byte recursionAvailable = 0; // Recursion Available (RA)
  private byte reserved = 0; // Reserved (Z)
  private byte responseCode = 0; // Response Code (RCODE)
  private short questionNum = 0; // Question Count (QDCOUNT)
  private short answerRecordNum = 0; // Answer Record Count (ANCOUNT)
  private short authRecordNum = 0; // Authority Record Count (NSCOUNT)
  private short additionalRecordNum = 0; // Additional Record Count (ARCOUNT)

  private List<String[]> domainName; // Domain name, multiple questions = multiple domains
  private short[] recordType; // The type of record
  private short[] classType; // Usually set to 1 (internet)
  private int questionSize; // in byte

  private byte[] packet = new byte[512];

  public void buildHeader() {
    byte[] msg = new byte[12];
    msg[0] = (byte) (identifier >> 8); // 8 high bit
    msg[1] = (byte) (identifier); // 8 low bit
    msg[2] |= (isResponse << 7);
    msg[2] |= (opCode << 3);
    msg[2] |= (domainOwned << 2);
    msg[2] |= (isTruncated << 1);
    msg[2] |= (recursionDesired);
    msg[3] |= (recursionAvailable << 7);
    msg[3] |= (reserved << 4);
    msg[3] |= (responseCode);
    msg[4] = (byte) (questionNum >> 8);
    msg[5] = (byte) (questionNum);
    msg[6] = (byte) (answerRecordNum >> 8);
    msg[7] = (byte) (answerRecordNum);
    msg[8] = (byte) (authRecordNum >> 8);
    msg[9] = (byte) (authRecordNum);
    msg[10] = (byte) (additionalRecordNum >> 8);
    msg[11] = (byte) (additionalRecordNum);

    System.arraycopy(msg, 0, packet, 0, 12);
  }

  public void buildQuestion() {
    List<Byte> allQuestions = new ArrayList<>();
    for (int quesCnt = 0; quesCnt < questionNum; quesCnt++) {
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

  public short getIdentifier() {
    return identifier;
  }

  public void setIdentifier(short identifier) {
    this.identifier = identifier;
  }

  public byte getIsResponse() {
    return isResponse;
  }

  public void setIsResponse(byte isResponse) {
    this.isResponse = isResponse;
  }

  public byte getOpCode() {
    return opCode;
  }

  public void setOpCode(byte opCode) {
    this.opCode = opCode;
  }

  public byte getDomainOwned() {
    return domainOwned;
  }

  public void setDomainOwned(byte domainOwned) {
    this.domainOwned = domainOwned;
  }

  public byte getIsTruncated() {
    return isTruncated;
  }

  public void setIsTruncated(byte isTruncated) {
    this.isTruncated = isTruncated;
  }

  public byte getRecursionDesired() {
    return recursionDesired;
  }

  public void setRecursionDesired(byte recursionDesired) {
    this.recursionDesired = recursionDesired;
  }

  public byte getRecursionAvailable() {
    return recursionAvailable;
  }

  public void setRecursionAvailable(byte recursionAvailable) {
    this.recursionAvailable = recursionAvailable;
  }

  public byte getReserved() {
    return reserved;
  }

  public void setReserved(byte reserved) {
    this.reserved = reserved;
  }

  public byte getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(byte responseCode) {
    this.responseCode = responseCode;
  }

  public short getQuestionNum() {
    return questionNum;
  }

  public void setQuestionNum(short questionNum) {
    this.questionNum = questionNum;
  }

  public short getAnswerRecordNum() {
    return answerRecordNum;
  }

  public void setAnswerRecordNum(short answerRecordNum) {
    this.answerRecordNum = answerRecordNum;
  }

  public short getAuthRecordNum() {
    return authRecordNum;
  }

  public void setAuthRecordNum(short authRecordNum) {
    this.authRecordNum = authRecordNum;
  }

  public short getAdditionalRecordNum() {
    return additionalRecordNum;
  }

  public void setAdditionalRecordNum(short additionalRecordNum) {
    this.additionalRecordNum = additionalRecordNum;
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
}
