package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.monitoring_and_control.change_requests.ConsolidatedChangeRequestsRegistry;
import pages.monitoring_and_control.StatusReporRegistry;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MonitoringAndControl extends AbstractMenuItem {

    private SelenideElement monitoringAndControl = menu.$("li[f-menu-slide-menulistid='20047']");
    private SelenideElement statusReports = monitoringAndControl.$("li[f-menu-slide-menulistid=\"20042\"]");
    private SelenideElement requestsForChange = monitoringAndControl.$("li[f-menu-slide-menulistid=\"20045\"]");

    @Override
    public void expand() {
        monitoringAndControl.click();
        Selenide.sleep(1000);
    }

    @Step("Открыть статус отчеты")
    public StatusReporRegistry openStatusReports() {
        clickItem(monitoringAndControl, statusReports);
        return new StatusReporRegistry();
    }

    @Step("Открыть запросы на изменения")
    public ConsolidatedChangeRequestsRegistry openRequestsForChange() {
        clickItem(monitoringAndControl, requestsForChange);
        return new ConsolidatedChangeRequestsRegistry();
    }


    public MonitoringAndControl visible() {
        monitoringAndControl.shouldBe(visible);
        return this;
    }

    public MonitoringAndControl hasCorrectText() {
        monitoringAndControl.shouldHave(text("Мониторинг и контроль"));
        return this;
    }
}
