package pages.administration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateSystemPage extends AbstractAdminPage {
    public UpdateSystemPage() {
        super(
                Configuration.baseUrl + URLS.SCHEDULED_HISTORY,
                "Установка обновлений",
                $(By.xpath("//a[text()=\"Установка обновлений\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 403 при открытии страницы 'Установка обновлений'")
    public void shouldBePage() {
        assertTrue(WebDriverRunner.url().contains("Error/403"));
    }
}
