package pages.elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchForm {

    //private final SelenideElement searchInput = $(By.cssSelector(".f-search__view .f-search__header-row .k-textbox"));
    private final SelenideElement searchInput = $(By.xpath("//div[@class='f-search__header-row']//input[@placeholder='Поиск']"));
    private final SelenideElement searchIcon = $(By.cssSelector(".f-search__view .f-search__header-row .f-button .m-i-search"));
    private final SelenideElement clearButton = $(By.xpath("//button/span[contains(text(),'Очистить')]"));
    private final SelenideElement closeButton = $(By.xpath("//button/span[contains(text(),'Закрыть')]"));
    private final SelenideElement notFoundEntityText = $(By.xpath("//td[@class='f-table-td_static']"));
    private final SelenideElement searchButton = $(By.xpath("//button[@data-toggle = 'f-search']"));


    @Step("Проверка открытия формы поиска")
    public void shouldBeSearchForm() {
        $(searchInput).shouldBe(visible);
        $(searchIcon).shouldBe(visible);
        $(clearButton).shouldBe(visible);
        $(closeButton).shouldBe(visible);
        assertEquals($(searchInput).getAttribute("placeholder"), "Поиск");
    }

    @Step("Закрыть форму поиска")
    public void close() {
        $(closeButton).click();
    }

    @Step ("Проверка отсутствия сущности при глобальном поиске")
    public void checkEntityNotFoundInGlobalSearch(String entityName) {
        searchButton.shouldBe(visible).click();
        searchInput.setValue(entityName);
        notFoundEntityText.shouldBe(visible).shouldHave(text("Не найдено записей содержащих искомый текст"));
        clearButton.shouldBe(visible).click();
        closeButton.shouldBe(visible).click();
    }
}
