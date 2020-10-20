package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class CardRolesPage extends AbstractAdminPage {

    public CardRolesPage() {
        super(
                Configuration.baseUrl + URLS.CARD_ROLES,
                "Роль",
                $(By.xpath("//a[text()=\"Реестр ролей карточек\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения реестра 'Сотрудники'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
