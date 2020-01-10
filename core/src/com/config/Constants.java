package com.config;

/**
 * Game constants for use by Kroy.
 */
public final class Constants {

    private Constants() {
        // Any constants that need instantiation go here
    }

    // Firetruck One properties.
    public static final float[] FiretruckOneProperties = {
        100,  // HEALTH
        10f,  // ACCELERATION
        300f, // MAX_SPEED
        0.8f, // RESTITUTION
        1.5f, // RANGE
    };

    // Firetruck Two properties
    public static final float[] FiretruckTwoProperties = {
        100,  // HEALTH
        20f,  // ACCELERATION
        400f, // MAX_SPEED
        0.6f, // RESTITUTION
        1f,   // RANGE
    };

     // Enums
     public static enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    // Debug mode
    public static final boolean DEBUG_ENABLED = true;

    // Game settings
    public static final String GAME_NAME = "Kroy";
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final int MAP_WIDTH = 10000;
    public static final int MAP_HEIGHT = 10000;
    public static final float MAP_SCALE = 6f;
    public static final int TILE_DIMS = (int) (8 * MAP_SCALE);
    public static final String COLLISION_TILE = "BLOCKED";

    // Camera settings
    public static final float LERP = 1.5f;
    public static final float MIN_ZOOM = 1f;
    public static final float MAX_ZOOM = 2f;

    // Screen elements sizing
    public static final float SCREEN_CENTRE_X = SCREEN_WIDTH * 0.5f;
    public static final float SCREEN_CENTRE_Y = SCREEN_HEIGHT * 0.5f;
    public static final float SCORE_Y = SCREEN_HEIGHT * 0.45f;
    public static final float SCORE_X = SCREEN_WIDTH * 0.45f;

    // Sprite properties
    // Health
    public static final int ETFORTRESS_HEALTH = 1000;
    public static final int FIRESTATION_HEALTH = 1000;
    // Size
    public static final int FIRETRUCK_WIDTH = 2*TILE_DIMS;
    public static final int FIRETRUCK_HEIGHT = 1*TILE_DIMS;
    public static final int FIRESTATION_WIDTH = 5*TILE_DIMS;
    public static final int FIRESTATION_HEIGHT = 5*TILE_DIMS;
    public static final int ETFORTRESS_WIDTH = 5*TILE_DIMS;
    public static final int ETFORTRESS_HEIGHT = 5*TILE_DIMS;

}