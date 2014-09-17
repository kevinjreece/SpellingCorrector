package spell;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class MyTrie implements Trie {
	private MyNode root;
	private int word_count;
	private int node_count;
	
	public MyTrie() {
		root = new MyNode();
		word_count = 0;
		node_count = 1;
	}
	
	public void loadDict(String in_file) throws FileNotFoundException {
		Scanner reader = new Scanner(new BufferedInputStream(new FileInputStream(in_file)));
		//reader.useDelimiter("");
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
		writer.close();
		return;
	}
	
	@Override
	public void add(String word) {
		word = word.toLowerCase();
		root.add(word);
	}

	@Override
	public Trie.Node find(String word) {
		word = word.toLowerCase();
		return (Node) root.find(word);
	}

	@Override
	public int getWordCount() {
		return word_count;
	}

	@Override
	public int getNodeCount() {
		return node_count;
	}
	
	@Override
	public String toString() {
		return root.toString("");
	}
	// function delete(String next) -------------------------
	public Collection<? extends String> delete(String next) {
		//System.out.println("called delete(next) on " + next);
		ArrayList<String> similar = new ArrayList<String>();
		String word = "";
		for (int i = 0; i < next.length(); i++) {
			word = next.substring(0,i) + next.substring(i+1);
			similar.add(word);
			//System.out.println(word);
		}
		return similar;
	}
	// function transpose(String next) -------------------------
	public Collection<? extends String> transpose(String next) {
		//System.out.println("called transpose(next) on " + next);
		ArrayList<String> similar = new ArrayList<String>();
		String word = "";
		for (int i = 0; i < next.length() - 1; i++) {
			// word = (next up to i) + (char past i) + (char i) + (next from i+2 to the end)
			word = next.substring(0,i) + next.charAt(i+1) + next.charAt(i) + next.substring(i+2);
			similar.add(word);
			//System.out.println(word);
		}
		return similar; 
	}
	// function alter(String next) -------------------------
	public Collection<? extends String> alter(String next) {
		//System.out.println("called alter(next) on " + next);
		ArrayList<String> similar = new ArrayList<String>();
		String word = "";
		for (int i = 0; i < next.length(); i++) {
			char this_char = next.charAt(i);
			for (int j = 0; j < 26; j++) {
				char new_char = (char) ('a' + j);
				if (new_char != this_char) {
					word = next.substring(0, i) + new_char + next.substring(i+1);
					similar.add(word);
					//System.out.println(word);
				}
			}
		}
		return similar;
	}
	// function insert(String next) -------------------------
	public Collection<? extends String> insert(String next) {
		//System.out.println("called insert(next) on " + next);
		ArrayList<String> similar = new ArrayList<String>();
		String word = "";
		for (int i = 0; i <= next.length(); i++) {
			for (int j = 0; j < 26; j++) {
				char new_char = (char) ('a' + j);
				word = next.substring(0, i) + new_char + next.substring(i);
				similar.add(word);
				//System.out.println(word);
			}
		}
		return similar;
	}
	
	@Override
	public int hashCode() {
		return node_count + (31 * word_count) + root.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass())
			return false;
		if (o == this)
			return true;
		MyTrie other = (MyTrie) o;
		if (word_count != other.word_count || node_count != other.node_count)
			return false;
		return root.equals(other.root);
	}
	
	public class MyNode implements Node {
		private int value;
		private MyNode[] letters;
		
		public MyNode() {
			value = 0;
			letters = new MyNode[26];
		}
		
		public void add(String word) {
			//System.out.println("call MyNode.add(" + word + ")");
			if (word.equals("")) {
				word_count++;
				value++;
			}
			else {
				char next_char = word.charAt(0);
				String next_word = word.length() < 2 ? "" : word.substring(1);
				if (letters[next_char - 'a'] == null) {
					letters[next_char - 'a'] = new MyNode();
					node_count++;
				}
				letters[next_char - 'a'].add(next_word);
			}
			return;
		}
		
		public MyNode find(String word) {
			MyNode found = null;
			if (word.equals("")) {
				if (value > 0)
					found = this;
				else
					found = null;
			}
			else {
				char next_char = word.charAt(0);
				String next_word = word.length() < 2 ? "" : word.substring(1);
				if (letters[next_char - 'a'] == null)
					found = null;
				else
					found = letters[next_char - 'a'].find(next_word);
			}
			return found;
		}
		
		public String toString(String word) {
			//System.out.println("call MyNode.toString(" + word + ")");
			StringBuilder output = new StringBuilder();
			if (value > 0)
				output.append(word + " " + value + "\n");
			for (int i = 0; i < letters.length; i++) {
				if (letters[i] != null) {
					String new_word = word + Character.toString((char) ('a' + i));
					output.append(letters[i].toString(new_word));
				}
			}
			return output.toString();
		}
		
		@Override
		public int hashCode() {
			int hash = value;
			for (int i = 0; i < letters.length; i++) {
				if (letters[i] != null)
					hash += 31 * letters[i].hashCode();
			}
			return hash;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o.getClass() != this.getClass())
				return false;
			if (o == this)
				return true;
			MyNode other = (MyNode) o;
			if (other.value != value)
				return false;
			for (int i = 0; i < letters.length; i++) {
				if ((letters[i] == null && other.letters[i] != null) || (other.letters[i] == null && letters[i] != null))
					return false;
				else if (letters[i] == null)
					;
				else if (!letters[i].equals(other.letters[i]))
					return false;
			}
			return true;
		}
		
		@Override
		public int getValue() {
			return value;
		}
	}

}
