package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ScheduledTaskPage extends AbstractAdminPage {
    public ScheduledTaskPage() {
        super(
                Configuration.baseUrl + URLS.SCHEDULED_TASK,
                "Запланированные задачи",
                $(By.xpath("//a[text()=\"Запланированные задачи\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Запланированные задачи'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
