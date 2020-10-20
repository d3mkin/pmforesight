package pages.managementobjects.contracts;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.Contract;
import pages.elements.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class ContractPage extends BasePage {
    private final String name = "Контракт";
    //Вкладка общая информация
    private SelenideElement tabCommonInfo = $(By.linkText("Общая информация"));
    private SelenideElement contractNumberInput = $("#Number");
    private SelenideElement contractNameInput = $("#Name");
    private SelenideElement contractShortNameInput = $("#ShortName");
    private SelenideElement projectSelect = $("#control-group-ParentId");
    private SelenideElement typeSelect = $("#control-group-ContractTypeId");
    private SelenideElement lawSelect = $("#control-group-ContractFLId");
    private SelenideElement wrapExceptedPrice = $("#control-group-PlanSum .k-numerictextbox");
    private SelenideElement exceptedPriceInput = $("#PlanSum");
    private SelenideElement wrapActualPrice = $("#control-group-RealSum .k-numerictextbox");
    private SelenideElement actualPriceInput = $("#RealSum");
    private SelenideElement descriptionArea = $("#Description");
    private SelenideElement linkToEtpInput = $("#ProcurementLink");

    //Вкладка роли
    private SelenideElement tabRoles = $(By.linkText("Роли"));
    private SelenideElement customerSelect = $("#control-group-Owner");
    private SelenideElement executorSelect = $("#control-group-ContractorId");
    private SelenideElement responsiblePersonSelect = $("#control-group-Leader");
    private SelenideElement adminsMultiSelect = $("#control-group-Administrator");
    private SelenideElement membersMultiSelect = $("#control-group-WorkGroup");


    public void fillFields(Contract contract) {
        tabCommonInfo.click();
        typeOrSkip(contractNumberInput, contract.getContractNumber());
        typeOrSkip(contractNameInput, contract.getName());
        typeOrSkip(contractShortNameInput, contract.getShortName());
        searchInSelectAndClickToFirst(projectSelect, contract.getProject());
        searchInSelectAndClickToFirst(typeSelect, contract.getContractType());
        searchInSelectAndClickToFirst(lawSelect, contract.getLaw());
        typeOrSkipNumeric(wrapExceptedPrice, exceptedPriceInput, contract.getExceptedPrice());
        typeOrSkipNumeric(wrapActualPrice, actualPriceInput, contract.getActualPrice());
        typeOrSkip(descriptionArea, contract.getDescription());
        typeOrSkip(linkToEtpInput, contract.getLinkToEtp());

        tabRoles.click();
        searchInSelectAndClickToFirst(customerSelect, contract.getCustomer());
        searchInSelectAndClickToFirst(executorSelect, contract.getExecutor());
        searchInSelectAndClickToFirst(responsiblePersonSelect, contract.getResponsiblePerson());
//        searchInSelectAndClickToFirst(adminsMultiSelect, contract.getAdmins().get(0));
//        searchInSelectAndClickToFirst(membersMultiSelect, contract.getMembers().get(0));
    }

}
