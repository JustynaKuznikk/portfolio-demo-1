package projects.portfoliodemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.portfoliodemo.web.command.RegisterUserCommand;

@Service @Transactional
@Slf4j @RequiredArgsConstructor
public class UserService {

    public Long create(RegisterUserCommand registerUserCommand) {
        return null;
    }
}
