package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class SystemTourPage extends AbstractAdminPage {
    public SystemTourPage() {
        super(
                Configuration.baseUrl + URLS.SYSTEM_TOUR,
                "Тур по странице",
                $(".f-page__grid-name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Тур по странице'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
