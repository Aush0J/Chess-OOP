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

public class King extends Piece {
    private final static int[] possibleMoveCoordinates = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final int piecePosition, final Color pieceColor) {
        super(PieceType.KING, piecePosition, pieceColor, true);
    }
    public King(final int piecePosition, final Color pieceColor, final boolean isFirstMove) {
        super(PieceType.KING, piecePosition, pieceColor, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset : possibleMoveCoordinates){
            final int possibleDestinationCoordinate = this.piecePosition + currentCoordinateOffset;

            if(BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)){
                if(isFirstColumnExclusion(this.piecePosition, currentCoordinateOffset) || 
                   isEighthColumnExclusion(this.piecePosition, currentCoordinateOffset)){
                    continue;
                }

                final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);
                if(!possibleDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, possibleDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = possibleDestinationTile.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();
                    if(this.pieceColor != pieceColor){
                        legalMoves.add(new MajorAttackMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString(){
        return Piece.PieceType.KING.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9 );
    }
}
