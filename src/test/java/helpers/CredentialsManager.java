package helpers;

import com.codeborne.selenide.Credentials;

public class CredentialsManager {
    private static final String PROPERTIES_FILE = "credentials.properties";
    private final PropertiesResourceManager prm;

    private final Credentials userFA;
    private final Credentials userInvalidPassword;
    private final Credentials userInvalidLogin;
    private final Credentials userWorkGroup;
    private final Credentials userProjectManageTeam;
    private final Credentials userProjectOffice;
    private final Credentials userSteeringCommittee;//Управляющий коммитет
    private final Credentials userExternalObserver;
    private final Credentials userVPO_MPO;
    private final Credentials userProjectCommittee;
    private final Credentials userWorkGroupFA;
    private final Credentials userProjectManageTeamFA;
    private final Credentials userProjectOfficeFA;
    private final Credentials userSteeringCommitteeFA;
    private final Credentials userExternalObserverFA;
    private final Credentials userVPO_MPOFA;
    private final Credentials userProjectCommitteeFA;


    public CredentialsManager() {
        this.prm = new PropertiesResourceManager(PROPERTIES_FILE);
        this.userFA = new Credentials(prm.getProperty("userNameFA"), prm.getProperty("userPasswordFA"));
        this.userInvalidPassword = new Credentials(prm.getProperty("userNameFA"), prm.getProperty("userPasswordError"));
        this.userInvalidLogin = new Credentials(prm.getProperty("userNameError"), "userPasswordFA");
        this.userWorkGroup = new Credentials(prm.getProperty("userNameWorkingGroup"),
                prm.getProperty("userPasswordWorkingGroup"));
        this.userProjectManageTeam = new Credentials(prm.getProperty("userNameProjectManagementTeams"),
                prm.getProperty("userPasswordProjectManagementTeams"));
        this.userProjectOffice = new Credentials(prm.getProperty("userNameProjectOffice"), prm.getProperty("userPasswordProjectOffice"));
        this.userSteeringCommittee = new Credentials(prm.getProperty("userNameSteeringCommittee"), prm.getProperty("userPasswordSteeringCommittee"));
        this.userExternalObserver = new Credentials(prm.getProperty("userNameExternalObserver"), prm.getProperty("userPasswordExternalObserver"));
        this.userVPO_MPO = new Credentials(prm.getProperty("userNameVPO_NPO"), prm.getProperty("userPasswordVPO_NPO"));
        this.userProjectCommittee = new Credentials(prm.getProperty("userNameProjectCommittee"), prm.getProperty("userPasswordProjectCommittee"));
        this.userWorkGroupFA = new Credentials(prm.getProperty("userNameWorkingGroupFA"),
                prm.getProperty("userPasswordWorkingGroupFA"));
        this.userProjectManageTeamFA = new Credentials(prm.getProperty("userNameProjectManagementTeamsFA"),
                prm.getProperty("userPasswordProjectManagementTeamsFA"));
        this.userProjectOfficeFA = new Credentials(prm.getProperty("userNameProjectOfficeFA"), prm.getProperty("userPasswordProjectOfficeFA"));
        this.userSteeringCommitteeFA = new Credentials(prm.getProperty("userNameSteeringCommitteeFA"), prm.getProperty("userPasswordSteeringCommitteeFA"));
        this.userExternalObserverFA = new Credentials(prm.getProperty("userNameExternalObserverFA"), prm.getProperty("userPasswordExternalObserverFA"));
        this.userVPO_MPOFA = new Credentials(prm.getProperty("userNameVPO_NPOFA"), prm.getProperty("userPasswordVPO_NPOFA"));
        this.userProjectCommitteeFA = new Credentials(prm.getProperty("userNameProjectCommitteeFA"), prm.getProperty("userPasswordProjectCommitteeFA"));
    }

    public Credentials getUserFA() {
        return userFA;
    }

    public Credentials getUserInvalidPassword() {
        return userInvalidPassword;
    }

    public Credentials getUserInvalidLogin() {
        return userInvalidLogin;
    }

    public Credentials getUserWorkGroup() {
        return userWorkGroup;
    }

    public Credentials getUserProjectManageTeam() {
        return userProjectManageTeam;
    }

    public Credentials getUserProjectOffice() {
        return userProjectOffice;
    }

    public Credentials getUserSteeringCommittee() {
        return userSteeringCommittee;
    }

    public Credentials getUserExternalObserver() {
        return userExternalObserver;
    }

    public Credentials getUserVPO_MPO() {
        return userVPO_MPO;
    }

    public Credentials getUserProjectCommittee() {
        return userProjectCommittee;
    }

    public Credentials getUserWorkGroupFA() {
        return userWorkGroupFA;
    }

    public Credentials getUserProjectManageTeamFA() {
        return userProjectManageTeamFA;
    }

    public Credentials getUserProjectOfficeFA() {
        return userProjectOfficeFA;
    }

    public Credentials getUserSteeringCommitteeFA() {
        return userSteeringCommitteeFA;
    }

    public Credentials getUserExternalObserverFA() {
        return userExternalObserverFA;
    }

    public Credentials getUserVPO_MPOFA() {
        return userVPO_MPOFA;
    }

    public Credentials getUserProjectCommitteeFA() {
        return userProjectCommitteeFA;
    }
}
