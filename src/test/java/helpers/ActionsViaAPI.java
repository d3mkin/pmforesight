package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ActionsViaAPI {
    static private Cookies cookies;
    static private int projectId;
    static private int goalId;
    static private int userId;
    static private String projectName;
    static private String goalName;
    static private String userName;


    static long currentTime = System.currentTimeMillis();

    public static int getProjectId() {
        return projectId;
    }
    public static String getProjectNameFromAPI() {
        return userName;
    }

    public static int getGoalId() {
        return goalId;
    }
    public static String getGoalNameFromAPI() { return goalName; }

    public static int getUserId() {
        return goalId;
    }
    public static String getUserNameFromAPI() { return goalName; }

    @Step ("Получить cookie для авторизации")
    public static void getCookiesFromLogIn() {
        RestAssured.baseURI = "http://tgr.hera.test.local";
        cookies = RestAssured
                .given()
                    .urlEncodingEnabled(true)
                    .param("name", "admin")
                    .param("password", "123456")
                .when()
                    .post("Login?ReturnUrl=%2FPage%2FIndex")
                .then()
                    .statusCode(302)
                    .assertThat()
                    .cookie(".Foresight.Authentication", notNullValue())
                .extract()
                    .response()
                    .getDetailedCookies();
    }

    @Step ("Создать проект через API")
    public static void createProjectViaAPI(String projectStage, String projectLevel) {

        if (projectStage.equals("Инициирование")) projectStage = "40032";
        if (projectStage.equals("Подготовка")) projectStage = "3";
        if (projectStage.equals("Реализация")) projectStage = "4";
        if (projectStage.equals("Завершение")) projectStage = "40034";
        if (projectStage.equals("Постпроектный мониторинг")) projectStage = "6";
        if (projectStage.equals("Архив")) projectStage = "40033";

        if (projectLevel.equals("Ведомственный")) projectLevel = "null";
        if (projectLevel.equals("Федеральный")) projectLevel = "1";
        if (projectLevel.equals("Региональный")) projectLevel = "2";

        String body = "{\n" +
                "  classid: '46caa4ad-02b8-4d70-a1db-196bab860742', \n" +
                "  classname: 'Project',\n" +
                "  classtitle: 'Проект',\n" +
                "  entityname: 'Project',\n" +
                "  keyfield: 'ProjectId',\n" +
                "  namefield: 'Name',\n" +
                "  UserAccountId: 120613,\n" +
                "  UserAccount: 'admin',\n" +
                "  OwnerId: 120819,\n" +
                "  LeaderId: 120819,\n" +
                "  ProjectLevelId: "+projectLevel+",\n" +
                "  Name: 'CreatedFromAPI "+ System.currentTimeMillis() + "',\n" +
                "  EntityId: '46caa4ad-02b8-4d70-a1db-196bab860742',\n" +
                "  ParentId: 93114,\n" +
                "  ActivityPhaseId: "+projectStage+",\n" +
                "  ProjectTypeId: 2,\n" +
                "  PriorityId: 2,\n" +
                "  ControlSubjectId: 234,\n" +
                "  IsEBSynced: false,\n" +
                "  id: 'new'\n" +
                "}";

        getCookiesFromLogIn();
        Response response =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                .when()
                        .body(body)
                        .post("/entity/Project/new")
                 .then()
                        .statusCode(200)
                 .extract()
                        .response();

        projectId = response.path("id");
        userName = response.path ("Name");
    }

    @Step ("Открыть созданный через API проект")
    public  static void openProjectCreatedFromAPI() {
        Selenide.open(Configuration.baseUrl + "/Project/Form/auto/" + getProjectId());
    }

    @Step ("Открыть созданную через API цель")
    public  static void openGoalCreatedFromAPI() {
        Selenide.open(Configuration.baseUrl + "/Goal/Form/auto/" + getGoalId());
    }

    @Step ("Удалить проект через API")
    public static void deleteProjectCreatedFromAPI(){
        String body = String.format("{remove: [{entityName: 'Project', dataIds: ["+ getProjectId() +"]}], force: true, async: true}");

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
        .when()
                .body(body)
                .delete("/entity")
        .then()
                 .statusCode(200);
    }

    @Step ("Удалить цель через API")
    public static void deleteGoalCreatedFromAPI(){
        String body = String.format("{remove: [{entityName: 'Goal', dataIds: ["+ getGoalId() +"]}], force: true, async: true}");

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
        .when()
                .body(body)
                .delete("/entity")
        .then().log().all()
                .statusCode(200);
    }

    @Step ("Изменить стадию проекта через API")
    public static void changeProjectStageCreatedFromAPI(String moveToStage) {

        if (moveToStage.equals("Инициирование")) moveToStage = "40032";
        if (moveToStage.equals("Подготовка")) moveToStage = "3";
        if (moveToStage.equals("Реализация")) moveToStage = "4";
        if (moveToStage.equals("Завершение")) moveToStage = "40034";
        if (moveToStage.equals("Постпроектный мониторинг")) moveToStage = "6";
        if (moveToStage.equals("Архив")) moveToStage = "40033";

        String body = String.format("{ActivityPhaseId: "+ moveToStage +" }");
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
        .when()
                .body(body)
                .post("/entity/Project/"+ getProjectId() +"")
        .then()
                .statusCode(200);
    }

    @Step ("Создать цель через API")
    public static void createGoalViaAPI(String goalLevel) {

        if (goalLevel.equals("Ведомственный")) goalLevel = "null";
        if (goalLevel.equals("Федеральный")) goalLevel = "1";
        if (goalLevel.equals("Муниципальный")) goalLevel = "3";
        if (goalLevel.equals("Региональный")) goalLevel = "2";
        if (goalLevel.equals("Национальный")) goalLevel = "4";

        String body = "{\n" +
                "  ControlLevelId: "+goalLevel+",\n" +
                "  EditorsId: 120819,\n" +
                "  IsComplete: false,\n" +
                "  IsNationalProjectTask: false,\n" +
                "  Name: 'CreatedFromAPI "+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'admin',\n" +
                "  UserAccountId: '120613',\n" +
                "  classid: 'a7db6e46-462a-4087-a7e3-d5623648cca0',\n" +
                "  classname: 'Goal',\n" +
                "  classtitle: 'Цель',\n" +
                "  entityname: 'Goal',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'GoalId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        getCookiesFromLogIn();
        Response response =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                .when()
                        .body(body)
                        .post("/entity/Goal/new")
                .then()
                        .statusCode(200)
                        .extract()
                        .response();

        goalId = response.path("id");
        goalName = response.path ("Name");
    }

    @Step ("Создать пользователя через API")
    public static void createUserViaAPI() {
        String body = "{\n" +
                "  Account: '',\n" +
                "  Enabled: true,\n" +
                "  EntityId: '66d6d76c-067c-4aa7-b334-4e2748be4fb9',\n" +
                "  FirstName: 'UserFromAPI "+ System.currentTimeMillis() +"',\n" +
                "  IsAggregateMail: false,\n" +
                "  IsAssignment: true,\n" +
                "  IsBoss: false,\n" +
                "  IsFunctionalAdministrator: false,\n" +
                "  IsGroup: false,\n" +
                "  IsReceiveMail: false,\n" +
                "  IsShowSendMessage: false,\n" +
                "  IsSoftDeleted: false,\n" +
                "  Name: 'UserFromAPI "+ System.currentTimeMillis() +"',\n" +
                "  SurName: 'UserFromAPI "+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'admin',\n" +
                "  UserAccountId: '120613',\n" +
                "  classid: '66d6d76c-067c-4aa7-b334-4e2748be4fb9',\n" +
                "  classname: 'User',\n" +
                "  classtitle: 'Пользователь',\n" +
                "  entityname: 'User',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'UserId',\n" +
                "  namefield: 'FullName',\n" +
                "}";
        getCookiesFromLogIn();
        Response response =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                .when()
                        .body(body)
                        .post("/entity/User/batch")
                .then()
                        .statusCode(200)
                        .extract()
                        .response();

        userId = response.path("id");
        userName = response.path ("Name");
    }
}
