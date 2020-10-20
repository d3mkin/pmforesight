package pages.administration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityPage extends AbstractAdminPage {

    public EntityPage() {
        super(
                Configuration.baseUrl + URLS.ENTITY,
                "Сущности",
                $(".f-page__name"),
                $(".k-grid-content")
        );
    }

    private SelenideElement nameFilterInput = $("input[data-text-field=\"Name\"]");
    private SelenideElement nameFilterType = $("th span[data-field=\"Name\"] .k-select");
    private SelenideElement nameFilterClear = $("th span[data-field=\"Name\"] button[aria-label=\"очистить\"]");
    private SelenideElement captionFilterInput = $("input[aria-label=\"Заголовок\"]");
    private SelenideElement captionFilterType = $("th span[data-field=\"Title\"] .k-select");
    private SelenideElement captionFilterClear = $("th span[data-field=\"Title\"] button[aria-label=\"очистить\"]");
    private ElementsCollection searchResults = $$(".k-grid-content tr");
    private ElementsCollection searchResultsName = $$(".k-grid-content tr td:first-child");
    private ElementsCollection searchResultsCaption = $$(".k-grid-content tr td:last-child");


    @Step("Установить курсор в фильтр 'Название'")
    public void clickFilterName() {
        nameFilterInput.click();
        nameFilterInput.clear();
    }

    @Step("Ввести значение {0} в 'Название'")
    public void typeName(String name) {
        nameFilterInput.sendKeys(name);
    }

    @Step("Выбрать значение в выпадающем списке")
    public void selectItemByName(String name) {
        $("div[aria-hidden=\"false\"].k-animation-container")
                .$(By.xpath("//li[text()='" + name + "']"))
                .click();
    }

    @Step("Проверка наличия искомых элементов")
    public void shouldHaveResultByName(String... results) {
        searchResultsName.shouldHave(texts(results));
    }

    @Step("Очистить фильтр 'Название'")
    public void clearNameFilter() {
        nameFilterClear.click();
    }

    @Step("Установить курсор в фильтр 'Заголовок'")
    public void clickFilterCaption() {
        captionFilterInput.click();
        captionFilterInput.clear();
    }

    @Step("Ввести значение {0} в 'Заголовок'")
    public void typeCaption(String param) {
        captionFilterInput.sendKeys(param);
    }

    @Step("Проверка наличия искомых элементов")
    public void shouldHaveResultByCaption(String... results) {
        searchResultsCaption.shouldHave(texts(results));
    }

    @Step("Проверка отображения записей в таблице")
    public void shouldHaveResult() {
        searchResults.shouldHave(sizeGreaterThan(1));
    }

    @Step("Очистить фильтр 'Заголовок")
    public void clearCaptionFilter() {
        captionFilterClear.click();
    }

    @Step("Проверка открытия страницы 'Сущности'")
    @Override
    public void shouldBePage() {
        shouldHaveName();
        shouldHaveSearchFields();
        shouldHaveRightUrlAndTitle();
    }


    @Step("Проверка корректности урла и заголовка страницы")
    public void shouldHaveRightUrlAndTitle() {
        assertEquals(WebDriverRunner.getWebDriver().getTitle(), pageTitle,
                "Название страницы не соответствует " + pageTitle);
        assertEquals(WebDriverRunner.url(), url,
                "Урл страницы не соответствует " + url);
    }

    @Step("Проверка отображения полей поиска по названию и заголовку")
    public void shouldHaveSearchFields() {
        nameFilterInput.shouldBe(visible);
        captionFilterInput.shouldBe(visible);
    }

    @Step("Проверка отображения имени реестра")
    public void shouldHaveName() {
        name.shouldHave(text("Сущности"));
    }

}
