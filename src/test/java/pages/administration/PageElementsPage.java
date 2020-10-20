package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class PageElementsPage extends AbstractAdminPage {
    public PageElementsPage() {
        super(
                Configuration.baseUrl + URLS.PAGE_ELEMENTS,
                "MetaPageElement",
                $(By.xpath("//a[text()=\"MetaPageElement\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Элементы страницы'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
