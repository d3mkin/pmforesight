package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ReportsCategoriesPages extends AbstractAdminPage {
    public ReportsCategoriesPages() {
        super(
                Configuration.baseUrl + URLS.REPORTS_CATEGORIES,
                "ReportingCategory",
                $(By.xpath("//a[text()=\"ReportingCategory\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Категории отчетов'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
