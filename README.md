# DictionaryMaker
## Analysis
### Noun Analysis (Total Nouns: 247,148):
- Percentage of Noun Plurality defined: 3.87%
- Percentage of Noun Gender defined: 100%(4.14% Masculine 3.15% Feminine 92.73% Neuter)
- Percentage of Noun isCompound defined: 100%
- Percentage of Noun isCountable defined: 30.64%
- Percentage of Noun isProperName defined: 63.95%
- Percentage of Noun acceptsZeroArticle defined: 0%
- Percentage of Noun acronymFor defined: 0%
- Percentage of Noun abbreviationFor defined: 0%
- Percentage of Noun abbreviatedFrom defined: 0%
- Percentage of Noun irregularPluralForm defined: 0.36%
- Percentage of Noun base form defined: 100%
- Percentage of Noun Animacy defined: 100%
- Percentage of Noun Location defined: 43.52%
### Verb analysis (Total Verbs: 32,104):
- Percentage of Transivity Defined: 64.77%
- Percentage of verb Tense Defined: 39.26%
- Percentage of verb aspect Defined: 0%
- Percentage of verb type Defined: 0.09%
- Percentage of verb person Defined: 0.06%
- Percentage of verb phrasal Defined: 100%
- Percentage of verb isInfinitive Defined: 0.09%
- Percentage of verb base form Defined: 90.12%
- Percentage of verbIntensifierID Defined: 0%
### Adjective analysis (Total Adjectives: 50,723):
- Percentage of Position (Works in predicative and Attributive) Defined for Adjectives : 74.15%
- Percentage of isQualitative defined: 37.51%
- Percentage of isClassifying defined: 37.51%
- Percentage of commonlyPrecededWithAnd defined: 0%
- Percentage of Quantifier defined: 0%
- Percentage of IsProper defined: 100%
- Percentage of comparison type defined: 100%
- Percentage of hasDiminutiveSuffix defined: 100%
- Percentage of ajectiveOrderID defined: 5.72%
- Percentage of mustUseMoreMost defined: 36.96%
- Percentage of adjectiveItensifierID defined: 1.21%
- Percentage of base form defined: 99.72%
### Adverb Analysis (Total Adverbs: 13,478): 
- Percentage of IntensiferId defined: 5.42%
- Percentage of isRelativeAdverb defined: 100%
- Percentage of isComparativeAdverb defined: 0.8%
- Percentage of isSuperlativeAdverb defined: 0.8%
- Percentage of advIntensifier defined: 5.42%
- Percentage of noCompOrSuperForm defined: 29.03%
- Percentage of mustUseMoreMost defined: 29.03%
- Percentage of Irregular Form defined: 0.05%
- Percentage of base form defined: 99.89%
- Percentage of wordSenseID defined: 16.48%
### Determiner Analysis (Total Determiners: 11): 
- Percentage of determinerTypeID defined: 100%
### Interjection Analysis (Total Interjections: 428): 
- Percentage of interjectionTypeID defined: 0%
### Pronoun Analysis (Total Pronouns: 118): 
- Percentage of pronounPlurality defined: 100%
- Percentage of pronounGender defined: 100%
- Percentage of pronounCase defined: 100%
- Percentage of pronounType defined: 100%
### Conjunction Analysis (Total Conjunctions: 109): 
- Percentage of conjunctionType defined: 100%
### Preposition Analysis (Total Prepositions: 166): 
- Percentage of hadAdverbForm defined: 0%
### Quantifier Analysis (Total Quantifiers: 45): 
- Percentage of quantifierID defined: 100%
## Dictionary Overview
 This dictionary is a compilation of multiple lexicons and their data. The lexicons that are inside of the dictionary can be seen in the inputs folder, and under the Document Implementation section. The problems with this dictionary and the dictionary access methods are mainly to do with the inability to get 100% accurate base words, and not having 100% of the data we need. Base words are found by removing suffixes and then checking if the word that is left is contained in the dictionary. This leads to some problems. Either the original base word is not in the dictionary so suffixes and prefixes are not removed, or meaningful suffixes and prefixes are removed, causing words to have incorrect base words. However, we concluded that this method was the most accurate way to identify these base words. Keep this in mind when using methods that convert words, and possibly implement a backoff check of your own. The Analysis section shows the percentage of each field that we have defined. This gives you an idea of how useful the dictionary will be for your personal needs.

 JavaDoc: https://toddsandberg.github.io/Dictionary/doc/
## Accessing the Dictionary
- The WriteDictionary class takes all of the inputs and writes them to .tsv files and hashmaps. To rewrite the dictionary simply run this class.
- The DictionaryAccess class provides access to many features of the dictionary. There are getter methods for each partOfSpeech dictionary ex. getNounDictionary. getWordInfo(word) allows for a term look up and returns all part of speech's for the word, while getWordInfo(word,pos) returns the info on a specific part of speech. getMultipleWordInfo(sentence) uses coreNLP to lookup words based on the part of speech in the sentence. DictionaryAccess also has a changePOS method which converts a word from one part of speech to another. It also has a light verb converter which paraphrases from light verb sentences and to light verb sentences. You can either feed it a whole sentence or the parts of the sentence.
- (Look at this class to get started -->) The Accessor class is an example usage of the DictionaryAccess class and the Reformatter class.
- This repository also contains access to Most Common Lists based on 2grams and 3grams, which are written in the access2gram class.
- The Reformatter class allows the user to reformat a word depending on the words in the dictionary and the method called. These methods are based off of morphology and the words contained in the dictionary. There is a list of existing methods in the Accesser class under the getAllReformatted method.
- The various part of speech classes act as containers for the properties of words.
- MorphologyFinder is used to find the base word of each word in the dictionary.
## Building the Dictionary
To build the dictionary, all one needs to do is run the WriteDictionary class. However, this is usually unnecessary, since the dictionary comes prebuilt in the form of HashMaps and tsv's in the output's folder. The only times you would need to rebuild the dictionary is if the files got corrupted, or you wanted to add a document to it. To add a document you should read each word from the document. For each word you need to create a part of speech class that correlates with it, then call the merge(word,pos) method. This will automatically merge your words properties into the existing dictionary. It is recommended that you create a new method in WriteDictionary that reads in the document and merges, then call said method from the main.
## Document Output Formats:
- tsv files for each part of speech with words and their properties
- serialized dictionary HashMap's for each part of speech
- Lists of the most common word combinations in the format pos2 : {pos1=howCommon}
## Current Dictionary Write Time: 162 minutes and 40 seconds
