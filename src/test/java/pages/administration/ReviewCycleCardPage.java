package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ReviewCycleCardPage extends AbstractAdminPage {
    public ReviewCycleCardPage() {
        super(
                Configuration.baseUrl + URLS.REVIEW_CYCLE_CARD,
                "Карточки ЗИ",
                $(".f-page__grid-name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Карточки ЗИ'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
