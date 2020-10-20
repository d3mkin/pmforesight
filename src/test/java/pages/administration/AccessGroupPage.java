package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import helpers.URLS;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница доступ по группам
 */
public class AccessGroupPage extends AbstractAdminPage {
    public AccessGroupPage() {
        super(
                Configuration.baseUrl + URLS.ACCESS_GROUP,
                "Доступ по группам",
                $(".f-page__name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Доступ по группам'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }

    @Override
    @Step("Проверка отображенияя имени страницы")
    public void shouldHavePageName() {
        name.shouldHave(text("Доступ по группам"));
    }
}
