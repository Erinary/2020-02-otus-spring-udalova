package ru.otus.erinary.hw04.quiz.service.user;

import org.springframework.stereotype.Service;
import ru.otus.erinary.hw04.quiz.model.User;

/**
 * Реализация {@link UserService}
 */
@Service
public class UserServiceImpl implements UserService {

    private User currentUser;

    @Override
    public void createCurrentUser(final String name, final String surname) {
        currentUser = new User(name, surname);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean isUserCreated() {
        return currentUser != null;
    }
}
