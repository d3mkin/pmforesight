package pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import pages.elements.DeleteEntityDialog;

import java.io.File;
import java.util.ArrayList;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofMillis;

public abstract class BasePage {
    private ArrayList<String> allTabs;
    private String currentTab;
    protected SelenideElement window = $(".k-window");
    protected SelenideElement header = window.$(".k-window-titlebar");
    protected SelenideElement windowName = $x("//div[@class='k-widget k-window']//span[@class='k-window-title']");
    protected SelenideElement actions = header.$(".k-window-actions");
    protected SelenideElement content = window.$(".k-window-content");
    protected SelenideElement loadImage = $(".k-loading-image");

    //Разворачивает окно на полный экран
    protected SelenideElement expandButton = actions.$("[aria-label='window-Maximize']");

    //Форма редактирования
    private final SelenideElement editForm = $(".f-card__header .m-i-pencil2");
    //Кнопка Восстановить из полноэкронного режима
    protected SelenideElement restoreButton = actions.$("a[aria-label='window-restore']");
    protected SelenideElement saveAndOpenCardButton = $("a[aria-label='hyperlink-open']");
    protected SelenideElement saveButton = $("a[aria-label='Save']");
    protected SelenideElement saveAndCloseButton = $("a[aria-label='kendo-save-and-close']");
    protected SelenideElement closeButton = $("[aria-label='Close']");
    protected SelenideElement anyDropDown = $x("//div[contains(@class,'k-list-container k-popup k-group k-reset k-state-border-up')]");

    //Виджет календаря
    private final SelenideElement resultCalendarSelect = $x("//span[@class='k-widget k-datepicker']//span[@class='k-select']");
    private final SelenideElement CalendarInput = $x("//input[@id='DateAndTime']");
    private final SelenideElement calendarCurrentDay = $x("//a[@class='k-link k-nav-today']");

    //Селекты
    private final SelenideElement selectBlock = $("div.k-animation-container[aria-hidden='false']");
    private final SelenideElement selectFilterInput = selectBlock.$(".k-textbox");
    private final SelenideElement selectMultiSelectInput = $x("//div[@class='k-multiselect-wrap k-floatwrap']//input[@class='k-input']");
    private final SelenideElement selectFirstItem = selectBlock.$("li:first-child");
    private final ElementsCollection selectAllItems = $$("div.k-animation-container[aria-hidden='false'] li");

    //Диалоговое окно с предупреждением
    private final SelenideElement closeInDialog = $x("//button[@class='k-button k-primary']");
    private final ElementsCollection dialogWarnings = $$x("//div[@data-role='dialog']//li");
    private final SelenideElement dialogTitle = $(".k-dialog-title");
    private final SelenideElement dialogMessage = $(".k-dialog-content");
    private final SelenideElement closeDialogWithSave = $x("//button[@class='k-button k-primary']");
    private final SelenideElement closeDialogWithoutSave = $x("//body//button[2]");
    private final SelenideElement cancelDialog = $x("//body//button[3]");
    private final SelenideElement deleteEntity =  $x("//button[@class='k-button k-primary']");

    //Модальное окно загрузки файлов
    private final SelenideElement uploadInput = $x("//input[@id='f-file-uploader']");
    private final SelenideElement uploadedFileName = $x("//span[@class='k-file-name']");
    private final SelenideElement uploadedFileStatus = $x("//strong[@class='k-upload-status k-upload-status-total']");

    //Методы по работе с вкладками браузера
    //Получаем и записываем id вкладок браузера в список
    @Step ("Получить список открытых вкладок браузера")
    public void getBrowserTabs () {
        allTabs = new ArrayList<String>(WebDriverRunner.getWebDriver().getWindowHandles());
        currentTab = WebDriverRunner.getWebDriver().getWindowHandle();
    }

    //Нумерация начинается с 0 - поэтому первоначальная вкладка будет '0', а новая будет '1'
    @Step("Переключиться на вкладку {numberTab}")
    public void switchToBrowserTab(int numberTab){
        WebDriverRunner.getWebDriver().switchTo().window(allTabs.get(numberTab));
    }

    @Step("Переключиться на следующую вкладку браузера")
    public void switchToNextBrowserTab(){
        WebDriverRunner.getWebDriver().switchTo().window(allTabs.get(allTabs.size()-1));
    }

    @Step("Переключиться на предыдущую вкладку браузера")
    public void switchToPreviousBrowserTab(){
        WebDriverRunner.getWebDriver().switchTo().window(currentTab);
    }

