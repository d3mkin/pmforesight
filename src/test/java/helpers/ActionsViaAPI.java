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
    static private int programId;
    static private int portfolioId;
    static private int nationalProjectId;
    static private int nonProjectEventId;

    static private String projectName;
    static private String goalName;
    static private String userName;
    static private String programName;
    static private String portfolioName;
    static private String nationalProjectName;
    static private String nonProjectEventName;


    static long currentTime = System.currentTimeMillis();

    public static int getProjectId() {
        return projectId;
    }
    public static String getProjectNameFromAPI() {
        return projectName;
    }

    public static int getGoalId() {
        return goalId;
    }
    public static String getGoalNameFromAPI() { return goalName; }

    public static int getProgramId() {
        return programId;
    }
    public static String getProgramNameFromAPI() { return programName; }

    public static int getUserId() {
        return userId;
    }
    public static String getUserNameFromAPI() { return userName; }

    public static int getPortfolioId() {
        return portfolioId;
    }
    public static String getPortfolioNameFromAPI() { return portfolioName; }

    public static int getNationalProjectId() {
        return nationalProjectId;
    }
    public static String getNationalProjectNameFromAPI() { return nationalProjectName; }

    public static int getNonProjectEventId() {
        return nonProjectEventId;
    }
    public static String getNonProjectEventNameFromAPI() { return nonProjectEventName; }

    @Step ("Получить cookie для авторизации")
    public static void getCookiesFromLogIn() {
        RestAssured.baseURI = Configuration.baseUrl;
        cookies = RestAssured
                .given()
                    .urlEncodingEnabled(true)
                    .param("name", "fa")
                    .param("password", "1qaz@WSX")
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

    //Проект
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

        String testProjectBody = "{\n" +
                "  classid: '46caa4ad-02b8-4d70-a1db-196bab860742', \n" +
                "  classname: 'Project',\n" +
                "  classtitle: 'Проект',\n" +
                "  entityname: 'Project',\n" +
                "  keyfield: 'ProjectId',\n" +
                "  namefield: 'Name',\n" +
                "  UserAccountId: 100070,\n" +
                "  UserAccount: 'FA',\n" +
                "  OwnerId: 100070,\n" +
                "  LeaderId: 100070,\n" +
                "  ProjectLevelId: "+projectLevel+",\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() + "',\n" +
                "  EntityId: '46caa4ad-02b8-4d70-a1db-196bab860742',\n" +
                "  ParentId: 93092,\n" +
                "  ActivityPhaseId: "+projectStage+",\n" +
                "  ProjectTypeId: 2,\n" +
                "  PriorityId: 2,\n" +
                "  ControlSubjectId: 223,\n" +
                "  IsEBSynced: false,\n" +
                "  id: 'new'\n" +
                "}";

        String stageProjectBody = "{\n" +
                "  classid: '46caa4ad-02b8-4d70-a1db-196bab860742', \n" +
                "  classname: 'Project',\n" +
                "  classtitle: 'Проект',\n" +
                "  entityname: 'Project',\n" +
                "  keyfield: 'ProjectId',\n" +
                "  namefield: 'Name',\n" +
                "  UserAccountId: 100070,\n" +
                "  UserAccount: 'FA',\n" +
                "  OwnerId: 100070,\n" +
                "  LeaderId: 100070,\n" +
                "  ProjectLevelId: "+projectLevel+",\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() + "',\n" +
                "  EntityId: '46caa4ad-02b8-4d70-a1db-196bab860742',\n" +
                "  ParentId: 4,\n" +
                "  ActivityPhaseId: "+projectStage+",\n" +
                "  ProjectTypeId: 3,\n" +
                "  PriorityId: 2,\n" +
                "  ControlSubjectId: 238,\n" +
                "  IsEBSynced: false,\n" +
                "  id: 'new'\n" +
                "}";

        String body;
        getCookiesFromLogIn();

        if (Configuration.baseUrl.equals("http://tgr.hera.test.local")) {
            body = testProjectBody;
        } else {
            body = stageProjectBody;
        }

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
        projectName = response.path ("Name");
    }

    @Step ("Открыть созданный через API проект")
    public  static void openProjectCreatedFromAPI() {
        Selenide.open(Configuration.baseUrl + "/Project/Form/auto/" + getProjectId());
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

    //Цель
    @Step ("Создать цель через API")
    public static void createGoalViaAPI(String goalLevel) {

        if (goalLevel.equals("Ведомственный")) goalLevel = "null";
        if (goalLevel.equals("Федеральный")) goalLevel = "1";
        if (goalLevel.equals("Муниципальный")) goalLevel = "3";
        if (goalLevel.equals("Региональный")) goalLevel = "2";
        if (goalLevel.equals("Национальный")) goalLevel = "4";

        String testGoalBody = "{\n" +
                "  ControlLevelId: "+goalLevel+",\n" +
                "  EditorsId: 100070,\n" +
                "  IsComplete: false,\n" +
                "  IsNationalProjectTask: false,\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: 'a7db6e46-462a-4087-a7e3-d5623648cca0',\n" +
                "  classname: 'Goal',\n" +
                "  classtitle: 'Цель',\n" +
                "  entityname: 'Goal',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'GoalId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String stageGoalBody = "{\n" +
                "  ControlLevelId: "+goalLevel+",\n" +
                "  EditorsId: 100070,\n" +
                "  IsComplete: false,\n" +
                "  IsNationalProjectTask: false,\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: 'a7db6e46-462a-4087-a7e3-d5623648cca0',\n" +
                "  classname: 'Goal',\n" +
                "  classtitle: 'Цель',\n" +
                "  entityname: 'Goal',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'GoalId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String body;
        getCookiesFromLogIn();

        if (Configuration.baseUrl.equals("http://tgr.hera.test.local")) {
            body = testGoalBody;
        } else {
            body = stageGoalBody;
        }

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

    @Step ("Открыть созданную через API цель")
    public  static void openGoalCreatedFromAPI() {
        Selenide.open(Configuration.baseUrl + "/Goal/Form/auto/" + getGoalId());
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

    //Пользователь
    @Step ("Создать пользователя через API")
    public static void createUserViaAPI() {
        String testUserBody = "{\n" +
                "  Account: '',\n" +
                "  Enabled: true,\n" +
                "  EntityId: '66d6d76c-067c-4aa7-b334-4e2748be4fb9',\n" +
                "  FirstName: 'UserFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  IsAggregateMail: false,\n" +
                "  IsAssignment: true,\n" +
                "  IsBoss: false,\n" +
                "  IsFunctionalAdministrator: false,\n" +
                "  IsGroup: false,\n" +
                "  IsReceiveMail: false,\n" +
                "  IsShowSendMessage: false,\n" +
                "  IsSoftDeleted: false,\n" +
                "  Name: 'UserFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  SurName: 'UserFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: '66d6d76c-067c-4aa7-b334-4e2748be4fb9',\n" +
                "  classname: 'User',\n" +
                "  classtitle: 'Пользователь',\n" +
                "  entityname: 'User',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'UserId',\n" +
                "  namefield: 'FullName',\n" +
                "}";

        String stageUserBody = "{\n" +
                "  Account: '',\n" +
                "  Enabled: true,\n" +
                "  EntityId: '66d6d76c-067c-4aa7-b334-4e2748be4fb9',\n" +
                "  FirstName: 'UserFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  IsAggregateMail: false,\n" +
                "  IsAssignment: true,\n" +
                "  IsBoss: false,\n" +
                "  IsFunctionalAdministrator: false,\n" +
                "  IsGroup: false,\n" +
                "  IsReceiveMail: false,\n" +
                "  IsShowSendMessage: false,\n" +
                "  IsSoftDeleted: false,\n" +
                "  Name: 'UserFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  SurName: 'UserFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: '66d6d76c-067c-4aa7-b334-4e2748be4fb9',\n" +
                "  classname: 'User',\n" +
                "  classtitle: 'Пользователь',\n" +
                "  entityname: 'User',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'UserId',\n" +
                "  namefield: 'FullName',\n" +
                "}";

        String body;
        getCookiesFromLogIn();

        if (Configuration.baseUrl.equals("http://tgr.hera.test.local")) {
            body = testUserBody;
        } else {
            body = stageUserBody;
        }

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

    //Программа
    @Step ("Создать Программу через API")
    public static void createProgramViaAPI() {

        String testProgramBody = "{\n" +
                "  ActivityPhaseId: 40047,\n" +
                "  OwnerId: 100070,\n" +
                "  LeaderId: 100070,\n" +
                "  CustomerId: 100070,\n" +
                "  EntityId: '396abf01-5bf3-46a3-b4a5-a7ef4cdb8859',\n" +
                "  PriorityId: 2,\n" +
                "  ParentId: 292214,\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: '396abf01-5bf3-46a3-b4a5-a7ef4cdb8859',\n" +
                "  classname: 'LProgram',\n" +
                "  classtitle: 'Программа',\n" +
                "  entityname: 'LProgram',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'LProgramId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String stageProgramBody = "{\n" +
                "  ActivityPhaseId: 40047,\n" +
                "  OwnerId: 100070,\n" +
                "  LeaderId: 100070,\n" +
                "  CustomerId: 100070,\n" +
                "  EntityId: '396abf01-5bf3-46a3-b4a5-a7ef4cdb8859',\n" +
                "  PriorityId: 2,\n" +
                "  ParentId: 4,\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: '396abf01-5bf3-46a3-b4a5-a7ef4cdb8859',\n" +
                "  classname: 'LProgram',\n" +
                "  classtitle: 'Программа',\n" +
                "  entityname: 'LProgram',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'LProgramId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String body;
        getCookiesFromLogIn();

        if (Configuration.baseUrl.equals("http://tgr.hera.test.local")) {
            body = testProgramBody;
        } else {
            body = stageProgramBody;
        }

        Response response =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .when()
                        .body(body)
                        .post("/entity/LProgram/new")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        programId = response.path("id");
        programName = response.path ("Name");
    }

    @Step ("Открыть созданную через API Программу")
    public  static void openProgramCreatedFromAPI() {
        Selenide.open(Configuration.baseUrl + "/LProgram/Form/auto/" + getProgramId());
    }

    //Портфель
    @Step ("Создать Портфель через API")
    public static void createPortfolioViaAPI() {

        String testPortfolioBody = "{\n" +
                "  LeaderId: 100070,\n" +
                "  EntityId: 'bfed1f68-33a7-4428-855f-799640070a53',\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: 'bfed1f68-33a7-4428-855f-799640070a53',\n" +
                "  classname: 'Portfolio',\n" +
                "  classtitle: 'Портфель',\n" +
                "  entityname: 'Portfolio',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'PortfolioId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String stagePortfolioBody = "{\n" +
                "  LeaderId: 100070,\n" +
                "  EntityId: 'bfed1f68-33a7-4428-855f-799640070a53',\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: 'bfed1f68-33a7-4428-855f-799640070a53',\n" +
                "  classname: 'Portfolio',\n" +
                "  classtitle: 'Портфель',\n" +
                "  entityname: 'Portfolio',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'PortfolioId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String body;
        getCookiesFromLogIn();

        if (Configuration.baseUrl.equals("http://tgr.hera.test.local")) {
            body = testPortfolioBody;
        } else {
            body = stagePortfolioBody;
        }

        Response response =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .when()
                        .body(body)
                        .post("/entity/Portfolio/new")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        portfolioId = response.path("id");
        portfolioName = response.path ("Name");
    }

    //Нац. проект
    @Step ("Создать Нац.Проект через API")
    public static void createNationalProjectViaAPI() {

        String testNationalProjectBody = "{\n" +
                "  LeaderId: 100070,\n" +
                "  OwnerId: 100070,\n" +
                "  EntityId: 'e29b23c0-3d72-45eb-b400-9e26caa1e22d',\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: 'e29b23c0-3d72-45eb-b400-9e26caa1e22d',\n" +
                "  classname: 'NationalProject',\n" +
                "  classtitle: 'Национальный проект',\n" +
                "  entityname: 'NationalProject',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'NationalProjectId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String stageNationalProjectBody = "{\n" +
                "  LeaderId: 100070,\n" +
                "  OwnerId: 100070,\n" +
                "  EntityId: 'e29b23c0-3d72-45eb-b400-9e26caa1e22d',\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: 'e29b23c0-3d72-45eb-b400-9e26caa1e22d',\n" +
                "  classname: 'NationalProject',\n" +
                "  classtitle: 'Национальный проект',\n" +
                "  entityname: 'NationalProject',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'NationalProjectId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String body;
        getCookiesFromLogIn();

        if (Configuration.baseUrl.equals("http://tgr.hera.test.local")) {
            body = testNationalProjectBody;
        } else {
            body = stageNationalProjectBody;
        }

        Response response =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .when()
                        .body(body)
                        .post("/entity/NationalProject/new")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        nationalProjectId= response.path("id");
        nationalProjectName = response.path ("Name");
    }

    //Непроектное мероприятие
    @Step ("Создать Непроектное мероприятие через API")
    public static void createNonProjectEventViaAPI() {

        String testNonProjectEventBody = "{\n" +
                "  CustomerId: 100070,\n" +
                "  LeaderId: 100070,\n" +
                "  ParentId: 93092,\n" +
                "  ActivityPhaseId : 40045,\n" +
                "  EntityId: '327c5b7d-47d0-4b13-8ea5-82bef6255ed9',\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: '327c5b7d-47d0-4b13-8ea5-82bef6255ed9',\n" +
                "  classname: 'Event',\n" +
                "  classtitle: 'Непроектное мероприятие',\n" +
                "  entityname: 'Event',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'EventId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String stageNonProjectEventBody = "{\n" +
                "  CustomerId: 100070,\n" +
                "  LeaderId: 100070,\n" +
                "  ParentId: 2,\n" +
                "  ActivityPhaseId : 40045,\n" +
                "  EntityId: '327c5b7d-47d0-4b13-8ea5-82bef6255ed9',\n" +
                "  Name: 'CreatedFromAPI_"+ System.currentTimeMillis() +"',\n" +
                "  UserAccount: 'FA',\n" +
                "  UserAccountId: '100070',\n" +
                "  classid: '327c5b7d-47d0-4b13-8ea5-82bef6255ed9',\n" +
                "  classname: 'Event',\n" +
                "  classtitle: 'Непроектное мероприятие',\n" +
                "  entityname: 'Event',\n" +
                "  id: 'new',\n" +
                "  keyfield: 'EventId',\n" +
                "  namefield: 'Name',\n" +
                "}";

        String body;
        getCookiesFromLogIn();

        if (Configuration.baseUrl.equals("http://tgr.hera.test.local")) {
            body = testNonProjectEventBody;
        } else {
            body = stageNonProjectEventBody;
        }

        Response response =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .when()
                        .body(body)
                        .post("/entity/Event/new")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        nonProjectEventId= response.path("id");
        nonProjectEventName = response.path ("Name");
    }

    @Step ("Открыть созданное через API Непроектное мероприятие")
    public  static void openNonProjectEventFromAPI() {
        Selenide.open(Configuration.baseUrl + "/Event/Form/auto/" + getNonProjectEventId());
    }
}
