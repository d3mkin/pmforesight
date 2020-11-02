package pages.managementobjects.point;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.elements.BasePage;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class PointPage extends BasePage {

    private final SelenideElement completeButton_ViewForm = $(By.xpath("//a[text()='Выполнить']"));
    private final SelenideElement backInProgressButton_ViewForm = $(By.xpath("//a[text()='Вернуть в работу']"));
    private final SelenideElement approveButton_ViewForm = $(By.xpath("//a[text()='Согласовать']"));
    private final SelenideElement cancelButton_ViewForm = $(By.xpath("//a[text()='Отменить']"));
    private final SelenideElement actualCompletionDateInput_EditForm = $(By.xpath("//input[@id='FactDate']"));
    private final SelenideElement actualCompletionDate_ViewForm = $(By.xpath("//div[@name='FactDate']"));
    private final SelenideElement commentInput_EditForm = $(By.xpath("//textarea[@id='Comment']"));
    private final SelenideElement commentInputIsRequired_EditForm = $(By.xpath("//div[@id='control-group-Comment']//span[@class='required-input']"));
    private final SelenideElement forecastCompletionDateInput_EditForm = $(By.xpath("//input[@id='ForecastDate']"));
    private final SelenideElement forecastDateInputIsRequired_EditForm = $(By.xpath("//div[@id='control-group-ForecastDate']//span[@class='required-input']"));
    private final SelenideElement forecastCompletionDate_ViewForm = $(By.xpath("//div[@name='ForecastDate']"));
    private final SelenideElement documentsWidget_EditForm = $(By.xpath("//div[@id='DocDiv']//documents[@class='f-documents']"));
    private final SelenideElement documentsWidgetIsRequired_EditForm = $(By.xpath("//div[@id='control-group-DocDiv']//span[@class='required-input']"));
    private final SelenideElement pointStatusField_ViewForm = $(By.xpath("//div[@class='f-card__info']"));
    private final SelenideElement uploadDocumentButton_EditForm = $(By.xpath("//div[@id='control-group-DocDiv']//div[@data-tooltip='Загрузить документ']"));

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
        documentsWidget_EditForm.shouldBe(visible);
        documentsWidgetIsRequired_EditForm.shouldBe(visible);
    }

    @Step ("Нажать кнопку Согласовать")
    public void clickApproveButton(){
        approveButton_ViewForm.shouldBe(visible).click();
        commentInput_EditForm.shouldBe(visible);
        documentsWidget_EditForm.shouldBe(visible);
        documentsWidgetIsRequired_EditForm.shouldBe(visible);
    }

    @Step ("Нажать кнопку Отменить")
    public void clickCancelButton(){
        cancelButton_ViewForm.shouldBe(visible).click();
        commentInput_EditForm.shouldBe(visible);
        commentInputIsRequired_EditForm.shouldBe(visible);
        documentsWidget_EditForm.shouldBe(visible);
        documentsWidgetIsRequired_EditForm.shouldBe(visible);
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
        clickUploadFileOnEditForm(); //Загружаем документ
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose(); //Сохраняем изменения в форме Выполнения
        checkCompletionDateField(date);
        checkPointStatus("Выполнена");
    }

    @Step ("Вернуть в работу КТ и загрузить документ")
    public void backInProgressPointAndUploadFile (String date, File file) {
        clickBackInProgressButton(); //Возвращаем в работу
        clickExpand();
        fillComment("Какая-то причина для возврата");
        clickUploadFileOnEditForm(); //Загружаем документ
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        fillForecastCompletionDate(date);
        clickSaveAndClose(); //Сохраняем изменения
        checkForecastCompletionDateField(date);
        checkPointStatus("В работе");
    }

    @Step ("Оменить КТ и загрузить документ")
    public void cancelPointAndUploadFile (String date, File file) {
        clickCancelButton(); //Отменить КТ
        clickExpand();
        fillComment("Какая-то причина отмены");
        clickUploadFileOnEditForm(); //Загружаем документ
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose(); //Сохраняем изменения
        checkPointStatus("Отменено");
    }

    @Step ("Согласовать КТ и загрузить документ")
    public void approvePointAndUploadFile (File file) {
        clickApproveButton(); //Согласовать КТ
        clickExpand();
        clickUploadFileOnEditForm(); //Загружаем документ
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose(); //Сохраняем изменения
        checkPointStatus("Подтверждена");
    }
}
