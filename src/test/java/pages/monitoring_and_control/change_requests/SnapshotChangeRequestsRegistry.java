package pages.monitoring_and_control.change_requests;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.Header;
import pages.elements.MainMenu;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SnapshotChangeRequestsRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=SnapshotChangeRequest";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");
    private SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public SnapshotChangeRequestsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Step("Открыть реестр 'Запросы на изменение' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Запросы на изменение' через меню")
    public void openFromMenu() {
        mainMenu.monitoringAndControl().openRequestsForChange();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Запросы на изменение'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public SnapshotChangeRequestsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public SnapshotChangeRequestsRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public SnapshotChangeRequestsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Поиск Запроса на изменения {changeRequestsName} в реестре")
    public void searchChangeRequests(String changeRequestsName) {
        sleep(1000);
        controlPanel.typeSearchValue(changeRequestsName);
        //controlPanel.clickSearch();
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
        $x("//span[contains(text(),'"+viewName+"')]").shouldBe(visible);
        $("#f-grid-viewlist .k-input").shouldHave(text(""+viewName+""));
    }

    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible, Duration.ofMinutes(10));
        $(".k-loading-mask").shouldNotBe(exist, Duration.ofMinutes(10));
        tableWithEntities.shouldBe(visible);
    }

    @Step("Проверить что Запрос на изменение отображает проект {projectName}, наименование {changeRequestName} и статус {changeRequestStatus}")
    public void checkChangeRequestData(String projectName, String changeRequestName, String changeRequestStatus){
        $("div[class='slick-cell l1 r1'] a").shouldBe(visible).shouldHave(text(projectName));
        $("div[class='slick-cell l2 r2'] a").shouldBe(visible).shouldHave(text(changeRequestName));
        $("div[class='slick-cell l3 r3']").shouldBe(visible).shouldHave(text(changeRequestStatus));
    }
}
