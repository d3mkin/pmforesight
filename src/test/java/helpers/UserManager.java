package helpers;


import model.User;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class UserManager {
    private static final String PROPERTIES_FILE = "users.properties";
    private final PropertiesResourceManager prm;

    //Пользователи
    private final User workGroup;
    private final User projectManageTeam;
    private final User projectOffice;
    private final User steeringCommittee;//Управляющий коммитет
    private final User externalObserver;
    private final User VPO_MPO;
    private final User projectCommittee;
    //Админы
    private final User workGroupFA;
    private final User projectManageTeamFA;
    private final User projectOfficeFA;
    private final User steeringCommitteeFA;//Управляющий коммитет
    private final User externalObserverFA;
    private final User VPO_MPO_FA;
    private final User projectCommitteeFA;
    //Пользователь для написания тестов
    private final User bumaginFA;


    public UserManager() {
        this.prm = new PropertiesResourceManager(PROPERTIES_FILE);
        this.workGroup = initUser("workGroup");
        this.projectManageTeam = initUser("projectManageTeam");
        this.projectOffice = initUser("projectOffice");
        this.steeringCommittee = initUser("steeringCommittee");
        this.externalObserver = initUser("externalObserver");
        this.VPO_MPO = initUser("VPO_MPO");
        this.projectCommittee = initUser("projectCommittee");

        this.workGroupFA = initUser("workGroupFA");
        this.projectManageTeamFA = initUser("projectManageTeamFA");
        this.projectOfficeFA = initUser("projectOfficeFA");
        this.steeringCommitteeFA = initUser("steeringCommitteeFA");
        this.externalObserverFA = initUser("externalObserverFA");
        this.VPO_MPO_FA = initUser("VPO_MPO_FA");
        this.projectCommitteeFA = initUser("projectCommitteeFA");
        this.bumaginFA = initUser("bumaginFA");
    }

    private User initUser(String prefix) {
        User user = new User(
                prm.getProperty(prefix + ".login"),
                prm.getProperty(prefix + ".password"));
        user.setName(new String(prm.getProperty(prefix + ".FIO").getBytes(StandardCharsets.ISO_8859_1)));
        if (prefix.endsWith("FA")) {
            user.setFA(true);
        }
        return user;
    }

    public Object getWorkGroup() {
        return workGroup;
    }

    public Object getProjectManageTeam() {
        return projectManageTeam;
    }

    public Object getProjectOffice() {
        return projectOffice;
    }

    public Object getSteeringCommittee() {
        return steeringCommittee;
    }

    public Object getExternalObserver() {
        return externalObserver;
    }

    public Object getVPO_MPO() {
        return VPO_MPO;
    }

    public Object getProjectCommittee() {
        return projectCommittee;
    }

    public Object getWorkGroupFA() {
        return workGroupFA;
    }

    public Object getProjectManageTeamFA() {
        return projectManageTeamFA;
    }

    public Object getProjectOfficeFA() {
        return projectOfficeFA;
    }

    public Object getSteeringCommitteeFA() {
        return steeringCommitteeFA;
    }

    public Object getExternalObserverFA() {
        return externalObserverFA;
    }

    public Object getVPO_MPO_FA() {
        return VPO_MPO_FA;
    }

    public Object getProjectCommitteeFA() {
        return projectCommitteeFA;
    }

    public Object getBumaginFA() {
        return bumaginFA;
    }
}
