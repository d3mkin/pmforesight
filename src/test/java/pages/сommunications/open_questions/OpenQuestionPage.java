package pages.сommunications.open_questions;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.OpenQuestion;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class OpenQuestionPage extends BasePage {
    private final SelenideElement nameInput = $(By.xpath("//input[@id='Name']"));
    private final SelenideElement executorInput = $(By.xpath("//div[@id='control-group-Responsible']//span[@class='k-input']"));
    private final SelenideElement initDayInput = $(By.xpath("//input[@id='InitDate']"));
    private final SelenideElement planDayInput = $(By.xpath("//input[@id='PlanDate']"));
    private final SelenideElement afterMonthPlanPeriod = $(By.xpath("//span[@name='quickDates']//span[3]"));

    public void fillFields(OpenQuestion openQuestion) {
        typeText(nameInput, openQuestion.getName());
        searchAndSelectFirstFromSelect(executorInput, openQuestion.getExecutor());
        typeDate(initDayInput, openQuestion.getInitDate());
        afterMonthPlanPeriod.click();
    }
}
