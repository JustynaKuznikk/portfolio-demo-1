package projects.portfoliodemo.provider;

import projects.portfoliodemo.web.command.RegisterUserCommand;

public class CommandProvider {

    public static RegisterUserCommand registerUserCommand(String username, String password) {
        RegisterUserCommand command = new RegisterUserCommand();
        command.setUsername(username);
        command.setPassword(password);
        return command;
    }
}
