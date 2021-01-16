package projects.portfoliodemo.converter;

import org.springframework.stereotype.Component;
import projects.portfoliodemo.data.user.UserSummary;
import projects.portfoliodemo.domain.model.User;
import projects.portfoliodemo.web.command.EditUserCommand;
import projects.portfoliodemo.web.command.RegisterUserCommand;

import static projects.portfoliodemo.converter.ConverterUtils.requiredNotNull;

@Component
public class UserConverter {

    public User from(RegisterUserCommand registerUserCommand) {
        requiredNotNull(registerUserCommand);
        return User.builder()
                .username(registerUserCommand.getUsername())
                .password(registerUserCommand.getPassword())
                .build();
    }

    public User from(EditUserCommand editUserCommand, User user) {
        return null;
    }

    public UserSummary toUserSummary(User user) {
        return null;
    }
}
