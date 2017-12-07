package se.perrz.model.util;

import com.google.common.base.Preconditions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PersonKey {

  private static String salt = ")I)=AIHFNDNASLDNLIJ)P)?JFA=A2?DKAD=J";

  public static String from(String personalId) {
    Preconditions.checkNotNull(personalId);
    Preconditions.checkArgument(personalId.length() == 12, "invalid len " + personalId.length());

    String input = personalId + salt;

    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(input.getBytes());
      return new String(Base64.getEncoder().encode(messageDigest.digest()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

  }
}
