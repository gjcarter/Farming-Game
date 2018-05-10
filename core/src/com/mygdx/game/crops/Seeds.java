package com.mygdx.game.crops;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.screens.PlayScreen;
import com.mygdx.game.tools.Items;

public class Seeds {
    private Texture texture;
    private Rectangle boundingRect;
    private Items.Item item;
    private int price;


    public Seeds(String string, Rectangle rectangle){
        this.texture = new Texture("seeds/" + string + "s.png");
        this.boundingRect = new Rectangle(rectangle.x, rectangle.y, texture.getWidth(), texture.getHeight());
        setSeedPrice(string);

    }

    public void setSeedPrice(String string){
            if(string.equals("gourdseed")) {
                this.price = 4;
                this.item = Items.Item.GOURD;
            }
            else if(string.equals("cornseed")) {
                this.price = 5;
                this.item = Items.Item.CORN;
            }
            else if(string.equals("carrotseed")) {
                this.price = 2;
                this.item = Items.Item.CARROT;
            }
            else if(string.equals("artichokeseed")) {
                this.price = 8;
                this.item = Items.Item.ARTICHOKE;
            }
            else if(string.equals("tomatoseed")) {
                this.price = 4;
                this.item = Items.Item.TOMATO;
            }
            else if(string.equals("potatoseed")) {
                this.price = 5;
                this.item = Items.Item.POTATO;
            }
            else if(string.equals("pepperseed")) {
                this.price = 7;
                this.item = Items.Item.PEPPER;
            }
    }

    public int getPrice() {
        return price;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getBoundingRect() {
        return boundingRect;
    }

    public Items.Item getItem() {
        return item;
    }
}
