package at.tests;

import at.BaseTest;
import at.entyties.Note;
import at.enums.ColorsSelector;
import at.enums.NoteType;
import at.listeners.AllureOnFailListener;
import at.pages.BasketPage;
import at.pages.NoteMainPage;
import at.pages.ReminderPage;
import at.pages.TextNotePage;
import at.utils.NativeActions;
import at.utils.TrickUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.sleep;

@Listeners(AllureOnFailListener.class)
public class NoteTest extends BaseTest {

    private Note note = Note.builder()
            .type(NoteType.TEXT)
            .title("Note title")
            .content("description as a text")
            .color(ColorsSelector.PURPLE)
            .build();

    private NoteMainPage noteMainPage;
    private TextNotePage textNotePage;
    private ReminderPage reminderPage;
    private BasketPage basketPage;


    @Test(priority = 10, description = "Создание новой заметки")
    public void createNewNote() {
        noteMainPage = new NoteMainPage();
        textNotePage = new TextNotePage();
        noteMainPage.openMainSettings()
                .selectSettingItem("Вид")
                .setNotesView("Подробности");
        noteMainPage.addNewEntry(note);
        textNotePage.fillNoteData(note);
        note.setCreatedTime(TrickUtils.getDeviceTime());
        NativeActions.goBack();
        NativeActions.goBack();
        noteMainPage.checkNoteExist(note);
        noteMainPage.checkNoteData(note);
    }

    @Test(priority = 20, description = "Редактирование созданной заметки")
    public void editNote() {
        note.setContent("this is new description for note edit");
        note.setColor(ColorsSelector.BLUE);

        noteMainPage = new NoteMainPage();
        textNotePage = new TextNotePage();
        noteMainPage.openNote(note)
                .openNoteForEdit()
                .chooseNoteColor(note)
                .fillDescriptionOfNote(note);
        note.setCreatedTime(TrickUtils.getDeviceTime());
        NativeActions.goBack();
        NativeActions.goBack();
        noteMainPage.checkNoteExist(note);
        noteMainPage.checkNoteData(note);
    }

//    @Test(priority = 30, description = "Проверка установки и срабатывания напоминания")
//    public void checkNoteReminder() {
//        noteMainPage = new NoteMainPage();
//        textNotePage = new TextNotePage();
//        reminderPage = new ReminderPage();
//        noteMainPage.openNote(note);
//        textNotePage.openAdditionalNoteMenu()
//                .selectAdditionalOption("Напоминание");
//        reminderPage.selectReminderType("Время сигнала")
//                .switchToReminderTimeInput();
//        String deviceTime = TrickUtils.getAppiumDriver().getDeviceTime();
//        String timeForReminder = TrickUtils.getTimeForReminder(deviceTime);
//        reminderPage.fillReminderTime(timeForReminder)
//                .saveNoteReminder();
//        NativeActions.goBack();
//        NativeActions.goHome();
//        String deviceTime2 = TrickUtils.getAppiumDriver().getDeviceTime();
//        long howManyToSleep = TrickUtils.getDateDiff(deviceTime2, timeForReminder);
//        sleep(howManyToSleep);
//        TrickUtils.checkRemindNotification(note.getTitle());
//        sleep(1000);
//    }

    @Test(priority = 40, description = "Помещение заметки в корзину")
    public void removeNoteToBasket() {
        noteMainPage = new NoteMainPage();
        textNotePage = new TextNotePage();
        noteMainPage.openNote(note);
        textNotePage.openAdditionalNoteMenu()
                .selectAdditionalOption("Удалить");
        textNotePage.comfirmNoteDeleting();
        noteMainPage.checkNoteNotExist(note);
    }

    @Test(priority = 50, description = "Удаление заметки из корзины")
    public void deleteNote() {
        noteMainPage = new NoteMainPage();
        basketPage = new BasketPage();
        noteMainPage
                .openSideBar()
                .selectSideBarItem("Корзина");
        basketPage
                .checkNoteInBasket(note.getTitle())
                .clearBasket()
                .confirmClearBasket();
    }
}
