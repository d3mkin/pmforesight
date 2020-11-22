package pages.administration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import helpers.URLS;
import pages.elements.ControlPanel;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DocumentsPage extends AbstractAdminPage {

    private ControlPanel controlPanel;

    public DocumentsPage() {
        super(
                Configuration.baseUrl + URLS.DOCUMENTS,
                "Документ",
                $(".f-page__grid-name"),
                $(".f-grid__grid"));
        controlPanel = new ControlPanel();
    }

    @Override
    @Step("Проверка открытия страницы 'Документы'")
    public void shouldBePage() {
        shouldHavePageName();
        shouldHaveContent();
        shouldHaveRightUrlAndTitle();
        checkControlPanel();
    }

    @Step("Проверка наличия любых групп на странице")
    public void checkGroup() {
        controlPanel.clickHideAll();
        $$(".slick-group-title").shouldHave(sizeGreaterThan(1));
    }

    @Step("Проверка отображения иконки сортировки")
    public void checkSortIcon() {
        $("div[title=\"Номер\"]").click();
        $(".slick-sort-indicator").shouldBe(visible);
    }

    @Step("Проверка наличия элементов в панели управления")
    public void checkControlPanel() {
        checkGroup();
        checkSortIcon();
        controlPanel.shouldHaveSearchInput();
        controlPanel.shouldHaveAddButton();
        controlPanel.shouldHaveEditByClick();
        controlPanel.shouldHaveExpandAll();
        controlPanel.shouldHaveHideAll();
        //controlPanel.shouldHaveExport();
        controlPanel.shouldHaveUpdate();
        controlPanel.shouldHaveExtendFilter();
        controlPanel.shouldHaveReset();
    }
}
