package tw.yukina.sitcon.issue.assistant.views.security;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import tw.yukina.sitcon.issue.assistant.manager.AccountManager;
import tw.yukina.sitcon.issue.assistant.manager.AuthManager;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

import java.util.Collections;

@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    public static final String ROUTE = "login";

    private final AuthManager authManager;
    private final UserRepository userRepository;

    private final LoginForm login = new LoginForm();
    private final TextField username;
    private final Button continueButton;
    private final Section section;

    public LoginView(AuthManager authManager, UserRepository userRepository){
        this.authManager = authManager;
        this.userRepository = userRepository;

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);
        login.setVisible(false);

        H2 h2 = new H2("Log in");

        username = new TextField("Username");
        username.setRequired(true);
        username.setMinWidth("312px");

        continueButton = new Button("Continue");
        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        continueButton.setMinWidth("312px");
        continueButton.addClickShortcut(Key.ENTER);
        continueButton.addClickListener(this::continueWithUsername);

        section = new Section(h2, username);
        add(section, continueButton, login);
    }

    private void continueWithUsername(ClickEvent<Button> buttonClickEvent) {

        if(userRepository.findByName(username.getValue()) != null){
            authManager.createAndSendTempPassword(username.getValue());
            Notification notification = new Notification(
                    "Temporary password has send to your telegram account", 10000);
            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            notification.open();

            UI.getCurrent().getPage().executeJs("document.getElementById(\"vaadinLoginUsername\").value = '" + username.getValue() +"';");
            continueButton.setVisible(false);
            section.setVisible(false);
            login.setVisible(true);
        } else {
            Notification notification = new Notification(
                    "Couldn't find your account, please check your username", 10000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(!event.getLocation().getQueryParameters().getParameters().
                getOrDefault("error", Collections.emptyList()).isEmpty()) {
            login.setError(true);
            continueButton.setVisible(false);
            section.setVisible(false);
            login.setVisible(true);
        }
    }
}