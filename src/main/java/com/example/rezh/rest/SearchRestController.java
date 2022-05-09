package com.example.rezh.rest;


import com.example.rezh.services.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/search")
public class SearchRestController {

    private final SearchService searchService;

    @GetMapping()
    public ResponseEntity makeFullSearch(@RequestParam(defaultValue = "") String find,
                                   @RequestParam(required = false) Boolean news,
                                   @RequestParam(required = false) Boolean projects,
                                   @RequestParam(required = false) Boolean documents,
                                   @RequestParam(required = false) Boolean history,
                                   @RequestParam(required = false) Boolean appeals,
                                   @RequestParam(required = false) Boolean ballots) {
        try {
          return ResponseEntity.ok().body(searchService.makeFullSearch(find, news, projects, documents, history, appeals, ballots));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
