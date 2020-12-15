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

public class Queen extends Piece {
    private final static int[] possibleMoveVectorCoordinates = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(final int piecePosition, final Color pieceColor) {
        super(PieceType.QUEEN, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: possibleMoveVectorCoordinates){
            int possibleDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)) {
                if(isFirstColumnExclusion(this.piecePosition, currentCoordinateOffset) || 
                   isEighthColumnExclusion(this.piecePosition, currentCoordinateOffset)){
                    break;
                }
                possibleDestinationCoordinate += currentCoordinateOffset;
                if(BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)){
                    final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

                    if(!possibleDestinationTile.isTileOccupied()){
                        legalMoves.add(new MajorMove(board, this, possibleDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = possibleDestinationTile.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();
                        if(this.pieceColor != pieceColor){
                            legalMoves.add(new AttackMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString(){
        return Piece.PieceType.QUEEN.toString();
    }
    
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
