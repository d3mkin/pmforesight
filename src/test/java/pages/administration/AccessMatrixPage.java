package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import helpers.URLS;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class AccessMatrixPage extends AbstractAdminPage {
    public AccessMatrixPage() {
        super(
                Configuration.baseUrl + URLS.ACCESS_MATRIX,
                "Матрица доступа",
                $(".f-page__name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Матрица доступа'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }

    @Override
    public void shouldHavePageName() {
        name.shouldHave(text("Матрица доступа"));
    }
}
