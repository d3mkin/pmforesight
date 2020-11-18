package pages.goals_and_indicators.indicator;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import model.Indicator;
import pages.elements.BasePage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.$;

public class IndicatorPage extends BasePage {
    private final SelenideElement nameInput_EditForm = $(By.xpath("//input[@name='Name']"));
    private final SelenideElement estimationTypeInput_EditForm = $(By.xpath("//div[@id='control-group-KPIValueTypeId']//span[@class='k-input']"));
    private final SelenideElement kpiTypeInput_EditForm = $(By.xpath("//div[@id='control-group-KPIPeriodId']//span[@class='k-input']"));
    private final SelenideElement unitInput_EditForm = $(By.xpath("//input[@id='Unit']"));
    private final SelenideElement basicValueInput_EditForm = $(By.xpath("//div[@id='control-group-CurrentFact']//input[@class='k-formatted-value k-input']"));
    //private final SelenideElement approvingDocInput_EditForm = $(By.xpath("//div[@id='control-group-ApprovingDocumentId']//span[@class='k-dropdown-wrap k-state-default']"));
    private final SelenideElement approvingDocInput_EditForm = $("#control-group-ApprovingDocumentId .k-input");
    private final SelenideElement rolesTab_EditForm = $(By.xpath("//li[@id='tab2']//a"));
    private final SelenideElement responsibleInput_EditForm = $(By.xpath("//div[@id='control-group-Responsible']//span[@class='k-widget k-dropdown']"));
    private final SelenideElement linkTab = $(By.xpath("//li[@id='tab3']"));
    private final SelenideElement addValueButton_ViewForm = $(By.xpath("//div[@id='ValueTable']//a[@data-tooltip ='Добавить']"));
    private final SelenideElement periodInput_EditForm = $(By.xpath("//div[@id='control-group-D_MonthId']//span[@class='k-widget k-dropdown']"));
    private final SelenideElement planInput_EditForm = $(By.xpath("//div[@id='control-group-Plan']//input[@class='k-formatted-value k-input']"));
    private final SelenideElement forecastInput_EditForm = $(By.xpath("//div[@id='control-group-Forecast']//input[@class='k-formatted-value k-input']"));
    private final SelenideElement factInput_EditForm = $(By.xpath("//div[@id='control-group-Fact']//input[@class='k-formatted-value k-input']"));
    private final SelenideElement periodValueInTable = $(By.xpath("//body//td[2]"));
    private final SelenideElement planValueInTable = $(By.xpath("//body//td[3]"));
    private final SelenideElement forecastValueInTable = $(By.xpath("//body//td[5]"));
    private final SelenideElement factValueInTable = $(By.xpath("//body//td[6]"));
    private final SelenideElement indicatorTooltipInTable = $(By.xpath("//body//td[2]//a"));
    private final SelenideElement percentageOfAchievementInTable = $(By.xpath("//body//td[7]"));
    private final SelenideElement editIndicatorButton_ViewForm =$(By.xpath("//ul[@class='f-card__buttons']//li[1]"));
    private final SelenideElement editIndicatorValueButton_ViewForm =$(By.xpath("//div[@class='k-grid-content k-auto-scrollable']//tr[last()]//span[@class='k-icon k-i-edit']"));
    private final SelenideElement indicatorValueStatus =$(By.xpath("//div[@class='k-grid-content k-auto-scrollable']//tr[last()]//td/span"));

    @Step("Заполнить поля в карточке Индикатора")
    public void fillRequiredFields(Indicator indicator) {
        typeOrSkip(nameInput_EditForm, indicator.getName());
        searchInSelectAndClickToFirstWithCheckDropDown(estimationTypeInput_EditForm, indicator.getEstimationType());
        searchInSelectAndClickToFirstWithCheckDropDown(kpiTypeInput_EditForm, indicator.getKPI());
        searchInAutocompleteAndClickToFirst(unitInput_EditForm, indicator.getUnit());
        typeOrSkip(basicValueInput_EditForm, indicator.getBasicValue());
        searchInSelectAndClickToFirstWithCheckDropDown(approvingDocInput_EditForm,indicator.getApprovingDoc());
        rolesTab_EditForm.click();
        responsibleInput_EditForm.shouldBe(visible);
        searchInSelectAndClickToFirstWithCheckDropDown(responsibleInput_EditForm, indicator.getResponsible());
    }

    @Step("Проверить что на вкладке 'Связи' по умолчанию 'Объект управления' равен текущему проекту")
    public void checkDefaultControlObject(String projectName) {
        linkTab.click();
        $(By.xpath("//span[@class='k-dropdown-wrap k-state-disabled']")).shouldHave(text(projectName));
    }

