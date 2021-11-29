package tests.widget;

import helpers.TestSuiteName;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import model.User;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.index.IndexPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.WIDGETS)
@Tag("Regression")
@Tag("Smoke")
public class WidgetTests  extends BaseTest {
    private SingInPage singIn;
    IndexPage indexPage;

    @BeforeEach
    public void setupPages() {
        indexPage = new IndexPage();
        singIn = new SingInPage();
        singIn.open();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @Disabled
    @ParameterizedTest (name = "Создание и удаление виджета")
    @Tag("ATEST-185")
    @MethodSource("helpers.UserProvider#mainFA")
    public void createAndDeleteWidgetTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        int i = 1;
        indexPage.widgetPanel().waitUntilLoadHide();
        Boolean beforeIsMyProjectExpand = indexPage.widgetPanel().isMyProjectExpand();
        Boolean beforeIsMyContractExpand = indexPage.widgetPanel().isMyContractExpand();
        indexPage.widgetPanel().clickEdit();
        indexPage.widgetPanel().shouldHaveControlButtonsWhenEdit();
        indexPage.widgetPanel().shouldNotHideContentInMyProjectAndMyContract();
        indexPage.widgetPanel().shouldHaveButtonOnAllWidgets();
        indexPage.widgetPanel().clickAddWidget();
        indexPage.widgetPanel().shouldHaveNewWidgetWithRightButton();
        indexPage.widgetPanel().clickSave();
        indexPage.widgetPanel().shouldNotHaveButtonForEdit();
        Boolean afterIsMyProjectExpand = indexPage.widgetPanel().isMyProjectExpand();
        Boolean afterIsMyContractExpand = indexPage.widgetPanel().isMyContractExpand();
        Assert.assertSame("Блок Мои контракты не свернут после сохранения", beforeIsMyContractExpand,
                afterIsMyContractExpand);
        Assert.assertSame("Блок Мои проекты не свернут после сохранения", beforeIsMyProjectExpand,
                afterIsMyProjectExpand);
        indexPage.widgetPanel().shouldHaveControlButtonsWhenAddEnd();
        indexPage.widgetPanel().clickEdit();
        indexPage.widgetPanel().shouldHaveControlButtonsWhenEdit();
        indexPage.widgetPanel().waitUntilLoadHide();
        indexPage.widgetPanel().shouldNotHideContentInMyProjectAndMyContract();
        indexPage.widgetPanel().shouldHaveButtonOnAllWidgets();
        indexPage.widgetPanel().deleteLastAddWidget();
        indexPage.widgetPanel().shouldNotHaveNewWidget();
        indexPage.widgetPanel().clickSave();
        indexPage.widgetPanel().shouldNotHaveButtonForEdit();
        indexPage.widgetPanel().shouldHaveControlButtonsWhenAddEnd();
    }

    @ParameterizedTest (name = "Виджет Бюджета объекта госпрограммы. Значения с разрядностью \"тыс\" указываются некорректно на графике")
    @Tag("ATEST-186")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkBudgetStateProgramWidgetTest(User user) {

    }

    @ParameterizedTest (name = "Виджета статуса \"Отменено\" для ряда сущностей")
    @Tag("ATEST-187")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkCanceledStatusWidgetTest(User user) {

    }

    @ParameterizedTest (name = "Ошибка консоли в редактировании виджетов при нажатии на кнопку \"Не сохранять\"")
    @Tag("ATEST-188")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkConsoleErrorNonSaveWidgetTest(User user) {

    }

    @Disabled
    @ParameterizedTest (name = "При сворачивании виджетов Предложения по проекту они наслаиваются друг на друга")
    @Tag("ATEST-189")
    @MethodSource("helpers.UserProvider#mainFA")
    public void projectProposalsCollapseWidgetTest(User user) {

    }

    @ParameterizedTest (name = "Ошибка при переходе в виджет \"Панель руководителя\"")
    @Tag("ATEST-190")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkManagerPanelWidgetTest(User user) {

    }

    @ParameterizedTest (name = "Сектор Подтверждено на круговой диаграмме \"Мои КТ\"")
    @Tag("ATEST-191")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkMyPointsWidgetTest(User user) {

    }

    @ParameterizedTest (name = "Виджет \"Мои сообщения\": отображение сообщений из сущностей пользователя и личных сообщений")
    @Tag("ATEST-192")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkMyMessagesWidgetTest(User user) {

    }

    @ParameterizedTest (name = "При создании Портфеля (из реестра) его статус - \"Отменен\" в виджете в карточке просмотра")
    @Tag("ATEST-194")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkPortfolioStatusWidgetTest(User user) {

    }

    @ParameterizedTest (name = "Отмена компонентов портфеля (программы, проекты) не влияет на его индикатор в виджете")
    @Tag("ATEST-195")
    @MethodSource("helpers.UserProvider#mainFA")
    public void checkGantIndicatorsAfterCancelPortfolioTest(User user) {

    }
}
