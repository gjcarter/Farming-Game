package com.mygdx.game.tools;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FarmGame;
import com.mygdx.game.crops.Crop;

import com.mygdx.game.screens.PlayScreen;
import com.mygdx.game.sprites.Entity;


public class PlayerController implements InputProcessor {

    private Entity player;
    private Crop crop;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private PlayScreen screen;
    Vector3 tp;


    public PlayerController(PlayScreen screen, Entity player) {
        this.player = player;
        this.screen = screen;
        tp = new Vector3();
        left = false;
        right = false;
        up = false;
        down = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
            this.left = true;
        if(keycode == Input.Keys.UP || keycode == Input.Keys.W)
            this.up = true;
        if(keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
            this.down = true;
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
           this.right = true;
        if(keycode == Input.Keys.PLUS )
            screen.getTimer().setTimeRatio(9000);
        if(keycode == Input.Keys.MINUS)
            screen.getTimer().setTimeRatio(600);
        if(keycode == Input.Keys.NUM_1)
            screen.setMouseCrop(screen.getItems().get(0));
        if(keycode == Input.Keys.NUM_2)
            screen.setMouseCrop(screen.getItems().get(1));
        if(keycode == Input.Keys.NUM_3)
            screen.setMouseCrop(screen.getItems().get(2));
        if(keycode == Input.Keys.NUM_4)
            screen.setMouseCrop(screen.getItems().get(3));
        if(keycode == Input.Keys.NUM_5)
            screen.setMouseCrop(screen.getItems().get(4));
        if(keycode == Input.Keys.NUM_6)
            screen.setMouseCrop(screen.getItems().get(5));
        if(keycode == Input.Keys.NUM_7)
            screen.setMouseCrop(screen.getItems().get(6));
        if(keycode == Input.Keys.NUM_8)
            screen.setMouseCrop(screen.getItems().get(7));
       if(keycode == Input.Keys.NUMPAD_0) {
            screen.getTimer().setDaysPassed(screen.getCurrentDays() + 1);
            screen.getTimer().setStartTime(screen.getCurrentDays() + 1, 7, 0, 0);
        }
        if (keycode == Input.Keys.NUMPAD_1) {
            screen.addMoney(1000);

        }



        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
            this.left = false;
        if(keycode == Input.Keys.UP || keycode == Input.Keys.W)
            this.up = false;
        if(keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
            this.down = false;
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
           this.right = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 coords = screen.getCam().unproject(tp.set(screenX, screenY,0));
        if(Math.abs(player.getPlayerCenterX()- coords.x) < 50 && Math.abs(player.getPlayerCenterY()-coords.y) < 50) {

            for (int i = 0; i < screen.getSeeds().size; i++) {
                if(screen.getSeeds().get(i).getBoundingRect().contains(coords.x, coords.y)){
                    screen.buySeed(screen.getSeeds().get(i));
                }
            }

            for (int i = 0; i < screen.numCrops; i++) {
                if (screen.getCrops().get(i).getFrameSprite().getBoundingRectangle().contains(coords.x, coords.y)) {
                    if (screen.currentItem.getItem() == Items.Item.BUCKET) {
                        screen.getCrops().get(i).setWatered(true);
                        FarmGame.manager.get("water.mp3", Sound.class).play(2);
                    }
                    if (screen.getCrops().get(i).getGrowthStage() == 3) {
                        screen.addMoney(screen.getCrops().get(i).getPrice());
                        screen.getCrops().removeIndex(i);
                        screen.numCrops--;
                        FarmGame.manager.get("dirt.mp3", Sound.class).play();
                        return false;
                    } else {
                        return false;
                    }
                }
            }
            if (screen.currentType == Items.ItemType.SEED) {
                TiledMapTileLayer ground = (TiledMapTileLayer) screen.getMap().getLayers().get("Ground");
                TiledMapTileLayer.Cell cell = ground.getCell(Math.round(coords.x * FarmGame.UNIT_SCALE),
                        Math.round(coords.y * FarmGame.UNIT_SCALE));
                if (cell != null) {
                    Object grass = cell.getTile().getProperties().get("Grass");
                    if (grass != null && screen.currentItem.getNum() > 0) {
                        crop = new Crop(screen.currentItem.getItem(), coords.x, coords.y);
                        screen.addCrop(crop);
                        screen.removeSeeds(screen.currentItem.getItem());
                        screen.numCrops++;
                        FarmGame.manager.get("dirt.mp3", Sound.class).play();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
       screen.intType+= amount;
       if(screen.intType > screen.getItems().size-1)
           screen.intType = 0;
       if(screen.intType < 0)
           screen.intType = screen.getItems().size-1;
       screen.setMouseCrop(screen.getItems().get(screen.intType));
            return false;
    }

    public void update(float delta){
        processInput(delta);
    }

    private void processInput(float delta) {
        if(up){
            player.move(Entity.Direction.UP, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.UP, delta);
        }
        else if(down){
            player.move(Entity.Direction.DOWN, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.DOWN, delta);
        }
        else if(right){
            player.move(Entity.Direction.RIGHT, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.RIGHT, delta);
        }
        else if(left) {
            player.move(Entity.Direction.LEFT, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.LEFT, delta);
        }
        else {
            player.setState(Entity.State.IDLE);
            player.setDirection(player.getDirection(), delta);
        }
    }
}
