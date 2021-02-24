package tests.entity_generation.directories;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.Contractor;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.directories.ContractorsRegistry;
import pages.directories.contractors.CreateContractorsPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.ENTITY_CREATION)
@Story("Подрядчик")
@Tag("entityGeneration")
@Tag("Regression")
public class CreateContractorTests extends BaseTest {
    private ContractorsRegistry contractRegistry;
    private CreateContractorsPage createModal;
    private Contractor contractor;
    private SingInPage singIn;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        contractRegistry = new ContractorsRegistry();
        createModal = new CreateContractorsPage();
        long currentTime = System.currentTimeMillis();
        contractor = new Contractor();
        contractor.setName("Тест_C580_" + currentTime);
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest (name = "Создание сущности Подрядчика из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-134")
    @TmsLink("580")
    public void CreateContractorsShouldHaveMessageAboutRequiredFieldsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        contractRegistry.open();
        contractRegistry.controlPanel().clickAddButton();
        createModal.modalWindowShouldBeOpened();
        createModal.modalWindowShouldHaveTitle("Подрядчики");
        createModal.clickSave();
        createModal
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Наименование\""
                );
        createModal.closeDialog();
        createModal.clickClose();
        contractRegistry.shouldBeRegistry();
    }

    //TODO: Написать тест на создание и проверку
}
