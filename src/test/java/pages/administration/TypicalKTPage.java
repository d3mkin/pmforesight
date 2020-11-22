package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class TypicalKTPage extends AbstractAdminPage {
    public TypicalKTPage() {
        super(Configuration.baseUrl + URLS.TYPICAL_KT,
                "Типовая контрольная точка",
                $(".f-page__grid-name"),
                $(".f-grid"));
    }

    @Step("Проверка отображения страницы '{pageTitle}'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
