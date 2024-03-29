package pages.administration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractAdminPage {
    protected String url;
    protected String pageTitle;
    protected SelenideElement name;
    protected SelenideElement content;

    public AbstractAdminPage(String url, String pageTitle, SelenideElement name, SelenideElement content) {
        this.url = url;
        this.pageTitle = pageTitle;
        this.name = name;
        this.content = content;
    }

    @Step("Открыть реестр '{pageTitle}' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    public abstract void shouldBePage();

    @Step("Проверка отображения корректности урла и заголовка страницы")
    public void shouldHaveRightUrlAndTitle() {
        $(".k-loading-mask").shouldBe(disappear, Duration.ofMinutes(5));
        $("title").shouldHave(attribute("text", pageTitle), Duration.ofMillis(Configuration.timeout));
        assertEquals(url, WebDriverRunner.url(),
                "Урл страницы не соответствует " + url);
    }

    @Step("Проверка отображения контента на странице")
    public void shouldHaveContent() {
        content.shouldBe(visible);
    }

    @Step("Проверка отображения названия реестра")
    public void shouldHavePageName() {
        name.shouldBe(visible);
    }
}
