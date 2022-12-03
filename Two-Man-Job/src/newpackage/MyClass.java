
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
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//comment the above line and uncomment below line to use Chrome
import org.openqa.selenium.chrome.ChromeDriver;
public class MyClass {


    public static void main(String[] args)throws Exception  {
    	 // URL to the Website to interact with using Selenium
    	String baseUrl = "http://127.0.0.1:5500/AFI_91-104.html";
    	
    	String[] path= {"giovanniKEY.txt","andrewKEY.txt"};
    	System.out.println("Giovanni's KEy  "+KeyReader(path)[0]);
    	System.out.println("Andrew's KEy  "+KeyReader(path)[1]);
    	
    	
    	 

        }

       // This Method interacts with the HTMLS Object. Utilize Selenium with Google Chrome WebDrive
       void  Operator(String baseUrl,String operator1Key,String operator2Key)throws InterruptedException {

    	   
    	   
    	   
   		System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe"); // Set the  path to where the Chrome Webdriver is located 
   		WebDriver driver = new ChromeDriver();
       	 
           // launch Fire fox and direct it to the Base URL
           driver.get(baseUrl);
    
           String key ="nscnsnjdncn2";
           char[] k = key.toCharArray();
           
           String str ="";
          
           
           for(int i =0; i<key.length(); i++) {
           	
           	if(key.length()-1 == i) {
           		str+= k[i];
               	driver.findElement(By.id("exampleInputPassword1")).sendKeys(str); 
               	driver.findElement(By.id("exampleInputPassword2")).sendKeys(str); 
               	Thread.sleep(400);
               	
           	}
           	else {
           		str+= k[i];
               	driver.findElement(By.id("exampleInputPassword1")).sendKeys(str); 
               	driver.findElement(By.id("exampleInputPassword2")).sendKeys(str);         	
               	Thread.sleep(400);
           		driver.findElement(By.id("exampleInputPassword1")).clear();
           		driver.findElement(By.id("exampleInputPassword2")).clear();
           	}
        	Thread.sleep(400);
           	
        	driver.findElement(By.xpath("//*[@id=\"btn-ops\"]")).click(); 
        	
        	Thread.sleep(400);
            //close Fire fox
            driver.close();
       }
       
    }
    
       static String[]  KeyReader(String[] path) {
    	   String[] listOfKey = new String[2];
    	   
    	   for(int i=0; i< path.length;i++) {
    		   String filename = path[i];
    		   try {
    			   File Obj = new File(filename);
    			   Scanner reader =  new Scanner(Obj);
    			   while(reader.hasNextLine()) {
    				   String share =  reader.nextLine(); 
//    				   System.out.println(share.length());
    				   listOfKey[i] = share;
    			   }
    			   reader.close();
    		   } 
    		   catch(FileNotFoundException e) {
    			   System.out.println("KEY DOES NOT EXIST ");
    			   e.printStackTrace();
    		   }
    		   
  
    	   }
    	   
    	   
		return listOfKey;
    	   }
    
    
    
    

}