package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import helpers.URLS;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class EntitySchemaPage extends AbstractAdminPage {
    public EntitySchemaPage() {
        super(
                Configuration.baseUrl + URLS.ENTITY_SCHEMA,
                "Карта сущностей",
                $(".f-page__name"),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка открытия страницы 'Карты сущностей'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }

    @Override
    @Step("Проверка отображения наименования страницы")
    public void shouldHavePageName() {
        name.shouldHave(text("Карта сущностей"));
    }
}
