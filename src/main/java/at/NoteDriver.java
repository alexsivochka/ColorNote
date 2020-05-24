package at;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class NoteDriver implements WebDriverProvider {

    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("newCommandTimeout", 60 * 5);
//        capabilities.setCapability("app", file.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.socialnmobile.dictapps.notepad.color.note");
        capabilities.setCapability("appActivity", "com.socialnmobile.colornote.activity.Main");
        capabilities.setCapability("language", "ru");
        capabilities.setCapability("locale", "UK");
        capabilities.setCapability("unicodeKeyboard", true);



        try {
            return new AndroidDriver(capabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }}