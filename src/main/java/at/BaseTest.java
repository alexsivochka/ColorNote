package at;

import at.utils.AppiumServerJava;
import at.utils.Emulator;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;

@Log4j
public class BaseTest {

    private AppiumServerJava appiumServer = new AppiumServerJava();
    Emulator emulator = new Emulator();
    ConfVar confVar = ConfVar.getInstance();

    @BeforeSuite
    public void setUp() {
        emulator.start(confVar.deviceName);
        startAppium();
    }

    @Step("Запуск appiumServer")
    public void startAppium() {
        int port = confVar.appiumPort;
        if(!appiumServer.checkIfServerIsRunnning(port)) {
            appiumServer.startServer();
            log.info("Appium Server успешно запущен на порту - " + port);
        } else {
            log.info("Appium Server уже был ранее запущен на порту - " + port);
        }
    }

    @BeforeClass
    @Step("Инициализация драйвера")
    public void startDriver() {
        Configuration.startMaximized = false;
        Configuration.browserSize = null;
        Configuration.timeout = 15000;
        Configuration.browser = NoteDriver.class.getName();
        open();
        log.info("Драйвер успешно инициализирован");
    }

    @AfterClass
    @Step("Закрытие драйвера")
    public void stopDriver() {
        close();
        log.info("Драйвер успешно закрыт");
    }

    @Step("Остановка Appium server")
    public void stopAppiumServer() {
        int port = confVar.appiumPort;
        if(appiumServer.checkIfServerIsRunnning(port)) {
            appiumServer.stopServer();
            log.info("Appium Server успешно остановлен на порту - " + port);
        } else {
            log.info("Appium Server уже ранее был остановлен на порту - " + port);
        }
    }

    @AfterSuite
    public void tearDown() {
        emulator.close();
        stopAppiumServer();
    }
}

