package pages.managementobjects.result;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Result;
import pages.elements.BasePage;
import pages.elements.DeleteEntityDialog;

import static com.codeborne.selenide.Selenide.$;

public class ResultPage extends BasePage {
    private final SelenideElement resultName = $(By.xpath ("//input[@name='Name']"));
    private final SelenideElement resultType = $(By.xpath("//div[@id='control-group-ActivityResultsTypeId']//span[@class='k-input']"));
    private final SelenideElement resultUnit = $(By.xpath ("//input[@name='Unit']"));
    private final SelenideElement federalResultLink = $(By.xpath("//div[@id='control-group-FederalLevelActivityResultId']//span[@class='k-dropdown-wrap k-state-default']"));
    private final SelenideElement resultValue = $(By.xpath ("//input[@class='k-formatted-value k-input']"));
    private final SelenideElement tabRoles = $(By.cssSelector("#tab-roles > a"));
    private final SelenideElement responsibleRole = $(By.xpath("//div[@class='k-widget k-multiselect k-multiselect-clearable']"));
    private final SelenideElement valueTableTrashButton = $(By.xpath("//tbody//tr//a[@class='itv-remove-button k-grid-trash']"));
    private final SelenideElement closeResultButton = $(By.xpath("//span[@class='f-icon m-i-cross']"));
    private final SelenideElement parentEntityResult = $ (By.xpath("//div[@id='control-group-Parent']//span[@class='k-input']"));
    private final SelenideElement resultCalendarDate = $ (By.xpath("//input[@name='ResultDate']"));

    @Step("Заполнить поля в карточке Результата")
    public void fillFields(Result result) {
        typeOrSkip(resultName, result.getName());
        searchInAutocompleteAndClickToFirst(resultUnit, result.getUnit());
        searchInSelectAndClickToFirstWithCheckDropDown(resultType, result.getType());
        searchInSelectAndClickToFirstWithCheckDropDown(federalResultLink, result.getFederalResultLink());
        searchInSelectAndClickToFirstWithCheckDropDown(parentEntityResult, result.getParentEntity());
        //typeDate(resultCalendarDate,result.getDate());
        selectResultCalendarCurrentDay();
        typeOrSkip(resultValue, result.getValue());
        tabRoles.click();
        responsibleRole.waitUntil(Condition.visible, 1000);
        searchAndSelectFirstFromMultiSelect(responsibleRole, result.getResponsible());

    }

    @Step("Удалить поле со значением в таблице 'Значение результата'")
    public void clickDeleteValueFromTable() {
        valueTableTrashButton.click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step ("Закрыть карточку результата")
    public void clickCloseResultCard(){
        WebDriverRunner.getWebDriver().close();
    }

    @Step ("Заполнить поле 'Связь с результатом федерального уровня' ")
    public void fillFederalResultLink(Result federalResult) {
        searchInSelectAndClickToFirstWithCheckDropDown(federalResultLink, federalResult.getName());
    }

    @Step ("Заполнить поле 'Родительский результат'")
    public void fillParentEntityResult(Result parentResult){
        searchInSelectAndClickToFirstWithCheckDropDown(parentEntityResult, parentResult.getName());
    }

    @Step ("Проверить недоступность поля 'Родительский результат'")
    public void checkParentEntityResultIsNotVisible() {
        parentEntityResult.shouldNotBe(Condition.visible);
    }

    @Step ("Заполнить поле 'Тип результата'")
    public void fillResultType(String result){
        searchInSelectAndClickToFirstWithCheckDropDown(resultType, result);
    }
}
