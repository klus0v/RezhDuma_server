package com.example.rezh.entities;

import com.example.rezh.entities.votes.Vote;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String phone;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private String firstName;

    private String lastName;

    private String patronymic;

    private Boolean enable = false;

    private Long tgId;



    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Appeal> appeals = new ArrayList<>();

    public void addAppeal(Appeal appeal) {
        appeals.add(appeal);
        appeal.setUser(this);
    }

    public void removeAppeal(Appeal appeal) {
        appeals.remove(appeal);
        appeal.setUser(null);
    }

    @ManyToMany
    @JoinTable(
            name = "users_votes ",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id"))
    private List<Vote> votes = new ArrayList<>();
}
