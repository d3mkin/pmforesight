package pages.elements;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.auth.LogoutPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class Header {

    private SelenideElement headerLink = $(By.cssSelector("a > .f-header__text"));
    private SelenideElement search = $(By.cssSelector("button[data-toggle=\"f-search\"]"));
    private SelenideElement accountButton = $(By.cssSelector("#button_account"));
    private SelenideElement accountText = $(By.cssSelector("#button_account span.f-header__text"));
    private SelenideElement accountMenu = $(By.cssSelector(".f-popup_visible_bottom #f_popup_account > ul.f-menu__list"));
    private SelenideElement itemProfile = $(By.cssSelector(".f-popup_visible_bottom #f_popup_account a[href=\"/page/profile\"]"));
    private SelenideElement itemChangePassword = $(By.cssSelector(".f-popup_visible_bottom #f_popup_account a[onclick=\"Dialogs.SetPassword(Asyst.Workspace.currentUser.Id);\"]"));
    private SelenideElement itemAdmin = $(By.cssSelector(".f-popup_visible_bottom #f_popup_account a[href=\"/admin\"]"));
    private SelenideElement itemLogout = $(By.cssSelector(".f-popup_visible_bottom a[href=\"/logout\"] "));
    private SelenideElement buttonPMForce = $(By.cssSelector("a.f-header__link"));


    @Step("Название сайта должно быть {0}")
    public void shouldHaveHeaderText(String headerText) {
        headerLink.shouldHave(text(headerText));
    }

    @Step("Имя пользователя должно быть {0}")
    public void shouldHaveAccountText(String name) {
        accountText.shouldHave(text(name));
    }

    @Step("Нажать кнопку выход в выпадающем меню")
    public LogoutPage logout() {
        if (WebDriverRunner.isFirefox()) {
            sleep(5000);
        }
        if (accountMenu.isDisplayed()) {
            itemLogout.click();
        } else {
            accountButton.click();
            accountMenu.waitUntil(visible, 15000);
            itemLogout.click();
        }
        return new LogoutPage();
    }

    @Step("Нажать на кнопку перехода к авторизации")
    public void goToLoginPage() {
        buttonPMForce.click();
    }

    @Step("Открыть форму поиска")
    public void openSearchForm() {
        search.click();
    }

    @Step("Нажать на ПМ Форсайт")
    public void clickOnHeader() {
        headerLink.click();
    }

    @Step("Открыть меню пользователя")
    public void clickOnMenu() {
        accountButton.click();
        accountMenu.waitUntil(visible, 2000);
    }

    @Step("Выпадающее меню должно содержать пункты 'Профиль', 'Смена пароля...', 'Администрирование', 'Выход'")
    public void shouldHaveAccountMenu() {
        itemProfile.shouldHave(text("Профиль"));
        itemChangePassword.shouldHave(text("Смена пароля..."));
        itemAdmin.shouldHave(text("Администрирование"));
        itemLogout.shouldHave(text("Выход"));
    }
}
