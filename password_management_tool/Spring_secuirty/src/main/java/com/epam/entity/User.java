package com.epam.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="T_User")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="masterusername",nullable = false,unique = true)
    private String masterUserName;

    @Column(name="masterpassword")
    private String masterPassword;

    public User(String masterUserName, String masterPassword) {
        this.masterUserName = masterUserName;
        this.masterPassword = masterPassword;
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    List<Group> groups;
}
