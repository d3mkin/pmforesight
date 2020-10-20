package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import helpers.URLS;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SetsAccessMatrixPage extends AbstractAdminPage {

    public SetsAccessMatrixPage() {
        super(
                Configuration.baseUrl + URLS.SETS_ACCESS_MATRIX,
                "Наборы матрицы доступа",
                $(".f-page__name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Наборы матрицы доступа'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }

    @Override
    @Step("Проверка отображенияя имени страницы")
    public void shouldHavePageName() {
        name.shouldHave(text("Наборы матрицы доступа"));
    }
}
