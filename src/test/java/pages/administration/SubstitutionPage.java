package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

/**
 * Страница замещения
 */
public class SubstitutionPage extends AbstractAdminPage {
    public SubstitutionPage() {
        super(
                Configuration.baseUrl + URLS.SUBSTITUTION,
                "Замещение",
                $(By.xpath("//a[text()=\"Замещение\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Замещение'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
