package brokenLink;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class brokenLinktest1 {

		public static void main(String[] args) {
			WebDriver driver;
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("https://vnsny.sharepoint.com/sites/VNSHealth");
			//driver.get("https://www.vnshealth.org/");
			
			WebElement uname = driver.findElement(By.xpath("//input[@type='email']"));
			uname.sendKeys("802072@vnshealth.org");
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			WebElement next = driver.findElement(By.id("idSIButton9"));
			next.click();
			WebElement pwd = driver.findElement(By.xpath("//input[@id='i0118']"));
			pwd.sendKeys("myPassword");
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			WebElement signIn = driver.findElement(By.xpath("//input[@type='submit']"));
			signIn.click();
			
			//Storing the links in a list and traversing through the links
			List<WebElement> links = driver.findElements(By.tagName("a"));

			//Print the number of links and the count of links.
			System.out.println("No of links are "+ links.size());

			//checking the links fetched.
			for(int i=0;i<links.size();i++)
			{
				WebElement E1= links.get(i);
				String url= E1.getAttribute("href");
				verifyLinks(url);
			}

			//driver.quit();
		}


		public static void verifyLinks(String linkUrl)
		{
			try
			{
				URL url = new URL(linkUrl);

				// Creating url connection and getting the response code
				HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
				httpURLConnect.setConnectTimeout(5000);
				httpURLConnect.connect();
				if(httpURLConnect.getResponseCode()>=400)
				{
					System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage()+"is a broken link");
				}

				//Fetching and Printing the response code obtained
				else{
					System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage());
				}
			}catch (Exception e) {
			}
		}
	}
