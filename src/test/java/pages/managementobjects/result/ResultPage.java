package pages.managementobjects.result;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import model.Result;
import pages.BasePage;
import pages.elements.DeleteEntityDialog;

import static com.codeborne.selenide.Selenide.*;

public class ResultPage extends BasePage {
    private final SelenideElement resultName = $x("//input[@name='Name']");
    private final SelenideElement resultType = $x("//div[@id='control-group-ActivityResultsTypeId']//span[@class='k-input']");
    private final SelenideElement resultUnit = $x("//input[@name='Unit']");
    private final SelenideElement federalResultLink = $("#control-group-FederalLevelActivityResultId .k-input");
    private final SelenideElement resultValue = $x("//input[@class='k-formatted-value k-input']");
    private final SelenideElement tabRoles = $("#tab-roles > a");
    private final SelenideElement responsibleRole = $x("//div[@class='k-widget k-multiselect k-multiselect-clearable']");
    private final SelenideElement acceptorRole = $("#control-group-Acceptor .k-input");
    private final SelenideElement valueTableTrashButton = $x("//tbody//tr//a[@class='itv-remove-button k-grid-trash']");
    private final SelenideElement closeResultButton = $x("//span[@class='f-icon m-i-cross']");
    private final SelenideElement parentEntityResult = $x("//div[@id='control-group-Parent']//span[@class='k-input']");
    private final SelenideElement resultCalendarDate = $x("//input[@name='ResultDate']");

    @Step("Заполнить поля в карточке Результата")
    public void fillFields(Result result) {
        typeText(resultName, result.getName());
        searchInAutocompleteAndClickToFirst(resultUnit, result.getUnit());
        searchAndSelectFirstFromSelect(resultType, result.getType());
//        sleep(2000);
        searchAndSelectFirstFromSelect(federalResultLink, result.getFederalResultLink());
        searchAndSelectFirstFromSelect(parentEntityResult, result.getParentEntity());
        //typeDate(resultCalendarDate,result.getDate());
        selectResultCalendarCurrentDay();
        typeText(resultValue, result.getValue());
        tabRoles.click();
        responsibleRole.waitUntil(Condition.visible, 1000);
        searchAndSelectFirstFromMultiSelect(responsibleRole, result.getResponsible());
        searchAndSelectFirstFromSelect(acceptorRole, result.getAcceptor());

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
        searchAndSelectFirstFromSelect(federalResultLink, federalResult.getName());
    }

    @Step ("Заполнить поле 'Родительский результат'")
    public void fillParentEntityResult(Result parentResult){
        searchAndSelectFirstFromSelect(parentEntityResult, parentResult.getName());
    }

    @Step ("Проверить недоступность поля 'Родительский результат'")
    public void checkParentEntityResultIsNotVisible() {
        parentEntityResult.shouldNotBe(Condition.visible);
    }

    @Step ("Заполнить поле 'Тип результата'")
    public void fillResultType(String result){
        searchAndSelectFirstFromSelect(resultType, result);
    }
}
