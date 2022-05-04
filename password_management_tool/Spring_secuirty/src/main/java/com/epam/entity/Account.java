package com.epam.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "T_Account")
@Setter
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="url")
    private String url;

    @ManyToOne
    @JoinColumn(name="group_FK")
    @JsonBackReference
    private Group group;

    public Account(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }
}