    @Step ("Нажать на кнопку Добавить Плановые, прогнозные и фактические значения показателя")
    public void clickAddValueButton() {
        addValueButton_ViewForm.click();
    }

    @Step ("Нажать на кнопку Редактировать значения показателя")
    public void clickEditValueButton() {
        editIndicatorValueButton_ViewForm.click();
    }


    @Step("Заполнить плановые, прогнозные и фактические значения показателя")
    public void fillIndicatorsValues(Indicator indicator) {
        searchInSelectAndClickToFirstWithCheckDropDown(periodInput_EditForm, indicator.getPeriod());
        typeOrSkip(planInput_EditForm, indicator.getPlan());
        typeOrSkip(forecastInput_EditForm, indicator.getForecast());
        typeOrSkip(factInput_EditForm, indicator.getFact());
    }

    @Step("Проверить корректность рассчёта и отображения '% достижение плана'")
    public void checkPercentageOfAchievement(Indicator indicator) {
        switch (indicator.getEstimationType()) {
            case ("Возрастающий"):
                if (Double.parseDouble(indicator.getPlan()) <= Double.parseDouble(indicator.getBasicValue())) {
                    checkValuesAreDisplayed(indicator);
                    percentageOfAchievementInTable.shouldHave(text("–"));
                } else {
                    checkValuesAreDisplayed(indicator);
                    percentageOfAchievementInTable.shouldHave(text(countIncreasingPercentage(indicator)));
                }
                break;
            case ("Убывающий"):
                if (Double.parseDouble(indicator.getPlan()) >= Double.parseDouble(indicator.getBasicValue())) {
                    checkValuesAreDisplayed(indicator);
                    percentageOfAchievementInTable.shouldHave(text("–"));
                } else {
                    checkValuesAreDisplayed(indicator);
                    percentageOfAchievementInTable.shouldHave(text(countIncreasingPercentage(indicator)));
                }
                break;
            case ("Фиксированный"):
                if (Double.parseDouble(indicator.getPlan()) == Double.parseDouble(indicator.getBasicValue())) {
                    checkValuesAreDisplayed(indicator);
                    percentageOfAchievementInTable.shouldHave(text("–"));
                } else {
                    checkValuesAreDisplayed(indicator);
                    percentageOfAchievementInTable.shouldHave(text(countIncreasingPercentage(indicator)));
                }
                break;
            default:
                break;
        }
    }

    @Step("Проверка корректного отображения значений показателя в таблице")
    public void checkValuesAreDisplayed(Indicator indicator) {
        periodValueInTable.shouldHave(text(indicator.getPeriod()));
        planValueInTable.shouldHave(text(indicator.getPlan()));
        forecastValueInTable.shouldHave(text(indicator.getForecast()));
        factValueInTable.shouldHave(text(indicator.getFact()));
    }

    @Step("Расчёт % достижения плана для показателя в зависимости от типа")
    public String countIncreasingPercentage(Indicator indicator) {
        double factValue = Float.parseFloat(indicator.getFact());
        double baseValue = Float.parseFloat(indicator.getBasicValue());
        double planValue = Float.parseFloat(indicator.getPlan());
        double result = 0;

        switch (indicator.getEstimationType()) {
            case ("Возрастающий"):
                result = ((factValue - baseValue) * 100) / Math.abs(planValue - baseValue);
                break;
            case ("Убывающий"):
                result = ((baseValue - factValue) * 100) / Math.abs(planValue - baseValue);
                break;
            case ("Фиксированный"):
                result = ((Math.abs(baseValue - planValue) - Math.abs(planValue - factValue)) * 100) / Math.abs(baseValue - planValue);
                break;
            default:
                break;
        }
        //Округляем до 2ух знаков после запятой и возвращаем значение в виде строки
        return Double.toString(Math.round(result * 100.0) / 100.0);
    }

    @Step ("Изменить базовое значение показателя")
    public void changeBasicValue(Indicator indicator,String newBasicValue){
        indicator.setBasicValue(newBasicValue);
        editIndicatorButton_ViewForm.click();
        basicValueInput_EditForm.shouldBe(visible);
        //Костыль для инпута базвого значения. Зачищает форму ввода и кливает по модальному окну
        basicValueInput_EditForm.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $(By.xpath("//form[@id='KPIEditForm']")).click();
        
        typeOrSkip(basicValueInput_EditForm, newBasicValue);
        clickSaveAndClose();
        $(By.xpath("//div[@name='CurrentFact']")).shouldBe(visible).shouldHave(text(newBasicValue));
    }

    @Step("Изменить Фактическое значение")
    public void changeFactValue (Indicator indicator,String newFactValue){
        indicator.setFact(newFactValue);
        editIndicatorValueButton_ViewForm.click();
        factInput_EditForm.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $(By.xpath("//form[@id='KPIValueEditForm']")).click();
        typeOrSkip(factInput_EditForm, indicator.getFact());
    }

