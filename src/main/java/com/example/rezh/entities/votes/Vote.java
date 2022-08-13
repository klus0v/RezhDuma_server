package com.example.rezh.entities.votes;


import com.example.rezh.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @DateTimeFormat()
    private LocalDateTime voteDate = LocalDateTime.now();

    @DateTimeFormat()
    private LocalDateTime expirationDate;

    public Vote(String topic) {
        this.topic = topic;
    }

    @OneToMany(
            mappedBy = "vote",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
        question.setVote(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setVote(null);
    }

    @ManyToMany(mappedBy = "votes")
    private List<User> users = new ArrayList<>();

    private Boolean isActive = true;
}
