public class Header {
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

  public byte[] buildHeader() {
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

    return msg;
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
}
