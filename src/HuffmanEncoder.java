import java.util.HashMap;
import java.util.Set;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Comparator;
import java.lang.StringBuilder;
import java.util.Iterator;
import java.io.IOException;

public class HuffmanEncoder {

	private static HashMap<Character, Integer> freqMap = new HashMap<>();
	private static HashMap<Character, String> encodingMap = new HashMap<>();
	private static LinkedList<HuffmanNode> list = new LinkedList<>();
	private static HuffmanNode rootNode;

	public static void main(String[] args) throws IOException {
		huffmanEncoder(args[0], args[1], args[2]);
	}

	public static void huffmanEncoder(String inputFileName, 
		String encodingFileName, String outputFileName) throws IOException {
		readFile(inputFileName);
		generateNode();
		generateTree();
		generateEncoding();
		encodeFile(encodingFileName, outputFileName); 
	}

	public static void readFile(String inputFileName) throws IOException {
		FileReader fReader = new FileReader(inputFileName);
		BufferedReader bReader = new BufferedReader(fReader);
		String line = null;
		while ((line = bReader.readLine()) != null) {
			for (int i = 0; i < line.length(); i++) {
				if (freqMap.containsKey(line.charAt(i)))
					freqMap.put(line.charAt(i), freqMap.get(line.charAt(i))+1);
				else
					freqMap.put(line.charAt(i), 0);
			}
		}

		fReader.close();
		bReader.close();
	}

	public static void generateNode() {		
		// collection of keys in the map
		Set<Character> characterSet = freqMap.keySet();
		// traverse the set 
		Iterator<Character> characterIter = characterSet.iterator();

		while (characterIter.hasNext()) {
			Character letter = characterIter.next();
			int frequency = freqMap.get(letter);

			HuffmanNode node = new HuffmanNode(letter, frequency, null, null);
			list.add(node);
		}

	}

	public static HuffmanNode mergeNode(HuffmanNode left, HuffmanNode right) {
		return new HuffmanNode(null, left.getFreq()+right.getFreq(),
										left, right);		
	}


	// other way: write a separate class
	// anonymous class implements comparator
	// Comparator: interface. new interface<>() { define methods inside anonymous class
	// }
	public static void generateTree() {
		Comparator<HuffmanNode> comparator = new Comparator<HuffmanNode>() {
			public int compare(HuffmanNode node1, HuffmanNode node2) {
				if (node1.getFreq() < node2.getFreq()) {
					return -1;
				} else if (node1.getFreq() > node2.getFreq()) {
					return 1;
				} else {
					return 0;
				}
			}
		};

		PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(comparator);
		// traverse the list
		Iterator<HuffmanNode> iterList = list.iterator();		
		while (iterList.hasNext()) {
			queue.add(iterList.next());
		}
		
		while (queue.size() != 1) {
			queue.add(mergeNode(queue.poll(), queue.poll()));
		}

		rootNode = queue.poll();
	}

	public static void generateEncoding() {
		traverse(rootNode, new StringBuilder());
	}

	public static void traverse(HuffmanNode node, StringBuilder encoding) {
		if (node.getLeft() == null) {
			encodingMap.put(node.getChar(), encoding.toString());
		} else {
			traverse(node.getLeft(), encoding.append("0"));						
			traverse(node.getRight(), encoding.append("1"));		
		}
	}

	public static void encodeFile(String encodingFileName, String outputFileName) throws IOException {
		FileReader fReader = new FileReader(encodingFileName);
		BufferedReader bReader = new BufferedReader(fReader);

		FileWriter fWriter = new FileWriter(outputFileName);
		BufferedWriter bWriter = new BufferedWriter(fWriter);

		String line = null;
		while ((line = bReader.readLine()) != null) {
			for (int i = 0; i < line.length(); i++) {
				String encoding = encodingMap.get(line.charAt(i));
				if (encoding != null)	
					bWriter.write(encoding, 0, encoding.length());
			}
		}
		fReader.close();
		bReader.close();
		fWriter.close();
	}
}