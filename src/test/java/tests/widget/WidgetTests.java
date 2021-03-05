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
}
