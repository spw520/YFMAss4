package com.entities;

// LibGDX imports
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

// Custom class import
import com.misc.Constants.*;
import com.screens.GameScreen;
import com.sprites.SimpleSprite;

// Constants import
import static com.misc.Constants.ETFORTRESS_HEIGHT;
import static com.misc.Constants.ETFORTRESS_WIDTH;

import com.Saves.ETFortressData;
import com.Saves.GameData;
import com.Saves.SaveManager;
import com.Saves.TruckData;

/**
 * The ET Fortress implementation, a static sprite in the game.
 * 
 * @author Archie
 * @since 16/12/2019
 */
public class ETFortress extends SimpleSprite {

    // Private values for this class to use
    private final Texture destroyed;
    private boolean flooded;
    private final FortressType type;
    private final GameScreen gameScreen;
    private float difScale;

    /**
     * Overloaded constructor containing all possible parameters.
     * Drawn with the given texture at the given position.
     * 
     * @param texture           The texture used to draw the ETFortress with.
     * @param destroyedTexture  The texture used to draw the ETFortress with. when it has been destroyed.
     * @param scaleX            The scaling in the x-axis.
     * @param scaleY            The scaling in the y-axis.
     * @param xPos              The x-coordinate for the ETFortress.
     * @param yPos              The y-coordinate for the ETFortress.
     * @param type              {@link FortressType} given to fortress
     * @param difficulty        The difficulty level of the game, used to determine the difficulty scale
     * @param gameScreen        GameScreen to send popup messages to
     * 
     * ADDED FOR ASSESMENT 4
     * @param gamePath          gamePath is the path to the load file
     */
    public ETFortress(Texture texture, Texture destroyedTexture, float scaleX, float scaleY, float xPos, float yPos, FortressType type, int difficulty, GameScreen gameScreen, String gamePath) {
        super(texture);
        this.gameScreen = gameScreen;
        this.destroyed = destroyedTexture;
        this.flooded = false;

        

        //the difficulty scale changes the base's range, health and healing by that factor
        if (difficulty==1) this.difScale=0.8f;
        if (difficulty==2) this.difScale=1;
        if (difficulty==3) this.difScale=1.5f;

        this.type = type;
        this.setScale(scaleX, scaleY);
        this.setPosition(xPos, yPos);
        this.setSize(ETFORTRESS_WIDTH * this.getScaleX(), ETFORTRESS_HEIGHT * this.getScaleY());
        this.getHealthBar().setMaxResource((int) (type.getHealth()*difScale));
        super.resetRotation(90);

        //if there is a game data loaded from the path then load then
        // use that data to give the towers health
        GameData gameData = SaveManager.loadGame(gamePath);
        if (gameData != null){
            ETFortressData fortress = gameData.getFortress(type);
            this.getHealthBar().setCurrentAmount((int)fortress.getHP());
        }	
    }

    /**
     * Update the fortress so that it is drawn every frame.
     * @param batch  The batch to draw onto.
     */
    public void update(Batch batch) {
        super.update(batch);
        // If ETFortress is destroyed, change to flooded texture
        // If ETFortress is damaged, heal over time
        if (!flooded && this.getHealthBar().getCurrentAmount() <= 0) {
            this.removeSprite(this.destroyed);
            this.flooded = true;
            this.gameScreen.showPopupText("You have destroyed " + gameScreen.getETFortressesDestroyed()[0] + " / " + gameScreen.getETFortressesDestroyed()[1] +
                    " fortresses", 1, 7);
        } else if (!flooded && this.getInternalTime() % 150 == 0 && this.getHealthBar().getCurrentAmount() != this.getHealthBar().getMaxAmount()) {
            // Heal ETFortresses every second if not taking damage
			this.getHealthBar().addResourceAmount((int) (type.getHealing()*difScale));
        }
    }

    /**
     * Check to see if the ETFortress should fire another projectile. The pattern the ETFortress
     * uses to fire can be modified here. 
     * 
     * @return boolean  Whether the ETFortress is ready to fire again (true) or not (false)
     */
    public boolean canShootProjectile() {
        return this.getHealthBar().getCurrentAmount() > 0 && this.getInternalTime() < 120 && this.getInternalTime() % 30 == 0;
    }

    // ==============================================================
    //			          Edited for Assessment 3
    // ==============================================================
    /**
     * Simple method for detecting whether a vector, fire truck,
     * is within attacking distance of that fortress' range
     *
     * @param position  vector to check
     * @return          <code>true</code> position is within range
     *                  <code>false</code> otherwise
     */
    public boolean isInRadius(Vector2 position) {
        return this.getCentre().dst(position) <= type.getRange()*difScale;
    }

    /**
     * Overloaded method for drawing debug information. Draws the hitbox as well
     * as the range circle.
     * 
     * @param renderer  The renderer used to draw the hitbox and range indicator with.
     */
    @Override
    public void drawDebug(ShapeRenderer renderer) {
        super.drawDebug(renderer);
        renderer.circle(this.getCentreX(), this.getCentreY(), this.type.getRange()*difScale);
    }

    public boolean isFlooded() {
        return this.flooded;
    }

    public FortressType getType() {
        return this.type;
    }
}