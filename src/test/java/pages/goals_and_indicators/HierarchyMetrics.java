package pages.goals_and_indicators;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HierarchyMetrics implements Registry {
    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/registerKPIHierarhy";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.InlineTableViewHierarchyTable");

    public HierarchyMetrics() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Override
    public void open() {
        Selenide.open(url);
    }

    @Override
    public void openFromMenu() {
        mainMenu.goalsAndIndicators().openGoalHierarchy();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    public void shouldBeRegistry() {

    }

    public HierarchyMetrics shouldHaveCorrectLink() {
        assertEquals(WebDriverRunner.url(), url, "Урл не соответствет " + url);
        return this;
    }

    public HierarchyMetrics shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    public HierarchyMetrics shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
