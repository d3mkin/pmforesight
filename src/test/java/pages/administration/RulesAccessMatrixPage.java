package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class RulesAccessMatrixPage extends AbstractAdminPage {
    public RulesAccessMatrixPage() {
        super(
                Configuration.baseUrl + URLS.RULES_ACCESS_MATRIX,
                "Правило матрицы доступа",
                $(By.xpath("//a[text()=\"Правило матрицы доступа\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Правила матрицы доступа'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
