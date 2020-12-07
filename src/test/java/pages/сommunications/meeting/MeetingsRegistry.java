package pages.сommunications.meeting;

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
 * Совещание
 */
public class MeetingsRegistry implements Registry {
    private final Header header;
    private final MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private SelenideElement mainContainer = $("#mainBodyContainer");
    private final String url = Configuration.baseUrl + "/page/register?view=meeting";
    private final SelenideElement registryName = $("#f-grid-title span");
    private final SelenideElement table = $("div.f-grid__grid");
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement foundCheckBox = $(By.cssSelector("div[class='slick-cell l0 r0 slick-cell-checkboxsel']"));
    private final SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public MeetingsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Step("Осуществить поиск совещания")
    public void searchMeeting(String value) {
        controlPanel.typeSearchValue(value);
    }

    @Step("Проверка отображения совещания после создания записи")
    public void shouldHaveCreatedRecord() {
        firstFoundRow.shouldBe(visible);
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Override
    @Step("Открыть реестр 'Совещание' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Совещание' через меню")
    public void openFromMenu() {
        mainMenu.communication().openMeetings();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Совещание'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public MeetingsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public MeetingsRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Совещание"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public MeetingsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Выбрать найденную запись")
    public void selectRow() {
        foundCheckBox.click();
    }

    public void clickDelete() {
        sleep(1000);
        controlPanel.clickDelete();
    }

    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allRows.shouldNot(visible);
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
    }

    @Step ("Проверить что Совещание не отображается в реестре")
    public void checkOrderNotExist(String meetingName){
        checkRegistryIsLoaded();
        searchMeeting(meetingName);
        shouldNotHaveResults();
    }

    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible);
        tableWithEntities.shouldBe(visible);
    }
}
