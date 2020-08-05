package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class BrowserDriver {




  public static WebDriver   driver=null;
  public static String os=System.getProperty("os","windows");

    @Parameters({"url"})
    @BeforeMethod
    public void setUp(@Optional("http://www.google.com")String url){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\ahads\\IdeaProjects\\EnterPrise2\\generic\\drivers\\chromedriver.exe");
        driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.get("https://www.enterprise.com/en/home.html");
    }




    @AfterMethod
    public void closeUp(){
        driver.quit();
    }


}


