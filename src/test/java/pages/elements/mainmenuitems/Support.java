package pages.elements.mainmenuitems;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Support {

    private final SelenideElement menu = $(".f-menu_main");
    private final SelenideElement support = $("#f-menu-support");
    private final SelenideElement labelSupport = $(By.xpath("//span[contains(@class,'k-window-title') and text()='Служба поддержки. Есть вопрос? Задайте его, воспользовавшись формой ниже']"));
    private final SelenideElement submit = $(By.xpath("//button[text()='Отправить']"));
    private final SelenideElement close = $("a[aria-label='Close']");
    private final SelenideElement requiredEmail = $(By.xpath("//span[contains(@data-for,\"email\")]"));
    private final SelenideElement requiredPhone = $(By.xpath("//span[contains(@data-for,\"phone\")]"));
    private final SelenideElement requiredDescription = $x("//textarea[contains (@name, 'Name') and contains(@required, 'required')]");


    @Step("Проверка открытия окна Служба поддержки")
    public void open() {
        support.click();
        labelSupport.shouldBe(visible);
    }
    @Step("Проверка кнопок в окне Служба поддержки")
    public void checkButton() {
        submit.shouldBe(visible);
        close.shouldBe(visible);
    }
    @Step("Проверка полей обязательных для заполнения в окне Служба поддержки")
    public void checkReqFields() {
        requiredDescription.shouldBe(visible);
    }

    public void clickCancel() {
        close.click();
    }
}
