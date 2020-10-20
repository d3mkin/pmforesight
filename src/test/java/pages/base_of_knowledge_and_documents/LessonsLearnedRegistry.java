package pages.base_of_knowledge_and_documents;

import com.codeborne.selenide.*;
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
 * Извлеченные уроки
 */
public class LessonsLearnedRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=Lesson";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");
    private SelenideElement ComboBoxTypeSearch = $(By.xpath("//span[contains(@aria-live,\"polite\")]"));
    private SelenideElement TypeCSearchAll = $(By.xpath("//li[contains(@class,\"k-item\") and text()='Уроки моих активностей']"));
    private SelenideElement groupCheckBox = $("span.slick-group-select-checkbox");
    private SelenideElement firstFoundCheckBox = $(".slick-cell-checkboxsel input");
    private ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row");
    private SelenideElement expandResult = $(By.xpath("//span[contains(@class,\"slick-group-toggle collapsed\")]"));
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public LessonsLearnedRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Извлеченные уроки' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть 'Извлеченные уроки' через меню")
    public void openFromMenu() {
        mainMenu.knowledgeBaseAndDocuments().openLessons();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Извлеченные уроки'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public LessonsLearnedRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public LessonsLearnedRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Извлеченные уроки"));
        return this;
    }


    @Step("Проверка отображения табличной части")
    public LessonsLearnedRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Выбор типа поиска из выподающего списка все уроки")
    public void chooseTypeOfSearchAll() {
        ComboBoxTypeSearch.click();
        TypeCSearchAll.waitUntil(visible, 2000);
        TypeCSearchAll.click();
    }

    @Step("Осуществить поиск извлеченных уроков")
    public void searchLesson(String value) {
        sleep(3000);
        controlPanel.typeSearchValue(value);
    }

    @Step("Выбрать первую запись")
    public void selectFirstRow() {
        firstFoundCheckBox.click();
    }

    @Step("Нажать кнопку удалить в реестре")
    public void clickDelete() {
        controlPanel.clickDelete();
    }

    @Step("Подтвердить удаление")
    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }

    @Step("Выбрать первую запись")
    public void expandResult() {
        expandResult.click();
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
    }

    @Step ("Проверить что Извлеченный урок не отображается в реестре")
    public void checkLessonNotExist(String lessonName){
        checkRegistryIsLoaded();
        searchLesson(lessonName);
        shouldNotHaveResults();
    }

    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible);
        tableWithEntities.shouldBe(visible);
    }
}
