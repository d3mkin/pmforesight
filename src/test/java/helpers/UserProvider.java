package helpers;

public class UserProvider {
    private static final UserManager userManager = new UserManager();

    public static Object[][] allUsers() {
        return new Object[][]{
                {userManager.getWorkGroup()},
                {userManager.getProjectManageTeam()},
                {userManager.getProjectOffice()},
                {userManager.getSteeringCommittee()},
                {userManager.getExternalObserver()},
                {userManager.getVPO_MPO()},
                {userManager.getProjectCommittee()}
        };
    }


    public static Object[][] PO_UK_VN_DPO_PK() {
        return new Object[][]{
                {userManager.getProjectOffice()},
                {userManager.getSteeringCommittee()},
                {userManager.getExternalObserver()},
                {userManager.getVPO_MPO()},
                {userManager.getProjectCommittee()}
        };
    }


    public static Object[][] WG_PC() {
        return new Object[][]{
                {userManager.getWorkGroup()},
                {userManager.getProjectCommittee()}
        };
    }


    public static Object[][] PO_PK() {
        return new Object[][]{
                {userManager.getProjectOffice()},
                {userManager.getSteeringCommittee()}
        };
    }

    public static Object[][] allUsersToCreateRisk() {
        return new Object[][]{
                {userManager.getWorkGroup()},
                {userManager.getProjectManageTeam()},
                {userManager.getProjectOffice()},
                {userManager.getVPO_MPO()},
                {userManager.getProjectCommittee()}
        };
    }

    public static Object[][] allUsersFA() {
        return new Object[][]{
                {userManager.getWorkGroupFA()},
                {userManager.getProjectManageTeamFA()},
                {userManager.getProjectOfficeFA()},
                {userManager.getSteeringCommitteeFA()},
                {userManager.getExternalObserverFA()},
                {userManager.getVPO_MPO_FA()},
                {userManager.getProjectCommitteeFA()}
        };
    }


    public static Object[][] UsersFA() {
        return new Object[][]{
                {userManager.getProjectManageTeamFA()},
        };
    }

    public static Object[][] mainFA() {
        return new Object[][]{
                {userManager.getMainFA()}
        };
    }

    public static Object[][] PMT_PO_VPO() {
        return new Object[][]{
                {userManager.getProjectManageTeam()},
                {userManager.getProjectOffice()},
                {userManager.getVPO_MPO()}
        };
    }

    /**
     * Метод возвращает пользователей
     * "Рабочая группа",
     * "Команда управления проектами",
     * "Проектный офис",
     * "Внешний наблюдатель",
     * "ВПО/MPO"
     */

    public Object[][] usersForContracts() {
        return new Object[][]{
                {userManager.getWorkGroup()},
                {userManager.getProjectManageTeam()},
                {userManager.getProjectOffice()},
                {userManager.getExternalObserver()},
                {userManager.getVPO_MPO()},
        };
    }

    /**
     * Метод возвращает пользователей
     * "Проектный комитет",
     * "Проектный офис"
     */

    public Object[][] usersForEmployee() {
        return new Object[][]{
                {userManager.getProjectCommittee()},
                {userManager.getProjectOffice()}
        };
    }

    /**
     * Метод возвращает пользователей
     * "Рабочая группа",
     * "Команда управления проектами",
     * "Проектный офис",
     * "ВПО/MPO",
     * "Проектный комитет"
     */

    public Object[][] usersForInitiative() {
        return new Object[][]{
                {userManager.getWorkGroup()},
                {userManager.getProjectManageTeam()},
                {userManager.getProjectOffice()},
                {userManager.getVPO_MPO()},
                {userManager.getProjectCommittee()},
        };
    }
}
