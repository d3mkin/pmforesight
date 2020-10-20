package pages.directories.external_organization;

import com.codeborne.selenide.SelenideElement;
import model.ExternalOrganizations;
import pages.elements.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class CreateExternalOrganizationsPage extends BasePage {

    public SelenideElement nameArea = $("textarea[name=\"Name\"]");
    public SelenideElement descriptionArea = $("textarea[name=\"Description\"]");
    public SelenideElement contacts = $("textarea[name=\"Contacts\"]");


    public void fillFields(ExternalOrganizations externalorganizations) {
        sleep(3000);
        typeOrSkip(nameArea, externalorganizations.getName());
        typeOrSkip(descriptionArea, externalorganizations.getDescription());
        typeOrSkip(contacts, externalorganizations.getContacts());
    }
}
