package at.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TrickUtils {
    private static AppiumDriver appiumDriver = (AppiumDriver) WebDriverRunner.getWebDriver();
    private static AndroidDriver androidDriver = (AndroidDriver) WebDriverRunner.getWebDriver();

    public static AppiumDriver getAppiumDriver(){
        return appiumDriver;
    }

    public static AndroidDriver getAndroidDriver(){
        return androidDriver;
    }

    public static long getDateDiff(String first, String second){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd'T'HH:mm:ssXXX", Locale.UK); // 2020-03-06T11:02:02+02:00
        long diff = 0L;
        try {
            Date firstDate = sdf.parse(first);
            Date secondDate = sdf.parse(second);
            diff = Math.abs(secondDate.getTime() - firstDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    @Step("Получить цвет элемента")
    public static String getElementColor(SelenideElement elem) {

        Point center = elem.getLocation();
        int colorRGB, red, green, blue;
        BufferedImage read = null;
        try {
            read = ImageIO.read(appiumDriver.getScreenshotAs(OutputType.FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        colorRGB = read.getRGB(center.x, center.y);
        red = (colorRGB & 0x00ff0000) >> 16;
        green = (colorRGB & 0x0000ff00) >> 8;
        blue = colorRGB & 0x000000ff;

        String colorHexValue = String.format("#%02X%02X%02X", red, green, blue);
        return colorHexValue;
    }

    public static String getDeviceTime(){
        String deviceDime = appiumDriver.getDeviceTime();
        String returnTime = null;
        Pattern pattern = Pattern.compile("(?<=T)\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(deviceDime);
        if (matcher.find())
        {
            returnTime = matcher.group(0).replaceFirst("0","");
        }
        return returnTime;
    }

    public static String getTimeForReminder(String currentDeviceTime){
        SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-dd'T'H:mm:ssXXX", Locale.UK);
        Date d = null;
        try {
            d = df.parse(currentDeviceTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 1);
        return df.format(cal.getTime());
    }

    @Step("Закрыть нотификацию с напоминанием")
    public static void closeReminderNotification(SelenideElement element){
        int leftX = element.getLocation().getX();
        int leftY = element.getLocation().getY();
        int rightX = element.getSize().getWidth();
        new TouchAction(androidDriver)
                .press(PointOption.point(leftX, leftY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(100)))
                .moveTo(PointOption.point(rightX, leftY))
                .release()
                .perform();
    }

//    @Step("Закрыть нотификации")
//    private static void closeNotification(){
//        androidDriver.pressKeyCode(AndroidKeyCode.BACK);
//    }

    @Step("Проверить появление напоминания {0} в нотификациях")
    public static void checkRemindNotification(String remindTitle){
        androidDriver.openNotifications();
        List<SelenideElement> appNotifications = $$(By.id("android:id/title"));
        for(SelenideElement element : appNotifications) {
            String extractAppName = element.getText();
            if (extractAppName.equals(remindTitle)) {
                element.click();
//                closeReminderNotification(element);
                break;
            }
        }
    }
}

