package pages.auth;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutPage {

    private String url = Configuration.baseUrl + "/logout";
    private SelenideElement logoutTitle = $(".f-logout__title");
    private SelenideElement logoutMessage = $(".f-logout__message");

    public void open() {
        Selenide.open(url);
    }

    @Step("Проверка перехода на страницу выхода")
    public void shouldHaveCorrectLink() {
        assertEquals(url, WebDriverRunner.url());
    }

    @Step("Проверка отображения прощания и сообщения о выходе")
    public void shouldHaveLogoutMessages() {
        logoutTitle.shouldHave(text("До свидания"));
        logoutMessage.shouldHave(text("Вы успешно вышли из системы"));
    }
}
