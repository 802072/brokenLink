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

public class brokenLinkTest {
	WebDriver driver;
	String myhomePage = "https://vnsny.sharepoint.com/sites/VNSHealth";
	String myurl = "";
	HttpURLConnection myhuc = null;
	int responseCode = 200;
	public static String status = "passed";

	@BeforeTest
	public void openPage() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(myhomePage);

		WebElement uname = driver.findElement(By.xpath("//input[@type='email']"));
		uname.sendKeys("802072@vnshealth.org");
		WebElement next = driver.findElement(By.id("idSIButton9"));
		next.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement pwd = driver.findElement(By.xpath("//input[@id='i0118']"));
		pwd.sendKeys("myPassword");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WebElement signIn = driver.findElement(By.xpath("//input[@id='idSIButton9']"));
		signIn.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> mylinks = driver.findElements(By.tagName("a"));
		Iterator<WebElement> myit = mylinks.iterator();
		while (myit.hasNext()) {

			myurl = myit.next().getAttribute("href");
			System.out.println(myurl);
			if (myurl == null || myurl.isEmpty()) {
				System.out.println("Empty URL or an Unconfigured URL");
				continue;
			}

			if (!myurl.startsWith(myhomePage)) {
				System.out.println("This URL is from another domain");
				continue;
			}

			try {
				myhuc = (HttpURLConnection) (new URL(myurl).openConnection());
				myhuc.setRequestMethod("HEAD");
				myhuc.connect();
				responseCode = myhuc.getResponseCode();
				System.out.println("The response code is:"+responseCode);
				if (responseCode >= 400) {
					System.out.println(myurl + " This link is broken");
				} else {
					System.out.println(myurl + " This link is valid");
				}

			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}



