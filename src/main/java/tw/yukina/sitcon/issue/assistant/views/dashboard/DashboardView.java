package tw.yukina.sitcon.issue.assistant.views.dashboard;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.RippleClickableCard;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.gitlab4j.api.models.Project;
import org.springframework.boot.info.BuildProperties;
import tw.yukina.sitcon.issue.assistant.config.TelegramConfig;
import tw.yukina.sitcon.issue.assistant.repository.GroupRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;
import tw.yukina.sitcon.issue.assistant.service.NotificationService;
import tw.yukina.sitcon.issue.assistant.views.dialog.AbstractDialog;
import tw.yukina.sitcon.issue.assistant.views.grid.GroupGrid;
import tw.yukina.sitcon.issue.assistant.views.grid.HookFilterGrid;
import tw.yukina.sitcon.issue.assistant.views.grid.UserGrid;
import tw.yukina.sitcon.issue.assistant.views.main.MainView;

import java.io.IOException;

@Route(value = "Dashboard", layout = MainView.class)
@PageTitle("Dashboard")
@RouteAlias(value = "", layout = MainView.class)
public class DashboardView extends Div {

    private final BuildProperties buildProperties;

    private final NotificationService notificationService;

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final TelegramConfig telegramConfig;
    private final Project project;

    private final HookFilterGrid hookFilterGrid;
    private final UserGrid userGrid;
    private final GroupGrid groupGrid;

    private PrimaryLabel triggerLabel;
    private PrimaryLabel userLabel;
    private PrimaryLabel groupLabel;

    public DashboardView(BuildProperties buildProperties, TelegramConfig telegramConfig, Project project,
                         NotificationService notificationService, UserRepository userRepository,
                         GroupRepository groupRepository, HookFilterGrid hookFilterGrid,
                         UserGrid userGrid, GroupGrid groupGrid){
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.buildProperties = buildProperties;
        this.telegramConfig = telegramConfig;
        this.hookFilterGrid = hookFilterGrid;
        this.userGrid = userGrid;
        this.groupGrid = groupGrid;
        this.project = project;

        createCardView();
    }

    private void createCardView(){
        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);

        flexLayout.add(cardWrapper(createSysInfoCard()));
        flexLayout.add(cardWrapper(createEnabledItem()));

        add(flexLayout);
    }

    private HorizontalLayout cardWrapper(Component... components){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(components);
        horizontalLayout.setPadding(true);
        return horizontalLayout;
    }

    private Component createSysInfoCard(){
        VerticalLayout verticalLayout = new VerticalLayout(
                new TitleLabel("Service Information").withWhiteSpaceNoWrap(),
                getVersionInfo(),
                getTelegramInfo(),
                getGitLabInfo());
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);

        ClickableCard card = new ClickableCard(verticalLayout);
        card.setMinWidth("300px");
        return card;
    }

    private Component createEnabledItem(){
        VerticalLayout verticalLayout = new VerticalLayout(new TitleLabel("Enabled Items").withWhiteSpaceNoWrap());

        triggerLabel = new PrimaryLabel(generateUnitOfLabel(notificationService.getFilterCount(), "trigger"));
        userLabel = new PrimaryLabel(generateUnitOfLabel(userRepository.count(), "user"));
        groupLabel = new PrimaryLabel(generateUnitOfLabel(groupRepository.count(), "group"));

        RippleClickableCard triggerClickableCard = generateRippleClickSubCard("vaadin:tasks", triggerLabel);
        triggerClickableCard.addClickListener(event ->{
            VerticalLayout dialogLayout = new VerticalLayout(hookFilterGrid.getGrid());
            AbstractDialog abstractDialog = new AbstractDialog("triggers", dialogLayout);
            abstractDialog.setWidth("500px");
            abstractDialog.open();
        });

        RippleClickableCard userClickableCard = generateRippleClickSubCard("vaadin:user", userLabel);
        userClickableCard.addClickListener(event ->{
            VerticalLayout dialogLayout = new VerticalLayout(userGrid.getGrid());
            AbstractDialog abstractDialog = new AbstractDialog("users", dialogLayout);
            abstractDialog.setWidth("550px");
            abstractDialog.open();
        });

        RippleClickableCard groupClickableCard = generateRippleClickSubCard("vaadin:users", groupLabel);
        groupClickableCard.addClickListener(event ->{
            VerticalLayout dialogLayout = new VerticalLayout(groupGrid.getGrid());
            AbstractDialog abstractDialog = new AbstractDialog("groups", dialogLayout);
            abstractDialog.setWidth("400px");
            abstractDialog.open();
        });

        verticalLayout.add(triggerClickableCard);
        verticalLayout.add(userClickableCard);
        verticalLayout.add(groupClickableCard);

        Card card = new Card(verticalLayout);
        card.expand(verticalLayout);
        card.setMinWidth("300px");
        return card;
    }

    public void updateEnabledItem(){
        triggerLabel = new PrimaryLabel(generateUnitOfLabel(notificationService.getFilterCount(), "trigger"));
        userLabel = new PrimaryLabel(generateUnitOfLabel(userRepository.count(), "user"));
        groupLabel = new PrimaryLabel(generateUnitOfLabel(groupRepository.count(), "group"));
    }

    private RippleClickableCard generateRippleClickSubCard(String icon, Component component){
        RippleClickableCard card = new RippleClickableCard(
                new IconItem(new Icon(icon), component));
        card.setWidthFull();
        return card;
    }

    private IconItem getVersionInfo(){
        String version = buildProperties.getVersion();
        return new IconItem(new Icon("vaadin:cube"),"Version: ", version);
    }

    private IconItem getTelegramInfo(){
        try {
            String telegramBotStatus = telegramConfig.getBotStatue();

            if(telegramBotStatus.contains("ok")){
                return new IconItem(generateCheckIcon(), "Telegram",
                        "Online - @" + telegramConfig.getTelegramUsername());
            }else return new IconItem(generateErrorIcon(), "Telegram",
                    "Offline - " + telegramBotStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new IconItem(generateErrorIcon(), "Telegram", "Error");
    }

    private IconItem getGitLabInfo(){
        if(project != null){
            return new IconItem(generateCheckIcon(), "GitLab",
                    "Online - " + project.getNameWithNamespace());
        }else return new IconItem(generateErrorIcon(), "GitLab", "Error");
    }

    private Icon generateCheckIcon(){
        Icon check = new Icon("vaadin:check");
        check.setColor("#2ECC71");
        return check;
    }

    private Icon generateErrorIcon(){
        Icon check = new Icon("vaadin:close");
        check.setColor("#E74C3C");
        return check;
    }

    private String generateUnitOfLabel(long integer, String unit){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer);
        if(integer > 1)stringBuilder.append(" ").append(unit).append("s");
        else stringBuilder.append(" ").append(unit);

        return stringBuilder.toString();
    }
}
