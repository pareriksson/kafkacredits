package se.perrz.model.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonKeyTest {

  @Test
  public void testFrom() throws Exception {
    String key = PersonKey.from("201201011234");
    Assert.assertEquals("tH+CvdFSj3Lo3+Wvvm2Yb51x/GEN7fNOszJvY3vYP94=", key);

  }
}
