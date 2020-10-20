package pages.base_of_knowledge_and_documents.lessons_learned;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.LessonLearned;
import pages.elements.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LessonLearnedPage extends BasePage {

    //Общая информация
    private SelenideElement labelOfTab = $(By.xpath("//span[contains(@class,\"k-window-title\")]"));
    private SelenideElement activityDropDown = $(By.xpath("//div[contains(@id,\"control-group-Activity\")]/..//span[contains(@class,\"k-input\")]"));
    private SelenideElement name = $(By.xpath("//input[contains(@data-binding-title,\"Наименование\")]"));
    private SelenideElement description = $(By.xpath("//textarea[contains(@name,\"Description\")]"));
    private SelenideElement generalInfluence = $(By.xpath("//textarea[contains(@name,\"Impact\")]"));
    private SelenideElement recommendations = $(By.xpath("//textarea[contains(@name,\"Recommedation\")]"));
    private SelenideElement positiveLessonCheckBox = $(By.xpath("//input[contains(@name,\"IsPositive\")]"));
    //Роли
    private SelenideElement roles = $(By.xpath("//a[text()='Роли']"));
    private SelenideElement editors = $(By.xpath("//div[contains(@class,\"k-multiselect-wrap k-floatwrap\")]"));

    public void fillFields(LessonLearned lessonLearned) {
        labelOfTab.shouldBe(visible);
        description.waitUntil(visible, 10000);
        searchInSelectAndClickToFirstWithCheckDropDown(activityDropDown, lessonLearned.getActivity());
        typeOrSkip(name, lessonLearned.getName());
    }
}
