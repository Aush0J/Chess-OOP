package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece {
    private final static int[] possibleMoveCoordinates = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        int possibleDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinate : possibleMoveCoordinates){
            possibleDestinationCoordinate = this.piecePosition + currentCoordinate;

            if(true){
                final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

                if(!possibleDestinationTile.isTileOccupied()){
                    legalMoves.add(new Move());
                } else {
                    final Piece pieceAtDestination = possibleDestinationTile.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();
                    if(this.pieceColor != pieceColor){
                        legalMoves.add(new Move());
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
