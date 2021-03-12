package tw.yukina.sitcon.issue.assistant.views.playground.notification;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.RippleClickableCard;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.ItemBody;
import com.github.appreciated.card.label.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import tw.yukina.sitcon.issue.assistant.entity.GitLabWebhookFilter;
import tw.yukina.sitcon.issue.assistant.entity.RecentNotification;
import tw.yukina.sitcon.issue.assistant.service.NotificationService;
import tw.yukina.sitcon.issue.assistant.views.main.MainView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "NewNotification", layout = MainView.class)
@PageTitle("New Notification")
@CssImport("styles/views/newitem/new-item-view.css")
public class NewItemView extends Div {

    private final NotificationService notificationService;

    public NewItemView(NotificationService notificationService){
        this.notificationService = notificationService;
        creatCard();
    }

    private void creatCard(){

        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);

        Icon okIcon = new Icon("vaadin:check");
        okIcon.setColor("#2ECC71");
        Icon okIcon2 = new Icon("vaadin:check");
        okIcon2.setColor("#2ECC71");
        Icon cube = new Icon("vaadin:cube");

        IconItem item = new IconItem(okIcon, "Telegram", "Online - @YukinaBot");
        IconItem item1 = new IconItem(cube,"Version: ", "0.0.1-beta on docker");
        IconItem iconItem = new IconItem(okIcon2, "GitLab", "Online - SITCON/Test");

        item.setMargin(false);
        iconItem.setMargin(false);

        VerticalLayout verticalLayout = new VerticalLayout(
                new TitleLabel("Service Information").withWhiteSpaceNoWrap(), item1, iconItem, item);
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);

        ClickableCard card2 = new ClickableCard(verticalLayout);
        card2.setMinWidth("300px");

        VerticalLayout verticalLayout1 = new VerticalLayout(new TitleLabel("Enabled Items").withWhiteSpaceNoWrap());
        ItemBody itemBody = new ItemBody("32", "GitLab Triggers");

        PrimaryLabel primaryLabelComponent = new PrimaryLabel("32 triggers");
//        VerticalLayout verticalLayout2 = new VerticalLayout(primaryLabelComponent, new Text("GitLab Triggers"));

        RippleClickableCard subCard = new RippleClickableCard(
                new IconItem(new Icon("vaadin:tasks"), primaryLabelComponent));
        subCard.setWidthFull();
        RippleClickableCard subCard2 = new RippleClickableCard(new IconItem(new Icon("vaadin:user"), "12 users"));
        subCard2.setWidthFull();

        verticalLayout1.add(subCard, subCard2);
//        verticalLayout1.expand(subCard);
        verticalLayout1.setMargin(false);
        verticalLayout1.setSpacing(true);
//        verticalLayout.setHeight("100%");

        Card card = new Card(verticalLayout1);
        card.expand(verticalLayout1);

        Card card1 = new Card(new TitleLabel("Recent events"));
//        List<RecentNotification> personList = notificationService.getRecentNotificationList();
        GitLabWebhookFilter gitLabWebhookFilter = new GitLabWebhookFilter();
        gitLabWebhookFilter.setName("編輯組發文申請");
        List<RecentNotification> personList = new ArrayList<>();
        personList.add(new RecentNotification(LocalDateTime.now(), "", gitLabWebhookFilter));
        personList.add(new RecentNotification(LocalDateTime.now(), "", gitLabWebhookFilter));
        personList.add(new RecentNotification(LocalDateTime.now(), "", gitLabWebhookFilter));
        personList.add(new RecentNotification(LocalDateTime.now(), "", gitLabWebhookFilter));
        VerticalLayout verticalLayout2 = new VerticalLayout();
        Grid<RecentNotification> grid = new Grid<>();
        grid.setItems(personList);
        grid.addColumn(RecentNotification::getDateTime).setHeader("Time");
        grid.addColumn(RecentNotification::getFilterName).setHeader("Name");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        verticalLayout2.add(grid);
        card1.add(verticalLayout2);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(card2);
        horizontalLayout.setPadding(true);

        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        card.setMinWidth("300px");
        horizontalLayout1.add(card);
        horizontalLayout1.setPadding(true);

        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        card1.setMinWidth("400px");
