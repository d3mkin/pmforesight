package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class TemplatesCalendarPage extends AbstractAdminPage {
    public TemplatesCalendarPage() {
        super(Configuration.baseUrl + URLS.TEMPLATE_CALENDAR_PLAN,
                "Шаблон плана",
                $(".f-page__grid-name"),
                $(".f-grid")
        );
    }

    @Step("Проверка отображения страницы 'Шаблон плана'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
