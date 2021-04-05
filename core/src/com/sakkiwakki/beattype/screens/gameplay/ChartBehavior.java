package com.sakkiwakki.beattype.screens.gameplay;

public interface ChartBehavior {
    public void moveCamera(int speed);
    public void startGame();
    public void showJudgement(String judgement);
    public void changeScore();
    public void death();
}
