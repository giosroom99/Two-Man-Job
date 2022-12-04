// Andrew Krasuski
package newpackage;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import java.util.Scanner;
import java.lang.Math;
import java.math.BigInteger;
import java.security.SecureRandom;


public class SecretSharing extends TeaAlgorithim {
	// cited from https://mkyong.com/java/how-to-convert-hex-to-ascii-in-java/
	 // Char -> Decimal -> Hex
    public static String convertStringToHex(String str) {
        StringBuffer hex = new StringBuffer();
        // loop chars one by one
        for (char temp : str.toCharArray()) {
            // convert char to int, for char `a` decimal 97
            int decimal = (int) temp;
            // convert int to hex, for decimal 97 hex 61
            hex.append(Integer.toHexString(decimal));
        }
        return hex.toString();
    }
    // Hex -> Decimal -> Char
    public static String convertHexToString(String hex) {
        StringBuilder result = new StringBuilder();
        // split into two chars per loop, hex, 0A, 0B, 0C...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String tempInHex = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(tempInHex, 16);
            // convert the decimal to char
            result.append((char) decimal);
        }
        return result.toString();
    } 
    // End of citation   
	public static boolean accessResponse(String result, String S) {
		if(result.equals(S)) {
			return true;
		}
		else {
			return false;
		}
	}
	// secretResult and larange calculate the constant only
	public static BigInteger secretResult(BigInteger[] y, BigInteger[] numerator, BigInteger[] denominator) {
		BigInteger accumulator = BigInteger.valueOf(0), total = BigInteger.valueOf(0);
		for(int i = 0; i < numerator.length; i++) {
			accumulator = y[i].multiply(numerator[i]);
			accumulator = accumulator.divide(denominator[i]);
			total = total.add(accumulator);
			accumulator = BigInteger.valueOf(0);
		}
		return total;
	}
	public static void larange(BigInteger[] x, BigInteger[] numerator, BigInteger[] denominator, int K) {
		for(int k = 0; k < x.length; k++) {
			BigInteger accumulateNumerator = BigInteger.valueOf(1);
			BigInteger accumulateDenominator = BigInteger.valueOf(1);
			for(int j = 0; j < x.length; j++) {

				if(k == j) {
				}
				else {
					numerator[k] = (x[j]).negate();
					denominator[k] = (x[k].subtract(x[j]));
					accumulateNumerator = accumulateNumerator.multiply(numerator[k]);
					accumulateDenominator = accumulateDenominator.multiply(denominator[k]);
				}
			}
			numerator[k] = accumulateNumerator;
			denominator[k] = accumulateDenominator;
		}
	}
	public static void pointGenerator(BigInteger[] x2, BigInteger[] y, BigInteger[] coef, BigInteger s) {
		BigInteger x = BigInteger.valueOf(1), accumulator = BigInteger.valueOf(0), total = BigInteger.valueOf(0);

		for(int i = 0; i < x2.length; ++i) {
			x2[i] = x;

			for(int j = 0; j < coef.length; j++) {
				

				for(int k = 1; k <= j+1; k++) {
					if(k == 1) {

					}
					else {
						x = x.multiply(x);
					}

				}
				accumulator = x;
				accumulator = accumulator.multiply(coef[j]);

				total = total.add(accumulator);
			}


			total = total.add(s);
			y[i] = total;

			accumulator = BigInteger.valueOf(0);
			total = BigInteger.valueOf(0);
			x = x.add(BigInteger.valueOf(1));
		}

	}


}
