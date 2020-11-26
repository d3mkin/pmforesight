package pages.goals_and_indicators.goal;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Goal;
import model.Indicator;
import pages.BasePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;


public class GoalPage extends BasePage {
    private final SelenideElement nameInput_EditForm = $(By.xpath("//input[@id='Name']"));
    private final SelenideElement managementLevelInput_EditForm = $("#control-group-ControlLevelId .k-input");
    private final SelenideElement editorsInput_EditForm = $(By.xpath("//div[@class='k-multiselect-wrap k-floatwrap']"));
    private final SelenideElement addIndicatorButton = $(By.xpath("//inlinetableview[@data-view-name='GoalKPIView']//a[@data-tooltip = 'Добавить']"));
    private final SelenideElement searchIndicatorInput = $(By.xpath("//inlinetableview[@data-view-name='GoalKPIView']//input[@placeholder='Поиск...']"));
    private final SelenideElement firstFoundIndicator = $(By.xpath("//inlinetableview[@data-view-name='GoalKPIView']//td[2]/a"));

    @Step("Заполнить карточку редактирования Цели")
    public void fillFields(Goal goal) {
        typeText(nameInput_EditForm, goal.getName());
        searchAndSelectFirstFromSelect(managementLevelInput_EditForm, goal.getManagementLevel());
        searchAndSelectFirstFromMultiSelect(editorsInput_EditForm, goal.getEditors());
    }

    @Step ("Нажать добавить индикатор индикатор")
    public void addIndicator() {
        addIndicatorButton.click();
    }

    @Step ("Найти индикатор в таблице 'Показатели цели' и отрыть его")
    public void searchAndOpenIndicator(Indicator indicator) {
        searchIndicatorInput.shouldBe(visible).setValue(indicator.getName());
        firstFoundIndicator.shouldHave(text(indicator.getName())).click();
    }

    @Step ("Проверить что индикатор отображается в таблице")
    public void checkIndicatorIsDisplayed (Indicator indicator) {
        sleep(2000);
        searchIndicatorInput.shouldBe(visible).setValue(indicator.getName());
        firstFoundIndicator.shouldHave(text(indicator.getName()));
    }
}
