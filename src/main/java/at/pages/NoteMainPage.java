package at.pages;

import at.entyties.Note;
import at.utils.TrickUtils;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NoteMainPage {

    private SelenideElement newEntryButton = $(By.id("main_btn1"));
    private SelenideElement menuSettingsButton = $(By.id("main_btn3"));
    private ElementsCollection noteTitlesCollection = $$(By.id("title"));
    private ElementsCollection noteTimesCollection = $$(By.id("date"));
    private ElementsCollection notesContent = $$(By.id("content"));
    private ElementsCollection colorsNotesCollection = $$(By.id("color"));
    private SelenideElement imgSidebar = $(By.id("img_sidebar"));


    @Step("Перейти к созданию новой заметки. Выбрать тип создаваемой заметки")
    public void addNewEntry(Note note) {
        newEntryButton.shouldBe(visible, enabled).click();
        selectNoteType(note);
    }

    @Step("Открыть сайдбар")
    public SideBarPage openSideBar() {
        imgSidebar.shouldBe(visible,enabled).click();
        return new SideBarPage();
    }

    @Step("Выбрать тип создаваемой заметки - {note.type}")
    private void selectNoteType(Note note){
        String type = null;
        switch (note.getType()){
            case TEXT:
                type = "Текст";
                break;
            case LIST:
                type = "Перечень";
                break;
        }
        String path = String.format("//*[@text='%s']", type);
        $(By.xpath(path)).shouldBe(visible).click();
    }

    @Step("Сверить данные по созданной заявке")
    public void checkNoteData(Note note){
        checkCreatedNoteTitle(note);
        checkCreatedNoteContent(note);
        checkCreatedNoteColor(note);
        checkCreatedNoteTime(note);
    }

    @Step("Сверить время созданной заметки совпадает с ожидаемым - {note.createdTime}")
    private void checkCreatedNoteTime(Note note) {
        String time = noteTimesCollection.shouldHave(sizeGreaterThanOrEqual(1))
                .first()
                .getText();
        assertThat(time).isEqualToIgnoringCase(note.getCreatedTime());
    }

    @Step("Проверить title созданной заметки совпадает с ожидаемым - {note.title}")
    private void checkCreatedNoteTitle(Note note){
        String title = noteTitlesCollection.shouldHave(sizeGreaterThanOrEqual(1))
                .first()
                .getText();
        assertThat(title).isEqualToIgnoringCase(note.getTitle());
    }

    @Step("Проверить description созданной заметки совпадает с ожидаемым - {note.content}")
    private void checkCreatedNoteContent(Note note){
        String content = notesContent.shouldHave(sizeGreaterThanOrEqual(1))
                .first()
                .getText();
        assertThat(content).isEqualToIgnoringCase(note.getContent());
    }

    @Step("Проверить что цвет созданной заметки с ожидаемым - {note.color}")
    private void checkCreatedNoteColor(Note note){
        String color = TrickUtils.getElementColor(colorsNotesCollection.first());
        assertThat(color).isEqualToIgnoringCase(note.getColor().getHexValue());
    }

    @Step("Проверить что заметка с title {note.title} отсутствует в списке")
    public void checkNoteNotExist(Note note){
        assertThat(noteTitlesCollection.texts().contains(note.getTitle())).isFalse();
    }

    @Step("Проверить что заметка с title {note.title} присутствует в списке")
    public void checkNoteExist(Note note){
        assertThat(noteTitlesCollection.shouldHave(texts(note.getTitle())));
    }

    @Step("Открыть заметку {note.title}")
    public TextNotePage openNote(Note note){
        String notePath = String.format("//*[@text='%s']", note.getTitle());
        $(By.xpath(notePath)).shouldBe(visible).click();
        return new TextNotePage();
    }

    @Step("Открыть меню основных настроек")
    public NoteMainPage openMainSettings(){
        menuSettingsButton.shouldBe(visible).click();
        return this;
    }

    @Step("Выбрать в основных настройках пункт - {0}")
    public NoteMainPage selectSettingItem(String value){
        String note = String.format("//*[@text='%s']", value);
        $(By.xpath(note)).shouldBe(visible).click();
        return this;
    }

    @Step("Установить значение для отображения - {0}")
    public NoteMainPage setNotesView(String value){
        String note = String.format("//*[@text='%s']", value);
        $(By.xpath(note)).shouldBe(visible).click();
        return this;
    }
}

