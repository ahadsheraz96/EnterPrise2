package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import javafx.scene.shape.Path;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporter.ExtentManager;
import reporter.ReportTestManager;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BrowserDriverReal {
    public static WebDriver driver = null;
    public static final String browserstack_username = System.getProperty("bsUName");
    public static final String browserstack_automateKey = System.getProperty("bsSecretKey");
    public static final String saucelabs_username = null;
    public static final String saucelabs_accesskey = null;
    private static ExtentReports extent;
    private static File screenShotFolder = Paths.get(System.getProperty("user.dir"), "screenshots").toFile();

    /**
     * set up extent reports
     *
     * @param context
     * @throws IOException
     */
    @BeforeSuite
    public void reportSetUp(ITestContext context) throws IOException {

        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
       screenShotFolder.mkdir();
        clearOldScreenShot(screenShotFolder);

    }

    private void clearOldScreenShot(File screenshotFolder) throws IOException {

        if (screenshotFolder.exists()) {
            FileUtils.cleanDirectory(screenshotFolder);
        }
    }


    @Parameters({"useCloudEnv", "cloudEnvName", "os", "os_version", "browserName", "browserVersion", "url"})
    @BeforeTest
    public void setUp(@Optional("false") boolean useCloudEnv, @Optional("false") String cloudEnvName,
                      @Optional("windows") String os, @Optional("10") String os_version, @Optional("firefox") String browserName, @Optional("34")
                              String browserVersion, @Optional("https://www.google.com") String url) throws IOException {


        if (useCloudEnv) {
            if (cloudEnvName.equalsIgnoreCase("browserstack")) {
                getCloudDriver(cloudEnvName, browserstack_username, browserstack_automateKey, os, os_version, browserName, browserVersion);
            } else if (cloudEnvName.equalsIgnoreCase("saucelabs")) {
                getCloudDriver(cloudEnvName, saucelabs_username, saucelabs_accesskey, os, os_version, browserName, browserVersion);
            }
        } else {
            getLocalDriver(browserName, os);
        }

        //getLocalDriver(browserName, os);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); // 20
        driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS); //35
        //driver.manage().window().maximize();
        driver.get(url);

    }
    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        ReportTestManager.startTest(method.getName());
        ReportTestManager.getTest().assignCategory(className);
    }

    public WebDriver getLocalDriver(String browserName, String os) {

        // if (browserName == "Chrome")
        if (browserName.equalsIgnoreCase("chrome")) {

            /**
             * Command Line Arguments
             * https://peter.sh/experiments/chromium-command-line-switches/
             * */

            ChromeOptions options = new ChromeOptions();
            // options.setHeadless(true);
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--incognito");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\ahads\\IdeaProjects\\EnterPrise2\\generic\\drivers\\chromedriver.exe");
                driver = new ChromeDriver(options);
                //  TestLogger.log("Chrome Browser Launched");
            } else if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\ahads\\IdeaProjects\\EnterPrise2\\generic\\drivers\\chromedriver.exe");
                driver = new ChromeDriver(options);
                // TestLogger.log("Chrome Browser Launched");
            }
        } else if (browserName.equalsIgnoreCase("firefox")) {
            /**
             * https://chercher.tech/java/chrome-firefox-options-selenium-webdriver
             *
             * */
            FirefoxOptions options = new FirefoxOptions();
            //options.setHeadless(true);
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--private");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);


            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\ahads\\IdeaProjects\\EnterPrise2\\generic\\drivers\\geckodriver.exe");
                driver = new FirefoxDriver(options);
            } else if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\ahads\\IdeaProjects\\EnterPrise2\\generic\\drivers\\geckodriver.exe");
                driver = new FirefoxDriver(options);
            }
        }

        return driver;
    }

    public WebDriver getCloudDriver(String envName, String envUsername, String envAccessKey, String os, String os_version, String browserName,
                                    String browserVersion) throws IOException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", browserName);
        caps.setCapability("browser_version", browserVersion);
        caps.setCapability("os", os);
        caps.setCapability("os_version", os_version);

        // WebDriver driver = new RemoteWebDriver(new URL(URL), caps);

        if (envName.equalsIgnoreCase("Saucelabs")) {
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + envAccessKey + "@ondemand.saucelabs.com:80/wd/hub"), caps);

        } else if (envName.equalsIgnoreCase("Browserstack")) {

            caps.setCapability("resolution", "1024x768");
            //  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
            driver = new RemoteWebDriver(new URL("https://" + envUsername + ":" + envAccessKey + "@hub-cloud.browserstack.com/wd/hub"), caps);
        }
        return driver;
    }

    @AfterSuite
    public void generateReport() {
        extent.flush();
        extent.close();
    }

    private Date getTime(long startMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startMillis);
        return calendar.getTime();
    }

    private static String generateScreenShotName(String screenshotName, DateFormat formatter) {
        String name;
        name = screenshotName + "_" + formatter.format(new Date()) + "_.png";
        return name;
    }

    public static String takeScreenShot(WebDriver driver, String screenshotName) {
        DateFormat formatter = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
        String name = null;
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            name = generateScreenShotName(screenshotName, formatter);

            FileUtils.copyFile(file, Paths.get(screenShotFolder.getAbsolutePath(),name).toFile());


        } catch (IOException e) {
            System.err.println("Exception while taking screenshot " + e.getMessage());
        }

        return name;
    }

    @AfterMethod
    public void closeOut(ITestResult result) {
        ReportTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ReportTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups()) {
            ReportTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            ReportTestManager.getTest().log(LogStatus.PASS, "Test Passed");

        }
        if (result.getStatus() == ITestResult.FAILURE) {


            String shotName = takeScreenShot(driver, result.getName());
            // output exception info to the report
            ReportTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));

            // add screenCapture to report when test failed
            shotName = shotName.replace('\\', '/');
            System.out.println("shotName = " + shotName);

            ReportTestManager.getTest().log(LogStatus.FAIL,
                    ReportTestManager.getTest().addScreenCapture("../screenshots"+"/"+shotName));


        }
        if (result.getStatus() == ITestResult.SKIP) {
            ReportTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ReportTestManager.endTest();
        extent.flush();
        driver.manage().deleteAllCookies();

    }
   @AfterTest
   public void  teardown(){
       driver.quit();
   }
    protected String getStackTrace(Throwable t) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return "<br/>" + sw.toString().replace(System.lineSeparator(), "<br/>");
    }


}
