package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.сommunications.orders.OrderRegistry;
import pages.сommunications.meeting.MeetingsRegistry;
import pages.сommunications.open_questions.OpenQuestionsRegistry;
import pages.сommunications.QuestionsAndMeetingsRegistry;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class Communication extends AbstractMenuItem {

    private SelenideElement communication = menu.$(By.cssSelector("li[f-menu-slide-menulistid=\"5\"]"));
    private SelenideElement orders = communication.$(By.cssSelector("li[f-menu-slide-menulistid=\"6\"]"));
    private SelenideElement meetings = communication.$(By.cssSelector("li[f-menu-slide-menulistid=\"7\"]"));
    private SelenideElement meetingQuestions = communication.$(By.cssSelector("li[f-menu-slide-menulistid=\"8\"]"));
    private SelenideElement openQuestions = communication.$(By.cssSelector("li[f-menu-slide-menulistid=\"10023\"]"));

    @Step("Раскрыть меню 'Коммуникации'")
    public void expand() {
        communication.click();
        Selenide.sleep(1000);
    }

    @Step("Открыть 'Поручения'")
    public OrderRegistry openOrders() {
        clickItem(communication, orders);
        return new OrderRegistry();
    }

    @Step("Открыть 'Совещания'")
    public MeetingsRegistry openMeetings() {
        clickItem(communication, meetings);
        return new MeetingsRegistry();
    }

    @Step("Открыть 'Вопросы совещаний'")
    public QuestionsAndMeetingsRegistry openMeetingQuestions() {
        clickItem(communication, meetingQuestions);
        return new QuestionsAndMeetingsRegistry();
    }

    @Step("Открыть 'Открытые вопросы'")
    public OpenQuestionsRegistry openOpenQuestions() {
        clickItem(communication, openQuestions);
        return new OpenQuestionsRegistry();
    }

    @Override
    public Communication visible() {
        communication.shouldBe(visible);
        return this;
    }

    @Override
    public Communication hasCorrectText() {
        communication.shouldHave(text("Коммуникации"));
        return this;
    }
}
