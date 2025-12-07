package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void testMapToBoard() {
        //Given
        TrelloBoardDto trelloBoardDto  = new TrelloBoardDto("1", "B1", new ArrayList<>());
        //When
        TrelloBoard mappedTrelloBoard = trelloMapper.mapToBoard(trelloBoardDto);
        //Then
        assertEquals("B1", mappedTrelloBoard.getName());
    }

    @Test
    void testMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "B1", new ArrayList<>());
        trelloBoardsDto.add(trelloBoardDto);
        //When
        List<TrelloBoard> mappedTrelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);
        //Then
        assertEquals(1, mappedTrelloBoards.size());
        assertEquals("B1", mappedTrelloBoards.get(0).getName());
    }

    @Test
    void testMapToLists() {
        //Given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("1", "L1", false);
        trelloListsDto.add(trelloListDto);
        //When
        List<TrelloList> mappedTrelloLists = trelloMapper.mapToLists(trelloListsDto);
        //Then
        assertEquals(1, mappedTrelloLists.size());
        assertEquals("L1", mappedTrelloLists.get(0).getName());
    }

    @Test
    void testMapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1", "B1", new ArrayList<>());
        trelloBoards.add(trelloBoard);
        //When
        List<TrelloBoardDto> mappedTrelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(1, mappedTrelloBoardDtoList.size());
        assertEquals("B1", mappedTrelloBoardDtoList.get(0).getName());
    }

    @Test
    void testMapToListsDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        TrelloList l1 = new TrelloList("1", "L1", false);
        TrelloList l2 = new TrelloList("2", "L2", true);
        Collections.addAll(trelloLists, l1, l2);
        //When
        List<TrelloListDto> mappedTrelloListDtoList = trelloMapper.mapToListsDto(trelloLists);
        //Then
        assertEquals(2, mappedTrelloListDtoList.size());
        assertEquals("L2", mappedTrelloListDtoList.get(1).getName());
        assertEquals(true, mappedTrelloListDtoList.get(1).isClosed());
    }

    @Test
    void testMapToCardDto() {
        //Given
        TrelloCard card = new TrelloCard("n1", "d1", "p1", "idList1");
        //When
        TrelloCardDto mappedCardDto =  trelloMapper.mapToCardDto(card);
        //Then
        assertEquals("n1", mappedCardDto.getName());
    }

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("n1", "d1", "p1", "idList1");
        //When
        TrelloCard mappedCard = trelloMapper.mapToCard(cardDto);
        //Then
        assertEquals("n1", mappedCard.getName());
        assertEquals("idList1", mappedCard.getIdList());
    }
}
