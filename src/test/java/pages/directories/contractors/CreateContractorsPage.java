package pages.directories.contractors;

import com.codeborne.selenide.SelenideElement;
import model.Contractor;
import pages.elements.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class CreateContractorsPage extends BasePage {

    public SelenideElement nameArea = $("textarea[name=\"Name\"]");
    public SelenideElement descriptionArea = $("textarea[name=\"Description\"]");
    public SelenideElement INNInput = $("input[name=\"TIN\"]");
    public SelenideElement contacts = $("textarea[name=\"Contacts\"]");


    public void fillFields(Contractor contractor) {
        typeOrSkip(nameArea, contractor.getName());
        typeOrSkip(descriptionArea, contractor.getDescription());
        typeOrSkip(INNInput, contractor.getINN());
        typeOrSkip(contacts, contractor.getContacts());
    }
}
