package reporter;


import base.BrowserDriverReal;
import com.relevantcodes.extentreports.ExtentReports;
import org.testng.ITestContext;
import org.testng.Reporter;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class ExtentManager {
    private static ExtentReports extentReports;
    private static ITestContext context;



    private ExtentManager() {
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            init();
        }
        return extentReports;
    }

    /**
     * set up output directory of the test output
     * and add additional system info to the report
     */
    private synchronized static void init() {
        String reportPath = Paths.get("./reports").toAbsolutePath().toString();


        File outputDirectory = new File(reportPath);
        File resultDirectory = new File(outputDirectory.getParentFile(), "html");

        String outputPath = reportPath + File.pathSeparator + File.pathSeparator + "ExtentReport" +
                ".html";
        extentReports = new ExtentReports(outputPath, true);
        Reporter.log("Extent Report Directory" + resultDirectory, true);

        extentReports.addSystemInfo("Host Name", "Tester")
                .addSystemInfo("Environment", "QA")
                .addSystemInfo("User Name", "Team_Three");

        URL resource = BrowserDriverReal.class.getClassLoader().getResource(("report-config.xml"));

        if (resource == null){
            throw  new IllegalArgumentException("report-config.xml must be located in resources ");
        }
        try {
            extentReports. loadConfig(BrowserDriverReal.class.getClassLoader().getResource(("report-config.xml")));
        } catch (Exception e) {

            System.err.println(BrowserDriverReal.class.getClassLoader().getResource(("report-config.xml")));
        }


    }

    public static void setOutputDirectory(ITestContext context) {
        ExtentManager.context = context;

    }
}