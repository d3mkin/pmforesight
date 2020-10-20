package pages.personnel_management;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Обучение
 */
public class TrainingRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/register?view=CourseWork";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $(By.xpath("//div[contains(@class,\"f-grid__grid\")]"));

    public TrainingRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Step("Открыть реестр 'Обучение' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Обучение' через меню")
    public void openFromMenu() {
        mainMenu.personnelManagement().openCourseWork();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Обучение'")
    public void shouldBeRegistry() {
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка наличия имени реестра")
    public TrainingRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public TrainingRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Проверка корректности ссылки {this.url}")
    public TrainingRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }
}
