package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ReportCollectionFormPage extends AbstractAdminPage {
    public ReportCollectionFormPage() {
        super(
                Configuration.baseUrl + URLS.REPORT_COLLECTION_FORM,
                "Отчетные формы",
                $(By.xpath("//a[text()=\"Отчетные формы\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Отчетные формы'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
