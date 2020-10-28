package tests.authorization;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.index.IndexPage;
import tests.BaseTest;
import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.AUTH)
@Tag("Authorization")
@Tag("Regression")
public class AuthorizationTests extends BaseTest {

    private SingInPage singIn;
    private IndexPage index;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        index = new IndexPage();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }


    @ParameterizedTest (name = "Авторизация пользователя с валидным логином и паролем")
    @MethodSource("helpers.UserProvider#UsersFA")
    @TmsLink("161")
    //TODO: Добавить тэг с тикетом JIRA
    public void LoginWithRightCredentialsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        index.shouldBeIndexPage();
        index.widgetPanel().shouldHaveHeaderText("Информационная панель");
        index.widgetPanel().shouldBeVisibleAndHaveAnyWidgets();
    }

    @ParameterizedTest (name = "Выход из системы")
    @MethodSource("helpers.UserProvider#UsersFA")
    @TmsLink("163")
    //TODO: Добавить тэг с тикетом JIRA
    public void logOutTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        LogoutPage lg = index.header().logout();
        lg.shouldHaveCorrectLink();
        lg.shouldHaveLogoutMessages();
    }

    @ParameterizedTest (name = "Авторизация пользователя с НЕ валидным логином и паролем")
    @MethodSource("helpers.UserProvider#UsersFA")
    @TmsLink("647")
    //TODO: Добавить тэг с тикетом JIRA
    public void LoginWithWrongCredentialsTest(User user) {
        parameter("Пользователь", user.getName());
        User wrongUser = new User(user.getLogin() + "wrong", user.getPassword());
        singIn.asUser(wrongUser);
        singIn.shouldHaveMessageAboutWrongCredentials();
        Selenide.clearBrowserCookies();

        singIn.open();
        User userWithRightLogin = new User(user.getLogin(), "wrong_password");
        singIn.asUser(userWithRightLogin);
        singIn.shouldHaveMessageAboutWrongCredentials();
    }

}
