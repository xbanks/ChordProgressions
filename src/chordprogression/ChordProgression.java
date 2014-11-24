package chordprogression;

import java.util.*;
import java.io.*;
import javax.sound.midi.*;

/*
 * Keep in mind:
 * 	probability in the progressions
 * 
 */

public class ChordProgression {
    
    
        public ChordProgression(){
            
            String name = "Pro";
        
        }
	
//	public static void main(String args[]) throws FileNotFoundException{
//
//		System.out.println("NDFA Music Chord Progression Program.\n\n");
//		NDFAProgression();
//		
//	}
	
	/**
	 * Main method that controls the flow of the program
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Progression> NDFAProgression(int key, int progressionNum, int perLine) throws FileNotFoundException{
		
		ArrayList<String> scale = new ArrayList<String>();			//scale: holds a derived scale based on certain key
		ArrayList<String> notes = new ArrayList<String>();			//notes: holds all notes C-B currently including sharps, no flats
		ArrayList<String> chords = new ArrayList<String>();
		ArrayList<Progression> progressions = new ArrayList<Progression>();
		ArrayList<Progression> unique = new ArrayList<Progression>();
		
		Scanner in = new Scanner(System.in);
		
		//Inputs a file containing the notes and creates a scanner to read from the file 
		File inNotes = new File("resources/notes.txt");
		Scanner readNotes = new Scanner(inNotes);
		
		//Adding notes from file to the notes array
		while(readNotes.hasNextLine()){
			notes.add(readNotes.nextLine());
		}
		
		//derives scale based on key and notes
		scale = deriveScale(key, notes);

		
		chords = deriveChords(scale);					//derives chords
		
		for(int i = 0; i < progressionNum; i++){		//Adds all derived progression
			Progression progression = derivProgression(chords, 3, perLine, 1);
			progressions.add(progression);
			System.out.println((i+1) + " \t>> " + progression);
		}
		
		//findDuplicates(progressions);
		unique = findUniques(progressions);
		
                ArrayList<Progression> returnProgressions = new ArrayList<Progression>();
                
//                returnStrings.addAll(progressions);
//                returnStrings.add("\nUnique Set:\n\n");
                returnProgressions.addAll(unique);
		
//		try {
//			printToFile(unique);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		readNotes.close();
                return returnProgressions;
	}
	
	/**
	 * Finds the unique strings of progressions out of a set of derived lines of progressions
	 * and returns this set of unique strings
	 * @param progressions
	 * @return
	 */
	public static ArrayList<Progression> findUniques(ArrayList<Progression> progressions){
		int duplicates = 0;
		ArrayList<Progression> UniqueProgressions = new ArrayList<Progression>();
		//Creating a unique set of progression lines
				UniqueProgressions.add(progressions.get(0));
				for(int i = 1; i < progressions.size(); i++){
					Progression current = progressions.get(i);
					boolean unique = true;
					
					for(int j = 0; j < UniqueProgressions.size(); j++){
						String check = UniqueProgressions.get(j).toString();
						if(current.toString().compareTo(check) == 0)
							unique = false;
					}
					
					if(unique)
						UniqueProgressions.add(current);
					else
						duplicates++;
				}
	
				System.out.println("\nDuplicates: " + duplicates);
				System.out.println("Unique set of progression lines:\n");
				for(int i = 0; i < UniqueProgressions.size(); i++){
					System.out.println((i+1) + " \t>> " + UniqueProgressions.get(i));
				}
				
		return UniqueProgressions;
	}
	
	/**
	 * Derives the scale for a certain key based on notes given
	 * CURRENTLY NOT WORKING CORRECTLY ASK AMY! {Works for non-sharps} FIXED! Works correctly, ex: F = E#
	 * @param key
	 * @param notes
	 * @return
	 */
	private static ArrayList<String> deriveScale(int key, ArrayList<String> notes){
		ArrayList<String> scale = new ArrayList<String>();
		int position;
		int note = key;
		
		for(int i = 0; i < 7; i++){
			scale.add(notes.get(note));
			
			if(i == 2)
                            note+=1;
			else
                            note+=2;
			
			if(note >11)
                            note-=12;
			
		}
		
		return scale;
	}
		
