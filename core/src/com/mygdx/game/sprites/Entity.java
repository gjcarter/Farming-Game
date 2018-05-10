package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;




public class Entity extends Sprite {
   private Vector2 velocity;
   private Direction currentDirection;
   private Direction previousDirection;

   private Animation<TextureRegion> walkLeft;
   private Animation<TextureRegion> walkRight;
   private Animation<TextureRegion> walkUp;
   private Animation<TextureRegion> walkDown;

   private Array<TextureRegion> walkLeftFrames;
   private Array<TextureRegion> walkRightFrames;
   private Array<TextureRegion> walkUpFrames;
   private Array<TextureRegion> walkDownFrames;

   private Vector2 nextPosition;
   private Vector2 currentPosition;
   private State state = State.IDLE;
   private float frameTime;
   private Sprite frameSprite;
   private TextureRegion currentFrame;

   private Texture texture;

   public static Rectangle boundingBox;



    public enum State {
        IDLE, WALKING
    }

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public Entity(){
        this.nextPosition = new Vector2();
        this.currentPosition = new Vector2();
        this.boundingBox = new Rectangle();
        this.velocity = new Vector2(2.5f,2.5f);
        frameTime = 0f;
        currentDirection = Direction.DOWN;
        texture = new Texture("light.png");
        loadSprite();
        loadAnimations();

    }
    public void startingPosition(float x, float y){
        this.currentPosition.set(x,y);
        this.nextPosition.set(x,y);
    }
    public void update(float delta) {
        if(state == State.WALKING)
            frameTime = (frameTime + delta)%5;
        else
            frameTime = 0;
        boundingBox.set(nextPosition.x + 20, nextPosition.y, 24, 12);
    }

    private void loadSprite() {
        TextureRegion[][] textureFrames = TextureRegion.split(texture, 64, 64);
        frameSprite = new Sprite(textureFrames[0][0].getTexture(), 0, 0, 64, 64);
        currentFrame = textureFrames[0][0];
    }

    private void loadAnimations() {

        TextureRegion[][] textureFrames = TextureRegion.split(texture, 64, 64);
        walkDownFrames = new Array<TextureRegion>(9);
        walkUpFrames = new Array<TextureRegion>(9);
        walkLeftFrames = new Array<TextureRegion>(9);
        walkRightFrames = new Array<TextureRegion>(9);

        for(int i = 0; i < 8; i++){
            walkDownFrames.insert(i, textureFrames[10][i+1]);

        }
        for(int i = 0; i < 8; i++){
            walkUpFrames.insert(i, textureFrames[8][i+1]);
        }
        for(int i = 0; i < 9; i++){
            walkLeftFrames.insert(i, textureFrames[9][i]);
        }
        for(int i = 0; i < 9; i++){
            walkRightFrames.insert(i, textureFrames[11][i]);
        }

        walkDown = new Animation<TextureRegion>(.1f, walkDownFrames, Animation.PlayMode.LOOP);
        walkUp = new Animation<TextureRegion>(.1f, walkUpFrames, Animation.PlayMode.LOOP);
        walkLeft = new Animation<TextureRegion>(.1f, walkLeftFrames, Animation.PlayMode.LOOP);
        walkRight = new Animation<TextureRegion>(.1f, walkRightFrames, Animation.PlayMode.LOOP);
    }

    public void setDirection(Direction direction, float delta){
        this.previousDirection = this.currentDirection;
        this.currentDirection = direction;
        switch (currentDirection){
            case DOWN:
                currentFrame = walkDown.getKeyFrame(frameTime);
                break;
            case UP:
                currentFrame = walkUp.getKeyFrame(frameTime);
                break;
            case LEFT:
                currentFrame = walkLeft.getKeyFrame(frameTime);
                break;
            case RIGHT:
                currentFrame = walkRight.getKeyFrame(frameTime);
                break;
            default:
                break;
        }
    }

    public void move(Direction direction, float delta) {
        float x = currentPosition.x;
        float y = currentPosition.y;

        switch (direction){
            case DOWN:
                y -= velocity.y;
                break;
            case UP:
                y += velocity.y;
                break;
            case LEFT:
                x -= velocity.x;
                break;
            case RIGHT:
                x += velocity.x;
                break;
            default:
                break;
        }
        nextPosition.x = x;
        nextPosition.y = y;


    }

    public void setState(State state) {
        this.state = state;
    }

    public Sprite getFrameSprite() {
        return frameSprite;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void setCurrentPosition(float x, float y){
        frameSprite.setX(x);
        frameSprite.setY(y);
        this.currentPosition.x = x;
        this.currentPosition.y = y;
        this.nextPosition.x = x;
        this.nextPosition.y = y;
    }
    public void setCurrentToNext(){
        setCurrentPosition(nextPosition.x, nextPosition.y);
    }

    public static Rectangle getBoundingBox() {
        return boundingBox;
    }

    public static float getPlayerCenterX() {
        return boundingBox.x + boundingBox.width/2;
    }
    public static float getPlayerCenterY() {
        return boundingBox.y + boundingBox.height/2;
    }


}
