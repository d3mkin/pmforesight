package pages;

import pages.auth.LogoutPage;

public interface Registry {
    void open();

    void openFromMenu();

    LogoutPage logout();

    void shouldBeRegistry();
}
