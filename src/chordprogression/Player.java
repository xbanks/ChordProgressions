/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chordprogression;
import java.util.*;
import jm.JMC;
import jm.music.data.*;
import jm.util.*;

/**
 *
 * @author Xavier
 */
public class Player {
    private ArrayList<int[]> chords;
    private ArrayList<SingleChord> chordsInKey;
    private int key;
    
    public Player(int inKey){
        key = inKey;
        chords = new ArrayList<int[]>();
        chordsInKey = new ArrayList<SingleChord>();
        
        ChordHolder ch = new ChordHolder();
        
        chords.add(ch.C);
        chords.add(ch.CS);
        chords.add(ch.D);
        chords.add(ch.DS);
        chords.add(ch.E);
        chords.add(ch.F);
        chords.add(ch.FS);
        chords.add(ch.G);
        chords.add(ch.GS);
        chords.add(ch.A);
        chords.add(ch.AS);
        chords.add(ch.B);
        
        for(int i = 0; i < 7; i++)
        {
            chordsInKey.add(new SingleChord(chords.get(key)));
            System.out.println(key+1);
            if(i == 2)
                key+=1;
            else
                key+=2;
            if(key > 11)
                key-=12;
        }
        
//        for(int i = 0; i < chords.size(); i++)
//        {
//            System.out.println(chordsInKey.get(i).getChord().toString());
//        }
//        System.out.println(chordsInKey.toString());
         
    }
    
    public void PlayChords(ArrayList<Integer> nums, double tempo){
        Phrase phrase = new Phrase();
        
        for(int i = 0; i < nums.size(); i++)
        {
            int[] c = chordsInKey.get(nums.get(i)).getChord();
            double t = chordsInKey.get(nums.get(i)).getTempo();
            phrase.addChord(c, tempo);
        }
        
       // View.print(phrase);
        //View.pianoRoll(phrase);
        //View.sketch(phrase);
        Play.midi(phrase);
        
    }
}
