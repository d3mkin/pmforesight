package pages.managementobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Этапы / Работы / КТ
 */
public class StagesWorksAndPointsRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=point";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = mainContainer.$("div.f-grid__grid");
    private SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement loadingImage = $ (By.xpath("//div[@class='k-loading-image']"));
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public StagesWorksAndPointsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Этапы / Работы / КТ' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Этапы / Работы / КТ' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openStagesWorksKT();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Этапы / Работы / КТ'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public StagesWorksAndPointsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public StagesWorksAndPointsRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Этапы / Работы / КТ"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public StagesWorksAndPointsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Осуществить поиск сущности в реестре")
    public void searchEntity(String entityName) {
        sleep(1000);
        controlPanel.typeSearchValue(entityName);
    }

    @Step("Проверка отображения совещания после создания записи")
    public void shouldHaveCreatedEntity() {
        firstFoundRow.shouldBe(visible);
        sleep(1000);
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allRows.shouldNot(visible);
    }

    //TODO Забрать метод в общий класс
    @Step ("Проверка что сущность не отображается в реестре")
    public void checkEntityNotExist(String entityName){
        checkRegistryIsLoaded();
        searchEntity(entityName);
        shouldNotHaveResults();
    }

    //TODO Забрать метод в общий класс
    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible);
        tableWithEntities.shouldBe(visible);
    }

    //TODO Забрать метод в общий класс
    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
    }
}
