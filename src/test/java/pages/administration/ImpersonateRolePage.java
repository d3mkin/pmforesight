package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

/**
 * Олицетворение подразделений
 */
public class ImpersonateRolePage extends AbstractAdminPage {
    public ImpersonateRolePage() {
        super(
                Configuration.baseUrl + URLS.IMPERSONATE_ROLE,
                "Олицетворение подразделения",
                $(".f-page__grid-name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Замещение'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
