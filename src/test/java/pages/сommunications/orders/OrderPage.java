package pages.сommunications.orders;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import model.Order;
import pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class OrderPage extends BasePage {
    //Вкладка общая информация
    private final SelenideElement nameInput = $(By.xpath("//input[@id='Name']"));
    private final SelenideElement priorityInput = $(By.xpath("//div[@id='control-group-PriorityId']//span[@class='k-widget k-dropdown']"));
    private final SelenideElement planDateInput = $(By.xpath("//input[@id='PlanDate']"));
    private final SelenideElement tabRoles = $(By.xpath("//li[@id='tab-roles']"));
    private final SelenideElement executorInput = $(By.xpath("//div[@id='control-group-Responsible']//span[@class='k-input']"));

    public void fillFields(Order order ) {
        nameInput.waitUntil(visible, 10000);
        typeText(nameInput, order.getName());
        searchAndSelectFirstFromSelect(priorityInput, order.getPriority());
        typeDate(planDateInput, order.getPlanDate());
        tabRoles.click();
        searchAndSelectFirstFromSelect(executorInput, order.getExecutor());
    }
}
