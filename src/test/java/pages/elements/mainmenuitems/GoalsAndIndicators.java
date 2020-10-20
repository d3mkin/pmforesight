package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.goals_and_indicators.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class GoalsAndIndicators extends AbstractMenuItem {

    private SelenideElement goalsAndIndicators = menu.$(By.cssSelector("li[f-menu-slide-menulistid=\"21\"]"));
    private SelenideElement goal = menu.$("li[f-menu-slide-menulistid=\"10037\"]");
    private SelenideElement indicators = menu.$("li[f-menu-slide-menulistid=\"10036\"]");
    private SelenideElement goalsAndIndicatorsHierarchy = menu.$("li[f-menu-slide-menulistid=\"10038\"]");
    private SelenideElement goalHierarchy = menu.$("li[f-menu-slide-menulistid=\"10043\"]");
    private SelenideElement indicatorsHierarchy = menu.$("li[f-menu-slide-menulistid=\"20048\"]");

    @Override
    public void expand() {
        goalsAndIndicators.click();
        Selenide.sleep(1000);
    }

    @Override
    public GoalsAndIndicators visible() {
        goalsAndIndicators.shouldBe(visible);
        return this;
    }

    @Override
    public GoalsAndIndicators hasCorrectText() {
        goalsAndIndicators.shouldHave(text("Цели и показатели"));
        return this;
    }

    @Step("Открыть 'Реестр целей'")
    public GoalRegistry openGoalRegistry() {
        clickItem(goalsAndIndicators, goal);
        return new GoalRegistry();
    }

    @Step("Открыть 'Реестр показателей'")
    public IndicatorsRegistry openIndicatorsRegistry() {
        clickItem(goalsAndIndicators, indicators);
        return new IndicatorsRegistry();
    }

    @Step("Открыть 'Дерево целей и показателей'")
    public TreeOfGoalsAndIndicators openGoalsAndIndicatorsHierarchy() {
        clickItem(goalsAndIndicators, goalsAndIndicatorsHierarchy);
        return new TreeOfGoalsAndIndicators();
    }

    @Step("Открыть 'Иерархию целей'")
    public HierarchyOfGoals openGoalHierarchy() {
        clickItem(goalsAndIndicators, goalHierarchy);
        return new HierarchyOfGoals();
    }

    @Step("Открыть реестр 'Иерархию показателей'")
    public HierarchyMetrics HierarchyOfMetrics() {
        clickItem(goalsAndIndicators, indicatorsHierarchy);
        return new HierarchyMetrics();
    }
}