	/**
	 * Derives the seven different chords from a certain scale
	 * @param scale
	 * @return
	 */
	private static ArrayList<String> deriveChords(ArrayList<String> scale)
        {
            ArrayList<String> chords = new ArrayList<String>();
		
            for(int i = 0; i < 7; i++)
            {
                String chord = "";
                int point = i;
                chord += scale.get(point)+" ";
                for(int j = 0; j < 2; j++)
                {
                    point+=2;
                    if(point >6)
                        point-=7;
                    chord += scale.get(point)+" ";
                }
                chords.add(chord);
            }

		return chords;
	}
	
	/**
	 * Uses recursion in order to find a single progression line
	 * @param chords
	 * @param start
	 * @param numProgressions
	 * @param currentNum
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String deriveProgression(ArrayList<String> chords, int start, int numProgressions, int currentNum) throws FileNotFoundException{
		
		
            ArrayList<Integer> currentProbs = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> probabilities = new ArrayList<ArrayList<Integer>>();
            int next = start;
		
            File inFile = new File("resources/inputPercentages.txt");
	    Scanner reader = new Scanner(inFile);
	    Random rand = new Random();
	   
	    //Reads in all progressions from file
	    for(int i = 0; i < 7; i++)
            {
	    	Scanner stringread = new Scanner(reader.nextLine()); //Scanner of scanner?
	    	probabilities.add(new ArrayList<Integer>());
	    	while(stringread.hasNextInt())
                {
                    int in = stringread.nextInt();
                    //System.out.printf("%d ", in);
                    probabilities.get(i).add(in);
	    	}
	    	//System.out.println();
	    }
	    
	    for(int i = 0; i < probabilities.size(); i++)
            {
	    	int percent = 0;
	    	for(int j = 0; j < probabilities.get(i).size(); j++)
	    		percent+=probabilities.get(i).get(j);
	    	if(percent != 100)
                {
                    try {
                        redefine(inFile, i);
                    } 
                    catch (IOException e) 
                    {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                    }
	    	}
	    	
	    }
	    
	   // System.out.print(pros);
	    
	    int ran = rand.nextInt(100)+1;
	    for(int i = 0; i < 7; i++)
            {
	    	if(i == 0)
                    currentProbs.add(probabilities.get(start).get(i));
	    	else
                    currentProbs.add(currentProbs.get(i-1) + probabilities.get(start).get(i));
	    }
	    
	    System.out.print(ran + "% ");
	    
	    if(ran > 0 && ran <= currentProbs.get(0))
	    	next = 0;
	    else if( ran > currentProbs.get(0) && ran <= currentProbs.get(1))
	    	next = 1;
	    else if( ran > currentProbs.get(1) && ran <= currentProbs.get(2))
	    	next = 2;
	    else if( ran > currentProbs.get(2) && ran <= currentProbs.get(3))
	    	next = 3;
	    else if( ran > currentProbs.get(3) && ran <= currentProbs.get(4))
	    	next = 4;
	    else if( ran > currentProbs.get(4) && ran <= currentProbs.get(5))
	    	next = 5;
	    else if( ran > currentProbs.get(5) && ran <= currentProbs.get(6))
	    	next = 6;
	    
		String ret = chords.get(next);
		
		if(currentNum < numProgressions)
			return ret.substring(0, 2) + (next+1) + " -> " + deriveProgression(chords, next, numProgressions, currentNum+1);
		else
			return ret.substring(0, 2) + "" + (next+1);
		
	}
	
	/**
	 * Simply prints the derived strings of chord progressions to a file named "unique.txt"
	 * @param list
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private static void printToFile(ArrayList<String> list) throws FileNotFoundException, UnsupportedEncodingException{
		
		PrintWriter writer = new PrintWriter("resources/unique.txt", "UTF-8");
		
		for(int i = 0; i < list.size(); i++)
                    writer.println(i+" >> "+list.get(i));
		
		writer.close();
		
	}
	
	/**
	 * Rewrites the file (inputPercentages.txt) with correct percentages that are input by the user
	 * @param file
	 * @param brokenLine
	 * @throws IOException
	 */
	private static void redefine(File file, int brokenLine) throws IOException{
		Scanner in = new Scanner(System.in);
		String newString = "";
		int percentLeft = 100;
		
		Scanner inFile = new Scanner(file);
		ArrayList<String> Strings = new ArrayList<String>();
		
		//Prompts user to reset the percentages
		System.out.println("\nLine " + brokenLine + " does not equal 100% please fix.\n");
		for(int i = 0; i < 7; i++)
                {
                    System.out.printf("Percent Left: %d New Percent %d: ", percentLeft, (i+1));
                    int percent = in.nextInt();
                    
                    while(percent > percentLeft || percent < 0 || (i == 6 && percent != percentLeft))
                    {
                        System.out.println("Not valid");
                        System.out.printf("Percent Left: %d New Percent %d: ", percentLeft, (i+1));
                        percent = in.nextInt();
                    }
                    
                    percentLeft -= percent;
                    newString += percent + " ";
		}
		
		//Gets all strings from the file in order to rewrite
		for(int i =0; i < 7; i++)
                {
                    String fromFile = inFile.nextLine();
                    Strings.add(fromFile);
		}
		
		inFile.close();
		FileWriter writer = new FileWriter(file);	//using this without 'true' parameter overwrites the whole file 
		for(int i = 0; i < 7; i++)
                {
                    if(i == brokenLine)
                        writer.write(newString+"\n");
                    else
                        writer.write(Strings.get(i)+"\n");
		}
		writer.close();
		
		System.out.println("Line Fixed.\n");
	}
	
