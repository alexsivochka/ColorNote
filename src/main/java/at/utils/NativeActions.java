package at.utils;

import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class NativeActions {

    public static void goBack(){
        AndroidDriver driver = (AndroidDriver) WebDriverRunner.getWebDriver();
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public static void goHome(){
        AndroidDriver driver = (AndroidDriver) WebDriverRunner.getWebDriver();
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }


}

