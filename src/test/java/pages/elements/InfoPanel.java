package pages.elements;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class InfoPanel {
    private List<SelenideElement> allWidgetsHideIcon = $$(By.cssSelector(".f-widget__header-name button .f-icon"));
    private SelenideElement allWidgetsContent = $(By.cssSelector(".f-widget__content"));
    private SelenideElement allWidgetsNotExpand = $(By.cssSelector(".f-widget__header-name button .f-icon:not(.f-icon_rotate_90deg)"));
    private ElementsCollection allWidgets = $$(".f-widget-grid .f-widget");

    private SelenideElement editButton = $("#edit");
    private SelenideElement updateButton = $("#edit");
    private SelenideElement reloadButton = $("#reload");
    private SelenideElement endEditButton = $("#preview");
    private SelenideElement addWidgetButton = $("#add_widget");
    private SelenideElement saveButton = $("#save");
    private SelenideElement infoHeader = $(".f-page__name");

    @Step("Свернуть все виджеты и проверить что они свернулись")
    public void shouldHideAllWidgets() {
        List<SelenideElement> icons = allWidgetsHideIcon;
        for (SelenideElement icon : icons) {
            icon.click();
        }
        $(allWidgetsContent).shouldNot(visible);
    }

    @Step("Развернуть свернутые виджеты")
    public void expandAllHideWidgets() {
        String script = "document.querySelectorAll(\".f-widget__header-name button .f-icon:not(.f-icon_rotate_90deg)\").forEach(function(el){el.click()});";
        Selenide.executeJavaScript(script);
        Selenide.sleep(3000);
    }

    @Step("Развернуть все виджеты и проверить что они развернулись")
    public void shouldExpandAllWidgets() {
        List<SelenideElement> icons = $$(allWidgetsHideIcon);
        for (SelenideElement icon : icons) {
            icon.click();
        }
        $(allWidgetsContent).shouldBe(visible);
    }

    @Step("Проверка наличия панели с виджетами")
    public void shouldBeVisibleAndHaveAnyWidgets() {
        allWidgets.shouldHave(sizeGreaterThan(0));
    }

    @Step("Нажать кнопку редактировать")
    public void clickEdit() {
        editButton.click();
    }

    @Step("Проверка отображения кнопок \"Закончить редактирование\", \"Добавить\" и \"Сохранить\"")
    public void shouldHaveControlButtonsWhenEdit() {
        endEditButton.shouldBe(visible);
        addWidgetButton.shouldBe(visible);
        saveButton.shouldBe(visible);
    }

    @Step("Блоки \"Мои проекты\" и \"Мои контракты\" раскрыты без возможности свернуть")
    public void shouldNotHideContentInMyProjectAndMyContract() {
        SelenideElement myContract =
                $x("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'Мои контракты')]");
        myContract.scrollTo();
        myContract.$(".k-i-arrow-chevron-right").click();
        myContract.$(".f-widget__content").shouldBe(visible);
        SelenideElement myProject =
                $x("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'Мои проекты')]");
        myProject.scrollTo();
        myProject.$(".k-i-arrow-chevron-right").click();
        myProject.$(".f-widget__content").shouldBe(visible);
    }

    @Step("На всех виджетах должны быть кнопки \"Обновить\", \"Настройки\" и \"Удалить\"")
    public void shouldHaveButtonOnAllWidgets() {
        for (SelenideElement widget : allWidgets) {
            widget.scrollTo();
            SelenideElement refresh = widget.$(".f-i-refresh");
            refresh.shouldBe(visible);
            SelenideElement setting = widget.$(".f-i-settings");
            setting.shouldBe(visible);
            SelenideElement trash = widget.$(".f-i-trash");
            trash.shouldBe(visible);
        }
    }

    @Step("Нажать кнопку добавить виджет")
    public void clickAdd() {
        addWidgetButton.click();
    }

    @Step("Проверка открытия нового виджета с кнопками \"Обновить\", \"Настройки\" и \"Удалить\"")
    public void shouldHaveNewWidgetWithRightButton() {
        SelenideElement newWidget = allWidgets.get(0);
        newWidget.shouldBe(visible);
        newWidget.$(".f-i-refresh").shouldBe(visible);
        newWidget.$(".f-i-settings").shouldBe(visible);
        newWidget.$(".f-i-trash").shouldBe(visible);
    }

    @Step("Нажать кнопку сохранить")
    public void clickSave() {
        saveButton.click();
    }

    @Step("Проверка скрытия кнопок \"Закончить редактирование\", \"Добавить\" и \"Сохранить\"")
    public void shouldNotHaveButtonForEdit() {
        endEditButton.shouldNot(visible);
        addWidgetButton.shouldNot(visible);
        saveButton.shouldNot(visible);
    }

    public Boolean isMyProjectExpand() {
        return $x("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'Мои контракты')]//div[contains(@class, \"f-widget__content\")]")
                .isDisplayed();
    }

    public Boolean isMyContractExpand() {
        return $x("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'Мои проекты')]//div[contains(@class, \"f-widget__content\")]")
                .isDisplayed();
    }

    @Step("Проверка отображения кнопок \"Обновить\" и \"Редактировать\"")
    public void shouldHaveControlButtonsWhenAddEnd() {
        updateButton.shouldBe(visible);
        editButton.shouldBe(visible);
    }

    @Step("Удалить последний добавленный виджет")
    public void deleteLastAddWidget() {
        SelenideElement newWidget = $x("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'New widget')]");
        newWidget.scrollTo();
        $x("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'New widget')]//span[contains(@class, 'f-i-trash')]").click();
        sleep(10000);
        $(".k-dialog button.k-primary").click();
    }

    @Step("Проверка удаления созданного виджета")
    public void shouldNotHaveNewWidget() {
        $x("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'New widget')]")
                .shouldNot(visible);
    }

    @Step ("Проверка заголовка панели с виджетами")
    public void shouldHaveHeaderText (String headerText) {
        infoHeader.shouldHave(text(headerText));
    }

    public void waitUntilLoadHide() {
        $$(".k-loading-image").shouldBe(CollectionCondition.size(0));
    }
}
