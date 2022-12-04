package newpackage;

//Andrew Krasuski

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class TeaAlgorithim extends HexMethods {


	public static final String DELTA = "0x9E3779B9";
	public static final String HEX = "0x";

	public static String hexSymbolRemover(String t) {
		//Gets rid of 0x, returns string after 0x
		t = t.substring(2);
		return t;
	}
	public static String binaryAddition(String s1, String s2) {
		String result = "";
		int carryBit = 0;
		//If lengths are not equal, pad the left of the shorter one with 0s until they are equal in length
		if(s1.length() != s2.length()) {
			if(s1.length() > s2.length()) {
				while(s1.length() != s2.length()) {
					s2 = "0" + s2;
				}
			}
			else if(s1.length() < s2.length()) {
				while(s1.length() != s2.length()) {
					s1 = "0" + s1;
				}
			}

		}

		char[] sC1 = s1.toCharArray();
		char[] sC2 = s2.toCharArray();
		
		//Binary addition
		for(int i = sC1.length-1; i >= 0; i--) {
			if(sC1[i] == '1') {
				if(sC1[i] == sC2[i]) {
					if(carryBit == 0) {
						result = "0" + result;
						carryBit = 1;
					}
					else {
						result = "1" + result;
						carryBit = 1;
					}
				}
				else {
					if(carryBit == 0) {
						result = "1" + result;
						carryBit = 0;
					}
					else {
						result = "0" + result;
						carryBit = 1;
					}
				}
			}
			if(sC1[i] == '0') {
				if(sC1[i] == sC2[i]) {
					if(carryBit == 0) {
						result = "0" + result;
						carryBit = 0;
					}
					else {
						result = "1" + result;
						carryBit = 0;
					}
				}
				else {
					if(carryBit == 0) {
						result = "1" + result;
						carryBit = 0;
					}
					else {
						result = "0" + result;
						carryBit = 1;
					}
				}
			}
		}
		return result;
	}
	public static String xor(String s1, String s2) {
		String result = "";
		
		//If lengths are not equal, pad the left of the shorter one with 0s until they are equal in length
		if(s1.length() != s2.length()) {
			if(s1.length() > s2.length()) {
				while(s1.length() != s2.length()) {
					s2 = "0" + s2;
				}
			}
			else if(s1.length() < s2.length()) {
				while(s1.length() != s2.length()) {
					s1 = "0" + s1;				}
			}
		}
		char[] sC1 = s1.toCharArray();
		char[] sC2 = s2.toCharArray();
		//Xor
		for(int i = sC1.length-1; i >= 0; i--) {

			if(sC1[i] == '1') {

				if(sC1[i] == sC2[i]) {
					result = "0" + result;
				}
				else {
					result = "1" + result;
				}
			}
			if(sC1[i] == '0') {
				if(sC1[i] == sC2[i]) {
					result = "0" + result;
				}
				else {
					result = "1" + result;
				}
			}
		}
	return result;
	}
	public static String shift(String bits, String direction, int numberOfTimes){
		String newString = "";
		char[] bitArray = bits.toCharArray();
		if(direction.equals("left")) {
			for(int i = 1; i <= numberOfTimes; i++) {
				for(int j = 0; j <= bitArray.length-2; j++) {
					bitArray[j] = bitArray[j+1];
				}
				bitArray[bitArray.length-1] = '0';
			}
		}
		else if(direction.equals("right")) {
			for(int f = 1; f <= numberOfTimes; f++) {
				for(int q = bitArray.length-1; q >= 1; q--) {
					bitArray[q] = bitArray[q-1];
				}
				bitArray[0] = '0';
			}
		}

		for(int k = 0; k <= bitArray.length-1; k++) {
			newString += bitArray[k];
		}

		return newString;
	}

	public static String negation(String bits) {

		String newString = "";
		String binaryOne = "1";

		char[] bitArray = bits.toCharArray();

		for(int i = 0; i <= bitArray.length-1; i++) {
			if(bitArray[i] == '0') {
				newString += "1";
			}
			else if(bitArray[i] == '1') {
				newString += "0";
			}
		}
		newString = binaryAddition(newString, binaryOne);
		return newString;	
		}

	public static String[] keySplit(String key){

		key = hexSymbolRemover(key);
		char kArray[] = key.toCharArray();
		String parseArray[] = new String[4];
		Arrays.fill(parseArray,"");
		int j = 0;
		for(int i = 0; i < 32; i++) {
			if(i % 8 == 0 && i != 0) {
				j++;
			}
			parseArray[j] += kArray[i];
		}
		return parseArray;
	}
	public static String teaEncrypt(String plaintext,String[] key){
		String ciphertext = "";
		String delta = "";
		String sum = "";
		String l = "",r = "";
		//plaintext = hexSymbolRemover(plaintext);
		char[] plainArray = plaintext.toCharArray();
		boolean lrSwitch = false;
		//Split plaintext into left and right blocks
		for(int o = 0; o <= 15; o++) {
			if(o % 8 == 0 && o != 0) {
				lrSwitch = true;
			}
			if(lrSwitch == false) {
				l += plainArray[o]; 
			}
			else {
				r += plainArray[o]; 
			}

		}
		//Convert plaintext hex to bits
		l = hexToBits(l);
		r = hexToBits(r);
		//Convert delta to bits
		delta = hexToBits(DELTA);

		String sumOne = "";
		String sumTwo = "";

		//Round Function
		for(int i = 1; i <= 32; i++) {			
			// sum = sum + delta
			sum = binaryAddition(sum,delta);

			//L = L + ((( R << 4) + K[0]) xor (R + sum) xor ((R >> 5) + K[1]))
			sumOne = xor(binaryAddition(shift(r, "left", 4), key[0]), binaryAddition(r,sum)); 
			sumTwo = binaryAddition(shift(r, "right", 5), key[1]);
			sumOne = xor(sumOne,sumTwo);
			l = binaryAddition(l, sumOne);

			//R = R + (((L << 4) + K[2]) xor (L + sum) xor ((L >> 5) + K[3]))
			sumOne = xor(binaryAddition(shift(l, "left", 4), key[2]), binaryAddition(l,sum)); 
			sumTwo = binaryAddition(shift(l, "right", 5), key[3]);
			sumOne = xor(sumOne,sumTwo);
			r = binaryAddition(r, sumOne);
		}
		ciphertext = bitsToHex(l, true);
		ciphertext += bitsToHex(r, false);

		return ciphertext;
	}
	public static String teaDecrypt(String ciphertext, String[] key){
		String plaintext = "";

		String delta = "";
		String sum = "";

		String l = "",r = "";
		ciphertext = hexSymbolRemover(ciphertext);
		char[] cipherArray = ciphertext.toCharArray();
		boolean lrSwitch = false;

		//Split plaintext into left and right blocks

		for(int o = 0; o <= 15; o++) {
			if(o % 8 == 0 && o != 0) {

				lrSwitch = true;
			}

			if(lrSwitch == false) {
				l += cipherArray[o]; 
			}
			else {
				r += cipherArray[o]; 
			}

		}

		//Convert plaintext hex to bits

		l = hexToBits(l);
		r = hexToBits(r);

		//Convert delta to bits

		delta = hexToBits(DELTA);

		sum = shift(delta, "left", 5);

		String sumOne = "";
		String sumTwo = "";

		//Round Function
		for(int i = 1; i <= 32; i++) {	
			
			//R = R - (((L << 4) + K[2]) xor (L + sum) xor ((L >> 5) + K[3]))
			sumOne = xor(binaryAddition(shift(l, "left", 4), key[2]), binaryAddition(l,sum)); 
			sumTwo = binaryAddition(shift(l, "right", 5), key[3]);
			sumOne = xor(sumOne,sumTwo);
			r = binaryAddition(r, negation(sumOne));

			//L = L - (((R << 4) + K[0]) xor (R + sum) xor ((R >> 5) + K[1]))
			sumOne = xor(binaryAddition(shift(r, "left", 4), key[0]), binaryAddition(r,sum)); 
			sumTwo = binaryAddition(shift(r, "right", 5), key[1]);
			sumOne = xor(sumOne,sumTwo);
			l = binaryAddition(l, negation(sumOne));

			// sum = sum + delta
			sum = binaryAddition(sum,negation(delta));
		}


		plaintext = bitsToHex(l, true);
		plaintext += bitsToHex(r, false);



//		System.out.println("Plaintext:  " + plaintext);



		return plaintext;
	}

	public static boolean isDigit(char c){
	    return '0' <= c && c <= '9';
	  }



}