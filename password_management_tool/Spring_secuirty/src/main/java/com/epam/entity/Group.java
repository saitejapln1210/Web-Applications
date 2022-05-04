package com.epam.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "T_Group")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_name")
    private String name;
    @OneToMany(mappedBy= "group",cascade=CascadeType.REMOVE
            ,fetch = FetchType.EAGER,orphanRemoval = true)
    @JsonManagedReference
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name="user_FK")
    @JsonBackReference
    private User user;

    public User getUser() {
        return user;
    }

    public Group(String name) {
        this.name = name;
    }

}
