package com.example.rezh.models;


import com.example.rezh.entities.votes.Answer;
import com.example.rezh.entities.votes.Question;
import com.example.rezh.entities.votes.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteModel {

    private Long id;
    private String topic;
    private LocalDateTime votingDate;
    private Boolean canVote;
    private List<QuestionModel> questions;

    @Data
    private static class AnswerModel {
        private Long id;
        private String answer;
        private Integer count;
    }

    @Data
    private static class QuestionModel {
        private Long id;
        private String question;
        private List<AnswerModel> answers;
    }

    public static VoteModel toModel(Vote voteEntity) {
        VoteModel vote = new VoteModel();

        vote.setId(voteEntity.getId());
        vote.setTopic(voteEntity.getTopic());
        vote.setVotingDate(voteEntity.getVoteDate());

        vote.setQuestions(new ArrayList<QuestionModel>());

        var questions = voteEntity.getQuestions();

        for (Question question : questions) {
            QuestionModel questionModel = new QuestionModel();
            List<AnswerModel> answerModels = new ArrayList<>();
            for (Answer answer: question.getAnswers()) {
                AnswerModel answerModel = new AnswerModel();
                answerModel.setId(answer.getId());
                answerModel.setAnswer(answer.getAnswer());
                answerModel.setCount(answer.getCount());
                answerModels.add(answerModel);
            }
            questionModel.setId(question.getId());
            questionModel.setQuestion(question.getQuestion());
            questionModel.setAnswers(answerModels);

            vote.questions.add(questionModel);
        }

        return vote;
    }

    public static List<VoteModel> toModel(List<Vote> voteEntities) {
        List<VoteModel> voteModels = new ArrayList<>();
        for (Vote voteEntity: voteEntities ) {
            voteModels.add(toModel(voteEntity));
        }
        return voteModels;
    }
}
