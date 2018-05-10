package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class GameTimeClock extends Actor {
    public final static float NIGHT_TIME = 19; // 7:00 pm
    private final static float DARKTODAWNTIME = 1; // hours of transition from dark to dawn
    public final static float DAWN_TIME = 7; // 7:00 am
    private final static float DAWNTOLIGHTTIME = 2.15f; // hours of transition from dawn to light
    public final static float DAY_TIME = 10.0f; // noon 12:00pm
    private final static float LIGHTTODUSKTIME = .75f; // hours of transition from light to dusk
    public final static float DUSK_TIME = 18.0f; // 6:00 pm
    private final static float DUSKTODARKTIME = 1; // hours of transition from dusk to dark

    private Timer_ worldTime = null;
    private double rotation = 0;
    private Sprite sprite;
    private Color ambient = new Color();
    private Label text = null;

    private final static Color DAWNCOLOR = new Color(0, 0, .75f, .2f);

    private final static Color DARKCOLOR = new Color(0, 0, 0, .6f);
    private final static Color LIGHTCOLOR = new Color(1, 1, 1, 0);

    private final static Color DUSKCOLOR = new Color(1, .3f, .67f, .1f);

    public GameTimeClock(Timer_ timer) {
        this.worldTime = timer;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // update clock
        worldTime.tick();

        // update the texture rotation based on time of day
        float degPerHour = 360 / 24;
        double time = worldTime.getElapsedInHours();


        rotation = time * (-degPerHour);

        Color current = new Color();
        Color lerp = null;
        double amt = 0;
        // between night time and dawn
        if (time >= NIGHT_TIME) {
            current.set(DARKCOLOR);
        } else if (time < DAWN_TIME) {
            // start at dark
            current.set(DARKCOLOR);
            // if in transition to dawn
            if (time >= (DAWN_TIME - DARKTODAWNTIME)) {
                // lerp to dawn
                lerp = DAWNCOLOR;
                amt = (time - (DAWN_TIME - DARKTODAWNTIME)) / DARKTODAWNTIME;
            }
        } else if
            // between dawn and day
                ((time >= DAWN_TIME) && (time < DAY_TIME)) {
            // start at dawn
            current.set(DAWNCOLOR);
            // if in transition to day
            if (time >= (DAY_TIME - DAWNTOLIGHTTIME)) {
                // lerp to day
                lerp = LIGHTCOLOR;
                amt = (time - (DAY_TIME - DAWNTOLIGHTTIME)) / DAWNTOLIGHTTIME;
            }
        } else if
            // between day and dusk
                ((time >= DAY_TIME) && (time < DUSK_TIME)) {
            // start at full light
            current.set(LIGHTCOLOR);
            // if in transition to dusk
            if (time >= (DUSK_TIME - LIGHTTODUSKTIME)) {
                // lerp to dusk
                lerp = DUSKCOLOR;
                amt = (time - (DUSK_TIME - LIGHTTODUSKTIME)) / LIGHTTODUSKTIME;
            }
        } else if
            // between dusk and night
                ((time >= DUSK_TIME) && (time < NIGHT_TIME)) {
            // start at dusk
            current.set(DUSKCOLOR);
            // if in transition to night
            if (time >= (NIGHT_TIME - DUSKTODARKTIME)) {
                // lerp to full dark
                lerp = DARKCOLOR;
                amt = (time - (NIGHT_TIME - DUSKTODARKTIME)) / DUSKTODARKTIME;
            }
        }


        //here we set the amibients start color
        ambient.set(current);
        // blend the start color with the lerp color if its set
        if (lerp != null) {
            ambient.lerp(lerp, (float) amt);
        }

    }

    public Color getAmbientLighting() {
        return ambient;
    }
}