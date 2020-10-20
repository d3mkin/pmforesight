package pages.goals_and_indicators;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HierarchyOfGoals implements Registry {
    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/registerGoalHierarhy";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = mainContainer.$(By.xpath("//a[text()='Цель']"));
    private SelenideElement table = $(By.xpath("//tr[contains(@class,\"line1\")]"));

    public HierarchyOfGoals() {
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

    public HierarchyOfGoals shouldHaveCorrectLink() {
        assertEquals(WebDriverRunner.url(), url, "Урл не соответствет " + url);
        return this;
    }

    public HierarchyOfGoals shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    public HierarchyOfGoals shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
