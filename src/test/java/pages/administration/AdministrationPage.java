package pages.administration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdministrationPage {
    private final String url = Configuration.baseUrl + "/admin";
    private final SelenideElement menu = $(By.cssSelector(".f-menu_main"));
    private final SelenideElement commonMenuBlock = $(By.xpath("//div[contains(@class,\"f-popup_visible_bottom\")]"));
    private final SelenideElement globalshearch = $(By.xpath("//button[contains(@data-func,\"Asyst.globalSearch.quick\")]"));
    private final SelenideElement gohome = $(By.xpath("//span[contains(@class,\"f-icon m-i-home\")]"));
    private final SelenideElement adminka = $(By.xpath("//span[text()='Админка']"));
    private final SelenideElement entity = $(By.xpath("//span[contains(@class,\"f-header__text\") and text()='Сущности']"));
    //стадии жизненного цикла
    private final SelenideElement lifeCycleStages = $(By.xpath("//span[text()='Стадии жизненного цикла']"));
    private final SelenideElement stages = commonMenuBlock.$(By.xpath("//span[text()='Стадии']"));
    private final SelenideElement settingTransitionStatusAndStage = commonMenuBlock.$("//span[text()='Настройка переходов по статусам/стадиям']");
    private final SelenideElement historyTransitionStatusAndStage = commonMenuBlock.$("//span[text()='История переходов по статусам/стадиям']");
    private final SelenideElement indicators = commonMenuBlock.$("//span[text()='Индикаторы']");

    //
    private final SelenideElement ct = $(By.xpath("//span[text()='КТ']"));
    private final SelenideElement documents = $(By.xpath("//span[text()='Документы']"));
    private final SelenideElement access = $(By.xpath("//span[text()='Доступ']"));
    private final SelenideElement approval = $(By.xpath("//span[text()='Согласования']"));
    private final SelenideElement status_reports = $(By.xpath("//span[text()='Статус-отчеты']"));
    private final SelenideElement system_settings = $(By.xpath("//span[text()='Системные настройки']"));
    private final SelenideElement decor = $(By.xpath("//span[text()='Оформление']"));
    private final SelenideElement dropdown = $(By.xpath("//div[contains(@class,\"f-popup f-popup_animation f-popup_visible_bottom\")]"));
    private final SelenideElement error = $(By.xpath("//span[text()='Ошибка 403']"));
    private final SelenideElement accessDenied = $(By.xpath("//div[contains(@class,\"f-control__container\")]"));
    private final SelenideElement PMforsait = $(By.xpath("//span[text()='ПМ Форсайт']"));

    @Step("Открыть по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Step("Проверка корректности ссылки")
    public void shouldHaveCorrectLink() {
        assertEquals(WebDriverRunner.url(), url, "Урл не соответствет " + url);
    }


    public void openEntity() {
        entity.click();
        $(".f-page__name").shouldHave(text("Сущности"));
        $(".f-page__content").shouldBe(visible);
    }

    @Step("Развернуть пункт меню 'Стадии жизненного цикла'")
    public void clickLifecycleStage() {
        lifeCycleStages.click();
    }

    @Step("Открыть пункт меню 'Стадии'")
    public void clickStages() {
        stages.click();
    }

    @Step("Раскрыть меню {0}")
    public void clickExpandedItem(String itemName) {
        $(By.xpath(String.format("//div[contains(@class, \"f-header\")]//button//span[text()='%s']", itemName))).click();
    }

    @Step("Нажать на пункт {0}")
    public void clickItemInExpandedMenu(String itemName) {
        $(By.xpath(String.format("//div[contains(@class,\"f-popup_visible_bottom\")]//span[text()='%s']", itemName)))
                .click();
    }

    public void subMenuShouldHaveItems(String... items) {
        $$(".f-popup_visible_bottom li.f-menu__item:not(.f-menu__item_separate)")
                .shouldHave(texts(items));
    }

    @Step("Проверка пункта 'Сущности'")
    public void checkEntity(EntityPage page) {
        openEntity();
        page.shouldBePage();
    }

    @Step("Открыть пункт меню 'Документы'")
    public void openDocuments() {
        documents.click();
    }

    @Step("Проверка пункта 'Стадии жизненного цикла'")
    public void checkLifeCycleEntity(StagesPage stages,
                                     SettingTransitionStatusAndStagePage settingTransition,
                                     HistoryTransitionStatusAndStagePage historyTransition,
                                     IndicatorsPage indicators) {
        String itemName = "Стадии жизненного цикла";
        clickExpandedItem(itemName);
        subMenuShouldHaveItems(
                "Стадии",
                "Настройка переходов по статусам/стадиям",
                "История переходов по статусам/стадиям",
                "Индикаторы"
        );
        clickItemInExpandedMenu("Стадии");
        stages.shouldBePage();
        clickExpandedItem(itemName);
        clickItemInExpandedMenu("Настройка переходов по статусам/стадиям");
        settingTransition.shouldBePage();
        clickExpandedItem(itemName);
        clickItemInExpandedMenu("История переходов по статусам/стадиям");
        historyTransition.shouldBePage();
        clickExpandedItem(itemName);
        clickItemInExpandedMenu("Индикаторы");
        indicators.shouldBePage();
    }

    @Step("Проверка пункта КТ")
    public void checkKT(TypeKTPage typeKT,
                        TypicalKTPage typicalKT,
                        TemplatesCalendarPage templatesCalendarPage,
                        TypicalPointCheckListPage typicalPointCheckList) {
        String itemName = "КТ";
        clickExpandedItem(itemName);
        subMenuShouldHaveItems(
                "Тип КТ",
                "Типовые КТ",
                "Шаблоны календарного плана",
                "Типовые пункты чек-листов"
        );
        clickItemInExpandedMenu("Тип КТ");
        typeKT.shouldBePage();
        clickExpandedItem(itemName);
        clickItemInExpandedMenu("Типовые КТ");
        typicalKT.shouldBePage();
        clickExpandedItem(itemName);
        clickItemInExpandedMenu("Шаблоны календарного плана");
        templatesCalendarPage.shouldBePage();
        clickExpandedItem(itemName);
        clickItemInExpandedMenu("Типовые пункты чек-листов");
        typicalPointCheckList.shouldBePage();
    }

    @Step("Проверка пункта 'Документы'")
    public void checkDocuments(DocumentsPage documents) {
        openDocuments();
        documents.shouldBePage();
    }

    public void checkAccess(GroupsPage groups, EmployeesPage employees, CardRolesPage cardRoles, UnitsPage units,
                            SubstitutionPage substitution, AccessMatrixPage accessMatrix,
                            SetsAccessMatrixPage setsAccessMatrix,
                            RulesAccessMatrixPage rulesAccessMatrix,
                            ImpersonateRolePage impersonateRole,
                            AccessGroupPage accessGroup) {
        String parentItem = "Доступ";
        clickExpandedItem(parentItem);
        subMenuShouldHaveItems("Группы",
                "Сотрудники",
                "Роли карточек",
                "Подразделения",
                "Замещения",
                "Матрица доступа",
                "Наборы матрицы доступа",
                "Правила матрицы доступа",
                "Олицетворение подразделений",
                "Доступ по группам"
        );
        clickItemInExpandedMenu("Группы");
        groups.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Сотрудники");
        employees.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Роли карточек");
        cardRoles.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Подразделения");
        units.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Замещения");
        substitution.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Матрица доступа");
        accessMatrix.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Наборы матрицы доступа");
        setsAccessMatrix.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Правила матрицы доступа");
        rulesAccessMatrix.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Олицетворение подразделений");
        impersonateRole.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Доступ по группам");
        accessGroup.shouldBePage();
    }

    @Step("Проверка пункта 'Согласования'")
    public void checkReview(ReviewCyclePage reviewCycle, ReviewersPage reviewers, ReviewCycleCardPage reviewCycleCard) {
        String parentItem = "Согласования";
        clickExpandedItem(parentItem);
        subMenuShouldHaveItems(
                "Циклы согласования",
                "Согласующие",
                "Формы согласования"
        );
        clickItemInExpandedMenu("Циклы согласования");
        reviewCycle.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Согласующие");
        reviewers.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Формы согласования");
        reviewCycleCard.shouldBePage();
    }

    @Step("Проверка пункта статус отчеты")
    public void checkStatusReports(ReportEventsPage reportEvents,
                                   ReportEventsCalendarPage reportEventsCalendar,
                                   ReportCollectionFormPage reportCollectionForm) {
        String parentItem = "Статус-отчеты";
        clickExpandedItem(parentItem);
        subMenuShouldHaveItems(
                "Отчетные мероприятия",
                "Календарь отчетных мероприятий",
                "Формы сбора отчетов"
        );
        clickItemInExpandedMenu("Отчетные мероприятия");
        reportEvents.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Календарь отчетных мероприятий");
        reportEventsCalendar.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Формы сбора отчетов");
        reportCollectionForm.shouldBePage();
    }

    @Step("Проверка пункта системные настройки")
    public void checkSystemSetting(EntitySettingPage entitySettingPage,
                                   EntitySchemaPage entitySchemaPage,
                                   ViewPage viewPage,
                                   NotificationsPage notificationsPage,
                                   NotificationsQueuePage notificationsQueuePage,
                                   ScheduledTaskPage scheduledTaskPage,
                                   ScheduledHistoryPage scheduledHistoryPage,
                                   SystemLogsPage systemLogsPage,
                                   UpdateSystemPage updateSystemPage,
                                   SystemInformationPage systemInformationPage) {
        String parentItem = "Системные настройки";
        clickExpandedItem(parentItem);
        subMenuShouldHaveItems(
                "Сущности",
                "Схема сущностей",
                "Представления",
                "Правила",
                "Рассылки",
                //TODO: сделать проверку для Уведомлений
                "Уведомления",
                "Очередь рассылки",
                "Запланированные задачи",
                "Запланированные задачи. История выполнения",
                "Логи системы",
                "Установка обновлений",
                "Информация о системе",
                "Маршрутизация"
        );
        clickItemInExpandedMenu("Сущности");
        entitySettingPage.shouldBePage();
        Selenide.open(url);
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Схема сущностей");
        entitySchemaPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Представления");
        viewPage.shouldBePage();
        Selenide.open(url);
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Рассылки");
        notificationsPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Очередь рассылки");
        notificationsQueuePage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Запланированные задачи");
        scheduledTaskPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Запланированные задачи. История выполнения");
        scheduledHistoryPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Логи системы");
        systemLogsPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Установка обновлений");
        updateSystemPage.shouldBePage();
        Selenide.open(url);
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Информация о системе");
        systemInformationPage.shouldBePage();

    }

    @Step("Проверка пункта оформление")
    public void checkView(
            MenuSettingPage menuSettingPage,
            PagesPage pagesPage,
            PageElementsPage pageElementsPage,
            ReportsPage reportsPage,
            ReportsCategoriesPages reportsCategoriesPages,
            SystemTourPage systemTourPage) {
        String parentItem = "Оформление";
        clickExpandedItem(parentItem);
        subMenuShouldHaveItems(
                "Настройка меню",
                "Страницы",
                "Элементы страниц",
                "Отчеты",
                "Категории отчетов",
                "Тур по системе"
        );
        clickItemInExpandedMenu("Настройка меню");
        menuSettingPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Страницы");
        pagesPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Элементы страниц");
        pageElementsPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Отчеты");
        reportsPage.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Категории отчетов");
        reportsCategoriesPages.shouldBePage();
        clickExpandedItem(parentItem);
        clickItemInExpandedMenu("Тур по системе");
        systemTourPage.shouldBePage();
    }
}
