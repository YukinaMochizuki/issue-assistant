package tw.yukina.sitcon.issue.assistant.views.grid;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.entity.account.Group;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.GroupRepository;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;

@Component
public class GroupGrid  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public Grid<Group> getGrid(){

        Grid<Group> grid = new Grid<>();
        grid.setItems(groupRepository.findAll());

        grid.addColumn(Group::getName).setHeader("Group name");
        grid.addColumn(Group::getChatId).setHeader("Chat id");

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