package at.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public class SideBarPage {

    private ElementsCollection sideBarMenuList = $$(By.id("text"));

    public void selectSideBarItem(String itemTitle) {
        sideBarMenuList.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1))
                .filterBy(Condition.text(itemTitle))
                .first()
                .click();

    }
}