    @Step("Закрыть текущую вкладку в браузере")
    public void closeCurrentBrowserTab() {
        //WebDriverRunner.getWebDriver().close();
        Selenide.closeWindow();
    }

    @Step("Проверка открытия модального окна")
    public void modalWindowShouldBeOpened() {
        window.shouldBe(visible);
        header.shouldBe(visible);
        actions.shouldBe(visible);
        content.shouldBe(visible);
    }

    @Step("Проверка закрытия модального окна")
    public void modalWindowShouldBeClosed() {
        window.shouldNotBe(visible);
        header.shouldNotBe(visible);
    }

    @Step("Проверка названия модального окна")
    public void modalWindowShouldHaveTitle(String titleName) {
        checkPageIsLoaded();
        $x("//*[contains(@class,'k-window-title') and text()="+titleName+"]");
    }

    @Step("Нажать развернуть на весь экран")
    public void clickExpand() {
        expandButton.click();
        $(".k-widget.k-window.k-window-maximized").shouldBe(visible);
    }

    @Step("Нажать кнопку восстановить")
    public void clickRestore() {
        restoreButton.click();
    }

    @Step("Нажать Сохранить и открыть карточку просмотра")
    public void clickSaveAndOpenCard() {
        saveAndOpenCardButton.click();
    }

    @Step("Нажать сохранить")
    public void clickSave() {
        saveButton.click();
    }

    @Step("Нажать Сохранить и закрыть")
    public void clickSaveAndClose() {
        saveAndCloseButton.click();
    }

    @Step ("Нажать закрать")
    public void clickClose() {
        closeButton.click();
    }

    @Step("Проверка закрытия окна")
    public void shouldBeClosed() {
        window.shouldNot(visible);
    }

    //Диалоговое окно про обязательные поля\несохраненные изменения
    @Step("Проверить наличие сообщений в информационном окне")
    public void shouldHaveMessageAboutRequiredFields(String... messages) {
        dialogWarnings.shouldHave(texts(messages));
    }

    public void shouldHaveMessageAboutUnsaved() {
        dialogMessage.shouldHave(text("На карточке есть несохраненные изменения Сохранить изменения?"));
    }
    public void shouldHaveMessage(String message) {
        dialogMessage.shouldHave(text(message));
    }

    @Step("Закрыть диалоговое окно")
    public void closeDialog() {
        closeInDialog.click();
    }

    public void dialogWindowShouldHaveTitle() {
        dialogTitle.shouldHave(text("Закрыть карточку"));
    }

    public void clickDialogCancel() {
        cancelDialog.click();
    }

    public void clickDialogCloseWithoutSave() {
        closeDialogWithoutSave.click();
    }

    public void clickDialogSave () {
        closeDialogWithSave.click();
    }

    @Step ("Подтвердить удаление сущности")
    public void confirmDeleteEntity() {deleteEntity.click();}

    @Step ("Подтвердить переход на следующую стадию")
    public void clickAcceptNextStageTransition() {
        dialogTitle.shouldHave(text("Перевод на следующую стадию"));
        dialogMessage.shouldHave(text("Вы действительно хотите завершить текущую стадию и перейти к следующей?"));
        closeDialog();
    }

    @Step ("Подтвердить переход на предыдушую стадию")
    public void clickAcceptPrevStageTransition() {
        dialogTitle.shouldHave(text("Возврат к предыдущей стадии"));
        dialogMessage.shouldHave(text("Вы действительно хотите вернуться к предыдущей стадии?"));
        closeDialog();
    }

    @Step("Открыть форму редактирования")
    public void clickEditForm() {
        editForm.click();
        modalWindowShouldBeOpened();
    }

    //Методы для заполненния данных в зависимости от типа поля

    @Step ("Ввести в текстовое поле значение {value}")
    public void typeText(SelenideElement el, String value) {
        if (value == null) {
            return;
        }
        el.setValue(value);
        sleep(2000);
    }

    @Step ("Очистить текстовое поле u ввести новое значение {value}")
    public void clearAndTypeText(SelenideElement el, String value) {
        if (value == null) {
            return;
        }
        el.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $(".k-window-title").click();
        el.setValue(value);
        sleep(2000);
    }

    public void typeNumeric(SelenideElement wrap, SelenideElement input, String value) {
        if (value == null) {
            return;
        }
        wrap.click();
        input.clear();
        wrap.click();
        input.sendKeys(value);
    }

    public  void searchInAutocompleteAndClickToFirst(SelenideElement el, String value) {
        el.click();
        el.sendKeys(value);
        sleep(1000);
        selectFirstItem.click();
    }

