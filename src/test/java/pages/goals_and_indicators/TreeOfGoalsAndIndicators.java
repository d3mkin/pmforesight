package pages.goals_and_indicators;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeOfGoalsAndIndicators implements Registry {
    private Header header;
    private MainMenu mainMenu;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/kpichart";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $(".f-page__name");
    private SelenideElement table = $(By.xpath("//div[contains(@class,\"kpichart__content\")]"));
    private SelenideElement clickDropDownDateStart = $(By.xpath("//span[contains(@aria-owns,\"kpichart_year_start_listbox\")]"));
    private SelenideElement clickDropDownDateEnd = $(By.xpath("//span[contains(@aria-owns,\"kpichart_year_finish_listbox\")]"));
    private SelenideElement activeDropDown = $("div[aria-hidden=\"false\"].k-animation-container");
    private SelenideElement appliedButton = $(By.xpath("//a[contains(@class,\"k-button k-button-icontext k-toolbar-last-visible\")]"));
    private SelenideElement checkKvartal = $(By.xpath("//div[contains(@class,\"p-col-kpi-values-content\")]"));
    //text[contains(@x,"30") and contains(@y,"6") and text()='2018 Янв']

    public TreeOfGoalsAndIndicators() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Дерево целей и показателей' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Дерево целей и показателей' через меню")
    public void openFromMenu() {
        mainMenu.goalsAndIndicators().openGoalsAndIndicatorsHierarchy();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Дерево целей и показателей'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
        sleep(2000);
    }

    @Step("Проверка корректности ссылки {this.url}")
    public TreeOfGoalsAndIndicators shouldHaveCorrectLink() {
        assertEquals(WebDriverRunner.url(), url, "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public TreeOfGoalsAndIndicators shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public TreeOfGoalsAndIndicators shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Выбрать год начала для сортировки")
    public void selectFirstYearRange(String year)
    {
        clickDropDownDateStart.click();
        activeDropDown.waitUntil(visible, 2000);
        $(By.xpath("//li[text()='"+year+"']")).click();
    }

    @Step("Выбрать год окончания для сортировки")
    public void selectEndYearRange(String year)
    {
        clickDropDownDateEnd.click();
        activeDropDown.waitUntil(visible, 2000);
        $(By.xpath("//*[@id=\"kpichart_year_finish_listbox\"]/li[text()='"+year+"']")).click();
    }
    @Step("Выбрать год окончания для сортировки")
    public void checkYearRange(String startDate, String endDate)
    {
        appliedButton.click();
        sleep(2000);
        assertTrue(checkKvartal.getText().startsWith(startDate+"\n1-й квартал\nЯнв"));
        assertTrue(checkKvartal.getText().endsWith(endDate+"\n4-й квартал\nОкт\nНоя\nДек"));
    }
}
