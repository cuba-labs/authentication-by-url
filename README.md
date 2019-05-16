### CUBA authentication by URL demo
This sample demonstrates two ways of how to log in CUBA Application by URL.

CUBA platform: 7.0.5

The first way is using parameters in URL navigation. For instance, let's say that
the token "e63cacd4-646b-4232-bd72-36ddff780bbf" is generated only for user "admin".
So we can add the following code to the `LoginWidow`:

```
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
```

In `onBeforeShow` we save the current state of URL with parameters in order to check
token and do log in after screen is shown.

Use the following link to log in:
```
localhost:8080/app/#login?st=e63cacd4-646b-4232-bd72-36ddff780bbf
```

The second way is using `HttpRequestFilter` and `ApplicationListener<AppStartedEvent>`.
In our filter, we check that address contains the specific parameter and if so save it to
HttpSession.

```
String st = request.getParameter("st");
if (!Strings.isNullOrEmpty(st)) {
    request.getSession().setAttribute("st", st);

    RequestContext.create(request, response);

    response.sendRedirect(ControllerUtils.getLocationWithoutParams(
            URI.create(request.getRequestURL().toString())));
}

chain.doFilter(request, response);
```

Redirect is used for removing parameters from address. In the ApplicationListener
we get given parameter, check it and do login.

```
private String secretToken = "e63cacd4-646b-4232-bd72-36ddff780bbf";

@Override
public void onApplicationEvent(AppStartedEvent event) {
    App app = event.getApp();
    Connection connection = app.getConnection();

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
}
```

Use the following link to log in:
```
localhost:8080/app?st=e63cacd4-646b-4232-bd72-36ddff780bbf
```

Note, in order to try the second approach remove code from `ExtLoginWindow` and
uncomment code in `AppStartedEventListener` and `ExternalLoginHttpFilter`.
