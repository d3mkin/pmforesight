package pages.risk_management.risks_and_opportunities;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.RisksAndOpportunities;
import pages.elements.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RisksAndOpportunitiesPage extends BasePage {
    //Вкладка общая информация
    private final SelenideElement riskAndOpportunitiesNameInput = $(By.xpath("//input[@id='Name']"));
    private final SelenideElement controlObjectSelect = $(By.cssSelector("#control-group-ActivityId"));
    private final SelenideElement typeRisksOpportunitiesSelect = $(By.cssSelector("#control-group-RiskTypeId"));
    private final SelenideElement categoryRSelect = $(By.cssSelector("#control-group-RiskCategoryId"));
    private final SelenideElement stateSelect = $(By.id("control-group-ActivityPhaseId"));
    private final SelenideElement riskProbabilitySelect = $(By.id("control-group-RiskProbabilityId"));
    private final SelenideElement levelOfInfluenceSelect = $(By.id("control-group-RiskImpactId"));
    private final SelenideElement approvingDocumentSelect = $(By.id("control-group-ApprovingDocumentId"));
    //Вкладка роли
    private final SelenideElement tabRoles = $(By.linkText("Роли"));
    private final SelenideElement initiatorSelect = $(By.xpath("//span[contains(@aria-labelledby,\"Initiator_label\")]"));
    private final SelenideElement responsibleSelect = $(By.xpath("//span[contains(@aria-owns,\"Responsible_listbox\")]"));


    public void fillFields(RisksAndOpportunities risksAndOpportunities) {
        riskAndOpportunitiesNameInput.waitUntil(visible, 10000);
        typeOrSkip(riskAndOpportunitiesNameInput, risksAndOpportunities.getRisksAndOpportunitiesName());
        searchInSelectAndClickToFirstWithCheckDropDown(controlObjectSelect, risksAndOpportunities.getControlObject());
        searchInSelectAndClickToFirstWithCheckDropDown(typeRisksOpportunitiesSelect, risksAndOpportunities.getTypeRisksOpportunities());
        searchInSelectAndClickToFirstWithCheckDropDown(categoryRSelect, risksAndOpportunities.getCategory());
        searchInSelectAndClickToFirstWithCheckDropDown(stateSelect, risksAndOpportunities.getState());
        searchInSelectAndClickToFirstWithCheckDropDown(riskProbabilitySelect, risksAndOpportunities.getRiskProbability());
        searchInSelectAndClickToFirstWithCheckDropDown(levelOfInfluenceSelect, risksAndOpportunities.getlevelOfInfluence());
        approvingDocumentSelect.shouldBe(visible);
        searchInSelectAndClickToFirst(approvingDocumentSelect, risksAndOpportunities.getApprovingDocument());
        tabRoles.click();
        initiatorSelect.waitUntil(visible, 10000);
        searchInSelectAndClickToFirstWithCheckDropDown(initiatorSelect, risksAndOpportunities.getInitiator());
        responsibleSelect.waitUntil(visible, 10000);
        searchInSelectAndClickToFirstWithCheckDropDown(responsibleSelect, risksAndOpportunities.getResponsible());
    }
}
