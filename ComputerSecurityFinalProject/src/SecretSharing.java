import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
import java.security.SecureRandom;

public class SecretSharing extends TeaAlgorithim {

	static int fixedNumber = 0;

	public static boolean accessResponse(int result, int S) {
		
		if(result == S) {
			return true;
		}
		else {
			return false;
		}
	}

	public static int secretResult(int[] yarray, int[] choices, int[] numerator, int[] denominator) {
		int accumulator = 0, total = 0;
		for(int i = 0; i < numerator.length; i++) {

			accumulator = yarray[choices[i]] * numerator[i];
			accumulator /= denominator[i];
			total += accumulator;

			accumulator = 0;

		}
		return total;
	}
	public static void larange(int[] xarray, int[] choices, int[] numerator, int[] denominator, int K) {




		if(K == 2) {

			for(int k = 0; k < choices.length; k++) {
				if(k % 2 == 0 && k == choices.length-1) {
					numerator[k] = 0;
					denominator[k] = 1;
				}
				else if(k % 2 == 0) {
					numerator[k] = -xarray[choices[k+1]];
					denominator[k] = xarray[choices[k]] - xarray[choices[k+1]];
				}
				else {
					numerator[k] = -xarray[choices[k-1]];
					denominator[k] = xarray[choices[k]] - xarray[choices[k-1]];
				}
			}
		}
		else if(K >= 3) {
			for(int k = 0; k < choices.length; k++) {

				int accumulateNumerator = 1;
				int accumulateDenominator = 1;
				for(int j = 0; j < choices.length; j++) {

					if(k == j) {

					}
					else {
						numerator[k] = (-xarray[choices[j]]);
						denominator[k] = (xarray[choices[k]] - xarray[choices[j]]);
						accumulateNumerator *= numerator[k];
						accumulateDenominator *= denominator[k];

					}

				}
				numerator[k] = accumulateNumerator;
				denominator[k] = accumulateDenominator;


			}

			//				if(k % 2 == 0 && k == choices.length-1) {
			//					numerator[k] = (-xarray[choices[0]]) * (-xarray[choices[1]]);
			//					denominator[k] = (xarray[choices[2]] - xarray[choices[0]]) * (xarray[choices[2]] - xarray[choices[1]]);
			//				}
			//				else if(k % 2 == 1) {
			//					numerator[k] = (-xarray[choices[k-1]]) * (-xarray[choices[2]]);
			//					denominator[k] = (xarray[choices[k]] - xarray[choices[k-1]]) * (xarray[choices[k]] - xarray[choices[2]]);
			//				}
			//				else {
			//					numerator[k] = (-xarray[choices[k+1]]) * (-xarray[choices[2]]);
			//					denominator[k] = (xarray[choices[k]] - xarray[choices[k+1]]) * (xarray[choices[k]] - xarray[choices[2]]);
			//				}
			//			}

		}


	}


	public static int randomNum() {
		int randomInt = 0;
		Random rand = new Random();


		fixedNumber += 50;



		return fixedNumber;
	}


	public static void pointGenerator(int[] xarray, int[] yarray, int[] coefficientArray, int secret) {
		int x = 1, accumulator = 0, total = 0;

		for(int i = 0; i < xarray.length; ++i) {
			xarray[i] = x;

			for(int j = 0; j < coefficientArray.length; j++) {
				accumulator = (int) Math.pow(x, j+1);
				accumulator *= coefficientArray[j];

				total += accumulator;
			}


			total += secret;
			yarray[i] = total;

			accumulator = 0;
			total = 0;
			x++;
		}

	}


	public static void main(String args[]) {

		SecureRandom secureRan = new SecureRandom();

		// Length of string for Secret
		int stringLength = 0;
		// secureRan.nextInt();


		// Initialize S, N, and K
		int S = 5, N = 7, K = 3, i = 0, j = 0, randomNum = 0;


		String stringS;

		System.out.println(S);

		stringS = String.valueOf(S);

		char[] minusCheck = stringS.toCharArray();
		boolean minusBool = false;

		if(minusCheck[0] == '-') {
			stringS = stringS.replace("-", "");
			minusBool = true;
		}



		//Padding

		int zeroRemove = 0;

		if(stringS.length() < 16) {
			while(stringS.length() < 16) {
				stringS += "0";
				zeroRemove++;
			}
		}

		// Turn back the string Secret to the integer Secret

		//		System.out.println(stringS);
		//		System.out.println(stringS.length());


		//		String zeroes = "";
		//		
		//		for(i = 0; i < zeroRemove; i++) {
		//			zeroes += "0";
		//		}
		//		stringS = stringS.replace(zeroes, "");
		//		if(minusBool) {
		//			stringS = "-" + stringS;
		//		}
		//		
		//		System.out.println(stringS);





		Scanner scan = new Scanner(System.in);

		Random rand = new Random();

		// x and y arrays to hold the generated coordinates
		int[] x = new int[N];
		int[] y = new int[N];

		// Store numerator and denominator for fractions
		int[] numerator = new int[K];
		int[] denominator = new int[K];

		int[] choices = new int[K];



		// Array to store the coefficients
		int[] coef = new int[K-1];

		int coefint = 15;
		// Generate the random number for each coefficient
		for(i = 0; i < coef.length; i++) {

			coef[i] = coefint;
			coefint += coefint;
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

		larange(x,choices,numerator,denominator, K);

		System.out.println("\nThe numerator and denominator of Larange are:");
		for(i = 0; i < choices.length; i++) {
			System.out.print("(" + numerator[i] + "," + denominator[i] + ") ");
		}


		int result = secretResult(y, choices, numerator, denominator);

		
		System.out.println("\n" + accessResponse(result, S));
		
		


		System.out.println("\nThe result is: " + result);



		//Tea encryption to get the key from Secret
		String fullKey = "0xBF6BBBCDEF00F000FEAFAFBFACCDEF01";


		//Obtain parsed key
		String[] key = keySplit(fullKey);

		//Convert parsed key from hex to bits

		for(i = 0; i <= key.length-1; i++) {

			key[i] = hexToBits(key[i]); 

		}

		String ciphertext = "";

		ciphertext = teaEncrypt(stringS, key);
		teaDecrypt(ciphertext, key);










	}

}
