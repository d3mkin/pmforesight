package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ReportEventsCalendarPage extends AbstractAdminPage {
    public ReportEventsCalendarPage() {
        super(
                Configuration.baseUrl + URLS.REPORT_EVENTS_CALENDAR,
                "Календарь отчетных мероприятий",
                $(By.xpath("//a[text()=\"Календарь отчетных мероприятий\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Календарь отчетных мероприятий'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
