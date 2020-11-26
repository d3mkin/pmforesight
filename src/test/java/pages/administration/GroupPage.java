package pages.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.User;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class GroupPage extends BasePage {
    private final SelenideElement searchInput = $(By.xpath("//input[@placeholder='Поиск']"));
    private final SelenideElement firstRaw = $(By.xpath("//div[@class='grid-canvas']/div"));
    private final SelenideElement groupMemberInput = $(By.xpath("//div[@id='control-group-GroupMember']//input[@class='k-input']"));

    @Step ("Добавить в группу {groupName} пользователя {userName}")
    public void addEmployeeToGroup(String groupName, User user) {

        searchInput.clear();
        searchInput.sendKeys(groupName);
        firstRaw.shouldBe(Condition.visible).click();
        sleep(2000);
        searchAndSelectFirstFromMultiSelect(groupMemberInput, user.getName());
        clickClose();
        closeDialog();
        sleep(2000);
    }
}
