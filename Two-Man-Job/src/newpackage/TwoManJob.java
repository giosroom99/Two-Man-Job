/**
 * 
 */
package newpackage;

import java.math.BigInteger;

/**
 * 
 *
 */
public class TwoManJob {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	



	
	public static void main(String[] args) throws InterruptedException {
		
		AFI_91_104 webOperator = new AFI_91_104();
		String baseURL= "http://127.0.0.1:5500/start.html";
		
		// TODO Auto-generated method stub
		fileReader USB = new fileReader();
		
		// Object of the Secret Sharing Class 
		SecretSharing sss = new SecretSharing();
		TeaAlgorithim tea = new TeaAlgorithim();
		HexMethods  hex = new HexMethods();
		
		
		String[] path= {"giovanniKEY.txt","andrewKEY.txt"};
		
		// Take the original plaintext and encrypt it using TEA to get the secret
		String encryptionKey = "0xBF6BBBCDEF00F000FEAFAFBFACCDEF01";
		String plaintext = "Milliard";
		System.out.println("Plaintext: "+"'"  +plaintext+"'"+  " Using Key: "+encryptionKey);
		
		
		String secretMessage = sss.convertStringToHex(plaintext).toUpperCase();
		
		
		String[] splitKey = tea.keySplit(encryptionKey);
		
		
		//Convert parsed key from hex to bits
				for(int i = 0; i <= splitKey.length-1; i++) {
					splitKey[i] = hex.hexToBits(splitKey[i]); 

				}
		
		// Encrypt to get ciphertext of hex of original message
		secretMessage = tea.teaEncrypt(secretMessage, splitKey);
		System.out.println("\nEncrypyted Plaintext: "+secretMessage);
		System.out.println();
		
		// Get rid of 0x in the front of string
		String secret = encryptionKey.substring(2);
		System.out.println("Apply SSS on " + secret );
		System.out.println();
		
		// Store BigInteger value of secret in S
		BigInteger S = new BigInteger(secret,16);

		// The secret can be revealed with any out of 10 points, and K = 2 shares can be used to reveal the secret

		int N = 10, K = 2, i = 0, j = 0;
		
		// x and y arrays to hold the generated coordinates
		BigInteger[] x = new BigInteger[N];
		BigInteger[] y = new BigInteger[N];
		
		// Store numerator and denominator for fractions
		BigInteger[] numerator = new BigInteger[K];
		BigInteger[] denominator = new BigInteger[K];
		
		// Array to store the coefficients
		BigInteger[] coef = new BigInteger[K-1];

		BigInteger coefint = BigInteger.valueOf(15);

		// Generate the random number for each coefficient
		for(i = 0; i < coef.length; i++) {

			coef[i] = coefint;
			coefint = coefint.add(coefint);
		}

		// Generate the 4 points
		sss.pointGenerator(x,y,coef,S);
		
		BigInteger[] bigX = new BigInteger[2];
		
		bigX[0] = BigInteger.valueOf(1);
		bigX[1] = BigInteger.valueOf(3);
		
	

		// perform larange polynomial calculations to get the constant l0 = (x - x1)/(x0 - x1) etc.
		sss.larange(bigX,numerator,denominator, K);

		
		// y0*l0 + y1*l1 etc.
		
		BigInteger[] bigY = new BigInteger[2];
				
		bigY[0] = new BigInteger(USB.KeyReader(path)[0]);
		bigY[1] = new BigInteger(USB.KeyReader(path)[1]);


		BigInteger result = sss.secretResult(bigY, numerator, denominator);
		

		// New key is constructed		
		String finalResult = result.toString(16);
		finalResult = finalResult.toUpperCase();

		finalResult = "0x" + finalResult;
		
		String[] keyCheck = tea.keySplit(finalResult);
		
		for(i = 0; i <= keyCheck.length-1; i++) {
			
						keyCheck[i] = hex.hexToBits(keyCheck[i]); 
								}
		
		secretMessage = tea.teaDecrypt(secretMessage, keyCheck);
				
		secretMessage = secretMessage.substring(2);
		
		secretMessage = sss.convertHexToString(secretMessage);
		
		System.out.println("The " + N + " points after applying Secret sharing on "+ encryptionKey +" are: \n");
		for(i = 0; i < x.length; i++) {
          System.out.println("(" + x[i] + "," + y[i] + ") ");
			}		

		System.out.println("\nReconstruct Key using 2 Shares (1,3): " + secretMessage);
		System.out.println("\n\n" + sss.accessResponse(secretMessage,plaintext));
		
		
		if(sss.accessResponse(secretMessage,plaintext)) {
			
			webOperator.Operator(baseURL,USB.KeyReader(path)[0],USB.KeyReader(path)[1]);
			
		}
		else {
			System.out.println("ACCESS DENIED");
		}


	}

}
