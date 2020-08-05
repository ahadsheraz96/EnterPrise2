package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage {
    /*
    All the locators should be here
    * */

      /**  driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
        driver.get("https://www.enterprise.com/en/home.html");
        driver.findElement(By.xpath("//*[@id='primary-nav']/ul[2]/li[1]/div/div[1]")).click();
        driver.findElement(By.cssSelector("#pickupLocationTextBox")).sendKeys("11003");
        driver.findElement(By.xpath(" //*[@class='location-input location-field selected']")).sendKeys("Hello");
        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[1]/section/div/div/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div/div/div/ul/li[1]/small")).click();
*/
        @FindBy(how= How.XPATH,using = "//*[@id='primary-nav']/ul[2]/li[1]/div/div[1]")
        WebElement reservation;

        @FindBy(how = How.CSS,using = "#navContentRent > div.menu.cols-4 > div:nth-child(1) > ul > li:nth-child(4) > a")
        WebElement receipt;
        @FindBy(how = How.CSS, using="#navContentRent > div.menu.cols-4 > div:nth-child(1) > ul > li:nth-child(5) > a")
        WebElement oneWayRental;

        @FindBy(how = How.CSS,using = "#navContentRent > div.menu.cols-4 > div:nth-child(1) > ul > li:nth-child(6) > a")
        WebElement LongTermRental;
        @FindBy(how = How.CSS,using = "#primary-nav > ul.primary-nav.active-cols-6 > li:nth-child(2) > div > div.primary-nav-label")
        WebElement Buy;
        @FindBy(how = How.XPATH,using = "//*[@id=\"navContentBuy\"]/div[1]/div[1]/ul/li[1]/a")
        WebElement BrowserSelection;
        @FindBy(how = How.XPATH,using="//div[@class='help']")
        WebElement Help;
        @FindBy(how=How.ID,using = "signInJoinButton")
        WebElement signIn;
        @FindBy(how = How.XPATH,using = "//*[@id=\"utility-eplus-email\"]")
       WebElement emailCredentions;
        @FindBy(how = How.ID,using = "utility-eplus-password")
       WebElement password;



    public void aetReceipt(){
        reservation.click();
        receipt.click();
    }


        public void reservation()

        {
            reservation.click();
            //LongTermRental.click();

        }

        public void OneWayRental(){
            reservation.click();
            oneWayRental.click();
        }
        public void longTermRental1(){
            reservation.click();
            LongTermRental.click();
        }
        public void BuyFunctionality(){
            Buy.click();
            BrowserSelection.click();
        }
        public void SelectionBrowser(){
            Buy.click();
            BrowserSelection.click();
        }
        public void helpButton(){
        Help.click();
        }
        public void signUpCredentails(){
        signIn.click();

        }
        public void emailClick(){
            signIn.click();
        emailCredentions.sendKeys("ahadsheraz96");
        password.sendKeys("1234567895214");
        }


}
