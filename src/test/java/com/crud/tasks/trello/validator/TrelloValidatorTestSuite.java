package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloValidatorTestSuite {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void validateTrelloBoards() {
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard board1 = new TrelloBoard("12", "test", new ArrayList<>());
        TrelloBoard board2 = new TrelloBoard("13", "Rest", new ArrayList<>());
        TrelloBoard board3 = new TrelloBoard("14", "Test", new ArrayList<>());
        Collections.addAll(trelloBoardList, board1, board2, board3);
        //When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoardList);
        //Then
        assertEquals(1, filteredBoards.size());
        assertEquals(board2.getName(), filteredBoards.get(0).getName());
    }
}