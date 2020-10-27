package pages.managementobjects.program;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.DeleteEntityDialog;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Программы
 */
public class ProgramRegistry implements Registry {

    private final MainMenu mainMenu;
    private final Header header;
    private final ControlPanel controlPanel;

    private final String url = Configuration.baseUrl + "/page/register?view=lprogram";
    private final SelenideElement mainContainer = $("#mainBodyContainer");
    private final SelenideElement registryName = $("#f-grid-title span");
    private final SelenideElement table = mainContainer.$("div.f-grid__grid");
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement foundCheckBox = $(By.cssSelector("div[class=\"slick-cell l0 r0 slick-cell-checkboxsel\"]"));
    private final SelenideElement firstProjectRow = table.$(By.xpath(".//div[@class = 'ui-widget-content slick-row even']"));
    private final SelenideElement firstProgramRow = table.$(By.xpath(".//div[contains(@class,\"ui-widget-content slick-row odd\")]"));

    public ProgramRegistry() {
        this.mainMenu = new MainMenu();
        this.header = new Header();
        this.controlPanel = new ControlPanel();
    }

    @Step("Осуществить поиск программы")
    public void searchProgram(String value) {
        sleep(1000);
        controlPanel.typeSearchValue(value);
        //controlPanel.clickSearch();
    }

    @Step("Проверка отображения совещания после создания записи")
    public void shouldHaveCreatedRecord() {
        firstFoundRow.shouldBe(visible);
    }

    @Override
    @Step("Открыть реестр 'Программы' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Программы' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openPrograms();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Программы'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public ProgramRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public ProgramRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Программы"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public ProgramRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Выбрать найденную запись")
    public void selectRow() {
        sleep(1000);
        foundCheckBox.click();
    }

    public void clickDelete() {
        controlPanel.clickDelete();
    }

    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    public void shouldNotHaveResults() {
        allRows.shouldNot(visible);
    }
    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Проверка отображения проекты после создания записи")
    public void clickFirstRow() {
        firstProjectRow.click();
    }

    @Step("Проверка отображения программы после создания записи")
    public void clickFirstProgramRow() {
        firstProgramRow.click();
    }
}
