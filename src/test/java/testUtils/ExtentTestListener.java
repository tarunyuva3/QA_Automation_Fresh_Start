package testUtils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // ✅ Fetches the description you added to your @Test annotations
        String description = result.getMethod().getDescription();
        String testName = (description != null && !description.isEmpty()) ? description : result.getMethod().getMethodName();
        
        ExtentReportManager.startTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass("✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();

        test.fail("❌ Test Failed");
        test.fail(result.getThrowable());

        try {
            String base64 = ((base.BaseTest) result.getInstance()).captureBase64Screenshot();

            if (base64 != null) {
                test.addScreenCaptureFromBase64String(base64, "Failure Screenshot");
            }
        } catch (Exception e) {
            test.warning("⚠️ Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("⚠️ Test Skipped");
        ExtentReportManager.getTest().skip(result.getThrowable());
    }

    // ✅ CRITICAL ADDITION: Triggers the HTML file generation when the suite ends
    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
    }
}