package at.utils;

import com.codeborne.selenide.Screenshots;
import io.qameta.allure.Attachment;

import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.google.common.io.Files.*;

public final class DriverUtils {

    @Attachment(type = "image/png")
    public static byte[] screenshot() throws IOException {
        if (getWebDriver() != null) {
            return toByteArray(Screenshots.takeScreenShotAsFile());
        } else return null;
    }
}

