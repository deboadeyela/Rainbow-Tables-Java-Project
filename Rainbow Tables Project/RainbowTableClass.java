
import java.util.*;

/**
 *  This class provides functionality to build rainbow tables (with a different reduction function per round) for 8 character long strings, which
    consist of the symbols "a .. z", "A .. Z", "0 .. 9", "!" and "#" (64 symbols in total).
    Properly used, it creates the following value pairs (start value - end value) after 10,000 iterations of hashFunction() and reductionFunction():
          start value  -  end value
          Kermit12        lsXcRAuN
          Modulus!        L2rEsY8h
          Pigtail1        R0NoLf0w
          GalwayNo        9PZjwF5c
          Trumpets        !oeHRZpK
          HelloPat        dkMPG7!U
          pinky##!        eDx58HRq
          01!19!56        vJ90ePjV
          aaaaaaaa        rLtVvpQS
          036abgH#        klQ6IeQJ
          
          
 *
 * @author Gbadebo Adeyela
 * @version 1.1

public class CT437_RainbowTable {
  /**
   * Constructor, not needed for this assignment
   */
  public CT437_RainbowTable() {

  }

  public static void main(String[] args) {
    long res = 0; // Variable for holding hash values
    int i; // Used to in for-loop to cycle throuh hash reductions
    boolean c = false; // Sets boolean value to false
    String start; // Variable for holding password value
    String[] hashValues = new String[] {"895210601874431214", "750105908431234638",
        "111111111115664932", "977984261343652499"}; // Array used to hold hash values that are
                                                     // being checked for match
    List<String> nameList = new ArrayList<>(Arrays.asList(hashValues)); // Convert array of hash
                                                                        // values to array
    List<Long> testList = new ArrayList(); // Arraylist used to hold hash values for password



    if (args != null && args.length > 0) { // Check for <input> value
      start = args[0];

      if (start.length() < 8) {
        System.out.println("Input " + start + " must be 8 characters long - Exit");
      } else {
        // Your code for problem 1 starts here
        // For-loop used to go through 10000 hash/reduction cycles and add hash values to Arraylist
        // for password
        for (i = 0; i < 10000; i++) {
          res = hashFunction(start);
          start = reductionFunction(res, i);
          testList.add(res);
        }
        // Prints results of encoding
        System.out.println("The encoding " + start + " for password " + args[0]
            + " is generated after 10,000 hash/reduction cycles \n");
        // code for problem 2 starts here
        // Prints to console that we are searching if there is a match for hash values
        System.out.println(
            "Searching if any of the following hash values: \n[895210601874431214, 750105908431234638, 111111111115664932,977984261343652499] matches the password "
                + args[0] + "\n");
        // For-loop used to go through Arraylist of hash values of password to check it matches one
        // of the four hash values
        for (int j = 0; j < testList.size(); j++) {
          if (nameList.contains(String.valueOf(testList.get(j)))) {
            String who = String.valueOf(testList.get(j));
            System.out.println("Hash value " + who + " matches password " + args[0] + "\n");
            c = true; // c is equal to true if match is found
          } /*
             * else{ System.out.println("No match found" ); break; }
             */
        }
        if (!c) {
          System.out.println("No match found"); // Sets c is equal to false if no match is found
        }
      }
    }

    else { // No <input>
      System.out.println("Use: CT437_RainbowTable <Input>");
    }
  }

  private static long hashFunction(String s) {
    long ret = 0;
    int i;
    long[] hashA = new long[] {1, 1, 1, 1};

    String filler, sIn;

    int DIV = 65536;

    filler = new String("ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH");

    sIn = s + filler; // Add characters, now have "<input>HABCDEF..."
    sIn = sIn.substring(0, 64); // // Limit string to first 64 characters

    for (i = 0; i < sIn.length(); i++) {
      char byPos = sIn.charAt(i); // get i'th character
      hashA[0] += (byPos * 17111); // Note: A += B means A = A + B
      hashA[1] += (hashA[0] + byPos * 31349);
      hashA[2] += (hashA[1] - byPos * 101302);
      hashA[3] += (byPos * 79001);
    }

    ret = (hashA[0] + hashA[2]) + (hashA[1] * hashA[3]);
    if (ret < 0)
      ret *= -1;
    return ret;
  }

  private static String reductionFunction(long val, int round) { // Note that for the first function
                                                                 // call "round" has to be 0,
    String car, out; // and has to be incremented by one with every subsequent call.
    int i; // I.e. "round" created variations of the reduction function.
    char dat;

    car = new String("0123456789ABCDEFGHIJKLMNOPQRSTUNVXYZabcdefghijklmnopqrstuvwxyz!#");
    out = new String("");

    for (i = 0; i < 8; i++) {
      val -= round;

      // This fix addresses the problem of negative remainders
      long temp = val % 63;
      if (temp < 0)
        temp += 63;
      dat = (char) temp;

      // Old version
      // dat = (char) (val % 63);
      val = val / 83;
      out = out + car.charAt(dat);
    }

    return out;
  }
}
