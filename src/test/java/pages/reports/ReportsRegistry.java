package pages.reports;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Отчеты
 */
public class ReportsRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/reports";

    public ReportsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Step("Открыть реестр 'Отчеты' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Отчеты' через меню")
    public void openFromMenu() {
        mainMenu.reportsr().openReports();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Отчеты'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
    }

    public ReportsRegistry shouldHaveCorrectLink() {
        assertEquals(WebDriverRunner.url(), url, "Урл не соответствет " + url);
        return this;
    }
}
