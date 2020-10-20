package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class IndicatorsPage extends AbstractAdminPage {

    public IndicatorsPage() {
        super(Configuration.baseUrl + URLS.INDICATORS,
                "Индикаторы сущностей",
                $(By.xpath("//a[text()=\"Индикаторы сущностей\"]")),
                $(".f-grid")
        );
    }

    @Step("Проверка отображения страницы 'Индикаторы сущностей'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
