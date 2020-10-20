package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class SystemLogsPage extends AbstractAdminPage {
    public SystemLogsPage() {
        super(
                Configuration.baseUrl + URLS.SYSTEM_LOGS,
                "Логи",
                $(By.xpath("//a[text()=\"Логи\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Логи'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
