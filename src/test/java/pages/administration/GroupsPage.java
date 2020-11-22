package pages.administration;

import com.codeborne.selenide.Configuration;
import helpers.URLS;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class GroupsPage extends AbstractAdminPage {

    public GroupsPage() {
        super(
                Configuration.baseUrl + URLS.GROUPS,
                "Группа",
                $(".f-page__grid-name"),
                $(".f-grid")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Группы'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
