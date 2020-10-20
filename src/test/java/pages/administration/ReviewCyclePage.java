package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class ReviewCyclePage extends AbstractAdminPage {
    public ReviewCyclePage() {
        super(
                Configuration.baseUrl + URLS.REVIEW_CYCLE,
                "Цикл согласования",
                $(By.xpath("//a[text()=\"Циклы согласования\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Циклы согласования'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
