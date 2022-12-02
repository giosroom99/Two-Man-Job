// Andrew Krasuski

import java.util.Scanner;
import java.lang.Math;
import java.math.BigInteger;
import java.security.SecureRandom;


public class SecretSharing extends TeaAlgorithim {
	
	// https://mkyong.com/java/how-to-convert-hex-to-ascii-in-java/
	
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
	public static boolean accessResponse(String result, String S) {

		if(result.equals(S)) {
			return true;
		}
		else {
			return false;
		}
	}

	
	// secretResult and larange calculate the constant only

	public static BigInteger secretResult(BigInteger[] y, int[] choices, BigInteger[] numerator, BigInteger[] denominator) {
		BigInteger accumulator = BigInteger.valueOf(0), total = BigInteger.valueOf(0);
		for(int i = 0; i < numerator.length; i++) {

			accumulator = y[choices[i]].multiply(numerator[i]);
			accumulator = accumulator.divide(denominator[i]);
			total = total.add(accumulator);

			accumulator = BigInteger.valueOf(0);

		}
		return total;
	}

	public static void larange(BigInteger[] x, int[] choices, BigInteger[] numerator, BigInteger[] denominator, int K) {


		for(int k = 0; k < choices.length; k++) {

			BigInteger accumulateNumerator = BigInteger.valueOf(1);
			BigInteger accumulateDenominator = BigInteger.valueOf(1);
			for(int j = 0; j < choices.length; j++) {

				if(k == j) {

				}
				else {
					numerator[k] = (x[choices[j]]).negate();
					denominator[k] = (x[choices[k]].subtract(x[choices[j]]));
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


	public static void main(String args[]) {


		// Take the original plaintext and encrypt it using TEA to get the secret

		String fullPlaintext = "0x0FCB45670CABCDEF";
		String encryptionKey = "0xBF6BBBCDEF00F000FEAFAFBFACCDEF01";


		String[] splitKey = keySplit(encryptionKey);
		
		// Convert Secret message to Hex String
		
		String secretMessage = convertStringToHex("SecretId").toUpperCase();
		System.out.println(secretMessage);
		

		//Convert parsed key from hex to bits

		for(int i = 0; i <= splitKey.length-1; i++) {

			splitKey[i] = hexToBits(splitKey[i]); 

		}

		// Encrypt to get ciphertext of hex of original message

		secretMessage = teaEncrypt(secretMessage, splitKey);
		System.out.println(secretMessage);


		// Get rid of 0x in the front of string

		String secret = encryptionKey.substring(2);

		System.out.println("The original secret key is: " + secret);


		// Store BigInteger value of secret in S

		BigInteger S = new BigInteger(secret,16);

		// The secret can be revealed with any out of 10 points, and K = 2 shares can be used to reveal the secret

		int N = 10, K = 2, i = 0, j = 0;

		//		System.out.println(S);


		// Input to choose coordinates
		Scanner scan = new Scanner(System.in);



		// x and y arrays to hold the generated coordinates
		BigInteger[] x = new BigInteger[N];
		BigInteger[] y = new BigInteger[N];

		// Store numerator and denominator for fractions
		BigInteger[] numerator = new BigInteger[K];
		BigInteger[] denominator = new BigInteger[K];

		// Store the choices made
		int[] choices = new int[K];



		// Array to store the coefficients
		BigInteger[] coef = new BigInteger[K-1];

		BigInteger coefint = BigInteger.valueOf(15);

		// Generate the random number for each coefficient
		for(i = 0; i < coef.length; i++) {

			coef[i] = coefint;
			coefint = coefint.add(coefint);
		}

		// Generate the 4 points
		pointGenerator(x,y,coef,S);

		System.out.println("The " + N + " points are: ");
		for(i = 0; i < x.length; i++) {
			System.out.print("(" + x[i] + "," + y[i] + ") ");
		}

		// Select pair of coordinates out of K
		System.out.println("\nType in your choices out of " + K + ": ");
		for(i = 0; i < choices.length; i++) {
			choices[i] = scan.nextInt()-1;
		}

		System.out.println("\nThe selected points are: ");
		for(i = 0; i < choices.length; i++) {
			System.out.print("(" + x[choices[i]] + "," + y[choices[i]] + ") ");
		}

		// perform larange polynomial calculations to get the constant l0 = (x - x1)/(x0 - x1) etc.
		larange(x,choices,numerator,denominator, K);

		System.out.println("\nThe numerator and denominator of Larange are:");
		for(i = 0; i < choices.length; i++) {
			System.out.print("(" + numerator[i] + "," + denominator[i] + ") ");
		}

		// y0*l0 + y1*l1 etc.

		BigInteger result = secretResult(y, choices, numerator, denominator);




		// New key is constructed
		
		String finalResult = result.toString(16);
		finalResult = finalResult.toUpperCase();


		
		finalResult = "0x" + finalResult;
		System.out.println("\nThe result is: " + finalResult);
		
		
		String[] keyCheck = keySplit(finalResult);
		
		for(i = 0; i <= keyCheck.length-1; i++) {
			
						keyCheck[i] = hexToBits(keyCheck[i]); 
			
					}
		
		secretMessage = teaDecrypt(secretMessage, keyCheck);
		
		System.out.println(secretMessage);
		
		secretMessage = secretMessage.substring(2);
		
		secretMessage = convertHexToString(secretMessage);
		
		
		// Original message is checked with the decrypted message 
		
		System.out.println("Secret message with reconstructed Key is: " + secretMessage);
		System.out.println("\n\n" + accessResponse(secretMessage,"SecretId"));



	}

}
