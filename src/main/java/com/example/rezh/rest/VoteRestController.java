package com.example.rezh.rest;


import com.example.rezh.entities.votes.Vote;
import com.example.rezh.models.VoteModel;
import com.example.rezh.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/ballots")
public class VoteRestController {

    private final VoteService voteService;

    //all
    @GetMapping("{id}")
    public ResponseEntity getOneBallot(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(VoteModel.toModel(voteService.getBallot(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/votes")
    public ResponseEntity getAllVotes() {
        try {
            return ResponseEntity.ok().body(VoteModel.toModel(voteService.getBallots(false)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/surveys")
    public ResponseEntity getSurveys() {
        try {
            return ResponseEntity.ok().body(VoteModel.toModel(voteService.getBallots(true)));
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
    public ResponseEntity createNewBallot(@RequestBody Vote ballot) {
        try {
            voteService.postBallot(ballot);
            return ResponseEntity.ok("Успешно создано");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    //users
    @PatchMapping(value = "/user/{id}", params = {"ballot"})
    public ResponseEntity putVoice(@PathVariable Long id,
                                   @RequestParam Long ballot,
                                   @RequestBody Long[] answers) {
        try {
            voteService.putVoice(id, ballot, answers);
            return ResponseEntity.ok("Ответ сохранен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}