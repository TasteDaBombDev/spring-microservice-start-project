package com.postme.coreAuthorisation.models;

import com.postme.coreAuthorisation.models.enums.UserRolesEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRolesEnum name;

    public Role() {

    }

    public Role(UserRolesEnum name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRolesEnum getName() {
        return name;
    }

    public void setName(UserRolesEnum name) {
        this.name = name;
    }
}
