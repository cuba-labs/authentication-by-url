package com.company.logindemo.web;

import com.haulmont.cuba.gui.UrlRouting;
import com.haulmont.cuba.gui.navigation.NavigationState;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.web.app.loginwindow.AppLoginWindow;
import com.haulmont.cuba.web.security.ExternalUserCredentials;

import javax.inject.Inject;

public class ExtAppLoginWindow extends AppLoginWindow {

    private NavigationState state;
    private String secretToken = "e63cacd4-646b-4232-bd72-36ddff780bbf";

    @Inject
    private UrlRouting urlRouting;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        state = urlRouting.getState();
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        String st = state.getParams().get("st");
        if (secretToken.equals(st)) {
            doLogin(new ExternalUserCredentials("admin"));
        }
    }
}