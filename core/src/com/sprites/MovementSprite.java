package com.sprites;

// LibGDX imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.config.Constants;
import com.config.Constants.Direction;
import com.config.Constants.Direction2;
//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

// Constants import
import javax.sound.midi.SysexMessage;

import java.util.ArrayList;

import static com.config.Constants.COLLISION_TILE;
import static com.config.Constants.TILE_DIMS;

/**
 * MovementSprite adds movement facilities to a sprite.
 * @author Archie
 * @since 08/12/2019
 */
public class MovementSprite extends SimpleSprite {

    // Private values to be used in this class only
    private float accelerationRate, decelerationRate, maxSpeed, restitution, rotationLockTime;
    private Vector2 speed;
    private TiledMapTileLayer collisionLayer, carparkLayer;

    /**
     * Creates a sprite capable of moving and colliding with the tiledMap and other sprites.
     * 
     * @param spriteTexture  The texture the sprite should use.
     * @param collisionLayer The layer of the map the sprite will collide with.
     */
    public MovementSprite(Texture spriteTexture, TiledMapTileLayer collisionLayer, TiledMapTileLayer carparkLayer) {
        super(spriteTexture);
        this.collisionLayer = collisionLayer;
        this.carparkLayer = carparkLayer;
        this.create();
    }

    /**
     * Creates a sprite capable of moving and but only colliding with other sprites.
     * 
     * @param spriteTexture  The texture the sprite should use.
     */
    public MovementSprite(Texture spriteTexture) {
        super(spriteTexture);
        this.create();
    }

    /**
     * Sets the inital values for all properties needed by the sprite.
     */
    private void create() {
        this.speed = new Vector2(0,0);
        this.accelerationRate = 10;
        this.decelerationRate = 6;
        this.rotationLockTime = 0;
        this.restitution = 0.1f;
        this.maxSpeed = 200;
    }

    /**
     * Update the sprite position and direction based on acceleration and
     * boundaries. This is called every game frame.
     * @param batch  The batch to draw onto.
     */
    public void update(Batch batch) {
        // Calculate the acceleration on the sprite and apply it
        accelerate();

        // Rotate sprite to face the direction its moving in
        updateRotation();

        super.update(batch);
        // Update rotationLockout if set
        if (this.rotationLockTime >= 0) this.rotationLockTime -= 1; 
    }

    /**
     * Increases the speed of the sprite in the given direction.
     * @param direction The direction to accelerate in.
     */
    public void applyAcceleration(Direction direction) {
        if (this.speed.y < this.maxSpeed && direction == Direction.UP) {
            this.speed.y += this.accelerationRate;
        }
        if (this.speed.y > -this.maxSpeed && direction == Direction.DOWN) {
            this.speed.y -= this.accelerationRate;
        }
        if (this.speed.x < this.maxSpeed && direction == Direction.RIGHT) {
            this.speed.x += this.accelerationRate;
        }
        if (this.speed.x > -this.maxSpeed && direction == Direction.LEFT) {
            this.speed.x -= this.accelerationRate;
        }
    }

    /**
     * Calculate the angle the sprite needs to rotate from it's current rotation to the new rotation.
     */
    private void updateRotation() {
        float currentRotation = this.getRotation();
        float desiredRotation = this.speed.angle();
        float angle = desiredRotation - currentRotation;
        if (this.speed.len() >= this.accelerationRate && this.rotationLockTime <= 0) {
            // Use the shortest angle
            angle = (angle + 180) % 360 - 180;
            float rotationSpeed = 0.05f * this.speed.len();
            this.rotate(angle * rotationSpeed * Gdx.graphics.getDeltaTime());
        }
    }

    /**
     * Apply acceleration to the sprite, based on collision boundaries and
     * existing acceleration.
     */
    private void accelerate() {
        // Calculate whether it hits any boundaries
        boolean collidesBlocked = this.collisionLayer != null && collidesWithTile(this.collisionLayer);
        boolean collidesCarpark = this.carparkLayer != null && collidesWithTile(this.carparkLayer);
        // Check if it collides with any tiles, then move the sprite
        if (collidesCarpark) {
            System.out.println("carpark");
        } else if (!collidesBlocked) {
            this.setX(this.getX() + this.speed.x * Gdx.graphics.getDeltaTime());
            this.setY(this.getY() + this.speed.y * Gdx.graphics.getDeltaTime());
            if (this.decelerationRate != 0) decelerate();
        } else {
            // Seperate the sprite from the tile and stop sprite movement
            collisionOccurred(this.speed.rotate(180).scl(0.05f));
            this.speed = new Vector2(0, 0);
        }
    }

