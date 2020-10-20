package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

/**
 * Админка страница стадий
 */
public class StagesPage extends AbstractAdminPage {

    public StagesPage() {
        super(
                Configuration.baseUrl + URLS.STAGES,
                "Стадии",
                $(".f-page__grid-name"),
                $(".f-grid")
        );
    }

    @Step("Проверка отображения страницы 'Стадии'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
