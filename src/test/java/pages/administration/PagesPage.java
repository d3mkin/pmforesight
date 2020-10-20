package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class PagesPage extends AbstractAdminPage {
    public PagesPage() {
        super(
                Configuration.baseUrl + URLS.PAGES,
                "MetaPage",
                $(By.xpath("//a[text()=\"MetaPage\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Страницы'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
