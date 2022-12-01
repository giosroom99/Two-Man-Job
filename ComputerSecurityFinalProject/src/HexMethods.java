//Andrew Krasuski

import java.util.Arrays;

public class HexMethods {

	public static String hexToBits(String input) {
		String newString = "";

		if(input.substring(0,2).equals("0x")) {
			input = input.substring(2);
		}
		
		char[] charArray = input.toCharArray();

		for(int i = charArray.length-1; i >= 0; i--) {

			switch(charArray[i]) {
			case '0':
				newString = "0000" + newString;
				break;
			case '1':
				newString = "0001" + newString;
				break;
			case '2':
				newString = "0010" + newString;
				break;
			case '3':
				newString = "0011" + newString;
				break;
			case '4':
				newString = "0100" + newString;
				break;
			case '5':
				newString = "0101" + newString;
				break;
			case '6':
				newString = "0110" + newString;
				break;
			case '7':
				newString = "0111" + newString;
				break;
			case '8':
				newString = "1000" + newString;
				break;
			case '9':
				newString = "1001" + newString;
				break;
			case 'A':
				newString = "1010" + newString;
				break;
			case 'B':
				newString = "1011" + newString;
				break;
			case 'C':
				newString = "1100" + newString;
				break;
			case 'D':
				newString = "1101" + newString;
				break;
			case 'E':
				newString = "1110" + newString;
				break;
			case 'F':
				newString = "1111" + newString;
				break;
			default:
				System.out.println();


			}
		}

		return newString;

	}
	public static String bitsToHex(String input, Boolean hexSymbolCheck) {

		String newString = "";
		char[] charArray = input.toCharArray();

		String[] stringArray = new String[8];
		Arrays.fill(stringArray,"");

		int j = 0;


		for(int i = 0; i < 32; i++) {
			if(i % 4 == 0 && i != 0) {
				j++;
			}

			stringArray[j] += charArray[i];
		}

		for(int i = stringArray.length-1; i >= 0; i--) {

			switch(stringArray[i]) {
			case "0000":
				newString = "0" + newString;
				break;
			case "0001":
				newString = "1" + newString;
				break;
			case "0010":
				newString = "2" + newString;
				break;
			case "0011":
				newString = "3" + newString;
				break;
			case "0100":
				newString = "4" + newString;
				break;
			case "0101":
				newString = "5" + newString;
				break;
			case "0110":
				newString = "6" + newString;
				break;
			case "0111":
				newString = "7" + newString;
				break;
			case "1000":
				newString = "8" + newString;
				break;
			case "1001":
				newString = "9" + newString;
				break;
			case "1010":
				newString = "A" + newString;
				break;
			case "1011":
				newString = "B" + newString;
				break;
			case "1100":
				newString = "C" + newString;
				break;
			case "1101":
				newString = "D" + newString;
				break;
			case "1110":
				newString = "E" + newString;
				break;
			case "1111":
				newString = "F" + newString;
				break;
			default:
				System.out.println();


			}
		}

		if(hexSymbolCheck == true) {
			newString = "0x" + newString;
		}

		return newString;

	}
}
