package tests.regulatory_documents;

import helpers.TestSuiteName;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import model.User;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.elements.ControlPanel;
import pages.index.IndexPage;
import tests.BaseTest;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofMillis;

@Story(TestSuiteName.REGULATORY_DOCUMENTS)
@Tag("regulatory")
public class RegulatoryDocumentsTests extends BaseTest {

    private SingInPage singIn;
    private IndexPage index;
    private ControlPanel controlPanel;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        index = new IndexPage();
        this.controlPanel = new ControlPanel();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Проверка количества нормативных документов")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkDocumentsCount(User user) {
        open("https://npdemo.pmforesight.ru/");
        singIn.asUser(user);
        index.widgetPanel().shouldHaveHeaderText("Информационная панель");
        index.widgetPanel().shouldBeVisibleAndHaveAnyWidgets();
        openDocumentsTab();
        changeView("Все нормативные документы");
        checkDocumentsSize(12);
    }

    @Step("Открыть 'Нормативные документы' в Базе знаний")
    public void openDocumentsTab() {
        open("https://npdemo.pmforesight.ru/page/register?view=LegalDoc");
        checkPageIsLoaded();
    }

    @Step ("Проверить что страница загрузилась")
    public void checkPageIsLoaded () {
        sleep(1000);
        $$(".k-loading-image").shouldBe(size(0), ofMillis(timeout));
        $(".k-loading-mask").shouldNotBe(exist, ofMillis(timeout));
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
        $x("//span[contains(text(),'"+viewName+"')]").shouldBe(visible);
        $("#f-grid-viewlist .k-input").shouldHave(text(""+viewName+""));
    }

    @Step ("Проверить кол-во документов в заголовке и в реестре")
    public void checkDocumentsSize(int expectedCount) {
        String titleCount = $(".slick-group-title span").getText().replaceAll("\\(|\\)", "");
        Assert.assertTrue("Не совпадает количество документов", Integer.parseInt(titleCount) == expectedCount);
        $(".slick-group-toggle").click();
        $$(".ui-widget-content.slick-row").shouldHave(size(expectedCount + 1));
    }
}

