package tests.project;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.Result;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.project.ProjectPage;
import pages.managementobjects.result.ResultPage;
import tests.BaseTest;
import helpers.ActionsViaAPI;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.RESULTS)
public class ResultTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
    private ResultPage resultPage;
    private Result result;
    private long currentTime;
    private Result federalResult;
    private Result regionalResult;
    private Result departmentalResult;
    private Result departmentalParentResult;
    private Result departmentalChildResult;
    private Result federalParentResult;
    private Result federalChildResult;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        projectPage = new ProjectPage();
        resultPage = new ResultPage();
        result = new Result();
        federalResult = new Result();
        regionalResult = new Result();
        departmentalResult = new Result();
        departmentalParentResult = new Result();
        departmentalChildResult = new Result();
        federalParentResult = new Result();
        federalChildResult = new Result();
        currentTime = System.currentTimeMillis();
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteProjectCreatedFromAPI();
        new LogoutPage().open();
    }

    @Story("Отображение таблиц Результатов в Проекте в соответствии с уровнем управления")
    @ParameterizedTest(name = "Отображение таблиц результатов Ведомственного проекта после его создания")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-10")
    @TmsLink("1039")
    public void checkDepResultsTable(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.shouldHaveDefaultResultsTable();
    }

    @Story("Создание Результата из проекта с учётом уровня управления")
    @ParameterizedTest(name = "Создание Результата Ведомственного проекта в Ведомственном проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-4")
    @TmsLink("411")
    public void createAndCheckDepResult(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Ведомственный");
        result
                .setName("Тест_C411_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveDefaultResultsTable();
        projectPage.shouldHaveDepartmentalResult(result.getName());
    }

    @Story("Отображение таблиц Результатов в Проекте в соответствии с уровнем управления")
    @ParameterizedTest(name = "Отображение таблиц результатов Федерального проекта после его создания")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-1")
    @TmsLink("1039")
    public void checkFederalResultsTable(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Федеральный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.shouldHaveFederalResultsTable();
    }

    @Story("Создание Результата из проекта с учётом уровня управления")
    @ParameterizedTest(name = "Создание Федерального результата в Федеральном проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @TmsLink("411")
    public void createAndCheckFedResultInFedProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Федеральный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Федеральный");
        result
                .setName("Тест_C411_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveFederalResultsTable();
        projectPage.shouldHaveFederalResult(result.getName());
    }

    @Story("Создание Результата из проекта с учётом уровня управления")
    @ParameterizedTest(name = "Создание Федерального результата в Региональном проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-6")
    @TmsLink("964")
    public void createAndCheckFedResultInRegProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Региональный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Федеральный");
        result
                .setName("Тест_C964_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveFederalResultsTable();
        projectPage.shouldHaveFederalResult(result.getName());
    }

    @Story("Отображение таблиц Результатов в Проекте в соответствии с уровнем управления")
    @ParameterizedTest(name = "Отображение таблиц результатов Регионального проекта после его создания")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-3")
    @TmsLink("1039")
    public void checkRegResultsTableInRegProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Региональный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.shouldHaveRegionalResultsTable();
    }

    @Story("Создание Результата из проекта с учётом уровня управления")
    @ParameterizedTest(name = "Создание Регионального результата в Региональном проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-8")
    @TmsLink("963")
    public void createAndCheckRegResultInRegProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Региональный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Региональный");
        result
                .setName("Тест_C963_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveRegionalResultsTable();
        projectPage.shouldHaveRegionalResult(result.getName());
    }

    @Story("Создание Результата из проекта с учётом уровня управления")
    @ParameterizedTest(name = "Создание Результата Регионального проекта, связанного с Результатом Федерального проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-7")
    @TmsLink("962")
    public void createAndCheckRegResultWithLinkedFedResultInRegProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Региональный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Федеральный");
        federalResult
                .setName("Федеральный_C962_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(federalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveFederalResultsTable();
        projectPage.clickAddResult("Региональный");
        regionalResult
                .setName("Региональный_C962" + currentTime)
                .setUnit("Единица")
                .setFederalResultLink(federalResult.getName())
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(regionalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveRegionalResultsTable();
        projectPage.shouldHaveFederalResult(federalResult.getName());
        projectPage.checkLinkBetweenFederalAndRegionalResults(regionalResult.getName());
        projectPage.shouldHaveRegionalResult(regionalResult.getName());
    }

    @Story("Отображение таблиц Результатов в Проекте в соответствии с уровнем управления")
    @ParameterizedTest(name = "Редактирование Результата Регионального проекта: связывание с Результатом Федерального проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-21")
    @TmsLink("1040")
    public void editAndCheckRegionalResultWithLinkedFederalResult(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Региональный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Федеральный");
        federalResult
                .setName("Федеральный_С1040_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(federalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveFederalResultsTable();
        projectPage.clickAddResult("Региональный");
        regionalResult
                .setName("Региональный_С1040_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(regionalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveRegionalResultsTable();
        projectPage.editRegionalResultCard();
        resultPage.fillFederalResultLink(federalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveFederalResult(federalResult.getName());
        projectPage.checkLinkBetweenFederalAndRegionalResults(regionalResult.getName());
        projectPage.shouldHaveRegionalResult(regionalResult.getName());
    }

    @Story("Связь результатов и КП проекта")
    @ParameterizedTest(name = "Отображение Результата в таблице 'Связь результатов и КП проекта' после создания Результата из проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-22")
    @TmsLink("1044")
    public void displayResultInLinkedTable(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Ведомственный");
        departmentalResult
                .setName("Ведомственный_С1044_" + currentTime)
                .setUnit("Единица")
                .setType("Утверждение документа")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(departmentalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.switchToLinkedKPAndResultsTableTab();
        projectPage.checkLinkedResultsInTable(departmentalResult.getName(),departmentalResult.getType());
        projectPage.switchToProjectResultsTab();

        projectPage.clickEditForm();
        projectPage.changeProjectLevel("Федеральный");
        projectPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Федеральный");
        federalResult
                .setName("Федеральный_С1044_" + currentTime)
                .setUnit("Единица")
                .setType("Утверждение документа")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(federalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.switchToLinkedKPAndResultsTableTab();
        projectPage.checkLinkedResultsInTable(federalResult.getName(),federalResult.getType());
        projectPage.switchToProjectResultsTab();

        projectPage.clickEditForm();
        projectPage.changeProjectLevel("Региональный");
        projectPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Региональный");
        regionalResult
                .setName("Региональный_С1044_" + currentTime)
                .setUnit("Единица")
                .setType("Утверждение документа")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(regionalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.switchToLinkedKPAndResultsTableTab();
        projectPage.checkLinkedResultsInTable(regionalResult.getName(),regionalResult.getType());
    }

    @Story("Связь результатов и КП проекта")
    @ParameterizedTest(name = "Валидация поля 'Тип КТ/Мероприятия' таблицы 'Связь Результатов и КП проекта' (все варианты)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-25")
    @TmsLink("1046")
    public void verifyValidationInLinkedTable(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        //Создаем Ведомственный результат и проверяем соответствие между типами результата и типами КТ\Мероприятий
        projectPage.clickAddResult("Ведомственный");
        departmentalResult
                .setName("Ведомственный_С1046_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(departmentalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.checkValidationInKPAndResultsLinkedTable(resultPage,"Ведомственный");

        //Создаем Федеральный результат и проверяем соответствие между типами результата и типами КТ\Мероприятий
        projectPage.clickEditForm();
        projectPage.changeProjectLevel("Федеральный");
        projectPage.clickSaveAndClose();
        projectPage.clickAddResult("Федеральный");
        federalResult
                .setName("Федеральный_С1046_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(federalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.checkValidationInKPAndResultsLinkedTable(resultPage,"Федеральный");

        //Создаем Региональный результат и проверяем соответствие между типами результата и типами КТ\Мероприятий
        projectPage.clickEditForm();
        projectPage.changeProjectLevel("Региональный");
        projectPage.clickSaveAndClose();
        projectPage.clickAddResult("Региональный");
        regionalResult
                .setName("Региональный_С1046_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(regionalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.checkValidationInKPAndResultsLinkedTable(resultPage,"Региональный");
    }

    @Story("Иерархия результатов")
    @ParameterizedTest(name = "Выбор родительского Результата в карточке создания Результата (Фед., Вед.)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-19")
    @TmsLink("1050")
    public void selectParentResultDuringCreation(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Ведомственный");
        departmentalParentResult
                .setName("Ведомственный Родительский_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(departmentalParentResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Ведомственный");
        departmentalChildResult
                .setName("Ведомственный Дочерний_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName())
                .setParentEntity(departmentalParentResult.getName());
        resultPage.fillFields(departmentalChildResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.checkIsTheResultChild("Ведомственный", departmentalChildResult.getName());

        projectPage.clickEditForm();
        projectPage.changeProjectLevel("Федеральный");
        projectPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Федеральный");
        federalParentResult
                .setName("Федеральный Родительский" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(federalParentResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Федеральный");
        federalChildResult
                .setName("Федеральный Дочерний" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName())
                .setParentEntity(federalParentResult.getName());
        resultPage.fillFields(federalChildResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.checkIsTheResultChild("Федеральный", federalChildResult.getName());
    }

    @Story("Иерархия результатов")
    @ParameterizedTest(name = "Выбор родительского Результата в карточке редактирования Результата (для Рез-тов Фед. и Вед. проектов)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-17")
    @TmsLink("1041")
    public void selectParentResultDuringEditing(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();

        projectPage.clickAddResult("Ведомственный");
        departmentalParentResult
                .setName("Ведомственный Родительский_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(departmentalParentResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Ведомственный");
        departmentalChildResult
                .setName("Ведомственный Дочерний_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(departmentalChildResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveDepartmentalResult(departmentalChildResult.getName());
        projectPage.openResultEditForm("Ведомственный");
        resultPage.fillParentEntityResult(departmentalParentResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.checkIsTheResultChild("Ведомственный", departmentalChildResult.getName());


        projectPage.clickEditForm();
        projectPage.changeProjectLevel("Федеральный");
        projectPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Федеральный");
        federalParentResult
                .setName("Федеральный Родительский_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(federalParentResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.clickAddResult("Федеральный");
        federalChildResult
                .setName("Федеральный Дочерний_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(federalChildResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveFederalResult(federalChildResult.getName());
        projectPage.openResultEditForm("Федеральный");
        resultPage.fillParentEntityResult(federalParentResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.checkIsTheResultChild("Федеральный", federalChildResult.getName());

    }

    @Story("Иерархия результатов")
    @ParameterizedTest(name = "Недоступность выбора родительского результата для рез-та Регионального проекта при создании/редактировании")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-18")
    @TmsLink("1052")
    public void checkParentResultDuringCreationAndEditing(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Региональный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Региональный");
        regionalResult
                .setName("Региональный проверка при создании_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.checkParentEntityResultIsNotVisible();
        resultPage.fillFields(regionalResult);
        resultPage.clickSaveAndClose();
        resultPage.checkPageIsLoaded();
        projectPage.shouldHaveRegionalResult(regionalResult.getName());
        projectPage.openResultEditForm("Региональный");
        resultPage.checkParentEntityResultIsNotVisible();
    }
}