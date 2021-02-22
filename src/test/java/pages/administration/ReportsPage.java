package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ReportsPage extends AbstractAdminPage {
    public ReportsPage() {
        super(
                Configuration.baseUrl + URLS.REPORTS,
                "Отчетность",
                $(".f-page__grid-name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Отчеты'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
