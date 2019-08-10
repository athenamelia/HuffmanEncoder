

public class HuffmanNode {
	private Character inChar;
	private int frequency;
	private HuffmanNode left, right;

	public HuffmanNode(Character c, int freq, HuffmanNode l, HuffmanNode r) {
		inChar = c;
		frequency = freq;
		left = l;
		right = r;
	}

	public Character getChar() {
		return inChar;
	}

	public int getFreq() {
		return frequency;
	}

	public HuffmanNode getLeft() {
		return left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public void setChar(Character newChar) {
		inChar = newChar;
	}

	public void setLeft(HuffmanNode newLeft) {
		left = newLeft;
	}

	public void setRight(HuffmanNode newRight) {
		right = newRight;
	}


}