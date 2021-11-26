package pages.monitoring_and_control.change_requests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.SnapshotChangeRequests;
import pages.BasePage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SnapshotChangeRequestsPage extends BasePage {
    private final SelenideElement editForm_NameInput = $("input#Name");
    private final SelenideElement editForm_CommentInput = $("textarea#Comment");
    private final SelenideElement editForm_changeReasonsTab = $("#tab2");
    private final SelenideElement editForm_ProjectReasonsInput = $("#ProjectReasons");
    private final SelenideElement viewForm_MainTab = $("a[href='#tab-main']");
    private final SelenideElement viewForm_ChangeReasonsTab = $("a[href='#tab-changes']");
    private final SelenideElement viewForm_ApproveTab = $("a[href='#tab-approve']");
    private final SelenideElement viewForm_ProjectReasonsWidget = $("#ProjectReasonsWidget");
    private final SelenideElement viewForm_ProjectReasonsField = $(".f-control__text[name='ProjectReasons']");
    private final SelenideElement viewForm_KPIApproveWidget = $("#KPIApproveWidget .k-i-arrow-chevron-right");
    private final SelenideElement viewForm_KPIName = $("td[data-field='kpiName'] a");
    private final SelenideElement viewForm_ParentName = $("[name='ParentId'] a");
    private final SelenideElement viewForm_ChangeRequestName = $("[name='Name']");
    private final SelenideElement viewForm_ChangeRequestComment = $("[name='Comment']");
    private final SelenideElement viewForm_AddChangesButton = $x("//span[contains(text(),'Внести изменения')]");


    @Step("Заполнить поля в карточке Запрос на изменение")
    public void fillRequiredFields(SnapshotChangeRequests changeRequest) {
        typeText(editForm_NameInput, changeRequest.getName());
        typeText(editForm_CommentInput, changeRequest.getComment());
//        editForm_changeReasonsTab.click();
//        typeText(editForm_ProjectReasonsInput,changeRequest.getProjectReasons() );
    }

    @Step("Проверить родительский объект, наименование и комментарий на форме просмотра Запроса на изменения")
    public void checkSnapshotData(String parentName, String changeRequestName, String changeRequestComment){
        viewForm_ParentName.shouldBe(visible).shouldHave(text(parentName));
        viewForm_ChangeRequestName.shouldBe(visible).shouldHave(text(changeRequestName));
        viewForm_ChangeRequestComment.shouldBe(visible).shouldHave(text(changeRequestComment));
    }

    @Step("Проверить, что на вкадке 'Обоснование изменений' присутствует причина {reasonName}")
    public void checkChangeReasons(String reasonName){
        viewForm_ChangeReasonsTab.click();
        checkPageIsLoaded();
        $x("//div[@id='tab-changes']//div[contains(@class,\"f-control__text\") and text()='"+ reasonName +"']").shouldBe(exist);
    }

    @Step("Проверить, что на вкадке 'Согласование изменений' присутствует Показатель {indicatorName}")
    public void checkApproveChangesKPI(String indicatorName){
        viewForm_ApproveTab.click();
        checkPageIsLoaded();
        viewForm_KPIApproveWidget.click();
        checkPageIsLoaded();
        if (!viewForm_KPIName.isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
            viewForm_KPIApproveWidget.click();
            checkPageIsLoaded();
        }
        viewForm_KPIName.shouldHave(text(indicatorName));
    }

    @Step("Нажать кнопку 'Внести изменения'")
    public void clickAddChanges(){
        viewForm_AddChangesButton.click();
        getBrowserTabs();
        switchToNextBrowserTab();
    }
}
