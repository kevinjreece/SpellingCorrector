package spell;

import java.io.IOException;

import spell.SpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		//String dict2name = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		MySpellCorrector corrector = new MySpellCorrector();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		
		//MySpellCorrector corrector2 = new MySpellCorrector();
		//corrector2.useDictionary(dict2name);
		//compareDictionaries(corrector, corrector2);
		
		System.out.println("Suggestion is: " + suggestion);
	}
	
	public static void compareDictionaries(MySpellCorrector num1, MySpellCorrector num2) {
		String same = num1.equals(num2) ? "Same" : "Different";
		System.out.println(same);
		return;
	}

}
