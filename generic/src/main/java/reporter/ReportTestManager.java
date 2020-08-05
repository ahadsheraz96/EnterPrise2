package reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ReportTestManager {
    private static Map<Integer, ExtentTest> map = new HashMap<>();
    private static ExtentReports report = ExtentManager.getInstance();
    private static ExtentReports updateReport = ExtentManager.getInstance();

    public static synchronized ExtentTest getTest() {
        return map.get((int) Thread.currentThread().getId());
    }

    public static synchronized void endTest() {
        report.endTest(map.get((int) Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTestClass(String className, String testName) {
        return startTest(className, testName, "");
    }
    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = report.startTest(testName, desc);
        map.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest startTest(String className, String testName, String desc) {
        ExtentTest test = updateReport.startTest(testName, desc);
        map.put((int) Thread.currentThread().getId(), test);
        return test;
    }

}