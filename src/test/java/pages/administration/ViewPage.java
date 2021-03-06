package pages.administration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewPage extends AbstractAdminPage {
    public ViewPage() {
        super(
                Configuration.baseUrl + URLS.VIEW,
                "Настройка представлений",
                $(".f-page__name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 403 при открытии страницы 'Представления'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
