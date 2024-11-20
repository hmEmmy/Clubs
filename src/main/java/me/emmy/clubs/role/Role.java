package me.emmy.clubs.role;

import lombok.Getter;

/**
 * @author hieu
 * @since 22/10/2023
 */
@Getter
public enum Role {
    LEADER("Leader"),
    ADMIN("Admin"),
    MEMBER("Member");

    private final String name;

    /**
     * Constructor for the Role class.
     *
     * @param name The name of the role
     */
    Role(String name){
        this.name = name;
    }
}