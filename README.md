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
### Verb analysis (Total Verbs: 28,930):
- Percentage of Transivity Defined: 71.87%
- Percentage of verb Tense Defined: 43.57%
- Percentage of verb aspect Defined: 0%
- Percentage of verb type Defined: 0.1%
- Percentage of verb person Defined: 0.07%
- Percentage of verb phrasal Defined: 100%
- Percentage of verb isInfinitive Defined: 0.1%
- Percentage of verb base form Defined: 100%
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
- The WriteDictionary class takes all of the inputs and writes them to .tsv files and hashmaps.
- The DictionaryAccess class provides access to many features of the dictionary. There are getter methods for each partOfSpeech dictionary ex. getNounDictionary. getWordInfo(word) allows for a term look up and returns all part of speech's for the word, while getWordInfo(word,pos) returns the info on a specific part of speech. getMultipleWordInfo(sentence) uses coreNLP to lookup words based on the part of speech in the sentence. DictionaryAccess also has a changePOS method which converts a word from one part of speech to another. It also has a light verb converter which paraphrases from light verb sentences and to light verb sentences. You can either feed it a whole sentence or the parts of the sentence.
- (Look at this class to get started -->) The Accessor class is an example usage of the DictionaryAccess class and the Reformatter class.
- This repository also contains access to Most Common Lists based on 2grams and 3grams, which are written in the access2gram class.
- The Reformatter class allows the user to reformat a word depending on the words in the dictionary and the method called. These methods are based off of morphology and the words contained in the dictionary. There is a list of existing methods in the Accesser class under the getAllReformatted method.
## Document Implementation
- subordinateConjunctions.txt Implemented
- nounGenderList.txt Implemented 
- First_Names.ser and Last_Names.ser and Proper_Places.ser Implemented
- default-lexicon.xml Implemented
- NounList_CountNounsOnly.txt and NounsList_MassNounsOnly.txt Implemented
- MobyWordListWithPOS.txt Implemented
- AdverbScales-Manual.csv Implemented
- locations.txt implemented
- verbnet implemented
- ADJADV.txt from nombank implemented
- propBank implemented
- shapes.csv implemented
- fn16lexunits.ttl implemented
- colors.txt implemented
- LEXICON.txt implemented
- Word_frequency_list.txt implemented
- Word Scales have been implemented
## Document Output Formats:
- tsv files for each part of speech with words and their properties
- serialized dictionary HashMap's for each part of speech
- Lists of the most common word combinations in the format pos2 : {pos1=howCommon}
## Current Dictionary Write Time: 142 minutes and 33 seconds
