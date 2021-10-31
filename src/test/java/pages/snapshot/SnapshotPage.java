package pages.snapshot;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.Snapshot;
import pages.BasePage;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SnapshotPage extends BasePage {
    private final SelenideElement nameInput = $("input#Name");
    private final SelenideElement commentInput = $("textarea#Comment");
    private final SelenideElement editorInput = $("#control-group-Editors input");
    private final SelenideElement reviewerInput = $("#control-group-Reviewer .k-input");
    private final SelenideElement snapshotStatus = $(".f-card__info");
    private final SelenideElement snapshotName = $("[name='Name']");
    private final SelenideElement sendToApproveButton = $x("//a[text() = 'Отправить на согласование']");
    private final SelenideElement approveButton = $x("//a[text() = 'Согласовать']");
    private final SelenideElement rejectButton = $x("//a[text() = 'Отклонить']");
    private final SelenideElement recallButton = $x("//a[text() = 'Отозвать']");
    private final SelenideElement deleteButton = $x("//a[text() = 'Удалить']");
    private final SelenideElement uploadDocumentButton_EditForm = $x("//div[@id='control-group-DocDiv']//div[@data-tooltip='Загрузить документ']");


    @Step("Заполнить поля карточки редактирования Слепка")
    public void fillFields(Snapshot snapshot) {
        typeText(nameInput, snapshot.getName());
        typeText(commentInput, snapshot.getComment());
        searchAndSelectFirstFromMultiSelect(editorInput, snapshot.getEditor());
        searchAndSelectFirstFromSelect(reviewerInput, snapshot.getReviewer());
    }

    @Step("Проверить наименование слепка и его статуса")
    public void checkNameAndStatus(String name, String status) {
        snapshotName.shouldBe(visible).shouldHave(Condition.text(name));
        snapshotStatus.shouldBe(visible).shouldHave(Condition.text(status));

    }

    @Step("Нажать на кнопку Загрузить документ в форме редактирования")
    public void clickUploadFileOnEditForm(){
        uploadDocumentButton_EditForm.click();
    }

    @Step("Отправить на согласование")
    public void sendToApprove(String snapshotComment, File file) {
        sendToApproveButton.shouldBe(visible).click();
        modalWindowShouldBeOpened();
        modalWindowShouldHaveTitle("Отправить на согласование");
        clickExpand();
        commentInput.setValue(snapshotComment);
        clickUploadFileOnEditForm();
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose();
    }

    @Step("Согласовать слепок")
    public void approveSnapshot(String snapshotComment, File file) {
        approveButton.shouldBe(visible).click();
        modalWindowShouldBeOpened();
        modalWindowShouldHaveTitle("Согласовать");
        clickExpand();
        commentInput.setValue(snapshotComment);
        clickUploadFileOnEditForm();
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose();
    }

    @Step("Отозвать слепок")
    public void recallSnapshot(String snapshotComment, File file) {
        recallButton.shouldBe(visible).click();
        modalWindowShouldBeOpened();
        modalWindowShouldHaveTitle("Отозвать");
        clickExpand();
        commentInput.setValue(snapshotComment);
        clickUploadFileOnEditForm();
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose();
    }

    @Step("Отклонить слепок")
    public void rejectSnapshot(String snapshotComment, File file) {
        rejectButton.shouldBe(visible).click();
        modalWindowShouldBeOpened();
        modalWindowShouldHaveTitle("Отклонить");
        clickExpand();
        commentInput.setValue(snapshotComment);
        clickUploadFileOnEditForm();
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose();
    }

    @Step("Удалить слепок")
    public void deleteSnapshot(String snapshotComment, File file) {
        deleteButton.shouldBe(visible).click();
        modalWindowShouldBeOpened();
        modalWindowShouldHaveTitle("Удалить");
        clickExpand();
        commentInput.setValue(snapshotComment);
        clickUploadFileOnEditForm();
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
        clickSaveAndClose();
    }

    @Step("Проверить, что Слепок в таблице имеет наименование {userName}, статус{status}, комментарий{snapshotComment} и название документа{docName}")
    public void shouldHaveRecordInTable(String userName, String status, String snapshotComment, String docName) {
        checkPageIsLoaded();
        clickOnProgressStatus();
        $("[data-column-field='UserName']").shouldHave(text(userName));
        $("[data-column-field='ButtonName']").shouldHave(text(status));
        $("[data-column-field='Comment']").shouldHave(text(snapshotComment));
        $("[data-column-field='Files'] a").shouldHave(attribute("data-tooltip", ""+docName+""));
        //click Close
        $(".k-window-content.k-content").pressEscape();
    }

    @Step("Проверить, что кнопка {workflowButton} отображается")
    public void checkWorkflowButtonExist(String workflowButton) {
        $x("//*[@class='EntityStateEdgeWorkflow']//a[.='"+workflowButton+"']").shouldBe(visible);
    }

    @Step("Нажать на кнопку 'Ход выполнения'")
    public void clickOnProgressStatus() {
        $$(".k-button").findBy(text("Ход выполнения")).click();
        $(".k-window-content.k-content").shouldBe(visible);
    }

    @Step("Проверить, что кнопка {workflowButton} НЕ отображается")
    public void checkWorkflowButtonNotExist(String workflowButton) {
        $x("//*[@class='EntityStateEdgeWorkflow']//a[.='"+workflowButton+"']").shouldNotBe(visible);
    }
}
