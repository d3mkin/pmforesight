package pages.managementobjects.point;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.Point;
import pages.BasePage;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PointPage extends BasePage {
    //Форма редактирования
    private final SelenideElement pointName = $("#Name");
    private final SelenideElement pointPlanDate = $x("//input[@id='PlanDate']");
    private final SelenideElement pointForecastDate = $x("//input[@id='ForecastDate']");
    private final SelenideElement approvingDoc = $("#control-group-ApprovingDocumentId .k-widget");
    //Форма просмотра
    private final SelenideElement completeButton_ViewForm = $x("//a[text()='Выполнить']");
    private final SelenideElement backInProgressButton_ViewForm = $x("//a[text()='Вернуть в работу']");
    private final SelenideElement approveButton_ViewForm = $x("//a[text()='Согласовать']");
    private final SelenideElement finishApproveButton_ViewForm = $x("//a[text()='Завершить согласование']");
    private final SelenideElement cancelButton_ViewForm = $x("//a[text()='Отменить']");
    private final SelenideElement actualCompletionDateInput_EditForm = $x("//input[@id='FactDate']");
    private final SelenideElement actualCompletionDate_ViewForm = $x("//div[@name='FactDate']");
    private final SelenideElement commentInput_EditForm = $x("//textarea[@id='Comment']");
    private final SelenideElement commentInputIsRequired_EditForm = $x("//div[@id='control-group-Comment']//span[@class='required-input']");
    private final SelenideElement forecastCompletionDateInput_EditForm = $x("//input[@id='ForecastDate']");
    private final SelenideElement forecastDateInputIsRequired_EditForm = $x("//div[@id='control-group-ForecastDate']//span[@class='required-input']");
    private final SelenideElement forecastCompletionDate_ViewForm = $x("//div[@name='ForecastDate']");
    private final SelenideElement documentsWidget_EditForm = $x(("//div[@id='DocDiv']//documents[@class='f-documents']"));
    private final SelenideElement documentsWidgetIsRequired_EditForm = $x("//div[@id='control-group-DocDiv']//span[@class='required-input']");
    private final SelenideElement pointStatusField_ViewForm = $x("//div[@class='f-card__info']");
    //TODO: вынести в базовый класс селектор кнопки Загрузить документ + метод клика по ней
    private final SelenideElement uploadDocumentButton_EditForm = $x("//div[@id='control-group-DocDiv']//div[@data-tooltip='Загрузить документ']");

    @Step ("Нажать кнопку Выполнить")
    public void clickCompleteButton(){
        completeButton_ViewForm.click();
        actualCompletionDateInput_EditForm.shouldBe(visible);
    }

    @Step ("Нажать кнопку Вернуть в работу")
    public void clickBackInProgressButton(){
        backInProgressButton_ViewForm.shouldBe(visible).click();
        commentInput_EditForm.shouldBe(visible);
        commentInputIsRequired_EditForm.shouldBe(visible);
        forecastCompletionDateInput_EditForm.shouldBe(visible);
        forecastDateInputIsRequired_EditForm.shouldBe(visible);
//        documentsWidget_EditForm.shouldBe(visible);
//        documentsWidgetIsRequired_EditForm.shouldBe(visible);
    }

    @Step ("Нажать кнопку Согласовать")
    public void clickApproveButton(){
        approveButton_ViewForm.click();
        commentInput_EditForm.shouldBe(visible);
//        documentsWidget_EditForm.shouldBe(visible);
//        documentsWidgetIsRequired_EditForm.shouldBe(visible);
    }

    @Step("Нажать 'Завершить согласование'")
    public void clickFinishApprove() {
        finishApproveButton_ViewForm.click();
        commentInput_EditForm.shouldBe(visible);
    }

    @Step ("Нажать кнопку Отменить")
    public void clickCancelButton(){
        cancelButton_ViewForm.shouldBe(visible).click();
        commentInput_EditForm.shouldBe(visible);
        commentInputIsRequired_EditForm.shouldBe(visible);
//        documentsWidget_EditForm.shouldBe(visible);
//        documentsWidgetIsRequired_EditForm.shouldBe(visible);
    }

    @Step ("Заполнить Фактическую дату окончания выполнения")
    public void fillActualCompletionDate (String completionDate){
        sleep(1000);
        typeDate(actualCompletionDateInput_EditForm, completionDate);
    }

    @Step ("Заполнить Прогнозную дату окончания выполнения")
    public void fillForecastCompletionDate(String forecastDate){
        sleep(1000);
        typeDate(forecastCompletionDateInput_EditForm, forecastDate);
    }

    @Step ("Проверить что на форме просмотра отображается Окончание (факт) равное дате {completionDate}")
    public void checkCompletionDateField(String completionDate){
        actualCompletionDate_ViewForm.shouldHave(text(completionDate));
    }

    @Step ("Проверить что на форме просмотра отображается Окончание (прогноз) равное дате {completionDate}")
    public void checkForecastCompletionDateField(String forecastDate){
        forecastCompletionDate_ViewForm.shouldHave(text(forecastDate));
    }

    @Step ("Заполнить поле комментарий")
    public void fillComment(String comment) {
        commentInput_EditForm.setValue(comment);
    }

    @Step ("Нажать на кнопку Загрузить документ в форме редактирования")
    public void clickUploadFileOnEditForm(){
        uploadDocumentButton_EditForm.click();
    }

    public void checkPointStatus(String status) {
        pointStatusField_ViewForm.shouldHave(text(status));
    }

    @Step ("Выполнить КТ и загрузить документ")
    public void completePointAndUploadFile (String date, File file) {
        clickCompleteButton(); //Выполняем КТ
        clickExpand();
        fillActualCompletionDate(date);
//        clickUploadFileOnEditForm(); //Загружаем документ
//        uploadFile(file);
//        checkFileIsUploaded(file);
//        closeUploadWindow();
        clickSaveAndClose();//Сохраняем изменения в форме Выполнения
        checkPageIsLoaded();
        checkCompletionDateField(date);
        checkPointStatus("Выполнена");
    }

    @Step ("Вернуть в работу КТ и загрузить документ")
    public void backInProgressPointAndUploadFile (String date, File file) {
        clickBackInProgressButton(); //Возвращаем в работу
        clickExpand();
        fillComment("Какая-то причина для возврата");
//        clickUploadFileOnEditForm(); //Загружаем документ
//        uploadFile(file);
//        checkFileIsUploaded(file);
//        closeUploadWindow();
        fillForecastCompletionDate(date);
        clickSaveAndClose(); //Сохраняем изменения
        checkPageIsLoaded();
        checkForecastCompletionDateField(date);
        checkPointStatus("В работе");
    }

    @Step ("Оменить КТ и загрузить документ")
    public void cancelPointAndUploadFile (String date, File file) {
        clickCancelButton(); //Отменить КТ
        clickExpand();
        fillComment("Какая-то причина отмены");
//        clickUploadFileOnEditForm(); //Загружаем документ
//        uploadFile(file);
//        checkFileIsUploaded(file);
//        closeUploadWindow();
        clickSaveAndClose(); //Сохраняем изменения
        checkPageIsLoaded();
        checkPointStatus("Отменено");
    }

    @Step ("Согласовать КТ и загрузить документ")
    public void approvePointAndUploadFile (File file) {
        clickApproveButton(); //Согласовать КТ
        clickExpand();
//        clickUploadFileOnEditForm(); //Загружаем документ
//        uploadFile(file);
//        checkFileIsUploaded(file);
//        closeUploadWindow();
        clickSaveAndClose(); //Сохраняем изменения
        checkPageIsLoaded();
        checkPointStatus("Выполнена");
    }

    @Step ("Согласовать КТ и загрузить документ")
    public void finishApprovePointAndUploadFile (File file) {
        clickFinishApprove(); //Согласовать КТ
        clickExpand();
//        clickUploadFileOnEditForm(); //Загружаем документ
//        uploadFile(file);
//        checkFileIsUploaded(file);
//        closeUploadWindow();
        clickSaveAndClose(); //Сохраняем изменения
        checkPageIsLoaded();
        checkPointStatus("Подтверждена");
    }

    public void fillFields(Point point) {
        typeText(pointName, point.getName());
        typeDate(pointPlanDate, point.getPlanDate());
        typeDate(pointForecastDate, point.getForecastDate());
        searchAndSelectFirstFromSelect(approvingDoc, point.getApprovingDocument());
    }
}
