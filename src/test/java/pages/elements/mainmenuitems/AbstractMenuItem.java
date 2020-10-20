package pages.elements.mainmenuitems;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class AbstractMenuItem {

    protected SelenideElement menu = $(".f-menu_main");
    protected SelenideElement expandedMenu = $(".f-menu__list");
    //protected By expandedMenu = By.cssSelector(".f-menu__list");

    void clickItem(SelenideElement parent, SelenideElement item) {
        parent.shouldBe(visible);
        item.click();
    }

    public abstract void expand();

    public abstract AbstractMenuItem visible();

    public abstract AbstractMenuItem hasCorrectText();
}
