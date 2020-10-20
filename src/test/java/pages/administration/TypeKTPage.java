package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class TypeKTPage extends AbstractAdminPage {
    public TypeKTPage() {
        super(Configuration.baseUrl + URLS.TYPE_KT,
                "Тип КТ",
                $(".f-page__grid-name"),
                $(".f-grid"));
    }

    @Step("Проверка отображения страницы '{pageTitle}'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
