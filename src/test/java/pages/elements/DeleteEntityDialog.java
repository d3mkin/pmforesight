package pages.elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class DeleteEntityDialog {
    private SelenideElement window = $(".k-dialog");
    private SelenideElement windowTitle = $(".k-dialog-title");
    private SelenideElement closeIcon = window.$(".k-dialog-close");
    private SelenideElement yesButton = $(By.xpath("//button[contains(text(),'Удалить')]"));
    private SelenideElement noButton = $("//button[contains(text(),'Нет')]");

    @Step("Подтвердить удаление")
    public void clickDeleteYes() {
        yesButton.click();
    }
}
