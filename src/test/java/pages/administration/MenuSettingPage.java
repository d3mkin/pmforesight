package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class MenuSettingPage extends AbstractAdminPage {
    public MenuSettingPage() {
        super(
                Configuration.baseUrl + URLS.MENU_SETTING,
                "Меню. Список",
                $(By.xpath("//a[text()=\"Меню. Список\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Настройка меню'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
