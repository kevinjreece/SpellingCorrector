package spell;

import java.io.IOException;
import java.util.ArrayList;

import spell.MyTrie.MyNode;

public class MySpellCorrector implements SpellCorrector {
	private MyTrie dict;
	
	public MySpellCorrector() {
		dict = new MyTrie();
	}

	public void printDictionary(String file_name) {
		try {
			dict.printDict(file_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void useDictionary(String dict_name) throws IOException {
		try {
			dict.loadDict(dict_name);
			//System.out.println(dict.hashCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass())
			return false;
		if (o == this)
			return true;
		MySpellCorrector other = (MySpellCorrector) o;
		return dict.equals(other.dict);
	}

	@Override
	public String suggestSimilarWord(String input)
			throws NoSimilarWordFoundException {
		input = input.toLowerCase();
		MyNode node_found = (MyNode) dict.find(input);
		String word_found = input;
		if (node_found == null) {
			word_found = "";
			ArrayList<String> similar = new ArrayList<String>();
			similar.add(input);
			do {
				//System.out.println(similar.toString());
				int init_size = similar.size();
				for (int i = 0; i < init_size; i++) {
					String next = similar.remove(0);// always removes the first word because it should act like a queue
					similar.addAll(dict.delete(next));
					similar.addAll(dict.transpose(next));
					similar.addAll(dict.alter(next));
					similar.addAll(dict.insert(next));
				}
				for (int i = 0; i < similar.size(); i++) {
					String similar_word = similar.get(i);
					MyNode similar_node = (MyNode) dict.find(similar_word);
					// If similar_word is found...
					if (similar_node != null) {
						// If this is the first word found...
						if (node_found == null) {
							word_found = similar_word;
							node_found = similar_node;
						}
						// If this is NOT the first word found...
						else {
							// Get the number of times the current found word occurs in the dictionary
							int old_num = node_found.getValue();
							// Get the number of times the new found word occurs in the dictionary
							int new_num = similar_node.getValue();
							// If the new found word occurs more frequently than the old one OR they have the same frequency and the new word is lexicographically first...
							if (new_num > old_num || ( new_num == old_num && similar_word.compareTo(word_found) < 0)) {
								word_found = similar_word;
								node_found = similar_node;
							}
						}
					}
				}
			} while (word_found.equals(""));
	
			//throw new NoSimilarWordFoundException();
		}
		return word_found;
	}
	
}
