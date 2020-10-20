package tests.entity_generation.contracts;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.Contract;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.contracts.ContractsRegistry;
import pages.managementobjects.contracts.ContractPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.DATA_GENERATION)
@Tag("entityGeneration")
@Tag("Regression")
public class CreateContractTests extends BaseTest {
    private SingInPage singIn;
    private ContractsRegistry contractRegistry;
    private ContractPage contractPage;
    private Contract contract;

    @BeforeEach
    public void loginAndSetup() {
        singIn = new SingInPage();
        singIn.open();
        contractRegistry = new ContractsRegistry();
        contractPage = new ContractPage();
        contract = new Contract();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest (name = "Создание сущности Контракт из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-132")
    @TmsLink("399")
    public void CreateContractShouldHaveMessageAboutRequiredFieldsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        contractRegistry.open();
        contractRegistry.controlPanel().clickAddButton();
        contractPage.shouldBeOpened();
        contractPage.shouldHaveTitle("Контракт");
        contractPage.clickSave();
        contractPage.shouldHaveMessageAboutRequiredFields(
                "Необходимо заполнить поле \"Наименование\"",
                "Необходимо заполнить поле \"Проект\"",
                "Необходимо заполнить поле \"Планируемая стоимость\"",
                "Необходимо заполнить поле \"Заказчик\"",
                "Необходимо заполнить поле \"Ответственный от заказчика\"");
        contractPage.closeDialog();
        contractPage.clickClose();
        contractPage.shouldBeClosed();
        contractRegistry.shouldBeRegistry();
    }

    @ParameterizedTest (name = "Создание сущности Контракт из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-133")
    @TmsLink("395")
    public void createContractWithSaveAndCloseTest(User user) {
        long currentTime = System.currentTimeMillis();
        contract
                .setContractName("Тест_C395_" + currentTime)
                .setProject("500-001")
                .setContractType("Открытый конкурс")
                .setStage("Инициирование")
                .setExceptedPrice("50")
                .setCustomer(user.getName())
                .setExecutor("Автотестов")
                .setResponsiblePerson(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        contractRegistry.open();
        contractRegistry.controlPanel().clickAddButton();
        contractPage.shouldBeOpened();
        contractPage.fillFields(contract);
        contractPage.clickSaveAndClose();
        contractRegistry.changeView("Все контракты");
        contractRegistry.clickExpandAll();
        contractRegistry.deleteContract(contract);
        contractRegistry.searchContract(contract.getName());
        contractRegistry.shouldNotHaveResults();
    }

}
