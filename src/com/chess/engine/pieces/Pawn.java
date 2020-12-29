package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import static com.chess.engine.board.Move.*;
//import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece {
    private final static int[] possibleMoveCoordinates = {7, 8, 9, 16};

    public Pawn(final int piecePosition, final Color pieceColor) {
        super(PieceType.PAWN, piecePosition, pieceColor, true);
    }
    public Pawn(final int piecePosition, final Color pieceColor, final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceColor, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset : possibleMoveCoordinates){
            final int possibleDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * currentCoordinateOffset);

            if(!BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)){
                continue;
            }

            if(currentCoordinateOffset == 8 && !board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                legalMoves.add(new MajorMove(board, this, possibleDestinationCoordinate));
            } else if(currentCoordinateOffset == 16 && this.isFirstMove() && 
                     (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.pieceColor.isBlack()) || 
                     (BoardUtils.SECOND_RANK[this.piecePosition] && this.pieceColor.isWhite())){
                final int behindPossibleDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * 8);
                if(!board.getTile(behindPossibleDestinationCoordinate).isTileOccupied() && !board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new PawnJump(board, this, possibleDestinationCoordinate));    
                }
            } else if(currentCoordinateOffset == 7 && 
                      !(isFirstColumnExclusion(this.piecePosition, currentCoordinateOffset, this.pieceColor) || 
                      isEighthColumnExclusion(this.piecePosition, currentCoordinateOffset, this.pieceColor))){
                if(board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(possibleDestinationCoordinate).getPiece();
                    if(this.pieceColor != pieceOnCandidate.getPieceColor()){
                        legalMoves.add(new PawnAttackMove(board, this, possibleDestinationCoordinate, pieceOnCandidate));
                    }
                }
            } else if(currentCoordinateOffset == 9 && 
                      !(isFirstColumnExclusion(this.piecePosition, currentCoordinateOffset, this.pieceColor) || 
                      isEighthColumnExclusion(this.piecePosition, currentCoordinateOffset, this.pieceColor))){
                if(board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(possibleDestinationCoordinate).getPiece();
                    if(this.pieceColor != pieceOnCandidate.getPieceColor()){
                        legalMoves.add(new PawnAttackMove(board, this, possibleDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString(){
        return Piece.PieceType.PAWN.toString();
    }
    
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset, final Color pieceColor) {
        return (candidateOffset == 7 && (BoardUtils.FIRST_COLUMN[currentPosition] && pieceColor.isBlack())) ||
               (candidateOffset == 9 && (BoardUtils.FIRST_COLUMN[currentPosition] && pieceColor.isWhite()));
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset, final Color pieceColor){
        return (candidateOffset == 7 && (BoardUtils.EIGHTH_COLUMN[currentPosition] && pieceColor.isWhite())) ||
               (candidateOffset == 9 && (BoardUtils.EIGHTH_COLUMN[currentPosition] && pieceColor.isBlack()));

    }
}
 