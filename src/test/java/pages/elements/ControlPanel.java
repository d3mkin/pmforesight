package pages.elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class ControlPanel {
    private final SelenideElement panel = $(".k-toolbar");
    private final SelenideElement addButton = panel.$("#f-add");
    private final SelenideElement searchInput = $(By.xpath("//input[@placeholder='Поиск']"));
    private final SelenideElement searchButton = panel.$("#f-viewSampleLabel");
    private final SelenideElement hideAllButton = panel.$("#f-collapse");
    private final SelenideElement expandAllButton = panel.$("#f-collapse");
    private final SelenideElement deleteButton = panel.$("#f-remove");
    private final SelenideElement editByClickButton = panel.$("#f-editonclick_false");
    private final SelenideElement exportButton = panel.$("#f-export");
    private final SelenideElement updateButton = panel.$("#f-reload");
    private final SelenideElement extendFilterButton = panel.$("#f-viewExtFilterEdit");
    private final SelenideElement resetFilterButton = panel.$("#f-viewExtFilterClear");
    private final SelenideElement viewList = $ (By.xpath("//span[@class='k-input']"));


    @Step("Открыть модальноее окно создания")
    public void clickAddButton() {
        addButton.click();
    }

    @Step("Ввести значение для поиска '{0}'")
    public void typeSearchValue(String value) {
        searchInput.clear();
        searchInput.setValue(value).pressEnter();
    }

    @Step("Осуществить выборку")
    public void clickSearch() {
        searchButton.click();
    }

    @Step("Удалить запись")
    public void clickDelete() {
        deleteButton.click();
    }

    public void clickHideAll() {
        hideAllButton.click();
    }

    @Step("Проверка отображения поля поиска")
    public void shouldHaveSearchInput() {
        searchInput.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки добавления")
    public void shouldHaveAddButton() {
        addButton.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки редактирования по клику")
    public void shouldHaveEditByClick() {
        editByClickButton.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки развернуть все группы")
    public void shouldHaveExpandAll() {
        expandAllButton.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки свернуть все группы")
    public void shouldHaveHideAll() {
        hideAllButton.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки экспорта")
    public void shouldHaveExport() {
        exportButton.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки обновить")
    public void shouldHaveUpdate() {
        updateButton.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки расширенный фильтр")
    public void shouldHaveExtendFilter() {
        extendFilterButton.shouldBe(visible);
    }

    @Step("Проверка отображения кнопки очистить фильтр")
    public void shouldHaveReset() {
        resetFilterButton.shouldBe(visible);
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        viewList.click();
        sleep(1000);
        $ (By.xpath("//div[@class='k-animation-container']//li[text()='"+viewName+"']")).shouldBe(visible).click();

    }

}
