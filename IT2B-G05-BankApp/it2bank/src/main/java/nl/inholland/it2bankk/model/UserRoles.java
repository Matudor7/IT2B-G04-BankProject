package nl.inholland.it2bankk.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
    Customer, Employee, User;

    @Override
    public String getAuthority() {
        return name();
    }
}
