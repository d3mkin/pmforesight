package pages.auth;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutPage {

    private String url = Configuration.baseUrl + "/logout";
    private SelenideElement byeText = $(By.xpath("//span[contains(text(),'До свидания')]"));
    private SelenideElement logoutMessage = $(By.xpath("//span[contains(text(),'До свидания')]"));

    public void open() {
        Selenide.open(url);
    }

    @Step("Проверка перехода на страницу выхода")
    public void shouldHaveCorrectLink() {
        assertEquals(url, WebDriverRunner.url());
    }

    @Step("Проверка отображения прощания и сообщения о выходе")
    public void shouldHaveLogoutMessages() {
        byeText.shouldBe(visible);
        logoutMessage.shouldBe(visible);
    }
}
