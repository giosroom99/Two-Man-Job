package newpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileReader {
	
    static String[]  KeyReader(String[] path) {
 	   String[] listOfKeys = new String[2];
 	   
 	   for(int i=0; i< path.length;i++) {
 		   String filename = path[i];
 		   try {
 			   File Obj = new File(filename);
 			   Scanner reader =  new Scanner(Obj);
 			   while(reader.hasNextLine()) {
 				   String share =  reader.nextLine(); 
// 				   System.out.println(share.length());
 				   listOfKeys[i] = share;
 			   }
 			   reader.close();
 		   } 
 		   catch(FileNotFoundException e) {
 			   System.out.println("KEY DOES NOT EXIST ");
 			   e.printStackTrace();
 		   }
 		   
 	   }

		return listOfKeys;
 	   }
 
 
 

}
