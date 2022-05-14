package com.example.rezh.rest;


import com.example.rezh.entities.votes.Vote;
import com.example.rezh.models.VoteModel;
import com.example.rezh.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
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
    public ResponseEntity getAllVotes(@RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer count) {
        try {
            var votes = voteService.getBallots(false);

            if (page != null && count != null)
                return ResponseEntity.ok(VoteModel.toModel(voteService.doPagination(votes, page, count)));

            return ResponseEntity.ok(VoteModel.toModel(votes));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/surveys")
    public ResponseEntity getSurveys(@RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer count) {
        try {
            var votes = voteService.getBallots(true);

            if (page != null && count != null)
                return ResponseEntity.ok(VoteModel.toModel(voteService.doPagination(votes, page, count)));

            return ResponseEntity.ok(VoteModel.toModel(votes));
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