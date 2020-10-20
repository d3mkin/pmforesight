package pages.auth;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Credentials;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.User;
import pages.index.IndexPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SingInPage {
    private String url = Configuration.baseUrl + "/Login?ReturnUrl=%2F";
    private SelenideElement username = $("#name");
    private SelenideElement password = $("#password");
    private SelenideElement submit = $(By.xpath("//*[@id='login']"));
    private SelenideElement label = $("label.k-checkbox-label");
    private SelenideElement errorMessage = $("div.validation-summary-errors");

    @Step("Открыть страницу авторизации")
    public void open() {
        Selenide.open(url);
    }

    @Step("Ввести логин {username}")
    public void typeUsername(String username) {
        this.username.setValue(username);
    }

    @Step("Ввести пароль {password}")
    public void typePassword(String password) {
        this.password.setValue(password);
    }

    @Step("Нажать кнопку Войти")
    public void submitForm() {
        submit.submit();
    }

    @Step("Нажать на чек бокс \"запомнить меня\"")
    public void clickLabelRememberMe() {
        label.click();
    }

    @Step("Должно появиться сообщение{0}")
    public void shouldUserIsNotFound(String userIsNotFoundError) {
        errorMessage.shouldHave(text(userIsNotFoundError));
    }

    @Step("Авторизоваться как {username}")
    public IndexPage as(String username, String password) {
        typeUsername(username);
        typePassword(password);
        submitForm();
        return new IndexPage();
    }

    @Step("Авторизоваться как {credentials.login}")
    public IndexPage as(Credentials credentials) {
        typeUsername(credentials.login);
        typePassword(credentials.password);
        submitForm();
        return new IndexPage();
    }

    @Step("Авторизоваться как {user.name}")
    public void asUser(User user) {
        typeUsername(user.getLogin());
        typePassword(user.getPassword());
        submitForm();
    }

    @Step("Проверка отображения сообщения 'Пользователь не найден или неверный пароль'")
    public void shouldHaveMessageAboutWrongCredentials() {
        errorMessage.shouldHave(text("Пользователь не найден или неверный пароль"));
    }
}