package com.example.rezh.rest;


import com.example.rezh.entities.votes.Vote;
import com.example.rezh.models.VoteModel;
import com.example.rezh.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.services.elasticbeanstalk.model.ConfigurationOptionValueType.List;


@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteRestController {

    private final VoteService voteService;

    //all
    @GetMapping("{id}")
    public ResponseEntity getOneVote(@PathVariable Long id,
                                       @RequestParam(required = false) Long user) {
        try {
            var voteModel = VoteModel.toModel(voteService.getBallot(id));

            voteService.checkUser(voteModel, user);

            return ResponseEntity.ok().body(voteModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("")
    public ResponseEntity getAllVotes(@RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer count,
                                      @RequestParam(required = false) String find,
                                      @RequestParam(required = false) Long user) {
        try {
            var votes = voteService.getVotes(find);
            List<VoteModel> votesModels;

            if (page != null && count != null)
                votesModels =  VoteModel.toModel(voteService.doPagination(votes, page, count));
            else
                votesModels  = VoteModel.toModel(votes);

            voteService.checkUser(votesModels, user);

            return ResponseEntity.ok().body(votesModels);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }


    //admin
    @DeleteMapping("/admin/{id}")
    public ResponseEntity deleteBallot(@PathVariable Long id) {
        try {
            voteService.deleteBallot(id);
            return ResponseEntity.ok("Успешно удалено");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/admin")
    public ResponseEntity createNewBallot(@RequestBody Vote vote) {
        try {
            voteService.postVote(vote);
            return ResponseEntity.ok("Успешно создано");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    //users
    @PatchMapping(value = "/user/{id}", params = {"vote"})
    public ResponseEntity putVoice(@PathVariable Long id,
                                   @RequestParam Long vote,
                                   @RequestBody Long[] answers) {
        try {
            voteService.putVoice(id, vote, answers);
            return ResponseEntity.ok("Ответ сохранен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}