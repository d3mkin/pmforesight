package pages.managementobjects.contracts;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.Contract;
import pages.BasePage;

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
    private SelenideElement customerSelect = $("#control-group-Owner .k-input");
    private SelenideElement executorSelect = $("#control-group-ContractorId .k-input");
    private SelenideElement responsiblePersonSelect = $("#control-group-Leader .k-input");
    private SelenideElement adminsMultiSelect = $("#control-group-Administrator .k-input");
    private SelenideElement membersMultiSelect = $("#control-group-WorkGroup .k-input");


    public void fillFields(Contract contract) {
        tabCommonInfo.click();
        typeText(contractNumberInput, contract.getContractNumber());
        typeText(contractNameInput, contract.getName());
        typeText(contractShortNameInput, contract.getShortName());
        searchAndSelectFirstFromSelect(projectSelect, contract.getProject());
        searchAndSelectFirstFromSelect(typeSelect, contract.getContractType());
        searchAndSelectFirstFromSelect(lawSelect, contract.getLaw());
        typeNumeric(wrapExceptedPrice, exceptedPriceInput, contract.getExceptedPrice());
        typeNumeric(wrapActualPrice, actualPriceInput, contract.getActualPrice());
        typeText(descriptionArea, contract.getDescription());
        typeText(linkToEtpInput, contract.getLinkToEtp());

        tabRoles.click();
        searchAndSelectFirstFromSelect(customerSelect, contract.getCustomer());
//        searchAndSelectFirstFromSelect(executorSelect, contract.getExecutor());
        searchAndSelectFirstFromSelect(responsiblePersonSelect, contract.getResponsiblePerson());
//        searchAndSelectFirstFromSelect(adminsMultiSelect, contract.getAdmins().get(0));
//        searchAndSelectFirstFromSelect(membersMultiSelect, contract.getMembers().get(0));
    }

}
