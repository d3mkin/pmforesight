package pages.administration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class TypicalPointCheckListPage extends AbstractAdminPage {
    public TypicalPointCheckListPage() {
        super(
                Configuration.baseUrl + URLS.TYPICAL_POINT_CHECK_LIST,
                "Типовой чек-лист",
                $(By.xpath("//a[text()=\"Типовой чек-лист\"]")),
                $(".f-grid")
        );
    }

    @Step("Открыть реестр 'Типовой чек-лист' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Step("Проверка отображения страницы 'Типовой чек-лист'")
    @Override
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