//        horizontalLayout2.add(card1);
        horizontalLayout2.setPadding(true);

        flexLayout.add(horizontalLayout, horizontalLayout1, horizontalLayout2);
        add(flexLayout);
    }

//    private void createWithTab(){
//        Tab tab1 = new Tab("Tab one");
//        Div page1 = new Div();
//        page1.setText("Page#1");
//
//        Tab tab2 = new Tab("Tab two");
//        Div page2 = new Div();
//        page2.setText("Page#2");
//        page2.setVisible(false);
//
//        Tab tab3 = new Tab("Tab three");
//        Div page3 = new Div();
//        page3.setText("Page#3");
//        page3.setVisible(false);
//
//        Map<Tab, Component> tabsToPages = new HashMap<>();
//        tabsToPages.put(tab1, page1);
//        tabsToPages.put(tab2, page2);
//        tabsToPages.put(tab3, page3);
//        Tabs tabs = new Tabs(tab1, tab2, tab3);
//        tabs.addThemeVariants();
//        Div pages = new Div(page1, page2, page3);
//
//        tabs.addSelectedChangeListener(event -> {
//            tabsToPages.values().forEach(page -> page.setVisible(false));
//            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
//            selectedPage.setVisible(true);
//        });
//
//        add(tabs, pages);
//    }



//    private TextField cardNumber = new TextField("Credit card number");
//    private TextField cardholderName = new TextField("Cardholder name");
//    private Select<Integer> month = new Select<>();
//    private Select<Integer> year = new Select<>();
//    private ExpirationDateField expiration = new ExpirationDateField(
//            "Expiration date", month, year);
//    private PasswordField csc = new PasswordField("CSC");
//
//    private Button cancel = new Button("Cancel");
//    private Button submit = new Button("Submit");
//
//    public NewItemView() {
//        setId("credit-card-view");
//
//        add(createTitle());
//        add(createFormLayout());
//        add(createButtonLayout());
//
//        cancel.addClickListener(e -> {
//            Notification.show("Not implemented");
//        });
//        submit.addClickListener(e -> {
//            Notification.show("Not implemented");
//        });
//    }
//
//    private Component createTitle() {
//        return new H3("New GitLab Notification");
//    }
//
//    private Component createFormLayout() {
//        FormLayout formLayout = new FormLayout();
//        formLayout.add(cardNumber, cardholderName, expiration, csc);
//        return formLayout;
//    }
//
//    private Component createButtonLayout() {
//        HorizontalLayout buttonLayout = new HorizontalLayout();
//        buttonLayout.addClassName("button-layout");
//        cardNumber.setPlaceholder("1234 5678 9123 4567");
//        cardNumber.setPattern("[\\d ]*");
//        cardNumber.setPreventInvalidInput(true);
//        cardNumber.setRequired(true);
//        cardNumber.setErrorMessage("Please enter a valid credit card number");
//        month.setPlaceholder("Month");
//        month.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
//        year.setPlaceholder("Year");
//        year.setItems(20, 21, 22, 23, 24, 25);
//        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        buttonLayout.add(submit);
//        buttonLayout.add(cancel);
//        return buttonLayout;
//    }
//
//    private class ExpirationDateField extends CustomField<String> {
//        public ExpirationDateField(String label, Select<Integer> month,
//                Select<Integer> year) {
//            setLabel(label);
//            HorizontalLayout layout = new HorizontalLayout(month, year);
//            layout.setFlexGrow(1.0, month, year);
//            month.setWidth("100px");
//            year.setWidth("100px");
//            add(layout);
//        }
//
//        @Override
//        protected String generateModelValue() {
//            // Unused as month and year fields part are of the outer class
//            return "";
//        }
//
//        @Override
//        protected void setPresentationValue(String newPresentationValue) {
//            // Unused as month and year fields part are of the outer class
//        }
//
//    }

}
