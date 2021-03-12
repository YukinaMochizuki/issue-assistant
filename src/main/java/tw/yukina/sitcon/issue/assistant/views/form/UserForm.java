package tw.yukina.sitcon.issue.assistant.views.form;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import lombok.Setter;
import tw.yukina.sitcon.issue.assistant.constants.Role;
import tw.yukina.sitcon.issue.assistant.entity.account.User;
import tw.yukina.sitcon.issue.assistant.repository.UserRepository;
import tw.yukina.sitcon.issue.assistant.views.dialog.AbstractDialog;

public class UserForm extends VerticalLayout {

    private Dialog dialog;
    private Grid<User> grid;

    private Binder<User> binder = new Binder<>(User.class);
    private FormLayout formLayout = new FormLayout();

    private User user;
    private UserRepository userRepository;

    public UserForm(User user, UserRepository userRepository){
        this.user = user;
        this.userRepository = userRepository;

        buildForm();
        binder.readBean(user);
    }

    public UserForm(UserRepository userRepository){
        this(new User(), userRepository);
    }

    private void buildForm(){
        TextField username = new TextField();
        username.setLabel("Username");

        IntegerField tgId = new IntegerField();
        tgId.setLabel("Telegram UserId");

        Select<Role> roleSelect = new Select<>();
        roleSelect.setItems(Role.MEMBER, Role.ADMIN);
        roleSelect.setValue(Role.MEMBER);

        formLayout.addFormItem(username, "Username");
        formLayout.addFormItem(tgId, "Telegram UserId");
        formLayout.addFormItem(roleSelect, "Role");

        //binder
        binder.forField(username).asRequired().bind(User::getName, User::setName);
        binder.forField(tgId).asRequired().bind(User::getTelegramUserId, User::setTelegramUserId);
        binder.forField(roleSelect).asRequired().withValidator(new Validator<Role>() {
            @Override
            public ValidationResult apply(Role value, ValueContext context) {
                if(value.equals(Role.GROUP))return ValidationResult.error("User can not have group role");
                return ValidationResult.ok();
            }
        }).bind(User::getRole, User::setRole);

        HorizontalLayout actions = new HorizontalLayout();
        Button save = new Button("Save");
        actions.add(save);
        save.getStyle().set("marginRight", "10px");

        save.addClickListener(event -> {
            try {
                binder.writeBean(user);
                userRepository.save(user);

                if(dialog != null)dialog.close();
                if(grid != null){
                    grid.setItems(userRepository.findAll());
                    grid.getDataProvider().refreshAll();
                }
            } catch (ValidationException e) {
                e.printStackTrace();
                new AbstractDialog("Warning", new VerticalLayout(new Text(e.getMessage())));
            }
        });

        add(formLayout, actions);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void setGrid(Grid<User> grid) {
        this.grid = grid;
    }
}
