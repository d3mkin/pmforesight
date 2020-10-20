package pages.index.widgets;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyProjectPage {
    private String getText;
    private String pathToFile;

    private final DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd-HHmm");

    private final String userName = System.getProperty("user.name");

    private final String url = Configuration.baseUrl + "/Page/Index";
    private final SelenideElement myProjectWidget = $(By.xpath("//div[contains(@class,\"f-widget f-widget_animation\") and contains(.//span, 'Мои проекты')]"));
    private final SelenideElement projectButton = $(By.xpath("//a[text()='Проекты']"));
    private final SelenideElement collapseMyProject = myProjectWidget.$(By.xpath("//span[contains(@class,\"f-icon k-icon k-i-arrow-chevron-right f-icon_animate f-icon_rotate_90deg\")]"));
    private final SelenideElement searchInmyProject = myProjectWidget.$(By.xpath("//input[contains(@placeholder,\"Поиск\")]"));
    private final SelenideElement downloadButton = myProjectWidget.$(By.xpath("//a[contains(@data-tooltip,\"Выгрузка\")]"));
    private final SelenideElement resultAfterSearch = myProjectWidget.$(By.xpath("//div[contains(@class,\"ui-widget-content slick-row even\")][2]"));
    private final SelenideElement labelOfOpenProject = $(By.xpath("//li[contains(@class,\"name\")]"));
    private final SelenideElement nameOfProject = myProjectWidget.$(By.xpath("//div[contains(@class,\"slick-cell l2 r2\")]"));
    private final SelenideElement closeOpenPorject = $(By.xpath("//div[contains(@id,\"closeForm\")]"));
    private final SelenideElement statusProject = $(By.xpath("//div[text()='Статус проекта']"));
    private final SelenideElement statusOfProjectAfterOpen = $(By.xpath("//div[contains(@class,\"tile-content-count smaller\")]"));

    @Step("Открыть 'Информационная панель'")
    public void open() {
        Selenide.open(url);
    }

    @Step("Проверка отображения виджета 'Мои проекты'")
    public void shouldBeWidgetsMyProject()
    {
        projectButton.waitUntil(visible, 20000);
        myProjectWidget.shouldBe(visible);
        projectButton.shouldBe(visible);
        collapseMyProject.shouldBe(visible);
    }
    @Step("Проверка корректности работы поиска в виджете 'Мои проекты'")
    public void searchdMyProject(String nameproject)
    {
        searchInmyProject.sendKeys(nameproject);
        sleep(2000);
        assertTrue(nameOfProject.getText().contains(nameproject));
    }

    @Step("Проверка открытия проекта из виджета 'Мои проекты'")
    public void openProjectFromWidgetMyProject(String nproject, String statusOfProject) {
        resultAfterSearch.click();
        assertTrue(labelOfOpenProject.getText().contains(nproject));
        statusProject.shouldBe(visible);
        assertTrue(statusOfProjectAfterOpen.getText().equals(statusOfProject));

    }

    @Step("Закрыть открытый проект из виджета 'Мои проекты'")
    public void closeOpenProject() {
        closeOpenPorject.click();
    }

}
