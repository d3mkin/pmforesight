package pages.managementobjects.nonprojectevent;

import com.codeborne.selenide.SelenideElement;
import model.NonProjectEvent;
import pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class NonProjectEventPage extends BasePage {
    private final SelenideElement eventNameInput = $("#Name");
    private final SelenideElement parentEntityInput = $("#control-group-ParentId span.k-widget.k-dropdown");
    private final SelenideElement tabRoles = $("#tab2");
    private final SelenideElement curatorRole = $("#control-group-Customer span.k-widget.k-dropdown");
    private final SelenideElement leaderRole = $("#control-group-Leader span.k-widget.k-dropdown");


    public void fillFields(NonProjectEvent event) {
        typeText(eventNameInput, event.getName());
        searchAndSelectFirstFromSelect(parentEntityInput, event.getParentEntity());
        tabRoles.click();
        curatorRole.waitUntil(visible, 1000);
        searchAndSelectFirstFromSelect(curatorRole, event.getCurator());
        sleep(500);
        searchAndSelectFirstFromSelect(leaderRole, event.getLeader());
    }
}
