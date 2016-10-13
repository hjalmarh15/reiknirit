package s4;



import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
	
	private Digraph graph;
	private ST<Integer, String> synsets;
	private ST<String, SET<Integer>> nouns;
	private SAP sap;
	private int graphLength;

	
	//constructor takes the name of two input files
	public WordNet(String synsets, String hypernyms) {
		
		//initialize our symbol tables
		this.synsets = new ST<Integer, String>();
		this.nouns = new ST<String, SET<Integer>>();

        //keep track of graphlength, this changes in the readSynsets function
        graphLength = 0;
        
        //read synsets from input file
        readSynsets(synsets);

        //initialize our graph with size of last ID + 1
        this.graph = new Digraph(graphLength + 1);

        //read hypernyms from input file
        readHypernyms(hypernyms);

        //creates new SAP with the digraph as parameters
        //sap checks if graph is a rooted DAG
        sap = new SAP(graph);
	}
	
	
	//returns all wordnet nouns
	public Iterable<String> nouns() {
		return nouns.keys();
	}
	
	//is the word a Wordnet Noun?
	public boolean isNoun(String word) {
		return nouns.contains(word);	
	}
	
	//distance between NounA and NounB
	public int distance(String nounA, String nounB) {
		
		//construct iterables of nounA and nounB
		SET<Integer> A = this.nouns.get(nounA);
		SET<Integer> B = this.nouns.get(nounB);
		return sap.length(A, B);
	}
	
	//a synset(second field of synsets.txt) that is a shortest common ancestor
	//of nounA and nounB
	public String sap(String nounA, String nounB) {
		SET<Integer> A = this.nouns.get(nounA);
		SET<Integer> B = this.nouns.get(nounB);
		
		int ancestor = sap.ancestor(A, B);
		return this.synsets.get(ancestor);
	}
	
	
	private void readSynsets(String synsets) {
		//new instream with the synsets file
		 In in = new In(synsets);
	        while (in.hasNextLine()) {	//while in is not finished
	            String line = in.readLine();		
	            String[] split = line.split(",");	
	            
	            String[] nouns = split[1].split(" ");


	            int id = Integer.parseInt(split[0]);
	            
	            //graphlength becomes the same as last used ID
	            graphLength = id;
	            
	            this.synsets.put(id, split[1]);
	            

	            for (String noun : nouns) {
	            	SET<Integer> nounSet = new SET<Integer>();
	                if (this.nouns.contains(noun)) {
	                    nounSet = this.nouns.get(noun);
	                }
	                nounSet.add(id);
	                this.nouns.put(noun, nounSet);
	            }
	        }
	}
	
	
	private void readHypernyms(String hypernyms) {
		  In in = new In(hypernyms);
	        while (in.hasNextLine()) {
	            String line = in.readLine();
	            String[] split = line.split(",");

	            int id = Integer.parseInt(split[0]);

	            for (int i = 1; i < split.length; i++) {
	            	//hypernyms are edges of the graph
	                graph.addEdge(id, Integer.parseInt(split[i]));
	            }
	        }
	}
	
	
	//do unit testing of this class
	public static void main(String[] args) {
		WordNet net = new WordNet("/Users/hjelmut/Downloads/wordnet/synsets.txt","/Users/hjelmut/Downloads/wordnet/hypernyms.txt");
		StdOut.println(net.sap("worm", "bird"));
		StdOut.println(net.distance("worm", "bird"));
	}
}
