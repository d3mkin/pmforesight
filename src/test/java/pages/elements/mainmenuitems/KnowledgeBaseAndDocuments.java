package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.base_of_knowledge_and_documents.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class KnowledgeBaseAndDocuments extends AbstractMenuItem {

    private SelenideElement knowledgeBaseAndDocuments = menu.$(By.cssSelector("li[f-menu-slide-menulistid=\"13\"]"));
    private SelenideElement lessons = menu.$("li[f-menu-slide-menulistid=\"10029\"]");
    private SelenideElement summary = menu.$("li[f-menu-slide-menulistid=\"10028\"]");
    private SelenideElement legalDocs = menu.$("li[f-menu-slide-menulistid=\"10033\"]");
    private SelenideElement library = menu.$("li[f-menu-slide-menulistid=\"14\"]");
    private SelenideElement extendsSearch = menu.$("li[f-menu-slide-menulistid=\"15\"]");

    @Override
    public void expand() {
        knowledgeBaseAndDocuments.click();
        Selenide.sleep(1000);
    }

    @Override
    public KnowledgeBaseAndDocuments visible() {
        knowledgeBaseAndDocuments.shouldBe(visible);
        return this;
    }

    @Override
    public KnowledgeBaseAndDocuments hasCorrectText() {
        knowledgeBaseAndDocuments.shouldHave(text("База знаний и документов"));
        return this;
    }

    @Step("Открыть 'Извлеченные уроки'")
    public LessonsLearnedRegistry openLessons() {
        clickItem(knowledgeBaseAndDocuments, lessons);
        return new LessonsLearnedRegistry();
    }

    @Step("Открыть 'Итоговые выводы'")
    public SummaryConclusionsRegistry openSummary() {
        clickItem(knowledgeBaseAndDocuments, summary);
        return new SummaryConclusionsRegistry();
    }

    @Step("Открыть 'Нормативные документы'")
    public RegulationsRegistry openLegalDocs() {
        clickItem(knowledgeBaseAndDocuments, legalDocs);
        return new RegulationsRegistry();
    }

    @Step("Открыть 'Библиотека документов'")
    public DocumentLibraryRegistry openLibrary() {
        clickItem(knowledgeBaseAndDocuments, library);
        return new DocumentLibraryRegistry();
    }

    @Step("Открыть 'Расширенный поиск'")
    public AdvancedSearchRegistry openExtendsSearch() {
        clickItem(knowledgeBaseAndDocuments, extendsSearch);
        return new AdvancedSearchRegistry();
    }

}
