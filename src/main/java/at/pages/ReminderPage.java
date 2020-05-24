package at.pages;

import at.utils.TrickUtils;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ReminderPage {

    private SelenideElement reminderTypeSelect = $(By.id("reminder_type"));
    private SelenideElement pinToStatusBarButton = $(By.id("quick_setting_status_bar"));
    private SelenideElement remindTodayButton = $(By.id("quick_setting_today"));
    private SelenideElement remind15MinutesButton = $(By.id("quick_setting_15min"));
    private SelenideElement remind30MinutesButton = $(By.id("quick_setting_30min"));

    private SelenideElement clearButton = $(By.id("button3"));
    private SelenideElement cancelButton = $(By.id("button2"));
    private SelenideElement doneButton = $(By.id("button1"));

    private SelenideElement reminderTimeSelect = $(By.id("reminder_time"));
    private SelenideElement reminderTimeToogle = $(By.id("toggle_mode"));

    private SelenideElement reminderHourInput = $(By.id("input_hour"));
    private SelenideElement reminderMinutesInput = $(By.id("input_minute"));

    @Step("Выбрать тип напоминания - {0}")
    public ReminderPage selectReminderType(String reminderType) {
        reminderTypeSelect.shouldBe(visible,enabled).click();
        $$(By.className("android.widget.CheckedTextView"))
                .shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1))
                .filterBy(text(reminderType))
                .get(0).click();
        return this;
    }

    @Step("Перейти к установке времени сигнала напоминания")
    public ReminderPage switchToReminderTimeInput(){
        reminderTimeSelect.shouldBe(visible, enabled).click();
        reminderTimeToogle.should(appear).click();
        return this;
    }

    @Step("Ввести время для напоминания - {0}")
    public ReminderPage fillReminderTime(String value){
        String returnTime = null;
        Pattern pattern = Pattern.compile("(?<=T)\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(value);
        if (matcher.find())
        {
            returnTime = matcher.group(0);
        }
        String hour = StringUtils.left(returnTime, 2);
        String minutes = StringUtils.right(returnTime, 2);
        reminderHourInput.shouldBe(enabled).setValue(hour);
        reminderMinutesInput.shouldBe(enabled).setValue(minutes);
        $(By.id("button1")).shouldBe(enabled).click();
        return this;
    }

    @Step("Сохранить напоминание в заметке")
    public void saveNoteReminder(){
        doneButton.shouldBe(visible, enabled).click();
    }
}
