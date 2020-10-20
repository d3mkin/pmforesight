package pages.сommunications.meeting;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.Meeting;
import pages.elements.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class MeetingPage extends BasePage {
    //создание совещания
    private SelenideElement nameMeeting = $(By.cssSelector("input[id='Name']"));
    private SelenideElement placeMeeting = $(By.cssSelector("#Place"));
    private SelenideElement datesMeeting = $(By.cssSelector("span[data-increment='14']"));
    private SelenideElement controlObjectList = $(By.cssSelector("#control-group-ActivityId span.k-widget.k-dropdown"));
    private SelenideElement tabRoles = $(By.cssSelector("#tab-roles > a"));
    private SelenideElement chairList = $(By.cssSelector("#control-group-Chairman span.k-widget.k-dropdown"));
    private SelenideElement secretaryList = $(By.cssSelector("#control-group-Secretary span.k-widget.k-dropdown"));

    public void fillFields(Meeting meeting) {
        typeOrSkip(nameMeeting, meeting.getName());
        typeOrSkip(placeMeeting, meeting.getLocation());
        datesMeeting.click();
        searchInSelectAndClickToFirstWithCheckDropDown(controlObjectList, meeting.getControlObject());
        tabRoles.click();
        searchInSelectAndClickToFirstWithCheckDropDown(secretaryList, meeting.getSecretary());
        searchInSelectAndClickToFirstWithCheckDropDown(chairList, meeting.getChairman());
    }



}
