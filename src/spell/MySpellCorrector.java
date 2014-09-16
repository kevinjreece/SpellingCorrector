package spell;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MySpellCorrector implements SpellCorrector {
	
	public void main(String[] argv) {
		try {
			MyTrie dict = new MyTrie();
			dict.loadDict(argv[0]);
			dict.printDict(argv[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
