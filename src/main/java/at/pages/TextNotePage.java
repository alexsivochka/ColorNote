package at.pages;

import at.entyties.Note;
import at.enums.ColorsSelector;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class TextNotePage {

    private SelenideElement noteTitle = $(By.id("edit_title"));
    private SelenideElement noteDescription = $(By.id("edit_note"));
    private SelenideElement noteColorButton = $(By.id("color_btn"));
    private SelenideElement noteAdditionalMenuButton = $(By.id("overflow_btn"));
    private SelenideElement noteEditButton = $(By.id("edit_btn"));
    private SelenideElement deleteNoteButtonOk = $(By.id("android:id/button1"));
//    private SelenideElement successSavingMessage = $(By.xpath("//android.widget.Toast[1]"));

    @Step("Заполнить данные по создаваемой заметке")
    public void fillNoteData(Note note){
        fillTitleOfNote(note);
        fillDescriptionOfNote(note);
        chooseNoteColor(note);
    }

    @Step("Заполнить поле title значением - {note.title}")
    public TextNotePage fillTitleOfNote(Note note){
        noteTitle.shouldBe(visible,enabled).sendKeys(note.getTitle());
        return this;
    }

    @Step("Заполнить описание заметки значением - {note.content}")
    public TextNotePage fillDescriptionOfNote(Note note){
        noteDescription.shouldBe(visible,enabled).clear();
        noteDescription.sendKeys(note.getContent());
        return this;
    }

    @Step("Выбрать цвет заметки - {note.color}")
    public TextNotePage chooseNoteColor(Note note) {
        noteColorButton.shouldBe(visible,enabled).click();
        String colorId = note.getColor().getKey();
        $(By.id(colorId)).shouldBe(visible, enabled).click();
        return this;
    }

//    @Step("Проверить, что заметка была успешно сохранена")
//    public void checkSavingNote(){
//        successSavingMessage.should(appear);
//    }

    @Step("Перейти к редактированию заметки")
    public TextNotePage openNoteForEdit(){
        noteEditButton.shouldBe(visible,enabled).click();
        return this;
    }

    @Step("Открыть дополнительное меню заметки")
    public TextNotePage openAdditionalNoteMenu(){
        noteAdditionalMenuButton.shouldBe(visible).click();
        return this;
    }

    @Step("Выбрать в дополнительном меню заметки пункт - {0}")
    public void selectAdditionalOption(String optionValue){
        String xpath = String.format("//*[@text='%s']", optionValue);
        $(By.xpath(xpath)).shouldBe(visible).click();
    }

    @Step("Подтвердить удаление заметки")
    public NoteMainPage comfirmNoteDeleting(){
        deleteNoteButtonOk.shouldBe(visible).click();
        return new NoteMainPage();
    }

    @Step("Проверить появление Toast-сообщения")
    public void checkToastMessage(String expectedToastMessage) {
        String name = $(By.xpath("//*[text()='Сохранено']")).shouldBe(visible).getAttribute("name");
        assertThat(name).isEqualTo(expectedToastMessage);
    }
}
