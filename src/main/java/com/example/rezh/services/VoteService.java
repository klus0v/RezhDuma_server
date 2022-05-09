package com.example.rezh.services;


import com.example.rezh.entities.File;
import com.example.rezh.entities.User;
import com.example.rezh.entities.votes.Answer;
import com.example.rezh.entities.votes.Question;
import com.example.rezh.entities.votes.Vote;
import com.example.rezh.repositories.AnswerRepository;
import com.example.rezh.repositories.UserRepository;
import com.example.rezh.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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

    public List<Vote> getBallots(Boolean survey) {
        return voteRepository.findAllBySurvey(survey);
    }

    public Vote getBallot(Long id) {
        return voteRepository.getById(id);
    }

    public void deleteBallot(Long id) {
        voteRepository.deleteById(id);
    }

    public void postBallot(Vote ballot) {
        Vote vote = new Vote();
        vote.setTopic(ballot.getTopic());
        vote.setSurvey(ballot.getSurvey());
        if (ballot.getQuestions() != null)
            addQuestions(ballot.getQuestions(), vote);
        voteRepository.save(vote);
    }




    private void addQuestions(List<Question> questions, Vote vote) {
        for (Question question : questions) {
            if (question != null) {

                Question voteQuestion = new Question();
                voteQuestion.setQuestion(question.getQuestion());

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
    public void putVoice(Long id, Long ballotId, Long[] answers) {


        User user = userRepository.getById(id);
        Vote voting = voteRepository.getById(ballotId);

        if (user.getVotes().contains(voting))
            return;

        user.getVotes().add(voting);
        for (Long answer : answers) {
            Answer currentAnswer = answerRepository.getById(answer);
            Integer count = currentAnswer.getCount() + 1;
            currentAnswer.setCount(count);
            answerRepository.save(currentAnswer);
        }

    }
}