    @Step("Изменить Плановое значение")
    public void changePlanValue (Indicator indicator,String newPlanValue){
        indicator.setPlan(newPlanValue);
        editIndicatorValueButton_ViewForm.click();
        planInput_EditForm.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $(By.xpath("//form[@id='KPIValueEditForm']")).click();
        typeOrSkip(planInput_EditForm, indicator.getPlan());
    }

    @Step ("Изменить Тип оценки показателя")
    public void changeEstimationType(Indicator indicator,String newEstimationType){
        indicator.setEstimationType(newEstimationType);
        editIndicatorButton_ViewForm.click();
        estimationTypeInput_EditForm.shouldBe(visible);
        searchInSelectAndClickToFirstWithCheckDropDown(estimationTypeInput_EditForm, newEstimationType);
        clickSaveAndClose();
        $(By.xpath("//div[@name='KPIValueTypeId']")).shouldBe(visible).shouldHave(text(newEstimationType));
    }

    @Step ("Проверить расчёт индикатора периода")
    public void checkPeriodIndicatorStatus(Indicator indicator){
        double planValue = Double.parseDouble(indicator.getPlan());
        double basicValue = Double.parseDouble(indicator.getBasicValue());
        double factValue = Double.parseDouble(indicator.getFact());

        final boolean incNoData = planValue <= basicValue;
        final boolean incReached = (factValue >= planValue) & (planValue > basicValue);
        final boolean incCloseToFail = (factValue < planValue) & (factValue >= (planValue - basicValue)* 0.8 + basicValue) & (planValue > basicValue);
        final boolean incNotReached = (factValue < planValue) & (factValue < (planValue - basicValue)* 0.8 + basicValue) & (planValue > basicValue);

        final boolean decNoData = planValue >= basicValue;
        final boolean decReached = (factValue <= planValue) & (planValue < basicValue);
        final boolean decCloseToFail = (factValue > planValue) & (factValue <= (basicValue - planValue)* 0.2 + planValue) & (planValue < basicValue);
        final boolean decNotReached = (factValue > planValue) & (factValue > (basicValue - planValue)* 0.2 + planValue) & (planValue < basicValue);

        final boolean fixNoData = planValue == basicValue;
        final boolean fixReached = (factValue == planValue) & (planValue != basicValue);
        final boolean fixCloseToFail = ((factValue >= planValue - (Math.abs(basicValue - planValue))*0.2) & (factValue < planValue))
                | ((factValue <= planValue + (Math.abs(basicValue - planValue))*0.2) & (factValue > planValue) & (planValue != basicValue));
        final boolean fixNotReached = ((factValue < planValue - (Math.abs(basicValue - planValue))*0.2))
                | ((factValue > planValue + (Math.abs(basicValue - planValue))*0.2) & (planValue != basicValue));

        switch (indicator.getEstimationType()) {
            case ("Возрастающий"):
                if (incNoData)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Нет данных"),
                                                    cssValue("color", "rgba(102, 102, 102, 1)"));
                if (incReached)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Достигнут"),
                                                    cssValue("color", "rgba(95, 175, 97, 1)"));
                if (incCloseToFail)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Срыв достижения"),
                                                    cssValue("color", "rgba(255, 210, 70, 1)"));
                if (incNotReached)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Не достигнут"),
                                                    cssValue("color", "rgba(255, 89, 64, 1)"));
                break;
            case ("Убывающий"):
                if (decNoData)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Нет данных"),
                                                    cssValue("color", "rgba(102, 102, 102, 1)"));
                if (decReached)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Достигнут"),
                                                    cssValue("color", "rgba(95, 175, 97, 1)"));
                if (decCloseToFail)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Срыв достижения"),
                                                    cssValue("color", "rgba(255, 210, 70, 1)"));
                if (decNotReached)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Не достигнут"),
                                                    cssValue("color", "rgba(255, 89, 64, 1)"));
                break;
            case ("Фиксированный"):
                if (fixNoData)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Нет данных"),
                            cssValue("color", "rgba(102, 102, 102, 1)"));
                if (fixReached)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Достигнут"),
                            cssValue("color", "rgba(95, 175, 97, 1)"));
                if (fixCloseToFail)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Срыв достижения"),
                            cssValue("color", "rgba(255, 210, 70, 1)"));
                if (fixNotReached)
                    indicatorValueStatus.shouldHave(attribute("data-tooltip", "Не достигнут"),
                            cssValue("color", "rgba(255, 89, 64, 1)"));
                break;
            default:
                break;
        }
    }
}