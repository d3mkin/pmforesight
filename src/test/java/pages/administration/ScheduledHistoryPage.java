package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ScheduledHistoryPage extends AbstractAdminPage {
    public ScheduledHistoryPage() {
        super(
                Configuration.baseUrl + URLS.SCHEDULED_HISTORY,
                "Запланированные задачи",
                $(".f-page__grid-name"),
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
