package pages.risk_management.risks_and_opportunities;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.RisksAndOpportunities;
import pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RisksAndOpportunitiesPage extends BasePage {
    //Вкладка общая информация
    private final SelenideElement riskAndOpportunitiesNameInput = $x("//input[@id='Name']");
    private final SelenideElement controlObjectSelect = $("#control-group-ActivityId .k-dropdown");
    private final SelenideElement typeRisksOpportunitiesSelect = $("#control-group-RiskTypeId");
    private final SelenideElement categoryRSelect = $("#control-group-RiskCategoryId");
    private final SelenideElement stateSelect = $("#control-group-ActivityPhaseId");
    private final SelenideElement riskProbabilitySelect = $("#control-group-RiskProbabilityId");
    private final SelenideElement levelOfInfluenceSelect = $("#control-group-RiskImpactId");
    private final SelenideElement approvingDocumentSelect = $("#control-group-ApprovingDocumentId");
    //Вкладка роли
    private final SelenideElement tabRoles = $(By.linkText("Роли"));
    private final SelenideElement initiatorSelect = $x("//span[contains(@aria-labelledby,'Initiator_label')]");
    private final SelenideElement responsibleSelect = $x("//span[contains(@aria-owns,'Responsible_listbox')]");


    public void fillFields(RisksAndOpportunities risksAndOpportunities) {
        riskAndOpportunitiesNameInput.waitUntil(visible, 10000);
        typeText(riskAndOpportunitiesNameInput, risksAndOpportunities.getRisksAndOpportunitiesName());
        searchAndSelectFirstFromSelect(controlObjectSelect, risksAndOpportunities.getControlObject());
        searchAndSelectFirstFromSelect(typeRisksOpportunitiesSelect, risksAndOpportunities.getTypeRisksOpportunities());
        searchAndSelectFirstFromSelect(categoryRSelect, risksAndOpportunities.getCategory());
        searchAndSelectFirstFromSelect(stateSelect, risksAndOpportunities.getState());
        searchAndSelectFirstFromSelect(riskProbabilitySelect, risksAndOpportunities.getRiskProbability());
        searchAndSelectFirstFromSelect(levelOfInfluenceSelect, risksAndOpportunities.getlevelOfInfluence());
        approvingDocumentSelect.shouldBe(visible);
        searchAndSelectFirstFromSelect(approvingDocumentSelect, risksAndOpportunities.getApprovingDocument());
        tabRoles.click();
        sleep(2000);
        searchAndSelectFirstFromSelect(initiatorSelect, risksAndOpportunities.getInitiator());
        searchAndSelectFirstFromSelect(responsibleSelect, risksAndOpportunities.getResponsible());
    }
}
