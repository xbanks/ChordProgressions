/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chordprogression;
import java.util.*;

/**
 *
 * @author Xavier
 */
public class Progression {
    private ArrayList<Integer> indexes;
    private ArrayList<String> string;
    
    public Progression(){
        indexes = new ArrayList<Integer>();
        string = new ArrayList<String>();
    }
    
    public void addIndex(int i){
        indexes.add(i);
    }
    
    public void addChord(String s){
        string.add(s);
    }
    
    public String toString(){
        String Rstring = "";
       
        for(int i = 0; i < string.size(); i++){
            
             if(Rstring.equals("")){
                Rstring += string.get(i);
            }
            else
                Rstring += "->" + string.get(i);
            
        }
        
        return Rstring;
    }
    
    public int getIndex(int i){
        return indexes.get(i);
    }
    
    public ArrayList<Integer> getIndexArray(){
        return indexes;
    }
    
    public int getIndexArraySize(){
        return indexes.size();
    }
    
}
