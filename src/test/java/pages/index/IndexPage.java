package pages.index;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import pages.elements.Header;
import pages.elements.WidgetPanel;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndexPage {
    private WidgetPanel widgetPanel;
    private MainMenu mainMenu;
    private Header header;
    private String url = Configuration.baseUrl + "/Page/Index";

    public IndexPage() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.widgetPanel = new WidgetPanel();
    }

    public void open() {
        Selenide.open(url);
    }

    @Step("Проверка обновления страницы")
    //Нужно обновить
    public void shouldUpdateAfterClickToMainPanelButton() {
        sleep(3000);
        header.shouldHaveHeaderText("ПМ Форсайт");
    }
    public Header header() {
        return header;
    }

    public WidgetPanel widgetPanel() {
        return widgetPanel;
    }

    public MainMenu mainMenu() {
        return mainMenu;
    }

    public void shouldUpdateAfterClickToHeaderLink() {
        sleep(3000);
        widgetPanel.shouldHaveHeaderText("Информационная панель");
    }

    @Step("Проверка коррекности урл страницы")
    public void shouldBeIndexPage() {
        if (WebDriverRunner.isFirefox()) {
            sleep(5000);
        }
        assertEquals(WebDriverRunner.url(), url, "Урл не соответствует " + url);
    }
}
