package pages.elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.elements.mainmenuitems.*;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class MainMenu {

    private final ManagementObjects managementObjects;
    private final MonitoringAndControl monitoringAndControl;
    private final Communication communication;
    private final ManagementOfRisks managementOfRisks;
    private final PersonnelManagement personnelManagement;
    private final GoalsAndIndicators goalsAndIndicators;
    private final KnowledgeBaseAndDocuments knowledgeBaseAndDocuments;
    private final Reports reports;
    private final Catalogs catalogs;
    private final Administration admin;
    private final Support support;


    private final SelenideElement mainPanelButton = $(By.cssSelector("a[href='/'].f-menu__link"));
    private final SelenideElement menu = $(By.cssSelector(".f-menu_main"));
    private final SelenideElement sidebarOpen = $(By.xpath("//span[text()='Главная панель']"));

    //Иконки
    private final SelenideElement hideIcon = $(By.cssSelector(".m-i-arrow-right2.f-icon_rotate_180deg"));
    private final SelenideElement expandIcon = $(By.cssSelector(".m-i-arrow-right2"));
    private final SelenideElement homeIcon = $(By.cssSelector(".m-i-home"));
    private final SelenideElement supportIcon = $(By.cssSelector(".m-i-bubble"));

    public MainMenu() {
        this.managementObjects = new ManagementObjects();
        this.monitoringAndControl = new MonitoringAndControl();
        this.communication = new Communication();
        this.managementOfRisks = new ManagementOfRisks();
        this.personnelManagement = new PersonnelManagement();
        this.goalsAndIndicators = new GoalsAndIndicators();
        this.knowledgeBaseAndDocuments = new KnowledgeBaseAndDocuments();
        this.reports = new Reports();
        this.catalogs = new Catalogs();
        this.admin =new Administration();
        this.support =new Support();
    }

    @Step("Нажать кнопку главная панель")
    public void clickMainPanelButton() {
        mainPanelButton.click();
    }

    @Step("Проверить что меню открылось")
    public void shouldOpen() {
        menu.shouldHave(cssClass("f-menu_opened"));
    }

    @Step("Меню в раскрытом виде содержит пункты")
    //TODO метод не работает для всех юзеров
    public void shouldHaveItemsWhenExpand() {
        managementObjects.visible().hasCorrectText();
        monitoringAndControl.visible().hasCorrectText();
        communication.visible().hasCorrectText();
        managementOfRisks.visible().hasCorrectText();
        personnelManagement.visible().hasCorrectText();
        goalsAndIndicators.visible().hasCorrectText();
        knowledgeBaseAndDocuments.visible().hasCorrectText();
        reports.visible().hasCorrectText();
        catalogs.visible().hasCorrectText();
    }

    @Step("Свернуть главное меню")
    public void hide() {
        hideIcon.click();
    }

    @Step("Развернуть главное меню")
    public void clickExpand() {
        if (sidebarOpen.isDisplayed()){
        }else {
            expandIcon.click();
            menu.waitUntil(cssClass("f-menu_opened"), 10000);
        }
    }

    @Step("Провера сворачивания меню")
    public void shouldHide() {
        menu.shouldHave(cssClass("f-menu_hidden"));
    }

    @Step("Проверка наличия иконок на свернутом меню")
    public void shouldHaveIconsWhenMenuHide() {
        homeIcon.shouldBe(visible);
        supportIcon.shouldBe(visible);
        expandIcon.shouldBe(visible);
    }

    @Step("Развернуть в меню блок 'Объекты управления'")
    public ManagementObjects managementObjects() {
        return managementObjects;
    }

    @Step("Развернуть в меню блок 'Мониторинг и контроль'")
    public MonitoringAndControl monitoringAndControl() {
        return monitoringAndControl;
    }

    @Step("Развернуть в меню блок 'Коммуникации'")
    public Communication communication() {
        return communication;
    }

    @Step("Развернуть в меню блок 'Управление рисками'")
    public ManagementOfRisks managementOfRisks() {
        return managementOfRisks;
    }

    @Step("Развернуть в меню блок 'Управление персоналом'")
    public PersonnelManagement personnelManagement() {
        return personnelManagement;
    }

    @Step("Развернуть в меню блок 'Цели и показатели'")
    public GoalsAndIndicators goalsAndIndicators() {
        return goalsAndIndicators;
    }

    @Step("Развернуть в меню блок 'База знаний и документов'")
    public KnowledgeBaseAndDocuments knowledgeBaseAndDocuments() {
        return knowledgeBaseAndDocuments;
    }

    @Step("Кликнуть на пункт меню 'Отчеты'")
    public Reports reportsr() {
        return reports;
    }

    @Step("Развернуть в меню блок 'Справочники'")
    public Catalogs catalogs() {
        return catalogs;
    }
    
    @Step("Развернуть в меню блок 'Администрирование'")
    public Administration administartion() {
        return admin;
    }
    
    @Step("Развернуть в меню блок 'Администрирование'")
    public Support supportt() {
        return support;
    }


    @Step("Проверка отображения пунктов общих для всех пользователей")
    public void shouldHaveCommonItems() {
        managementObjects.visible().hasCorrectText();
        communication.visible().hasCorrectText();
        knowledgeBaseAndDocuments.visible().hasCorrectText();
    }

    @Step("Свернуть основное меню")
    public void clickHide() {
        hideIcon.click();
    }

    //Тесты для функционального фдминистратора

    @Step("Проверка открытия пунктов 'Объекты управления' для функционального администратора")
    public void shouldHaveAndOpenManagementObjectsItemsForUserFA() {
        managementObjects.openGProgramAndObjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openGProgramHierarchy()
                .shouldHaveCorrectLink()
                //.shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPortfolio()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjectsInitiative()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNotProjectEvent()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openContracts()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openResults()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openStagesWorksKT()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Мониторинг и контроль' для функционального администратора")
    public void shouldHaveAndOpenMonitoringAndControlItemsForUserFA() {
        monitoringAndControl.openStatusReports()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        monitoringAndControl.openRequestsForChange()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для функционального администратора")
    public void shouldHaveAndOpenCommunicationForUserFA() {
        communication.openOrders()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetingQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Управление рисками' для функционального администратора")
    public void shouldHaveAndOpenManageOfRisksForUserFA() {
            managementOfRisks.openRisks()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
            managementOfRisks.openRiskMitigation()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
            managementOfRisks.openRiskResponding()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Управление персоналом' для функционального администратора")
    public void shouldHaveAndOpenPersonalManagementForUserFA() {
        personnelManagement.openOrgStructure()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        personnelManagement.openCourseWork()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }

    @Step("Проверка открытия пунктов 'Цели и показатели' для функционального администратора")
    public void shouldHaveAndOpenGoalAndIndicatorsForUserFA() {
        goalsAndIndicators.openGoalRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openIndicatorsRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openGoalsAndIndicatorsHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openGoalHierarchy()
                .shouldHaveCorrectLink()
                //.shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'База знаний и документов' для функционального администратора")
    public void shouldHaveAndOpenKnowledgeBaseAndDocumentsForUserFA() {
        knowledgeBaseAndDocuments.openLessons()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        knowledgeBaseAndDocuments.openSummary()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        knowledgeBaseAndDocuments.openLegalDocs()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        knowledgeBaseAndDocuments.openLibrary()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        knowledgeBaseAndDocuments.openExtendsSearch()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Отчеты' для функционального администратора")
    public void shouldHaveAndOpenReportsForUserFA() {
        reports.openReports()
                .shouldHaveCorrectLink();
    }
    @Step("Проверка открытия пунктов 'Справочники' для функционального администратора")
    public void shouldHaveAndOpenCatalogsForUserFA() {
        catalogs.openEmployees()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openOrgUnit()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openContractor()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openExternalOrganization()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openCourses()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Администрирование' для функционального администратора")
    public void shouldHaveAndOpenAdministrarioForUserFA() {
        admin.allItemInTopPanel();
        admin.shouldHaveCorrectLink();
        admin.dropDown();
        admin.goHomeCheck();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для функционального администратора")
    public void shouldHaveAndOpenSuportForUserFA() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }

    //Тесты для рабочей группы

    @Step("Проверка открытия пунктов 'Объекты управления' для пользователя из рабочей группы")
    public void shouldHaveAndOpenManagementObjectsItemsForUserWG() {
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjectsInitiative()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNotProjectEvent()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openContracts()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openResults()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openStagesWorksKT()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Мониторинг и контроль' для пользователя из рабочей группы")
    public void shouldHaveAndOpenMonitoringAndControlItemsForUserWG() {
        monitoringAndControl.openStatusReports()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        monitoringAndControl.openRequestsForChange()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для пользователя из рабочей группы")
    public void shouldHaveAndOpenCommunicationForUserWG() {
        communication.openOrders()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetingQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Управление рисками' для пользователя из рабочей группы")
    public void shouldHaveAndOpenManageOfRisksForUserWG() {
        managementOfRisks.openRisks()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskMitigation()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskResponding()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Управление персоналом' для пользователя из рабочей группы")
    public void shouldHaveAndOpenPersonalManagementForUserWG() {
        personnelManagement.openOrgStructure()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        personnelManagement.openCourseWork()
                .shouldHaveCorrectLink()
                .shouldHaveContent();

    }

    @Step("Проверка открытия пунктов 'Цели и показатели' для пользователя из рабочей группы")
    public void shouldHaveAndOpenGoalAndIndicatorsForUserWG() {
        goalsAndIndicators.openGoalRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openIndicatorsRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Справочники' для пользователя из рабочей группы")
    public void shouldHaveAndOpenCatalogsForUserWG() {
        catalogs.openEmployees()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        catalogs.openOrgUnit()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openContractor()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        catalogs.openExternalOrganization()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        catalogs.openCourses()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Администрирование' для пользователя из рабочей группы")
    public void shouldHaveAndOpenAdministrarioForUserWG() {
        admin.errorAccessDenied();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для пользователя из рабочей группы")
    public void shouldHaveAndOpenSuportForUserWG() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }

    //для пользователя из команды управления проектом

    @Step("Проверка открытия пунктов 'Объекты управления' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenManagementObjectsItemsForUserPM() {
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjectsInitiative()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNotProjectEvent()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openContracts()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openResults()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openStagesWorksKT()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Мониторинг и контроль' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenMonitoringAndControlItemsForUserPM() {
        monitoringAndControl.openStatusReports()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        monitoringAndControl.openRequestsForChange()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenCommunicationForUserPM() {
        communication.openOrders()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetingQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Управление рисками' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenManageOfRisksForUserPM() {
        managementOfRisks.openRisks()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskMitigation()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskResponding()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Управление персоналом' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenPersonalManagementForUserPM() {
        personnelManagement.openOrgStructure()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        personnelManagement.openCourseWork()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Цели и показатели' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenGoalAndIndicatorsForUserPM() {
        goalsAndIndicators.openGoalRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openIndicatorsRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Отчеты' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenReportsForUserPM() {
        reports.openReports()
                .shouldHaveCorrectLink();
    }
    @Step("Проверка открытия пунктов 'Справочники' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenCatalogsForUserPM() {
        catalogs.openEmployees()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        catalogs.openOrgUnit()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openContractor()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openExternalOrganization()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openCourses()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Администрирование' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenAdministrarioForUserPM() {
        admin.errorAccessDenied();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для пользователя из команды управления проектом")
    public void shouldHaveAndOpenSuportForUserPM() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }

    //для пользователя из команды Проектный комитет

    @Step("Проверка открытия пунктов 'Объекты управления' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenManagementObjectsItemsForUserPС() {
        managementObjects.openGProgramAndObjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openGProgramHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPortfolio()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjectsInitiative()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openResults()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openStagesWorksKT()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Мониторинг и контроль' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenMonitoringAndControlItemsForUserPС() {
        monitoringAndControl.openStatusReports()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        monitoringAndControl.openRequestsForChange()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenCommunicationForUserPС() {
        communication.openOrders()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Управление рисками' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenManageOfRisksForUserPС() {
        managementOfRisks.openRisks()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskMitigation()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        managementOfRisks.openRiskResponding()
                .shouldHaveCorrectLink()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Управление персоналом' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenPersonalManagementForUserPС() {
        personnelManagement.openOrgStructure()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        personnelManagement.openCourseWork()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Цели и показатели' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenGoalAndIndicatorsForUserPС() {
        goalsAndIndicators.openGoalsAndIndicatorsHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        goalsAndIndicators.openGoalHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.HierarchyOfMetrics()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Отчеты' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenReportsForUserPС() {
        reports.openReports()
                .shouldHaveCorrectLink();
    }
    @Step("Проверка открытия пунктов 'Справочники' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenCatalogsForUserPС() {
        catalogs.openEmployees()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        catalogs.openOrgUnit()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Администрирование' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenAdministrarioForUserPС() {
        admin.errorAccessDenied();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для пользователя из команды проектный комитет")
    public void shouldHaveAndOpenSuportForUserPС() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }

    //для пользователя из команды Проектный офис

    @Step("Проверка открытия пунктов 'Объекты управления' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenManagementObjectsItemsForUserPO() {
        managementObjects.openGProgramAndObjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openGProgramHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPortfolio()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjectsInitiative()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNotProjectEvent()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openContracts()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openResults()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openStagesWorksKT()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Мониторинг и контроль' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenMonitoringAndControlItemsForUserPO() {
        monitoringAndControl.openStatusReports()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        monitoringAndControl.openRequestsForChange()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenCommunicationForUserPO() {
        communication.openOrders()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetingQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Управление рисками' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenManageOfRisksForUserPO() {
        managementOfRisks.openRisks()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskMitigation()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskResponding()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Управление персоналом' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenPersonalManagementForUserPO() {
        personnelManagement.openOrgStructure()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        personnelManagement.openCourseWork()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Цели и показатели' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenGoalAndIndicatorsForUserPO() {
        goalsAndIndicators.openGoalRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openIndicatorsRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openGoalsAndIndicatorsHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openGoalHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.HierarchyOfMetrics()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Отчеты' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenReportsForUserPO() {
        reports.openReports()
                .shouldHaveCorrectLink();
    }
    @Step("Проверка открытия пунктов 'Справочники' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenCatalogsForUserPO() {
        catalogs.openEmployees()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openOrgUnit()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openContractor()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openExternalOrganization()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openCourses()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Администрирование' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenAdministrarioForUserPO() {
        admin.errorAccessDenied();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenSuportForUserPO() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }
    //для пользователя из команды Управляющий комитет

    @Step("Проверка открытия пунктов 'Объекты управления' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenManagementObjectsItemsForUserSC() {
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPortfolio()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openStagesWorksKT()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Мониторинг и контроль' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenMonitoringAndControlItemsForUserSC() {
        monitoringAndControl.openStatusReports()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenCommunicationForUserSC() {
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetingQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Управление рисками' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenManageOfRisksForUserSC() {
        managementOfRisks.openRisks()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskMitigation()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskResponding()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Управление персоналом' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenPersonalManagementForUserSC() {
        personnelManagement.openOrgStructure()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        personnelManagement.openCourseWork()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Цели и показатели' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenGoalAndIndicatorsForUserSC() {
        goalsAndIndicators.openGoalsAndIndicatorsHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.HierarchyOfMetrics()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Отчеты' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenReportsForUserSC() {
        reports.openReports()
                .shouldHaveCorrectLink();
    }
    @Step("Проверка открытия пунктов 'Справочники' для пользователя из команды управляющий комитет")
    public void shouldHaveAndOpenCatalogsForUserSC() {
        catalogs.openEmployees()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        catalogs.openOrgUnit()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Администрирование' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenAdministrarioForUserSC() {
        admin.errorAccessDenied();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для пользователя из команды проектный офис")
    public void shouldHaveAndOpenSuportForUserSC() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }
    //Тесты для ВПО/МПО

    @Step("Проверка открытия пунктов 'Объекты управления' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenManagementObjectsItemsForUserVPOMPO() {
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPortfolio()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjectsInitiative()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openNotProjectEvent()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openContracts()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openResults()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openStagesWorksKT()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Мониторинг и контроль' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenMonitoringAndControlItemsForUserVPOMPO() {
        monitoringAndControl.openStatusReports()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        monitoringAndControl.openRequestsForChange()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenCommunicationForUserVPOMPO() {
        communication.openOrders()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openMeetingQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }

    @Step("Проверка открытия пунктов 'Управление рисками' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenManageOfRisksForUserVPOMPO() {
        managementOfRisks.openRisks()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskMitigation()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementOfRisks.openRiskResponding()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Управление персоналом' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenPersonalManagementForUserVPOMPO() {
        personnelManagement.openOrgStructure()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        personnelManagement.openCourseWork()
                .shouldHaveCorrectLink()
                .shouldHaveContent();

    }

    @Step("Проверка открытия пунктов 'Цели и показатели' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenGoalAndIndicatorsForUserVPOMPO() {
        goalsAndIndicators.openGoalRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openIndicatorsRegistry()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openGoalsAndIndicatorsHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.openGoalHierarchy()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        goalsAndIndicators.HierarchyOfMetrics()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Отчеты' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenReportsForUserVPOMPO() {
        reports.openReports()
                .shouldHaveCorrectLink();
    }
    @Step("Проверка открытия пунктов 'Справочники' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenCatalogsForUserVPOMPO() {
        catalogs.openEmployees()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
        catalogs.openOrgUnit()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openContractor()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openExternalOrganization()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        catalogs.openCourses()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Администрирование' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenAdministrarioForUserVPOMPO() {
        admin.errorAccessDenied();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для пользователя из команды ВПО/МПО")
    public void shouldHaveAndOpenSuportForUserVPOMPO() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }
    //Тесты для Внешний наблюдатель

    @Step("Проверка открытия пунктов 'Объекты управления' для пользователя из команды внешний наблюдатель")
    public void shouldHaveAndOpenManagementObjectsItemsForUserEO() {
        managementObjects.openNationalProject()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPortfolio()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openPrograms()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        managementObjects.openProjects()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'Коммуникации' для пользователя из команды внешний наблюдатель")
    public void shouldHaveAndOpenCommunicationForUserEO() {
        communication.openMeetings()
                .shouldHaveCorrectLink()
                .shouldHaveName()
                .shouldHaveContent();
        communication.openOpenQuestions()
                .shouldHaveCorrectLink()
                .shouldHaveContent();
    }
    @Step("Проверка открытия пунктов 'База знаний и документов' для пользователя из команды внешний наблюдатель")
    public void shouldHaveAndOpenKnowledgeBaseAndDocumentsForUserEO() {
        knowledgeBaseAndDocuments.openLegalDocs()
                .shouldHaveCorrectLink()
                .shouldHaveContent();

    }
    @Step("Проверка открытия пунктов 'Администрирование' для пользователя из команды внешний наблюдатель")
    public void shouldHaveAndOpenAdministrarioForUserEO() {
        admin.errorAccessDenied();
    }
    @Step("Проверка открытия пунктов 'Служба поддержки' для пользователя из команды внешний наблюдатель")
    public void shouldHaveAndOpenSuportForUserEO() {
        support.checkButton();
        support.checkReqFields();
        support.clickCancel();
    }

}
