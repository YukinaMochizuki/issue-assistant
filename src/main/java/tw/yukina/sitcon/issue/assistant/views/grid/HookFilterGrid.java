package tw.yukina.sitcon.issue.assistant.views.grid;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.entity.GitLabWebhookFilter;
import tw.yukina.sitcon.issue.assistant.entity.RecentNotification;
import tw.yukina.sitcon.issue.assistant.repository.HookFilterRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

import java.util.List;

@Component
public class HookFilterGrid {

    @Autowired
    private HookFilterRepository hookFilterRepository;

    @Autowired
    private UserRepository userRepository;

    public Grid<GitLabWebhookFilter> getGrid(){

        Grid<GitLabWebhookFilter> grid = new Grid<>();
        grid.setItems(hookFilterRepository.findAll());

        grid.addColumn(GitLabWebhookFilter::getName).setHeader("Name");
        grid.addColumn(GitLabWebhookFilter::getAction).setHeader("Action");
//        grid.addColumn(GitLabWebhookFilter::getLabel).setHeader("Label");

        grid.addItemClickListener(event -> {
            openEditor(event.getItem().getName());
        });

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        return grid;
    }

    public void openEditor(String name) {
        Dialog dialog = new Dialog();
        dialog.add(new Text(name));

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        dialog.open();
    }
}
