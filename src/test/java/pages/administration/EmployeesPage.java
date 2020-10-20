package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class EmployeesPage extends AbstractAdminPage {

    public EmployeesPage() {
        super(
                Configuration.baseUrl + URLS.EMPLOYEES,
                "Пользователь",
                $(By.xpath("//a[text()=\"Сотрудники\"]")),
                $(".f-grid")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Сотрудники'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
