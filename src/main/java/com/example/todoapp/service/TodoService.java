package com.example.todoapp.service;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import com.example.todoapp.mapper.BoardMapper;
import com.example.todoapp.mapper.CardMapper;
import com.example.todoapp.mapper.TileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private TileMapper tileMapper;
    @Autowired
    private CardMapper cardMapper;

    public List<Board> findAllBoard(String user_uid) {
        return boardMapper.findAllByUserId(user_uid);
    }

    public Board create(Board board) {
        boardMapper.create(board);
        Integer id = boardMapper.getLastInsertId();
        board.setBoard_id(id);
        return board;
    }

    public Tile create(Tile tile) {
        // indicatorの設定
        // tile_idが一致するカードのMAX(indicator)を取得
        Integer maxIndicator = cardMapper.getMaxIndicator(tile.getBoard_id());
        if (Objects.isNull(maxIndicator)) {
            maxIndicator = 0;
        } else {
            maxIndicator += 1;
        }
        tile.setIndicator(maxIndicator);

        // DB更新
        tileMapper.create(tile);
        Integer id = tileMapper.getLastInsertId();
        tile.setTile_id(id);
        return tile;
    }

    public Card create(Card card) {
        // indicatorの設定
        // tile_idが一致するカードのMAX(indicator)を取得
        Integer maxIndicator = cardMapper.getMaxIndicator(card.getTile_id());
        if (Objects.isNull(maxIndicator)) {
            maxIndicator = 0;
        } else {
            maxIndicator += 1;
        }
        card.setIndicator(maxIndicator);

        // DB更新
        cardMapper.create(card);
        Integer id = cardMapper.getLastInsertId();
        card.setCard_id(id);
        return card;
    }

    public Board update(Board board) {
        boardMapper.update(board);
        return board;
    }

    public Tile update(Tile tile) {
        tileMapper.update(tile);
        return tile;
    }

    public Card update(Card card) {
        cardMapper.update(card);
        return card;
    }

    public void update(List<Tile> tiles) {
        // update tiles
        tiles.stream()
                .forEach(tile -> tileMapper.update(tile));

        // update cards
        tiles.stream()
                .flatMap(tile -> tile.getCards().stream())
                .forEach(card -> cardMapper.update(card));
    }

    public void deleteBoard(Integer boardId) {
        // delete board
        boardMapper.delete(boardId);

        // delete tiles
        List<Integer> tileIdList = tileMapper.getTilesByBoardId(boardId)
                .stream()
                .map(Tile::getTile_id)
                .collect(Collectors.toList());

        tileIdList.forEach(tileId -> tileMapper.delete(tileId));

        // delete cards
        tileIdList
                .stream()
                .flatMap(tileId -> cardMapper.getCardsByTileId(tileId).stream())
                .map(Card::getCard_id)
                .forEach(cardId -> cardMapper.delete(cardId));
    }

    public void deleteTile(Integer tileId) {
        // delete tiles
        tileMapper.delete(tileId);

        // delete cards
        cardMapper.getCardsByTileId(tileId)
                .stream()
                .map(Card::getCard_id)
                .forEach(cardId -> cardMapper.delete(cardId));
    }

    public void deleteCard(Integer cardId) {
        cardMapper.delete(cardId);
    }

    public boolean containsBoardId(Integer board_id) {
        Board board = boardMapper.load(board_id);
        if (Objects.isNull(board)) {
            return false;
        }
        return true;
    }

    public boolean containsTileId(Integer tile_id) {
        Tile tile = tileMapper.load(tile_id);
        if (Objects.isNull(tile)) {
            return false;
        }
        return true;
    }

    public boolean containsCardId(Integer card_id) {
        Card card = cardMapper.load(card_id);
        if (Objects.isNull(card)) {
            return false;
        }
        return true;
    }

    public boolean hasDuplicateIndicator(Card card) {
        List<Integer> indicators = cardMapper.getCardsByTileId(card.getTile_id())
                .stream()
                .filter(card1 -> card1.getCard_id() != card.getCard_id()) // 自分以外のカードに絞り込み
                .map(Card::getIndicator)
                .collect(Collectors.toList());

        // 自分以外のカードとIndicatorが一致してしまう場合はindicatorの重複を通知
        if (indicators.contains(card.getIndicator())) {
            return true;
        }
        return false;
    }

    public boolean hasDuplicateIndicator(Tile tile) {
        List<Integer> indicators = tileMapper.getTilesByBoardId(tile.getBoard_id())
                .stream()
                .filter(tile1 -> tile1.getTile_id() != tile.getTile_id()) // 自分以外のタイルに絞り込み
                .map(Tile::getIndicator)
                .collect(Collectors.toList());

        // 自分以外のタイルとIndicatorが一致してしまう場合はindicatorの重複を通知
        if (indicators.contains(tile.getIndicator())) {
            return true;
        }
        return false;
    }
}
