package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;

public class Timer_ {
    // time ratio definitions
    public final static float REALTIME = 1;
    public final static float DEMOTIME = 3600; // 1 real sec = 1 hour
    public final static float GAMETIME = 600; // 1 real sec = 10 minutes

    //how long timer has been running
    private double secondsSinceStart;
    // how long should the timer run
    private double runTimeSeconds;

    private float realToTimerRatio; // 1 real second = this many timer seconds

    // if timer is paused, no delta is added
    private boolean paused;
    // chrono timers have no end time, these timers are essentially clocks
    private boolean chrono;
    // i need this, you may not
    private int daysPassed;

    public Timer_() {
        this.runTimeSeconds = 0;
        this.secondsSinceStart = 0;
        this.daysPassed = 0;
        this.paused = true;
        this.realToTimerRatio = Timer_.GAMETIME;
        this.chrono = false;
    }

    // called every frame (in render())
    public void tick() {
        if (!(isDone()) || (chrono == true)) {
            if (!paused) {
                secondsSinceStart += (Gdx.graphics.getDeltaTime() * realToTimerRatio);

                // normalize chronological timers
                if (chrono == true) {
                    if (secondsSinceStart > 86400) {
                        secondsSinceStart -= 86400;
                        daysPassed++;
                    }
                }
            }
        }
    }

    // if you need to start a timer off at a time other than 0, for instance loading a saved game you would set the chrono clock timer to the saved time
    public void setStartTime(int days, int hour, int min, int sec) {
        daysPassed = days;
        secondsSinceStart = sec + (min * 60) + (hour * 3600);
    }

    public void setToChrono() {
        chrono = true;
    }

    public void setTimeRatio(float ratio) {
        realToTimerRatio = ratio;
    }

    // is the timer past the run time
    public boolean isDone() {
        if (secondsSinceStart >= runTimeSeconds) {
            return true;
        }
        return false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void Pause() {
        paused = true;
    }

    public void Start() {
        paused = false;
    }

    public String getFormattedElapsed() {
        return format(secondsSinceStart);
    }

    public String getFormattedRemaining() {
        return format(runTimeSeconds - secondsSinceStart);
    }

    // format the timers elapsed time in a clock format
    public String getFormattedTimeofDay() {
        int hours = (int) Math.floor(secondsSinceStart / 3600);
        String sub = " ";
        if (hours == 0) {
            hours = 12;
            sub = sub.concat("AM");
        } else if (hours >= 12) {
            if (hours > 12)
                hours -= 12;
            sub = sub.concat("PM");
        } else if ((hours < 12) && (hours > 0)) {
            sub = sub.concat("AM");
        }

        int minutes = (int) Math.floor((secondsSinceStart % 3600) / 60);

        String res = String.format("%1$d:%2$02d", hours, minutes);
        return res.concat(sub);
    }

    // format the specified seconds in HH:MM:SS format
    private String format(double d) {

        int hours = (int) Math.floor(d / 3600);
        int minutes = (int) Math.floor((d % 3600) / 60);
        double seconds = d % 60;

        String res = String.format("%1$02d:%2$02d:%3$03.1f", hours, minutes,
                seconds);
        return res;
    }

    public double getElapsedInSeconds() {
        return secondsSinceStart;
    }

    public double getElapsedInMinutes() {
        return (secondsSinceStart / 60);
    }

    public double getElapsedInHours() {
        return (secondsSinceStart / 3600);
    }

    public double getRemaining() {
        return (runTimeSeconds - secondsSinceStart);
        // this returns a negative number if timer is done, however could be modded to return 0 instead.
    }

    public int getDaysPast() {
        return daysPassed;
    }

    // starts a new timer
    public void StartNew(float runTimeInSeconds, boolean startNow,
                         boolean chronological) {
        runTimeSeconds = runTimeInSeconds;
        secondsSinceStart = 0;
        paused = !startNow;
        chrono = chronological;
    }

    public void setDaysPassed(int daysPassed) {
        this.daysPassed = daysPassed;
    }

    public float getTimeRatio() {
        return realToTimerRatio;
    }
}