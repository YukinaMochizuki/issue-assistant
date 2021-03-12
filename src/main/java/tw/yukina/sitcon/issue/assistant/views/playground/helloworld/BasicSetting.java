package tw.yukina.sitcon.issue.assistant.views.playground.helloworld;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import tw.yukina.sitcon.issue.assistant.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

import java.util.HashMap;
import java.util.Map;

@Route(value = "home", layout = MainView.class)
@PageTitle("Home")
@CssImport("./styles/views/home/home-view.css")
public class BasicSetting extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public BasicSetting() {
        setId("home-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");

        Dialog dialog = new Dialog();
        dialog.add(new Text("Close me with the esc-key or an outside click"));

        dialog.setWidth("400px");
        dialog.setHeight("150px");
        dialog.setDraggable(true);
        dialog.setModal(false);
        dialog.setResizable(true);
        dialog.setCloseOnEsc(true);

        Tab tab1 = new Tab("Tab one");
        Div page1 = new Div();
        page1.setText("Page#1");

        Tab tab2 = new Tab("Tab two");
        Div page2 = new Div();
        page2.setText("Page#2");
        page2.setVisible(false);

        Tab tab3 = new Tab("Tab three");
        Div page3 = new Div();
        page3.setText("Page#3");
        page3.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, page1);
        tabsToPages.put(tab2, page2);
        tabsToPages.put(tab3, page3);
        Tabs tabs = new Tabs(tab1, tab2, tab3);
        tabs.addThemeVariants();
        Div pages = new Div(page1, page2, page3);

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        dialog.add(tabs, pages);

        sayHello.addClickListener(event -> dialog.open());



        add(sayHello);

//        setVerticalComponentAlignment(Alignment.END, name, sayHello);
//        sayHello.addClickListener( e-> {
//            Notification.show("Hello " + name.getValue());
//        });
    }

}
