package com.example.rezh.services;


import com.example.rezh.entities.*;
import com.example.rezh.entities.votes.Vote;
import com.example.rezh.models.*;
import com.example.rezh.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Object makeFullSearch(String find, Boolean news, Boolean projects, Boolean documents, Boolean history, Boolean appeals, Boolean ballots, Integer page, Integer count) {

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
            allElements.add(NewsModel.toModel(doNewsPagination(newsRepository.findNews(searchWord), page, count)));
        if (projects || searchAll)
            allElements.add(ProjectModel.toModel(doProjectsPagination(projectRepository.findProjects(searchWord), page, count)));
        if (documents || searchAll)
            allElements.add(DocumentModel.toModel(doDocumentsPagination(documentRepository.findDocuments(searchWord), page, count)));
        if (history || searchAll)
            allElements.add(HistoryModel.toModel(doHistoryPagination(historyRepository.findHistory(searchWord), page, count)));
        if (appeals || searchAll)
            allElements.add(AppealModel.toModel(doAppealsPagination(appealRepository.findTopAppeals(searchWord, searchWord, "%", "%", "%"), page, count), true));
        if (ballots || searchAll)
            allElements.add(VoteModel.toModel(doVotesPagination(voteRepository.findVotes(searchWord), page, count)));

        return allElements;
    }

    private List<Vote> doVotesPagination(List<Vote> votes, Integer page, Integer count) {
        if (page == null || count == null)
            return votes;
        List<Vote> currentVote = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > votes.size() - 1)
                break;
            currentVote.add(votes.get(i));
        }
        return currentVote;
    }

    private List<Appeal> doAppealsPagination(List<Appeal> appeals, Integer page, Integer count) {
        if (page == null || count == null)
            return appeals;
        List<Appeal> currentAppeals = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > appeals.size() - 1)
                break;
            currentAppeals.add(appeals.get(i));
        }
        return currentAppeals;
    }

    private List<History> doHistoryPagination(List<History> history, Integer page, Integer count) {
        if (page == null || count == null)
            return history;
        List<History> currentHistory = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > history.size() - 1)
                break;
            currentHistory.add(history.get(i));
        }
        return currentHistory;
    }

    private List<Document> doDocumentsPagination(List<Document> documents, Integer page, Integer count) {
        if (page == null || count == null)
            return documents;
        List<Document> currentDocuments = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > documents.size() - 1)
                break;
            currentDocuments.add(documents.get(i));
        }
        return currentDocuments;
    }

    private List<Project> doProjectsPagination(List<Project> projects, Integer page, Integer count) {
        if (page == null || count == null)
            return projects;
        List<Project> currentProjects = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > projects.size() - 1)
                break;
            currentProjects.add(projects.get(i));
        }
        return currentProjects;
    }

    private List<News> doNewsPagination(List<News> news, Integer page, Integer count) {
        if (page == null || count == null)
            return news;
        List<News> currentNews = new ArrayList<>();
        for (int i = (page - 1) * count; i < page * count; i++) {
            if (i > news.size() - 1)
                break;
            currentNews.add(news.get(i));
        }
        return currentNews;
    }
}
