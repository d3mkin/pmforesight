package pages.directories.emloyees;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Employee;
import pages.elements.BasePage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class EmployeePage extends BasePage {
    //Вкладка Общая информация
    public SelenideElement commonTab = $(By.linkText("Общая информация"));
    public SelenideElement nameInput = $("#Name");
    public SelenideElement lastNameInput = $("#SurName");
    public SelenideElement firstNameInput = $("#FirstName");
    public SelenideElement secondNameInput = $("#SecondName");
    public SelenideElement positionInput = $("#Titile");
    public SelenideElement rankNumGroup = $("#control-group-Rank");
    public SelenideElement phoneInput = $("#Phone");
    public SelenideElement accountInput = $("#Account");
    public SelenideElement emailInput = $("#EMail");
    public SelenideElement unitMultiSelect = $("#UserOrgUnit");
    //Вкладка Права
    public SelenideElement rightsTab = $(By.xpath("//li[@id='tab2']//a"));
    public SelenideElement activeCheck = $("#label-Enabled");
    public SelenideElement assignToRolesCheck = $("#label-IsAssignment");
    public SelenideElement groupsBlock = $("#control-group-GroupMember");
    public SelenideElement getEmails = $("#label-IsReceiveMail");
    //
    public SelenideElement userNameField = $(By.xpath("//div[@id='card-Name']"));
    public SelenideElement userIsTop = $(By.xpath("//div[@name='IsBoss']"));
    public SelenideElement userGroupMember = $(By.xpath("//div[@id='Rights']//li/a"));
    public SelenideElement editForm = $(By.xpath("//span[@class='f-icon m-i-pencil2']"));
    public SelenideElement closeForm = $(By.xpath("//span[@class='f-icon m-i-cross']"));


    @Step ("Заполнить поля в карточке редактирования")
    public void fillFields(Employee employee) {
        commonTab.click();
        typeOrSkip(nameInput, employee.getName());
        typeOrSkip(lastNameInput, employee.getLastName());
        typeOrSkip(firstNameInput, employee.getFirstName());
        typeOrSkip(secondNameInput, employee.getSecondName());
        rightsTab.click();
        searchAndSelectFirstFromMultiSelect(groupsBlock, employee.getGroups());
    }

    @Step ("Проверить что карточка пользователя открыта")
    public void userCarsShouldBeOpened (Employee employee) {
        userNameField.shouldHave(text(employee.getName()));
    }

    @Step ("Проверить входит ли пользователь в Топ руководство или нет")
    public void checkUserStatusIsTop(String statusTop) {
        userIsTop.shouldHave(text(statusTop));
    }

    @Step ("Проверить что пользователь входит в группу")
    public void checkUserIsMemberOfGroup (String groupName) {
        $ (By.xpath("//div[@id='Rights']//li/a[text()='"+groupName+"']")).shouldBe(visible);
    }

    @Step ("Проверить что пользователь НЕ входит в группу")
    public void checkUserIsNotMemberOfGroup (String groupName) {
        $ (By.xpath("//div[@id='Rights']//li/a[text()='"+groupName+"']")).shouldNotBe(visible);
    }

    @Step ("Открыть карточку редактирования сотрудника")
    public void openEditForm () {
        editForm.click();
    }

    @Step ("Закрыть карточку просмотра")
    public void closeForm () {
        closeForm.click();
    }

    @Step ("Добавить пользователя в группу")
    public void addUserGroup (String groupName) {
        openEditForm();
        sleep(2000);
        rightsTab.shouldBe(visible).click();
        searchAndSelectFirstFromMultiSelect(groupsBlock, groupName);
        clickSaveAndClose();
    }

    @Step ("Удалить пользователя из группу")
    public void removeUserGroup (String groupName) {
        openEditForm();
        sleep(2000);
        rightsTab.shouldBe(visible).click();
        searchAndSelectFirstFromMultiSelect(groupsBlock, groupName);
        clickSaveAndClose();
    }

}
