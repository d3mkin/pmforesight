package pages.managementobjects.portfolio;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Portfolio;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class CreatePortfolioPage extends BasePage {
    private SelenideElement tabCommonInfo = $(By.linkText("Общая информация"));
    private SelenideElement tabRoles = $(By.linkText("Роли"));
    private SelenideElement name=$("#Name");
    private SelenideElement description=$("#Description");
    private SelenideElement target=$("#control-group-ActivityGoal");
    private SelenideElement supervisor=$("#control-group-Leader");
    private SelenideElement administrator=$("#control-group-Administrator");
    private SelenideElement workingGroup=$("#control-group-WorkGroup");



    @Step("В модальном окне создания портфеля заполнить обязательные поля на вкладке \"Общая информация\"")
    public void fillInConnonInfo(Portfolio portfolio){
        tabCommonInfo.click();
        typeText(name,portfolio.getNameValue());
        typeText(description,portfolio.getDescription());
        searchAndSelectFirstFromSelect(target, portfolio.getPurpose());
    }
    @Step("В модальном окне создания портфеля перейти на вкладку \"Роли\" и заполнить обязательные поля")
    public void fillRoles(Portfolio portfolio){
        tabRoles.click();
        searchAndSelectFirstFromSelect(supervisor, portfolio.getSupervisor());
        searchAndSelectFirstFromSelect(administrator, portfolio.getAdministrator());
        searchAndSelectFirstFromSelect(workingGroup, portfolio.getWorkingGroup());
    }


}
