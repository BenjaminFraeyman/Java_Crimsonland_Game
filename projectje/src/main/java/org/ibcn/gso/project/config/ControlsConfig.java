package org.ibcn.gso.project.config;

public class ControlsConfig {

    private String moveUp = "w";
    private String moveDown = "s";
    private String moveLeft = "a";
    private String moveRight = "d";
    private String reload = "R";
    public boolean chooser = true;
    private String HP = "H";
    private String LAUNCHER = "L";
    private String SHOTGUN = "K";
    private String IncrDamage = "J";
    private String restart = "Q";


    public String getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(String moveUp) {
        this.moveUp = moveUp;
    }

    public String getMoveDown() {
        return moveDown;
    }

    public void setMoveDown(String moveDown) {
        this.moveDown = moveDown;
    }

    public String getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(String moveLeft) {
        this.moveLeft = moveLeft;
    }

    public String getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(String moveRight) {
        this.moveRight = moveRight;
    }

    public String getReload() {
        return reload;
    }

    public void setReload(String reload) {
        this.reload = reload;
    }

    public String getMaxHP() {
        return HP;
    }

    public String getLauncher() {
        return LAUNCHER;
    }

    public String getSHOTGUN() {
        return SHOTGUN;
    }

    public String getIncrDamage() {
        return IncrDamage;
    }

    public String getRestart() {
        return restart;
    }
}
