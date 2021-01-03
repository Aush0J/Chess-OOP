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

public class Rook extends Piece {
    private final static int[] possibleMoveVectorCoordinates = {-8, -1, 1 ,8};

    public Rook(final int piecePosition, final Color pieceColor) {
        super(PieceType.ROOK, piecePosition, pieceColor, true);
    }
    public Rook(final int piecePosition, final Color pieceColor, final boolean isFirstMove){
        super(PieceType.ROOK, piecePosition, pieceColor, isFirstMove);
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
                            legalMoves.add(new MajorAttackMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString(){
        return Piece.PieceType.ROOK.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && candidateOffset == -1;
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && candidateOffset == 1;
    }
}
