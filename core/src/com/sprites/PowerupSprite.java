package com.sprites;

/* =================================================================
                       New class added for assessment 4
   ===============================================================*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.entities.Firetruck;
import com.misc.Constants;
import com.pathFinding.Junction;
import com.pathFinding.MapGraph;

import static com.misc.Constants.FIRETRUCK_REPAIR_SPEED;

/**
 * This sprite can be located around the map and when
 * a player drives over it, the mini game will begin
 */
public class PowerupSprite extends SimpleSprite {

    // using polygon as firetruck is a polygon, which is what it collides with
    private final Polygon hitBox;
    private int type;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean picked;

    /**
     * Constructor for powerup sprite
     *
     *
     *
     * @param
     */
    public PowerupSprite(MapGraph mapGraph, Texture tex, int t) {
        super(tex);

        Junction spawn = mapGraph.getJunctions().random();
        this.x = spawn.getX();
        this.y = spawn.getY();
        this.width=110;
        this.height=120;

        this.setBounds(x*Constants.TILE_DIMS, y*Constants.TILE_DIMS, 1.5f * Constants.TILE_DIMS, 1.5f * Constants.TILE_DIMS);
        this.hitBox = new Polygon(new float[]{0,0,width,0,width,height,0,height});
        this.hitBox.setPosition(getX(), getY());
        this.type = t;
        this.picked = false;
    }

    /**
     * Draw the sprite
     *
     * @param batch to be drawn to
     */
    public void update(Batch batch) {
        batch.draw(super.getTexture(), getX(), getY(), width, height);
    }

    public float getX() { return x-(width/2);}
    public float getY() { return y-(height/2);}
    public int getType() { return type; }
    public boolean getPicked() { return picked; }

    public void pickUp(Firetruck activeTruck) {
        if(type==1){
            activeTruck.getWaterBar().addResourceAmount((int) activeTruck.getWaterBar().getMaxAmount() - (int) activeTruck.getWaterBar().getCurrentAmount()); }
        if(type==2){
            activeTruck.getHealthBar().addResourceAmount((int) activeTruck.getHealthBar().getMaxAmount() - (int) activeTruck.getHealthBar().getCurrentAmount()); }
        if(type==3) {
            activeTruck.shieldEffect(); }
        if(type==4) {
            activeTruck.swordEffect(); }
        if(type==5) {
            activeTruck.rangeEffect(); }

        this.picked = true;
    }

    public Polygon getHitBox() {
        return this.hitBox;
    }
}