    /*public void searchInSelectAndClickToFirst(SelenideElement el, String value) {
        if (value == null) {
            return;
        }
        el.click();
        selectFilterInput.shouldBe(visible, ofMillis(timeout));
        selectFilterInput.sendKeys(value);
        sleep(2000);
        selectAllItems.shouldHave(size(1));
        selectFirstItem.click();
        sleep(2000);
        el.shouldHave(exactText(value), ofMillis(timeout));
    }*/

    @Step ("Выбрать в выпадающем списке значение {value}")
    public void searchAndSelectFirstFromSelect(SelenideElement el, String value) {
        if (value == null) {
            return;
        }
        el.click();
        sleep(2000);
        selectFilterInput
                .shouldBe(visible, ofMillis(timeout))
                .sendKeys(value);
        sleep(2000);
        selectAllItems.shouldHave(size(1));
        selectFirstItem.click();
        el.shouldHave(text(value), ofMillis(timeout));
    }

    @Step ("Выбрать в выпадающем списке значение {value}")
    public void searchAndSelectFirstFromMultiSelect (SelenideElement el, String value) {
        if (value == null) {
            return;
        }
        el.click();
        sleep(2000);
        selectMultiSelectInput
                .shouldBe(visible, ofMillis(timeout))
                .sendKeys(value);
        sleep(2000);
        selectAllItems.shouldHave(size(1));
        selectFirstItem.click();
        $(".k-window-title").click();
    }

    @Step ("Ввести дату {value}")
    public void typeDate(SelenideElement dateInput, String value) {
        if (value == null) {
            return;
        }
        dateInput.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.BACK_SPACE));
        String[] splitDate = value.split("\\.");
        dateInput.sendKeys(splitDate[0]);
        dateInput.sendKeys(Keys.ARROW_RIGHT);
        dateInput.sendKeys(splitDate[1]);
        dateInput.sendKeys(Keys.ARROW_RIGHT);
        dateInput.sendKeys(splitDate[2]);
    }
    //TODO: Сделать рефакторинг кода для тестов связанных с Результатами в части проставления даты

    @Step("Дата достижения Результата - выбрать текущий день")
    public void selectResultCalendarCurrentDay(){
        resultCalendarSelect.click();
        calendarCurrentDay.shouldBe(visible).click();
    }

    @Step("Дата достижения - выбрать текущий день")
    public void selectCalendarCurrentDay(){
        calendarCurrentDay.shouldBe(visible).click();
        calendarCurrentDay.shouldBe(visible).click();
    }

    public void chooseValueFromDropDown(SelenideElement el, String value) {
        el.click();
        selectFilterInput.setValue(value);
        selectAllItems.shouldHaveSize(1);
        selectFirstItem.click();
        sleep(1000);
        el.click();
        selectFilterInput.pressEnter();
        sleep(1000);
    }

    @Step ("Загрузить документ")
    public void uploadFile(File fileToUpload){
        uploadInput.uploadFile(fileToUpload);
    }

    @Step ("Проверить что документ успешно загружен")
    public void checkFileIsUploaded(File fileToUpload) {
        uploadedFileName.shouldHave(text(fileToUpload.getName()));
        uploadedFileStatus.shouldHave(text("Готово"));
    }

    @Step ("Закрыть форму загрузки документа")
    public void closeUploadWindow() {
        $x("//span[contains(text(),'Файлы')]").click();
        $x("//div[@class='k-widget k-window']//a[@role='button']").click();
    }

    @Step ("Проверить что страница загрузилась")
    public void checkPageIsLoaded () {
        //loadImage.shouldNotBe(visible, Duration.ofMinutes(1));
        $$(".k-loading-image").shouldBe(size(0), ofMillis(timeout));
        $(".k-loading-mask").shouldNotBe(exist, ofMillis(timeout));
    }

    @Step("Подтвердить удаление")
    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).waitUntil(visible,Configuration.timeout);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Нажать по пункту {menuName}")
    public void clickOnMenuItem(String menuName) {
        ElementsCollection menuItems = $$(".f-card__left.f-menu .f-menu__text");
        ElementsCollection popupMenuItems = $$(".f-popup__container .f-menu__text");
        SelenideElement additionalMenu = $(".f-card__left .k-i-more-vertical");
        if (menuItems.findBy(text(menuName)).isDisplayed()) {
            menuItems.findBy(text(menuName)).click();
        }
        else {
            additionalMenu.click();
            $(".f-popup__container").shouldBe(visible);
            popupMenuItems.findBy(text(menuName)).click();
        }
        $$(".f-card__left.f-menu .f-menu__text").findBy(text(menuName)).click();
    }
}
