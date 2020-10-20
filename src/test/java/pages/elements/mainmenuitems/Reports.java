package pages.elements.mainmenuitems;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.reports.ReportsRegistry;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class Reports extends AbstractMenuItem {

    private SelenideElement reports = menu.$(By.cssSelector("li a[href=\"/page/reports\"]"));

    @Override
    public void expand() {
        reports.click();
        Selenide.sleep(1000);
    }

    @Override
    public Reports visible() {
        reports.shouldBe(visible);
        return this;
    }

    @Override
    public Reports hasCorrectText() {
        reports.shouldHave(text("Отчеты"));
        return this;
    }

    @Step("Открыть 'Репорты'")
    public ReportsRegistry openReports() {
        reports.click();
        return new ReportsRegistry();
    }
}
