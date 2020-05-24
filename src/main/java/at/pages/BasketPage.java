package at.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketPage {

    private SelenideElement clearBasketButton = $(By.id("main_btn1"));
    private ElementsCollection basketNoteList = $$(By.id("title"));
    private SelenideElement alertBlockTitle = $(By.id("alertTitle"));
    private SelenideElement alertBlockMessage = $(By.id("android:id/message"));
    private SelenideElement cancelClearBasketButton = $(By.id("android:id/button2"));
    private SelenideElement confirmClearBasketButton = $(By.id("android:id/button1"));

    @Step("Проверить наличие заметки в корзине")
    public BasketPage checkNoteInBasket(String noteTitle){
        List<String> texts = basketNoteList
                .shouldHave(CollectionCondition.sizeGreaterThanOrEqual(0))
                .texts();
        assertThat(texts).contains(noteTitle);
        return this;
    }

    @Step("Нажать на кнокпу Очистить корзину")
    public BasketPage clearBasket(){
        clearBasketButton.shouldBe(visible, enabled).click();
        return this;
    }

    @Step("Подтвердить очистку корзины")
    public void confirmClearBasket(){
        alertBlockTitle.should(appear).shouldHave(text("Удалить"));
        alertBlockMessage.shouldBe(visible).shouldHave(text("Вы уверены что хотите очистить корзину?"));
        confirmClearBasketButton.shouldBe(enabled).click();
    }
}
