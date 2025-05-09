package org.ivn;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.ivn.android.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;

public class AndroidBaseTest {

    AppiumDriverLocalService service;
    AndroidDriver driver;
    LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void setUP(){
        service = new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("pixel3");
        driver = new AndroidDriver(service.getUrl(),options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
     try{
         if(driver !=null){
             driver.quit();
         }
     } finally {
         if (service !=null && service.isRunning()){
             service.stop();
         }
     }
    }

    @Test
    public void testInvalidCredentials(){
        driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
        System.out.println("App initiate");
        loginPage = new LoginPage(driver);
        loginPage.login("hola","mundo");
        Assert.assertEquals(loginPage.validateErrorMsg(),
                "Username and password do not match any user in this service.");
    }
    @Test
    public void lof() throws InterruptedException {
        driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
        System.out.println("App initiate2");
        loginPage = new LoginPage(driver);
        loginPage.login("standard_user","secret_sauce");
        driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc='test-Item'])[1]/android.view.ViewGroup")).click();
        loginPage.swipeRightWithDuration();
        Thread.sleep(Duration.ofSeconds(3));
    }

    @Test(dataProvider ="getData",groups = "smoke")
    public void navegateToBrowser(String usr_name, String pwd)  {
        driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
        System.out.println("App initiate3");
        loginPage = new LoginPage(driver);
        loginPage.login(usr_name,pwd);
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Menu']/android.view.ViewGroup/android.widget.ImageView")).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-WEBVIEW']")));
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-WEBVIEW']")).click();
        driver.findElement(AppiumBy.accessibilityId("test-enter a https url here...")).click();
        driver.pressKey(new KeyEvent(AndroidKey.A));
        driver.pressKey(new KeyEvent(AndroidKey.B));
     
        //driver.findElement(AppiumBy.accessibilityId("test-enter a https url here..."))
        //driver.findElement(AppiumBy.accessibilityId("test-enter a https url here...")).sendKeys("www.google.com");
        //driver.findElement(AppiumBy.accessibilityId("test-GO TO SITE")).click();
        //Set<String> context= driver.getContextHandles();
       // for(String s:context){
        //    System.out.println(s);
       // }
    }
    @DataProvider
    public Object[][] getData(){
        return new Object[][]{{"standard_user","secret_sauce"},{"standard_user","secret_sauce"}};
    }
}
