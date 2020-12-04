package tests.entity_generation.lesson_learned;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.LessonLearned;
import model.Program;
import model.Project;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.base_of_knowledge_and_documents.LessonsLearnedRegistry;
import pages.base_of_knowledge_and_documents.lessons_learned.LessonLearnedPage;
import pages.managementobjects.program.ProgramRegistry;
import pages.managementobjects.program.ProgramPage;
import pages.managementobjects.project.ProjectPage;
import pages.managementobjects.project.ProjectRegistry;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.ENTITY_CREATION)
@Story("Извлеченный урок")
@Tag("entityGeneration")
@Tag("Regression")
public class CreateLessonTests extends BaseTest {
    private SingInPage singIn;
    private ProgramPage createModal;
    private ProgramRegistry programRegistry;
    private Program program;
    private LessonLearned lessonLearned;
    private LessonLearnedPage lessonPage;
    private LessonsLearnedRegistry lessonRegistry;
    private ProjectPage projectPage;
    private ProjectRegistry projectRegistry;
    private Project project;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        createModal = new ProgramPage();
        programRegistry = new ProgramRegistry();
        program = new Program();
        lessonLearned = new LessonLearned();
        lessonPage = new LessonLearnedPage();
        lessonRegistry = new LessonsLearnedRegistry();
        projectPage = new ProjectPage();
        projectRegistry = new ProjectRegistry();
        project = new Project();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest (name = "Создание сущности Извлеченный урок из карточки Программы")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-140")
    @TmsLink("552")
    public void CreateLessonLearnedFromProgramWithSaveAndClose(User user) {
        parameter("Пользователь", user.getName());
        long currentTime = System.currentTimeMillis();
        program
                .setName("Тест_C552" + currentTime)
                .setPortfolio("Портфель национальных проектов")
                .setCustomer(user.getName())
                .setCurator(user.getName())
                .setSupervisor(user.getName());
        lessonLearned
                .setName("Тест_C552" + currentTime)
                .setRols(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        programRegistry.open();
        programRegistry.controlPanel().clickAddButton();
        createModal.shouldBeOpened();
        createModal.fillFields(program);
        createModal.clickSaveAndClose();
        programRegistry.searchProgram(program.getName());
        programRegistry.shouldHaveCreatedRecord(program.getName());
        programRegistry.clickFirstProgramRow();
        //createModal.openAndCheckProgramName(program.getName());
        createModal.positiveLessonsLearned();
        createModal.shouldBeOpened();

        lessonPage.fillFields(lessonLearned);
        createModal.clickSaveAndClose();
        createModal.checkCreateLesson(lessonLearned.getName());
        lessonRegistry.open();
        //lessonRegistry.chooseTypeOfSearchAll();
        lessonRegistry.searchLesson(lessonLearned.getName());
        //lessonRegistry.expandResult();
        lessonRegistry.selectFirstRow();
        lessonRegistry.clickDelete();
        lessonRegistry.acceptDelete();
        lessonRegistry.searchLesson(lessonLearned.getName());
        lessonRegistry.shouldNotHaveResults();

        programRegistry.open();
        programRegistry.shouldBeRegistry();
        programRegistry.searchProgram(program.getName());
        programRegistry.shouldHaveCreatedRecord(program.getName());
        programRegistry.selectRow();
        programRegistry.clickDelete();
        programRegistry.acceptDelete();
        programRegistry.searchProgram(program.getName());
        programRegistry.shouldNotHaveResults();
    }

    @ParameterizedTest(name = "Создание сущности Извлеченного урока из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-181")
    @TmsLink("546")
    public void CreateLessonLearnedWithSaveAndClose(User user) {
        parameter("Пользователь", user.getName());
        long currentTime = System.currentTimeMillis();
        lessonLearned
                .setActivity("Популяризация предпринимательства")
                .setName("Тест_C546_" + currentTime);
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        lessonRegistry.open();
        lessonRegistry.shouldBeRegistry();
        lessonRegistry.controlPanel().clickAddButton();
        lessonPage.shouldBeOpened();
        lessonPage.fillFields(lessonLearned);
        lessonPage.clickSaveAndClose();
        lessonRegistry.searchLesson(lessonLearned.getName());
        lessonRegistry.selectFirstRow();
        lessonRegistry.clickDelete();
        lessonRegistry.acceptDelete();
        lessonRegistry.searchLesson(lessonLearned.getName());
        lessonRegistry.shouldNotHaveResults();
    }

    @ParameterizedTest (name = "Создание сущности Извлеченный урок из карточки Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-182")
    @TmsLink("553")
    public void CreateLessonLearnedFromProjectWithSaveAndClose(User user) {
        parameter("Пользователь", user.getName());
        long currentTime = System.currentTimeMillis();
        project
                .setName("Тест_C553_" + currentTime)
                .setType("Информационные технологии")
                .setProgram("Портфель национальных проектов")
                .setCurator(user.getName())
                .setSupervisor(user.getName());
        lessonLearned
                .setName("Тест_C553_" + currentTime)
                .setRols(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        projectRegistry.open();
        projectRegistry.shouldBeRegistry();
        projectRegistry.controlPanel().clickAddButton();
        projectPage.shouldBeOpened();
        projectPage.fillFields(project);
        projectPage.clickSaveAndClose();
        projectRegistry.shouldBeRegistry();
        projectRegistry.searchProject(project.getName());
        projectRegistry.shouldHaveCreatedRecord(project.getName());
        projectRegistry.clickFirstRow();
        projectPage.positiveLessonsLearned();
        projectPage.shouldBeOpened();

        lessonPage.fillFields(lessonLearned);
        lessonPage.clickSaveAndClose();
        projectPage.checkCreateLesson(lessonLearned.getName());
        lessonRegistry.open();
        // lessonRegistry.chooseTypeOfSearchAll();
        lessonRegistry.searchLesson(lessonLearned.getName());
        // lessonRegistry.expandResult();
        lessonRegistry.selectFirstRow();
        lessonRegistry.clickDelete();
        lessonRegistry.acceptDelete();
        lessonRegistry.searchLesson(lessonLearned.getName());
        lessonRegistry.shouldNotHaveResults();
        projectRegistry.open();
        projectRegistry.shouldBeRegistry();
        projectRegistry.searchProject(project.getName());
        projectRegistry.shouldHaveCreatedRecord(project.getName());
        projectRegistry.selectRow();
        projectRegistry.clickDelete();
        projectRegistry.acceptDelete();
        projectRegistry.searchProject(project.getName());
        projectRegistry.shouldNotHaveResults();

    }
}
