package pages.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.File;
import java.util.ArrayList;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public abstract class BasePage {
    private ArrayList<String> tabs;
    protected SelenideElement window = $(".k-window");
    protected SelenideElement header = window.$(".k-window-titlebar");
    protected SelenideElement windowName = header.$(".k-window-title");
    protected SelenideElement actions = header.$(".k-window-actions");
    protected SelenideElement content = window.$(".k-window-content");
    //Разворачивает окно на полный экран
    protected SelenideElement expandButton = actions.$("a[aria-label='window-Maximize']");
    //Кнопка Восстановить из полноэкронного режима
    protected SelenideElement restoreButton = actions.$("a[aria-label='window-restore']");
    protected SelenideElement saveAndOpenCardButton = $("a[aria-label='hyperlink-open']");
    protected SelenideElement saveButton = $("a[aria-label='Save']");
    protected SelenideElement saveAndCloseButton = $("a[aria-label='kendo-save-and-close']");
    protected SelenideElement closeButton = $("a[aria-label='Close']");
    protected SelenideElement anyDropDown = $(By.xpath("//div[contains(@class,'k-list-container k-popup k-group k-reset k-state-border-up')]"));

    //Виджет календаря
    private final SelenideElement resultCalendarSelect = $(By.xpath ("//span[@class='k-widget k-datepicker']//span[@class='k-select']"));
    private final SelenideElement CalendarInput = $(By.xpath("//input[@id='DateAndTime']"));
    private final SelenideElement calendarCurrentDay = $(By.xpath("//a[@class='k-link k-nav-today']"));

    //Селекты
    private final SelenideElement selectBlock = $("div.k-animation-container[aria-hidden='false']");
    private final SelenideElement selectFilterInput = selectBlock.$(".k-textbox");
    private final SelenideElement selectMultiSelectInput = $ (By.xpath("//div[@class='k-multiselect-wrap k-floatwrap']//input[@class='k-input']"));
    private final SelenideElement selectFirstItem = selectBlock.$("li:first-child");
    private final ElementsCollection selectAllItems = $$("div.k-animation-container[aria-hidden='false'] li");

    //Диалоговое окно с предупреждением
    private final SelenideElement closeInDialog = $(By.xpath("//button[@class='k-button k-primary']"));
    private final ElementsCollection dialogWarnings = $$(By.xpath("//div[@data-role='dialog']//li"));
    private final SelenideElement dialogTitle = $(".k-dialog-title");
    private final SelenideElement dialogMessage = $(".k-dialog-content");
    private final SelenideElement closeDialogWithSave = $(By.xpath("//button[@class='k-button k-primary']"));
    private final SelenideElement closeDialogWithoutSave = $(By.xpath("//body//button[2]"));
    private final SelenideElement cancelDialog = $(By.xpath("//body//button[3]"));
    private final SelenideElement deleteEntity =  $(By.xpath("//button[@class='k-button k-primary']"));

    //Модальное окно загрузки файлов
    private final SelenideElement uploadInput = $(By.xpath("//input[@id='f-file-uploader']"));
    private final SelenideElement uploadedFileName = $(By.xpath("//span[@class='k-file-name']"));
    private final SelenideElement uploadedFileStatus = $(By.xpath("//strong[@class='k-upload-status k-upload-status-total']"));

    //Методы по работе с вкладками браузера
    //Получаем и записываем id вкладок браузера в список
    public void getBrowserTabs () {
        tabs = new ArrayList<String>(WebDriverRunner.getWebDriver().getWindowHandles());
    }

    //Нумерация начинается с 0 - поэтому первоначальная вкладка будет '0', а новая будет '1'
    public void switchToBrowserTab(int numberTab){
        WebDriverRunner.getWebDriver().switchTo().window(tabs.get(numberTab));
    }

    public void closeCurrentBrowserTab() {
        WebDriverRunner.getWebDriver().close();
    }

    @Step("Проверка открытия модального окна")
    public void shouldBeOpened() {
        window.shouldBe(visible);
        header.shouldBe(visible);
        windowName.shouldBe(visible);
        actions.shouldBe(visible);
        content.shouldBe(visible);
    }

    @Step("Проверка названия модального окна")
    public void shouldHaveTitle(String name) {
        windowName.shouldHave(text(name));
    }

    @Step("Нажать развернуть на весь экран")
    public void clickExpand() {
        expandButton.shouldBe(visible).click();
        $(By.xpath("//div[@class='k-widget k-window k-window-maximized']")).shouldBe(visible);
    }

    @Step("Нажать кнопку восстановить")
    public void clickRestore() {
        restoreButton.shouldBe(visible).click();
    }

    @Step("Нажать Сохранить и открыть карточку просмотра")
    public void clickSaveAndOpenCard() {
        saveAndOpenCardButton.shouldBe(visible).click();
    }

    @Step("Нажать сохранить")
    public void clickSave() {
        saveButton.shouldBe(visible).click();
    }

    @Step("Нажать Сохранить и закрыть")
    public void clickSaveAndClose() {
        saveAndCloseButton.shouldBe(visible).click();
    }

    @Step ("Нажать закрать")
    public void clickClose() {
        closeButton.shouldBe(visible).click();
    }

    @Step("Проверка закрытия окна")
    public void shouldBeClosed() {
        window.shouldNot(visible);
    }

    public void typeOrSkip(SelenideElement el, String value) {
        if (value == null) {
            return;
        }
        el.setValue(value);
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

    public void shouldHaveTitle() {
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


    //Методы для заполненния данных в зависимости от типа поля
    public void typeOrSkipNumeric(SelenideElement wrap, SelenideElement input, String value) {
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

    public void searchInSelectAndClickToFirst(SelenideElement el, String value) {
        if (value == null) {
            return;
        }
        el.click();
        sleep(2000);
        selectFilterInput.shouldBe(visible).setValue(value);
        sleep(2000);
        selectAllItems.shouldHaveSize(1);
        selectFirstItem.click();
    }

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

    public void searchAndSelectFirstFromMultiSelect (SelenideElement el, String value) {
        el.click();
        sleep(1000);
        selectMultiSelectInput.shouldBe(visible).setValue(value);
        sleep(1000);
        selectFirstItem.click();
    }

    public void searchInSelectAndClickToFirstWithCheckDropDown(SelenideElement el, String value) {
       if (value == null) {
           return;
      }
        el.click();
        sleep(1000);
        selectFilterInput.shouldBe(visible).setValue(value);
        sleep(1000);
        selectAllItems.shouldHaveSize(1);
        selectFirstItem.click();
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
        $(By.xpath("//a[@class='f-tab f-tab__link f_active']")).click();
        $(By.xpath("//div[@class='k-widget k-window']//a[@role='button']")).click();
    }
}
