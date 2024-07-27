package com.qnenet.quickneasy.security;


import com.qnenet.qneobjects.classes.QLoginUser;
import com.qnenet.quickneasy.security.users.QUsersManager;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final QUsersManager usersManager;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext, QUsersManager usersManager) {
        this.usersManager = usersManager;
        this.authenticationContext = authenticationContext;
    }

//    @Transactional
    public Optional<QLoginUser> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> usersManager.findByUsername(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }

}
