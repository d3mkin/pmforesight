package pages.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class SystemInformationPage extends AbstractAdminPage {
    public SystemInformationPage() {
        super(
                Configuration.baseUrl + URLS.SYSTEM_INFORMATION,
                "Система",
                $(".f-page__name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Правила матрицы доступа'")
    public void shouldBePage() {
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }

    @Override
    public void shouldHavePageName() {
        name.shouldHave(Condition.text("Система"));
    }
}
