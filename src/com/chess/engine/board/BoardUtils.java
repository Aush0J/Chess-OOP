package com.chess.engine.board;

import java.util.*;

import com.google.common.collect.ImmutableMap;

public class BoardUtils {
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;
    public static final int START_TILE_INDEX = 0;

    public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    
    public static final boolean[] EIGHTH_RANK = initRow(0); //first row
    public static final boolean[] SEVENTH_RANK = initRow(8); //second row
    public static final boolean[] SIXTH_RANK = initRow(16); //third row
    public static final boolean[] FIFTH_RANK = initRow(24); //fourth row
    public static final boolean[] FOURTH_RANK = initRow(32); //fifth row
    public static final boolean[] THIRD_RANK = initRow(40); //sixth row
    public static final boolean[] SECOND_RANK = initRow(48); //seventh row
    public static final boolean[] FIRST_RANK = initRow(56); //eighth row

    public static final String[] ALGEBREIC_NOTATION = initAlgebreicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initPositionToCoordinateMap();

    private BoardUtils() {
        throw new RuntimeException("YEET!");
    }

    private static Map<String, Integer> initPositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = START_TILE_INDEX; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBREIC_NOTATION[i], i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

    private static String[] initAlgebreicNotation() {
        return new String[] {
            "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
            "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
            "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
            "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
            "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
            "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
            "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
            "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        };
    }

    private static boolean[] initRow(int rowNum) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNum] = true;
            rowNum++;
        } while (rowNum % NUM_TILES_PER_ROW != 0);
        return row;
    }

    private static boolean[] initColumn(int colNum) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[colNum] = true;
            colNum += NUM_TILES_PER_ROW;
        } while (colNum < NUM_TILES);
        return column;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

	public static String getPositionAtCoordinate(final int coordinate) {
		return ALGEBREIC_NOTATION[coordinate];
	}
    
}
