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
                $(".f-page__grid-name"),
                $(".f-grid"));
    }

    @Step("Проверка открытия реестра 'Настройка переходов по статусам/стадиям' по прямой ссылке")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
