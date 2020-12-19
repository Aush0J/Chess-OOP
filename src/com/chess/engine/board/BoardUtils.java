package com.chess.engine.board;

public class BoardUtils {
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

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

	private BoardUtils(){
        throw new RuntimeException("YEET!");
    }

	private static boolean[] initRow(int rowNum) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNum] = true;
            rowNum++;
        } while(rowNum % NUM_TILES_PER_ROW != 0);
        return row;
    }

    private static boolean[] initColumn(int colNum) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[colNum] = true;
            colNum += NUM_TILES_PER_ROW;
        } while(colNum < NUM_TILES);
        return column;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
	    return coordinate >=0 && coordinate < NUM_TILES;
    }
    
}
