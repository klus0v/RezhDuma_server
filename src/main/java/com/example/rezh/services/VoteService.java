package com.example.rezh.services;


import com.example.rezh.entities.Appeal;
import com.example.rezh.entities.File;
import com.example.rezh.entities.User;
import com.example.rezh.entities.votes.Answer;
import com.example.rezh.entities.votes.Question;
import com.example.rezh.entities.votes.Vote;
import com.example.rezh.models.VoteModel;
import com.example.rezh.repositories.AnswerRepository;
import com.example.rezh.repositories.UserRepository;
import com.example.rezh.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;

    public List<Vote> getVotes(String find) {
        if (find != null)
            return voteRepository.findVotesWithWord("%" + find + "%");

        return voteRepository.findAll();
    }

    public List<Vote> doPagination(List<Vote> votes, Integer page, Integer count) {
        List<Vote> currentVote = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > votes.size() - 1)
                break;
            currentVote.add(votes.get(i));
        }
        return currentVote;
    }

    public Vote getBallot(Long id) {
        return voteRepository.getById(id);
    }

    @Transactional
    public void deleteBallot(Long id) {
        voteRepository.deleteById(id);
    }

    public void postVote(Vote ballot) {
        Vote vote = new Vote();
        vote.setTopic(ballot.getTopic());
        vote.setExpirationDate(ballot.getExpirationDate());
        if (ballot.getQuestions() != null)
            addQuestions(ballot.getQuestions(), vote);
        voteRepository.save(vote);
    }


    private void addQuestions(List<Question> questions, Vote vote) {
        for (Question question : questions) {
            if (question != null) {

                Question voteQuestion = new Question();
                voteQuestion.setQuestion(question.getQuestion());
                voteQuestion.setCheckbox(question.getCheckbox());

                if (question.getAnswers() != null)
                    addAnswers(question.getAnswers(), voteQuestion);

                voteQuestion.setVote(vote);
                vote.addQuestion(voteQuestion);
            }
        }
    }

    private void addAnswers(List<Answer> answers, Question question) {
        for (Answer answer : answers) {
            if (answer != null) {
                Answer questionAnswer = new Answer();

                questionAnswer.setAnswer(answer.getAnswer());

                questionAnswer.setQuestion(question);
                question.addAnswer(questionAnswer);
            }
        }
    }


    @Transactional
    public void putVoice(String token, Long voteId, Long[] answers) {

        var id = userService.GetUserId(token);

        User user = userRepository.getById(id);
        Vote voting = voteRepository.getById(voteId);
        if (user.getVotes().contains(voting))
            return;

        if (voting.getExpirationDate() != null && voting.getExpirationDate().compareTo(LocalDateTime.now()) <= 0)
            return;

        user.getVotes().add(voting);


        for (Long answer : answers) {
            Answer currentAnswer = answerRepository.getById(answer);
            Integer count = currentAnswer.getCount() + 1;
            currentAnswer.setCount(count);
            answerRepository.save(currentAnswer);
        }

    }

    public void checkUser(VoteModel vote, Long userID) {
        if (userID == null){
            vote.setCanVote(false);
            return;
        }
        User user = userRepository.getById(userID);
        Vote voting = voteRepository.getById(vote.getId());

        vote.setCanVote(!user.getVotes().contains(voting));
    }

    public void checkUser(List<VoteModel> votes, Long userID) {
        for (VoteModel vote : votes) {
            checkUser(vote, userID);
        }
    }
}
