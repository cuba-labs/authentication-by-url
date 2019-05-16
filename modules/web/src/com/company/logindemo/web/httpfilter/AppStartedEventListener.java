package com.company.logindemo.web.httpfilter;

import com.haulmont.cuba.security.global.LoginException;
import com.haulmont.cuba.web.App;
import com.haulmont.cuba.web.Connection;
import com.haulmont.cuba.web.security.ExternalUserCredentials;
import com.haulmont.cuba.web.security.events.AppStartedEvent;
import com.haulmont.cuba.web.sys.RequestContext;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Component
public class AppStartedEventListener implements ApplicationListener<AppStartedEvent> {

    private String secretToken = "e63cacd4-646b-4232-bd72-36ddff780bbf";

    @Inject
    private Logger log;

    @Override
    public void onApplicationEvent(AppStartedEvent event) {
        App app = event.getApp();
        Connection connection = app.getConnection();

        // todo uncomment
        /*
        if (!connection.isAuthenticated()) {
            RequestContext requestContext = RequestContext.get();
            if (requestContext != null) {
                HttpServletRequest request = requestContext.getRequest();
                String st = (String) request.getSession().getAttribute("st");
                if (secretToken.equals(st)) {
                    try {
                        connection.login(new ExternalUserCredentials("admin"));
                    } catch (LoginException e) {
                        log.warn("Unable to login by token {}", st);
                    }
                }
            }
        }
        */
    }
}
