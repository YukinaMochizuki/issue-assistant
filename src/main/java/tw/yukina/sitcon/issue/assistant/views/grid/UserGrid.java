package tw.yukina.sitcon.issue.assistant.views.grid;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;
import tw.yukina.sitcon.issue.assistant.views.dialog.AbstractDialog;
import tw.yukina.sitcon.issue.assistant.views.form.UserForm;

@Component
public class UserGrid {

    @Autowired
    private UserRepository userRepository;

    public Grid<User> getGrid(){

        Grid<User> grid = new Grid<>();
        grid.setItems(userRepository.findAll());

        grid.addColumn(User::getName).setHeader("Username");
        grid.addColumn(User::getDisplayName).setHeader("FullName");
        grid.addColumn(User::getTelegramUserName).setHeader("TG username");
        grid.addColumn(User::getRole).setHeader("Role");

        grid.addItemClickListener(event -> {
            openEditor(event.getItem().getName(), grid);
        });

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        return grid;
    }

    public void openEditor(String name, Grid<User> grid) {
        User user = userRepository.findByName(name);
        if(user == null)user = new User();
        UserForm userForm = new UserForm(user, userRepository);

        AbstractDialog dialog = new AbstractDialog("Edit user", userForm);
        dialog.setWidth("400px");
        userForm.setDialog(dialog);
        userForm.setGrid(grid);
        dialog.open();
    }
}
