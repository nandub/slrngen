package hellosaint.slrn;

import java.util.*;
import java.math.*;

public class SLRNFormat
{
  public static final String EAN13_Prefix = "2672";

  public static void main(String[] args) 
  {
    if (args.length > 0)
    {
      if (args[0].equals("slrn"))
      {
        String slrn = formatSLRN(args[1]); 
        System.out.println(slrn);
      }
      else if (args[0].equals("ean13"))
      {
        String ean = formatEAN13(args[1]);
        System.out.println(ean);
      }
    }
    else
    {
      String slrn = SLRNGenerator.createSLRN();
      System.out.println("SLRN Format: " + formatSLRN(slrn));
      System.out.println("EAN13 Format: " + formatEAN13(slrn));
    }
  }

  public static String formatSLRN(String slrn)
  {
    String first  = slrn.substring(0, 3);
    String second = slrn.substring(3, 6);
    String third  = slrn.substring(6, 9);
    return "SLRN " + (first + "-" + second + "-" + third);
  }

  public static String formatEAN13(String slrn)
  {
    String ean = EAN13_Prefix + slrn;
    return ean;
  }
}
