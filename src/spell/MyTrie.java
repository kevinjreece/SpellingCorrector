package spell;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MyTrie implements Trie {
	private MyNode root;
	private int num_words;
	private int num_nodes;
	
	public void loadDict(String in_file) throws FileNotFoundException {
		Scanner reader = new Scanner(new BufferedInputStream(new FileInputStream(in_file)));
		while (reader.hasNext()) {
			String new_word = reader.next();
			add(new_word);
		}
		reader.close();
	}
	
	public void printDict(String out_file) throws IOException {
		StringBuilder output = new StringBuilder();
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(out_file)));
		output.append(toString());
		writer.print(output.toString());
		return;
	}
	
	@Override
	public void add(String word) {
		root.add(word);
	}

	@Override
	public Node find(String word) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		root.toString("");
		return output.toString();
	}
	
	public class MyNode implements Node {
		private int word_count;
		private MyNode[] letters;
		
		public void add(String word) {
			if (word.equals("")) {
				num_words++;
				word_count++;
				if (num_words == 1)
					num_nodes++;
			}
			else {
				char next_char = word.charAt(0);
				String next_word = word.length() < 2 ? "" : word.substring(1);
				letters[next_char] = new MyNode();
				letters[next_char].add(next_word);
			}
			return;
		}
		
		public String toString(String word) {
			StringBuilder output = new StringBuilder();
			if (word_count > 0)
				output.append(word + " " + word_count + "\n");
			for (int i = 0; i < letters.length; i++) {
				if (!letters[i].equals(null)) {
					String new_word = word + Character.toString((char) ('a' + i));
					output.append(letters[i].toString(new_word));
				}
			}
			return output.toString();
		}
		
		@Override
		public int getValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
	}


}
