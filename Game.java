import java.util.*;
import java.io.*;
/** Description: This game lets a user insert 2 word and a length to 
	* see if it can get to the second word from the first word in that number of hops. 
	* Using HashChain, HashTable, Queue, SLList it can be done.
	* @Author Maya Ledvina
	* Date: 12/14/18
	*/
public class Game{

	public static void main(String[] args){
		String start = null;
		String end = null;
		int length = 0;
		
		HashChain<String, String> hash = new HashChain(10007);
		
		if (args.length == 3) {
			//open file and load table
			start = args[0]; //first word
			end = args[1]; // second
			length = Integer.parseInt(args[2]); // hop length
			openAndReadFile("dictionary.txt", hash); 
		} else {
			System.out.println("invalid number of arguments passed.  Ending.");
			System.exit(0);	// End gracefully.
			
		}
		//for debugging purposes
		SLList<HashNode>[] table = hash.getChainTable();
		for(int i = 0; i < table.length; i++){
			SLList<HashNode> l = table[i];
		}
		
		
		start(start, end, length, hash); 
		
		
	}

	/**
	*Description: takes the beginning word, end word, length and dictionary and looks for the words to get from start to end
	* by changing each letter then looking for the word in the dictionary. puts them into a hashchain and counts the "hops"
	* @param : start = first word, end= last word, length = length of hops, words = dictionary
	*/
	private static void start (String start, String end, int length, HashChain words) {
		ArrayList<Character> letters = new ArrayList<Character>(); // letters of alphabet
		
		for (int i = 'a'; i < 'z'; i++) {
			letters.add((char)i); 
		}
		
		char[] startChars = start.toCharArray();
		char [] copy = startChars;
		HashChain<String, String> visited = new HashChain<String, String> (10007); //words already visited
		TNode<String> parent = makeRoot(start, end, length, words); //parent is now the root
		QueueList<TNode> checked = new QueueList<TNode>(parent);
		SLList<String> path = new SLList<String>();
		String currentWord = start;
		boolean found = false;
		
		while(!found && !checked.isEmpty() ) { // not found yet
			for(int i = 0; i < currentWord.length(); i++) { //for each letter in the word				
				for(int j = 0; j < 25; j++) { 
					char c = letters.get(j);
					copy[i] = c;
					String check = new String(copy);
					//System.out.println(check);
					if (words.search(check, check)) { //search 
						
						if(!visited.search(check, check)) {
							HashNode<String, String> node = new HashNode<String, String>(check, check);
							//System.out.println("visited" + check);
							visited.insert(node); // insert node into chain
							//System.out.println(check + " is now TNode");
							TNode<String> t = new TNode<String>(check); 
							t.setParent(checked.front()); 
							checked.enqueue(t); //take off queue
							if(check.equals(end)) { // found it!
								//System.out.println("found " + end);
								found = true;
								path.add(t.getElement());
								int hops = 0; //get hops
								while(parent != null) { // there is more to do
									parent = t.getParent();
									t = parent;
									
									if (t != null) {
										path.add(t.getElement()); //get word, add to list
										hops++; // one hop this time
									}
								}
								path.reverse(); // reverse reverse!
								if (hops > length) {
									System.out.println("Solution may be beyond given depth.");
									System.out.println("There is no solution");
								} else {
									System.out.println("Can make it in " + hops + " hops"); // everybody clap your hands! 
									System.out.println(path);	
								}	
							}
						}					
					}
				}
				copy = currentWord.toCharArray();
				
			}
			checked.dequeue();
			//System.out.println(currentWord);
			
			if(checked.isEmpty()) {
				break;
			}
			currentWord = checked.front().getElement().toString();
			copy = currentWord.toCharArray();
		}
		if (!found) { // no can do
			System.out.println("There is no solution.");
		}
		
	}
	/**
	* Description: This takes the first word and makes it a root but before it checks to see if both words are in the hashchain, 
	* and the same length
	* @param : start = first word, end= last word, length = length of hops, words = dictionary
	*/
	private static TNode<String> makeRoot (String start, String end, int length, HashChain words) {
		if(words.search(start, start) == false || words.search(end, end) == false || start.length() != end.length()) {
			System.out.println("Words are not the same length or not in dictionary");
			return null;
		}			
		TNode root = new TNode<String>(start); // make it the root
		root.setParent(null); // no parent yet!
		return root;	// return first word
	}
	
	
	
		
	/**
	* Description: This takes the file and opens it and put the words onto the HashChain made in lab
	* Splits is based off of spaces
	* @param fileName = dictionary.txt, h = HashChain made in lab
	*/ 
	private static void openAndReadFile(String fileName, HashChain h){
		String line = null;
		
		try{
			FileReader fileReader = new FileReader(fileName);
			
			//wrap it in a buffered reader
			BufferedReader bufferedReader= new BufferedReader(fileReader);
			while((line = bufferedReader.readLine() ) != null){
				
				//split the string into tokens based on space
				String[] splitStr = line.split(" ");
				for(int i = 0; i<splitStr.length; i++){
					//System.out.println(splitStr[i]);
					h.insert(new HashNode(splitStr[i], splitStr[i] )); // add to hashchain
				}
	
			}
			bufferedReader.close();
		
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
}