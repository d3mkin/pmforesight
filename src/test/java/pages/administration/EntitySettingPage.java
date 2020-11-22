package pages.administration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO
public class EntitySettingPage extends AbstractAdminPage {
    public EntitySettingPage() {
        super(
                Configuration.baseUrl + URLS.ENTITY_SETTING,
                "Настройка сущностей",
                $(".f-page__name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Сущности'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
