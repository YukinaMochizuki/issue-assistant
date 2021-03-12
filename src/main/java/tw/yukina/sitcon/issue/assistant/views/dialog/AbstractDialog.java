package tw.yukina.sitcon.issue.assistant.views.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.Lumo;

@CssImport("./styles/views/dialog/abstract-dialog.css")
public class AbstractDialog extends Dialog {

    private Header header;
    private Footer footer;

    private VerticalLayout content;

    public AbstractDialog(String title, VerticalLayout content){
        setDraggable(true);
        setModal(false);
        setResizable(true);

        getElement().getThemeList().add("abstract-dialog");
        getElement().setAttribute("aria-labelledby", "dialog-title");

        H2 h2Title = new H2(title);
        h2Title.addClassName("dialog-title");

        Button close = new Button(VaadinIcon.CLOSE_SMALL.create());
        close.addClickListener(event -> close());

        header = new Header(h2Title, close);
        header.getElement().getThemeList().add(Lumo.DARK);
        add(header);

        content.addClassName("dialog-content");
        content.setAlignItems(FlexComponent.Alignment.STRETCH);
        add(content);
    }
}
