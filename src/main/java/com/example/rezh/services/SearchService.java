package com.example.rezh.services;


import com.example.rezh.models.*;
import com.example.rezh.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final NewsRepository newsRepository;
    private final ProjectRepository projectRepository;
    private final DocumentRepository documentRepository;
    private final HistoryRepository historyRepository;
    private final AppealRepository appealRepository;
    private final VoteRepository voteRepository;

    public Object makeFullSearch(String find, Boolean news, Boolean projects, Boolean documents, Boolean history, Boolean appeals, Boolean ballots) {

        news = news != null;
        projects = projects != null;
        documents = documents != null;
        history = history != null;
        appeals = appeals != null;
        ballots = ballots != null;

        List<Object> allElements = new ArrayList<>();
        String searchWord = "%" + find + "%";
        boolean searchAll = !news && !projects && !documents && !history && !appeals && !ballots;

        if (news || searchAll)
            allElements.add(NewsModel.toModel(newsRepository.findAllByTitleContainingOrTextContaining(searchWord, searchWord)));
        if (projects || searchAll)
            allElements.add(ProjectModel.toModel(projectRepository.findAllByTitleContainingOrTextContaining(searchWord, searchWord)));
        if (documents || searchAll)
            allElements.add(DocumentModel.toModel(documentRepository.findAllByTitleContainingOrTextContaining(searchWord, searchWord)));
        if (history || searchAll)
            allElements.add(HistoryModel.toModel(historyRepository.findAllByTitleContainingOrTextContaining(searchWord, searchWord)));
        if (appeals || searchAll)
            allElements.add(AppealModel.toModel(appealRepository.findAllByTextContainingOrResponseContaining(searchWord, searchWord)));
        if (ballots || searchAll)
            allElements.add(VoteModel.toModel(voteRepository.findAllByTopicContaining(searchWord)));

        return allElements;
    }
}