    /**
     * Decreases the speed of the sprite (direction irrelevant). Deceleration rate
     * is based upon the sprite's properties.
     */
    private void decelerate() {
        // Stops it bouncing from decelerating in one direction and then another etc..
        if (this.speed.y < this.decelerationRate && this.speed.y > -this.decelerationRate) {
            this.speed.y = 0f;
        } else {
            this.speed.y -= this.speed.y > 0 ? this.decelerationRate : -this.decelerationRate;
        }
        if (this.speed.x < this.decelerationRate && this.speed.x > -this.decelerationRate) {
            this.speed.x = 0f;
        } else {
            this.speed.x -= this.speed.x > 0 ? this.decelerationRate : -this.decelerationRate;
        }
    }

    /**
     * Checks what direction the sprite is facing and bounces it the opposite way.
     * @param seperationVector Vector containing the minimum distance needed to travel to seperate two sprites.
     */
    public void collisionOccurred(Vector2 seperationVector) {
        // Calculate how far to push the sprite back
        float pushBackX = seperationVector.x;
        float pushBackY = seperationVector.y;
        // For each direction, reverse the speed and set the sprite back a few coordinates out of the collision
        this.speed.y *= -this.restitution;
        this.speed.x *= -this.restitution;
        this.setRotationLock(0.5f);
        this.setY(this.getY() + pushBackY);
        this.setX(this.getX() + pushBackX);
    }



    /**
     * Checks if the tile at a location is a "blocked" tile or not.
     * @return Whether the hits a collision object (true) or not (false)
     */
    private boolean collidesWithTile(TiledMapTileLayer layer) {
        for (Vector2 vertex : getPolygonVertices(super.getHitBox())) {
            if (layer.getCell(((int) (vertex.x / TILE_DIMS)), ((int) (vertex.y / TILE_DIMS))) != null) {
                return true;
            }
        }
        return false;
    }

    Array<Vector2> getPolygonVertices(Polygon polygon) {
        float[] vertices = polygon.getTransformedVertices();
        Array<Vector2> result = new Array<>();
        for (int i = 0; i < vertices.length/2; i++) {
            float x = vertices[i * 2];
            float y = vertices[i * 2 + 1];
            result.add(new Vector2(x, y));
        }
        return result;
    }
    
    /**
     * Sets the amount of time the sprite cannot rotate for.
     * @param duration The duration the sprite cannot rotate in.
     */
    public void setRotationLock(float duration) {
        if (duration > 0 && this.rotationLockTime <= 0) {
            this.rotationLockTime = duration * 100;
        }
    }

    /**
     * Sets the amount the sprite will bounce upon collisions.
     * @param bounce The restitution of the sprite.
     */
    public void setRestitution(float bounce) {
        this.restitution = bounce;
    }

    /**
     * Sets the rate at which the sprite will accelerate.
     * @param rate The acceleration rate for the sprite.
     */
    public void setAccelerationRate(float rate) {
        this.accelerationRate = rate;
    }

    /**
     * Sets the rate at which the sprite will decelerate.
     * @param rate The deceleration rate for the sprite.
     */
    public void setDecelerationRate(float rate) {
        this.decelerationRate = rate;
    }


    /**
     * Sets the max speed the sprite can accelerate to.
     * @param amount The max speed value for the sprite.
     */
    public void setMaxSpeed(float amount) {
        this.maxSpeed = amount;
    }

     /**
     * Returns the max speed the sprite can accelerate to.
     * @return  The max speed value for the sprite.
     */
    public float getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * Sets the current speed of the sprite.
     * @param speed The speed the sprite should travel.
     */
    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    /**
     * Gets the current speed of the sprite.
     * @return The current speed of the sprite.
     */
    public Vector2 getSpeed() {
        return this.speed;
    }
}