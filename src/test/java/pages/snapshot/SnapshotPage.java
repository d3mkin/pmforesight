package pages.snapshot;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.Snapshot;
import pages.BasePage;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

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
        snapshotName.shouldBe(Condition.visible).shouldHave(Condition.text(name));
        snapshotStatus.shouldBe(Condition.visible).shouldHave(Condition.text(status));

    }

    @Step ("Нажать на кнопку Загрузить документ в форме редактирования")
    public void clickUploadFileOnEditForm(){
        uploadDocumentButton_EditForm.click();
    }

    @Step("Отправить на согласование")
    public void sendToApprove(String snapshotComment, File file) {
        sendToApproveButton.shouldBe(Condition.visible).click();
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
        approveButton.shouldBe(Condition.visible).click();
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

    public void shouldHaveRecordInTable(String userName, String status, String snapshotComment, String docName) {
        checkPageIsLoaded();
        $x("//div[@id='StateEdgeWorkflowWidget_container']//td[text()='"+ userName +"']").shouldBe(Condition.visible);
        $x("//div[@id='StateEdgeWorkflowWidget_container']//td[text()='"+ status +"']").shouldBe(Condition.visible);
        $x("//div[@id='StateEdgeWorkflowWidget_container']//td[text()='"+ snapshotComment +"']").shouldBe(Condition.visible);
        $x("//div[@id='StateEdgeWorkflowWidget_container']//a[text()='"+ docName +"']").shouldBe(Condition.visible);
    }


}
