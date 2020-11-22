package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class HistoryTransitionStatusAndStagePage extends AbstractAdminPage {

    public HistoryTransitionStatusAndStagePage() {
        super(Configuration.baseUrl + URLS.HISTORY_TRANSITION_STATUS_AND_STAGES,
                "Переход между статусами. Комментарий",
                $(".f-page__grid-name"),
                $(".f-grid")
        );
    }

    @Step("Проверка отображения страницы {Переход между статусами. Комментарий}")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
