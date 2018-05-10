package com.mygdx.game.crops;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.tools.Items;

public class Crop extends Sprite {


    private int growthStage;
    private int growthStageDuration;
    private int daysOld;
    private int price;
    private int daysNotWatered;
    private boolean isDead;
    private boolean isWatered;
    private float centerX;
    private float centerY;
    private Vector2 position;
    private Texture texture;
    private Sprite frameSprite;
    private TextureRegion currentFrame;
    private TextureRegion[][] textureFrames;
    private Array<TextureRegion> cropFrames;
    private TextureRegion dirtFrame;

    public Crop(Items.Item item, float x, float y) {
        this.position = new Vector2(x, y);
        this.growthStage = 0;
        this.daysOld = 0;
        this.isWatered = false;
        this.daysNotWatered = 0;
        this.isDead = false;
        centerX = x - 16;
        centerY = y - 16;
        texture = new Texture("farming/tilesets/plants.png");
        textureFrames = TextureRegion.split(texture, 32, 64);
        frameSprite = new Sprite(textureFrames[0][0].getTexture(), 0, 0, 32, 32);
        frameSprite.setX(Math.round(centerX/32)*32);
        frameSprite.setY(Math.round(centerY/32)*32);
        dirtFrame = textureFrames[5][2];
        loadinfo(item);
    }

    private void loadinfo(Items.Item type) {
        switch (type) {
            case TOMATO:
                cropFrames = new Array<TextureRegion>(5);
                for(int i = 0; i < 5; i++)
                    cropFrames.insert(i, textureFrames[i][0]);
                currentFrame = cropFrames.get(0);
                this.growthStageDuration = 2;
                this.price = 10;
                break;
            case POTATO:
                cropFrames = new Array<TextureRegion>(5);
                for(int i = 0; i < 5; i++)
                    cropFrames.insert(i, textureFrames[i][1]);
                currentFrame = cropFrames.get(0);
                this.growthStageDuration = 3;
                this.price = 20;
                break;
            case CARROT:
                cropFrames = new Array<TextureRegion>(5);
                for(int i = 0; i < 5; i++)
                    cropFrames.insert(i, textureFrames[i][2]);
                currentFrame = cropFrames.get(0);
                this.growthStageDuration = 2;
                this.price = 8;
                break;
            case PEPPER:
                cropFrames = new Array<TextureRegion>(5);
                for(int i = 0; i < 5; i++)
                    cropFrames.insert(i, textureFrames[i][4]);
                currentFrame = cropFrames.get(0);
                this.growthStageDuration = 4;
                this.price = 30;
                break;
            case GOURD:
                cropFrames = new Array<TextureRegion>(5);
                for(int i = 0; i < 5; i++)
                    cropFrames.insert(i, textureFrames[i][5]);
                currentFrame = cropFrames.get(0);
                this.growthStageDuration = 3;
                this.price = 19;
                break;
            case ARTICHOKE:
                cropFrames = new Array<TextureRegion>(5);
                for(int i = 0; i < 5; i++)
                    cropFrames.insert(i, textureFrames[i][3]);
                currentFrame = cropFrames.get(0);
                this.growthStageDuration = 5;
                this.price = 45;
                break;
            case CORN:
                cropFrames = new Array<TextureRegion>(5);
                for(int i = 0; i < 5; i++)
                    cropFrames.insert(i, textureFrames[i][6]);
                currentFrame = cropFrames.get(0);
                this.growthStageDuration = 3;
                this.price = 20;
                break;
            default:
                break;

        }
    }

    public void addDay(){
        if(isWatered) {
            daysNotWatered = 0;
            this.daysOld++;
        }
        if(!isWatered && growthStage != 3)
            daysNotWatered++;
        if(daysNotWatered == 2)
            isDead = true;
        this.isWatered = false;
        checkGrowth();
    }

    private void checkGrowth() {
        if(daysOld%growthStageDuration == 0 && growthStage != 3) {
            growthStage++;
            this.currentFrame = cropFrames.get(growthStage);
        }
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public Sprite getFrameSprite() {
        return frameSprite;
    }

    public int getPrice() {
        return price;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }
    public boolean isDead() {
        return isDead;
    }

    public boolean isWatered() {
        return isWatered;
    }
}
