package pages.snapshot;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.Snapshot;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class SnapshotPage extends BasePage {
    private final SelenideElement nameInput = $("input#Name");
    private final SelenideElement commentInput = $("textarea#Comment");
    private final SelenideElement editorInput = $("#control-group-Editors input");
    private final SelenideElement reviewerInput = $("#control-group-Reviewer .k-input");
    private final SelenideElement snapshotStatus = $(".f-card__info");
    private final SelenideElement snapshotName = $("[name='Name']");


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
}
