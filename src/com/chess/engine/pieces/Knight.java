package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import static com.chess.engine.board.Move.*;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece {
    private final static int[] possibleMoveCoordinates = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset : possibleMoveCoordinates){
            final int possibleDestinationCoordinate = this.piecePosition + currentCoordinateOffset;

            if(BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)){
                if(isFirstColumnExclusion(this.piecePosition, currentCoordinateOffset) || 
                   isSecondColumnExclusion(this.piecePosition, currentCoordinateOffset) || 
                   isSeventhColumnExclusion(this.piecePosition, currentCoordinateOffset) || 
                   isEighthColumnExclusion(this.piecePosition, currentCoordinateOffset)){
                    continue;
                }
                final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

                if(!possibleDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, possibleDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = possibleDestinationTile.getPiece();
                    final Color pieceColor = pieceAtDestination.pieceColor();
                    if(this.pieceColor != pieceColor){
                        legalMoves.add(new AttackMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
    }
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == 10 || candidateOffset == -6);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 17 || candidateOffset == 10 || candidateOffset == -6 || candidateOffset == -15);
    }
}
