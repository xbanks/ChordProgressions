/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chordprogression;

/**
 *
 * @author Xavier
 */
public class SingleChord {
    
    private int[] Chord;
    private double tempo;
    
    public SingleChord(int[] chord, double tempo)
    {
        this.Chord = chord;
        this.tempo = tempo;
    }
    
    public SingleChord(int[] chord) { this.Chord = chord; this.tempo = .5;}
    
    public void setChord(int[] chord) { this.Chord = chord; }
    
    public void setTempo(double tempo) { this.tempo = tempo; }
    
    public int[] getChord() { return this.Chord; }
    public double getTempo() { return this.tempo; }
    
}
