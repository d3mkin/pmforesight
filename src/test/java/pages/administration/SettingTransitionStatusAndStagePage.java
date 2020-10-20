package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class SettingTransitionStatusAndStagePage extends AbstractAdminPage {

    public SettingTransitionStatusAndStagePage() {
        super(Configuration.baseUrl + URLS.SETTING_TRANSITION_STATUS_AND_STAGES,
                "Переход между статусами. Настройка",
                $(By.xpath("//a[text()=\"Переход между статусами. Настройка\"]")),
                $(".f-grid"));
    }

    @Step("Проверка открытия реестра 'Переход между статусами. Настройка' по прямой ссылке")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
