package pages.elements.mainmenuitems;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Administration  {

    private SelenideElement menu = $(By.cssSelector(".f-menu_main"));
    private SelenideElement administration = menu.$(By.cssSelector("li a[href=\"/admin\"]"));
    private String url = Configuration.baseUrl + "/admin";
    private SelenideElement globalshearch = $(By.xpath("//button[contains(@data-func,\"Asyst.globalSearch.quick\")]"));
    private SelenideElement gohome = $(By.xpath("//span[contains(@class,\"f-icon m-i-home\")]"));
    private SelenideElement adminka = $(By.xpath("//span[text()='Админка']"));
    private SelenideElement entity = $(By.xpath("//span[contains(@class,\"f-header__text\") and text()='Сущности']"));
    private SelenideElement life_cycle_stages = $(By.xpath("//span[text()='Стадии жизненного цикла']"));
    private SelenideElement ct = $(By.xpath("//span[text()='КТ']"));
    private SelenideElement documents = $(By.xpath("//span[text()='Документы']"));
    private SelenideElement access = $(By.xpath("//span[text()='Доступ']"));
    private SelenideElement approval = $(By.xpath("//span[text()='Согласования']"));
    private SelenideElement status_reports = $(By.xpath("//span[text()='Статус-отчеты']"));
    private SelenideElement system_settings = $(By.xpath("//span[text()='Системные настройки']"));
    private SelenideElement decor = $(By.xpath("//span[text()='Оформление']"));
    private SelenideElement dropdown = $(By.xpath("//div[contains(@class,\"f-popup f-popup_animation f-popup_visible_bottom\")]"));

    private SelenideElement error = $(By.xpath("//span[text()='Ошибка 403']"));
    private SelenideElement accessDenied = $(By.xpath("//div[contains(@class,\"f-control__container\")]"));
    private SelenideElement PMforsait = $(By.xpath("//span[text()='ПМ Форсайт']"));



    @Step("Проверка открытия окна Администрирование")

    public void open() {
        administration.click();
    }
    public void shouldHaveCorrectLink() {
        assertEquals(WebDriverRunner.url(), url, "Урл не соответствет " + url);
    }

    @Step("Проверка всех кнопок на верхней панели")
    public void allItemInTopPanel() {
        globalshearch.shouldBe(visible);
        gohome.shouldBe(visible);
        adminka.shouldBe(visible);
        entity.shouldBe(visible);
        life_cycle_stages.shouldBe(visible);
        ct.click();
        documents.shouldBe(visible);
        access.shouldBe(visible);
        approval.shouldBe(visible);
        status_reports.shouldBe(visible);
        system_settings.shouldBe(visible);
        decor.shouldBe(visible);
    }
    @Step("Проверка содержания выпадающих списков")
    public void dropDown() {
        life_cycle_stages.click();
        dropdown.shouldBe(visible);
        ct.click();
        dropdown.shouldBe(visible);
        access.click();
        dropdown.shouldBe(visible);
        approval.click();
        dropdown.shouldBe(visible);
        status_reports.click();
        dropdown.shouldBe(visible);
        system_settings.click();
        dropdown.shouldBe(visible);
        decor.click();
        dropdown.shouldBe(visible);
    }
    @Step("Проверка перехода домой")
    public void goHomeCheck() {
        gohome.click();
        menu.shouldBe(visible);
    }
    @Step("Проверка перехода домой")
    public void errorAccessDenied() {
        error.shouldBe(visible);
        accessDenied.shouldBe(visible);
        PMforsait.click();
        menu.shouldBe(visible);
    }
}
