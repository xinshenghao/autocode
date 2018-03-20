package other;

import java.util.UUID;

import org.testng.annotations.Test;

public class NewTest {
  @Test
  public void f() {
	  System.out.println(UUID.randomUUID().toString());
  }
}
