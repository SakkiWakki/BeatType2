package com.sakkiwakki.beattype.screens.gameplay;

import com.badlogic.gdx.utils.Array;
import com.sakkiwakki.beattype.gameplay.notes.Note;

interface ChartParser {
    /**
     * Creates the chart, parses the file and relay the notes to the ArrayList
     * @param file
     * @param notes
     */
    public Array parseChart(String file, Array<? extends Note> notes);
}