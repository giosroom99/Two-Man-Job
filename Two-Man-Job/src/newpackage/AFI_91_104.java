package newpackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AFI_91_104 {
	
    void  Operator(String baseUrl,String operator1Key,String operator2Key)throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe"); // Set the  path to where the Chrome Webdriver is located 
		WebDriver driver = new ChromeDriver();
    	 
        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);
        
        // Wait
        Thread.sleep(1000); 
     // Then Click to Go to next Screen (LOGIN)
//        driver.findElement(By.xpath("/html/body/a")).click(); 
        driver.findElement(By.id("start")).click();
        
        // Wait
        Thread.sleep(1500); 
        
        char[] k1 = operator1Key.toCharArray();
        char[] k2 = operator2Key.toCharArray();
        
        String str1 ="";
        String str2 ="";
        
        for(int i =0; i<operator1Key.length(); i++) { 	
        	if(operator1Key.length()-1 == i) {
        		str1+= k1[i];
        		str2+= k2[i];
        		// SEND KEYS 
            	driver.findElement(By.id("exampleInputPassword1")).sendKeys(str1);
            	System.out.println(str1+"###########################################################################");
            	driver.findElement(By.id("exampleInputPassword2")).sendKeys(str2); 
            	Thread.sleep(150);  	
        	}
        	else {
        		str1+= k1[i];
        		str2+= k2[i];
        		// SEND KEYS 
            	driver.findElement(By.id("exampleInputPassword1")).sendKeys(str1); 
            	driver.findElement(By.id("exampleInputPassword2")).sendKeys(str2);         	
            	Thread.sleep(150);
        		driver.findElement(By.id("exampleInputPassword1")).clear();
        		driver.findElement(By.id("exampleInputPassword2")).clear();
        	}
    }
     	Thread.sleep(2000);
//     	driver.findElement(By.xpath("//*[@id=\"btn-ops\"]")).click(); 
     	driver.findElement(By.id("login")).click();
     	
     	Thread.sleep(2000);
//     	driver.findElement(By.xpath("/html/body/a")).click(); 
     	driver.findElement(By.id("process")).click();
 	
     	Thread.sleep(2000);
         //close Fire fox
         driver.close();
 }
 
}
