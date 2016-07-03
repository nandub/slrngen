package hellosaint.slrn;

import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.math.*;

// The EAN validation was taken from https://codereview.stackexchange.com/questions/115590/hold-and-validate-an-ean13-code

public class SLRNGenerator
{
  private static final RuntimeException NOT_VALID_EAN_EXCEPTION = new RuntimeException("NOT VALID EAN CODE");
  private static final int min             = 10000000;
  private static final int max             = 99999999;
  private static final String EAN13_Prefix = "2672";

  public static void main(String[] args) 
  {
    if (args.length > 0)
    {
      if (args[0].equals("create"))
      {
        String ean = createSLRN(); 
        System.out.println(ean);
      }
      else if (args[0].equals("verify"))
      {
        String slrn = args[1];
        System.out.println(slrn + " is valid: " + verifySLRN(slrn));
      }
    }
    else
    {
      System.out.println("Perfoming a few tests");
      String ean = createSLRN();
      System.out.println(ean + " is valid: " + verifySLRN(ean));
      System.out.println("123-456-781 is valid: " + verifySLRN("123-456-781"));
      System.out.println("406-083-338 is valid: " + verifySLRN("406-083-338"));
      System.out.println("406083338 is valid: " + verifySLRN("406083338"));
    }
  }

  public static String createSLRN() 
  {
    int eigth           = ThreadLocalRandom.current().nextInt(min, max+1); 
    int ninth           = calc9thDigit("" + eigth);
    return Integer.toString(eigth) + Integer.toString(ninth);
  }
  
  public static boolean verifySLRN(String stringNumber)
  {
    String result      = stringNumber.replaceAll("[-]", "");
    String eigthString = result.substring(0,result.length()-1);
    int ninth          = calc9thDigit(eigthString);
    return (eigthString + ninth).equals(result);
  }
  
  public static int calc9thDigit(String code)
  {
    String stringNumber = EAN13_Prefix + code;
    String codeWithoutVd = stringNumber.substring(0, 12);
    int e = sumEven(codeWithoutVd);
    int o = sumOdd(codeWithoutVd);
    int me = o * 3;
    int s = me + e;
    int dv = getEanVd(s);
    return dv;
  }

  public static void validate(String code) {
      if (code == null || code.length() != 13) {
          throw NOT_VALID_EAN_EXCEPTION;
      }
      //if (!CharMatcher.DIGIT.matchesAllOf(code)) {
      //    throw NOT_VALID_EAN_EXCEPTION;
      //}
      String codeWithoutVd = code.substring(0, 12);
      int pretendVd = Integer.valueOf(code.substring(12, 13));
      int e = sumEven(codeWithoutVd);
      int o = sumOdd(codeWithoutVd);
      int me = o * 3;
      int s = me + e;
      int dv = getEanVd(s);
      if (!(pretendVd == dv)) {
          throw NOT_VALID_EAN_EXCEPTION;
      }
  }

  private static int getEanVd(int s) {
      return 10 - (s % 10);
  }

  private static int sumEven(String code) {
      int sum = 0;
      for (int i = 0; i < code.length(); i++) {
          if (isEven(i)) {
              sum += Character.getNumericValue(code.charAt(i));
          }
      }
      return sum;
  }

  private static int sumOdd(String code) {
      int sum = 0;
      for (int i = 0; i < code.length(); i++) {
          if (!isEven(i)) {
              sum += Character.getNumericValue(code.charAt(i));
          }
      }
      return sum;
  }

  private static boolean isEven(int i) {
      return i % 2 == 0;
  }
}
