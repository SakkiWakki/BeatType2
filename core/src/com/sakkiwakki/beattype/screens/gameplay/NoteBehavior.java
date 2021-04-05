package com.sakkiwakki.beattype.screens.gameplay;

import com.badlogic.gdx.utils.Array;
import com.sakkiwakki.beattype.gameplay.notes.Note;

public interface NoteBehavior {
    public void noteSpawning();
    public void onHit();
    public void hitJudgement();

}
