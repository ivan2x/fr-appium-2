package org.ivn.android.pages;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    AndroidDriver driver;
    public LoginPage(AndroidDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @AndroidFindBy(accessibility ="test-Username" )
    private WebElement userNameField;
    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;
    @AndroidFindBy(accessibility = "test-LOGIN")
    private  WebElement loginBtn;
    @AndroidFindBy(xpath= "  //android.view.ViewGroup[@content-desc='test-Error message']//android.widget.TextView")
    private WebElement errorMsg;

    public void login(String user, String password){
        userNameField.sendKeys(user);
        passwordField.sendKeys(password);
        loginBtn.click();
    }

    public String validateErrorMsg(){
        return errorMsg.getText();

    }

    public void swipeRightWithDuration() {
        // Calculate parameters based on your coordinates
        int startX = 25;
        int startY = 1000;
        int endX = 800;
        int width = endX - startX;  // 775 pixels
        int height = 1;             // Minimal height since Y remains same

        // Execute the swipe gesture with 3 second duration
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "left", startX,
                "top", startY,
                "width", width,
                "height", height,
                "direction", "right",
                "percent", 1.0,       // 100% of the width
                "speed", 258      // pixels per second (775px / 3s)
        ));
    }

    }

