package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class UnitsPage extends AbstractAdminPage {

    public UnitsPage() {
        super(
                Configuration.baseUrl + URLS.UNITS,
                "Подразделения",
                $(".f-page__grid-name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Подразделения'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
