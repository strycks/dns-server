import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitFunctionTest {
  @Test
  public void testBit1() {
    byte a = 87; // 0101 0111
    byte b = 0;
    for (int i = 7; i >= 0; i--) {
      b <<= 1;
      b += Parser.getInstance().getBit(a, i);
    }
    Assertions.assertEquals(a, b);
  }

  @Test
  public void testChunk1() {
    byte a = 87; // 0101 0111
    byte c = 7; // 0000 0111
    byte b = Parser.getInstance().getChunk(a, 0, 3);
    Assertions.assertEquals(c, b);
  }
}
