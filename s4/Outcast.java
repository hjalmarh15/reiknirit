package s4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet wordnet;
	
	//constructor takes a wordnet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}
	
	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		String theOutcast = null;
		int maxDistance = 0;
		for(String x1 : nouns) {
			int distance = 0;
			for(String x2 : nouns) {
				//don't check distance between same nouns
				if(x1 != x2) {
					distance += this.wordnet.distance(x1, x2);
				}
			}
			
			//if total distance is larger than the  previous maxDistance
			if(distance > maxDistance) {
				maxDistance = distance;		//maxdistance becomes the total distance
				theOutcast = x1;			//and the outcast is the noun we're checking
			}
		}
		
		return theOutcast;
	}
	
	
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		In in = new In();
		for(int t = 2; t < args.length; t++) {
			in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
	
}
