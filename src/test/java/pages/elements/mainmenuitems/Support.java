package pages.elements.mainmenuitems;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Support {

    private final SelenideElement menu = $(".f-menu_main");
    private final SelenideElement support = $("#f-menu-support");
    private final SelenideElement labelSupport = $(By.xpath("//span[contains(@class,\"k-window-title k-dialog-title\") and text()='Служба поддержки']"));
    private final SelenideElement submit = $(By.xpath("//button[text()='Отправить']"));
    private final SelenideElement cancel = $(By.xpath("//button[text()='Отмена']"));
    private final SelenideElement requiredEmail = $(By.xpath("//span[contains(@data-for,\"email\")]"));
    private final SelenideElement requiredPhone = $(By.xpath("//span[contains(@data-for,\"phone\")]"));
    private final SelenideElement requiredDescription = $(By.xpath("//span[contains(@data-for,\"name\")]"));


    @Step("Проверка открытия окна Служба поддержки")
    public void open() {
        support.click();
        labelSupport.shouldBe(visible);
    }
    @Step("Проверка кнопок в окне Служба поддержки")
    public void checkButton() {
        submit.shouldBe(visible);
        cancel.shouldBe(visible);
    }
    @Step("Проверка полей обязательных для заполнения в окне Служба поддержки")
    public void checkReqFields() {
        submit.click();
        requiredEmail.shouldBe(visible);
        requiredPhone.shouldBe(visible);
        requiredDescription.shouldBe(visible);
    }

    public void clickCancel() {
        cancel.click();
    }
}
