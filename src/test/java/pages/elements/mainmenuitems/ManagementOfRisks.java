package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.risk_management.ImpactMeasureRegistry;
import pages.risk_management.ResponseActivitiesRegistry;
import pages.risk_management.RisksAndOpportunitiesRegistry;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class ManagementOfRisks extends AbstractMenuItem {

    private SelenideElement managementOfRisks = menu.$(By.cssSelector("li[f-menu-slide-menulistid=\"9\"]"));
    private SelenideElement risks = managementOfRisks.$("li[f-menu-slide-menulistid=\"10\"]");
    private SelenideElement riskMitigation = managementOfRisks.$("li[f-menu-slide-menulistid=\"11\"]");
    private SelenideElement riskResponding = managementOfRisks.$("li[f-menu-slide-menulistid=\"12\"]");

    @Step("Раскыть пункт 'Управление рисками'")
    @Override
    public void expand() {
        managementOfRisks.click();
        Selenide.sleep(1000);
    }

    @Step("Открыть 'Риски и возможности'")
    public RisksAndOpportunitiesRegistry openRisks() {
        clickItem(managementOfRisks, risks);
        return new RisksAndOpportunitiesRegistry();
    }

    @Step("Открыть 'Мероприятия воздействия'")
    public ImpactMeasureRegistry openRiskMitigation() {
        clickItem(managementOfRisks, riskMitigation);
        return new ImpactMeasureRegistry();
    }

    @Step("Открыть 'Мероприятия реагирования'")
    public ResponseActivitiesRegistry openRiskResponding() {
        clickItem(managementOfRisks, riskResponding);
        return new ResponseActivitiesRegistry();
    }

    @Override
    public ManagementOfRisks visible() {
        managementOfRisks.shouldBe(visible);
        return this;
    }

    @Override
    public ManagementOfRisks hasCorrectText() {
        managementOfRisks.shouldHave(text("Управление рисками"));
        return this;
    }
}
