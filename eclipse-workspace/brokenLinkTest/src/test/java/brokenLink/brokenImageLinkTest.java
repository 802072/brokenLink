package brokenLink;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.HttpClient;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class brokenImageLinkTest {

	
	public static void main(String[] args) {

		WebDriver driver;
		String myhomePage = "https://www.vnshealth.org/";
		String myurl = "";
		HttpURLConnection myhuc = null;
		int responseCode = 200;
		String status = "passed";
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(myhomePage);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Integer iBrokenImageCount = 0;

		try
		{
			iBrokenImageCount = 0;
			List<WebElement> image_list = driver.findElements(By.tagName("img"));
			
			/* Print the total number of images on the page */
			System.out.println("The page under test has " + image_list.size() + " images");
			for (WebElement img : image_list)
			{
				if (img != null)
				{
					if (img.getAttribute("naturalWidth").equals("0"))
					{
						System.out.println(img.getAttribute("outerHTML") + " is broken.");
						iBrokenImageCount++;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			status = "failed";
			System.out.println(e.getMessage());
		}
		status = "passed";
		System.out.println(myhomePage + " has " + iBrokenImageCount + " broken images");
	}

	
}



