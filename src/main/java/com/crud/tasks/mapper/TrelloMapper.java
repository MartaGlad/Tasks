package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloMapper {

    public TrelloBoard mapToBoard(final TrelloBoardDto trelloBoardDto) {
        return new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToLists(trelloBoardDto.getLists()));
    }

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDtos) {
        return trelloBoardDtos.stream()
                .map(trelloBoardDto ->
                        new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToLists(trelloBoardDto.getLists())))
                .collect(toList());
    }

    public List<TrelloList> mapToLists(final List<TrelloListDto> trelloListDtos) {
        return trelloListDtos.stream()
                .map(trelloListDto
                        -> new TrelloList(trelloListDto.getId(), trelloListDto.getName(), trelloListDto.isClosed()))
                .collect(toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream()
                .map(trelloBoard
                        -> new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(), mapToListsDto(trelloBoard.getLists())))
                .collect(toList());
    }

    public List<TrelloListDto> mapToListsDto(final List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(trelloList
                        -> new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(toList());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDesc(), trelloCard.getPos(), trelloCard.getIdList());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDesc(), trelloCardDto.getPos(), trelloCardDto.getIdList());
    }
}
