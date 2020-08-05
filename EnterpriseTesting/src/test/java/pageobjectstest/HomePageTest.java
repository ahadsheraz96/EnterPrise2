package pageobjectstest;

import base.BrowserDriver;
import base.BrowserDriverReal;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Priority;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobjects.HomePage;

public class HomePageTest extends BrowserDriverReal {
    /*
    all the test cases no elements
    associated with home page shall be written here
    */

    HomePage homePage=null;
    SoftAssert softAssert=new SoftAssert();

    @BeforeMethod
    public void initialiazeElements(){
         homePage = PageFactory.initElements(driver,HomePage.class);


    }
    @Test
    public void aetReceiptTest(){
        homePage.aetReceipt();
    }
    @Test
    public void reservationTest(){
        homePage.reservation();
        Assert.assertEquals(1,1);

    }

    @Test
    public void oneWayRentalTest(){

        homePage.OneWayRental();
    }

    @Test
    public void longTermRental1Test(){
        homePage.longTermRental1();
       // Assert.assertEquals(1,2);
        softAssert.assertEquals(1,2);
       // homePage.BuyFunctionality();
        //softAssert.assertAll();

    }
    @Test (priority=7)
    public void BuyFunctionalityTest(){
        homePage.BuyFunctionality();
    }

    @Test(priority=6)
    public void helpButtonTest(){
        homePage.helpButton();
    }
    @Test
    public void signUpCredentailsTest(){
        homePage.signUpCredentails();
    }
    @Test
    public void emailClickTest(){
        homePage.emailClick();
    }















}
