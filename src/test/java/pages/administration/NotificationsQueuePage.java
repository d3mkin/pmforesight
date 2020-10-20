package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;

import static com.codeborne.selenide.Selenide.$;

public class NotificationsQueuePage extends AbstractAdminPage {
    public NotificationsQueuePage() {
        super(
                Configuration.baseUrl + URLS.NOTIFICATIONS_QUEUE,
                "Письмо",
                $(By.xpath("//a[text()=\"Последние 200 писем\"]")),
                $("#mainBodyContainer")
        );
    }

    @Override
    @Step("Проверка отображения страницы 'Очередь рассылки'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
    }
}