        private static Progression derivProgression(ArrayList<String> chords, int start, int numProgressions, int currentNum) throws FileNotFoundException{
		
            
            Progression pro1 = new Progression();
            
            ArrayList<ArrayList<Integer>> probabilities = new ArrayList<ArrayList<Integer>>();
            int next = start;
		
            File inFile = new File("resources/inputPercentages.txt");
	    Scanner reader = new Scanner(inFile);
	    Random rand = new Random();
	   
	    //Reads in all progressions from file
	    for(int i = 0; i < 7; i++)
            {
	    	Scanner stringread = new Scanner(reader.nextLine()); //Scanner of scanner?
	    	probabilities.add(new ArrayList<Integer>());
	    	while(stringread.hasNextInt())
                {
                    int in = stringread.nextInt();
                    //System.out.printf("%d ", in);
                    probabilities.get(i).add(in);
	    	}
	    	//System.out.println();
	    }
	    
	    for(int i = 0; i < probabilities.size(); i++)
            {
	    	int percent = 0;
	    	for(int j = 0; j < probabilities.get(i).size(); j++)
                    percent+=probabilities.get(i).get(j);
	    	if(percent < 100)
                {
                    try 
                    {
                        redefine(inFile, i);
                    } 
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
	    	}
	    	
	    }
	    
	   // System.out.print(pros);
	    
            while(currentNum <= numProgressions)
            {    
                ArrayList<Integer> currentProbs = new ArrayList<Integer>();
                int ran = rand.nextInt(100)+1;
                for(int i = 0; i < 7; i++)
                {
                    if(i == 0)
                       currentProbs.add(probabilities.get(next).get(i));
                    else
                        currentProbs.add(currentProbs.get(i-1)+probabilities.get(next).get(i));
                }
                
                System.out.print(ran + "% ");
                System.out.print(currentProbs.toString());
                
                if(ran > 0 && ran <= currentProbs.get(0))
                    next = 0;
                else if( ran > currentProbs.get(0) && ran <= currentProbs.get(1))
                    next = 1;
                else if( ran > currentProbs.get(1) && ran <= currentProbs.get(2))
                    next = 2;
                else if( ran > currentProbs.get(2) && ran <= currentProbs.get(3))
                    next = 3;
                else if( ran > currentProbs.get(3) && ran <= currentProbs.get(4))
                    next = 4;
                else if( ran > currentProbs.get(4) && ran <= currentProbs.get(5))
                    next = 5;
                else if( ran > currentProbs.get(5) && ran <= currentProbs.get(6))
                    next = 6;

                String ret = chords.get(next);
                System.out.println(ret);
                pro1.addChord(ret.substring(0, 2));
                pro1.addIndex(next);
                currentNum++;
            }
               
            return pro1;
	}
	
}