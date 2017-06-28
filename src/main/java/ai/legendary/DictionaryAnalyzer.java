package ai.legendary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class DictionaryAnalyzer {
	public double adjectivePercentWorksInPredicativeAndAttributivePositionIdentified;
	public double verbTransitivityPercentIdentified;
	public double adjectiveIsQualitative;
	public double adjectiveIsClassifying;
	public double adjectiveCommonlyPrecededWithAnd;
	public double adjectiveQuantifier;
	public double adjectiveIsProper;
	public double advIntensiferID;
	public double determinerTypeID;
	public double interjectionTypeID;
	public double nounPlurality;
	public double nounGender;
	public double nounIsCompound;
	public double nounIsCountable;
	public double nounIsProperName;
	public double nounAcceptsZeroArticle;
	public double verbTense;
	public double verbAspect;
	public double pronounPlurality;
	public double pronounGender;
	public double pronounCase;
	public double pronounType;
	public double nounAnAcronymFor;
	public double nounAnAbbreviationFor;
	public double nounAbbreviatedFrom;
	public double verbType;
	public double verbPerson;
	public double verbPhrasal;
	public double verbIsInfinitive;
	public double adjectiveComparisonType;
	public double adjectiveHasDiminutiveSuffix;
	public double adverbIsRelative;
	public double adverbIsComparative;
	public double adverbIsSuperlative;
	public double adverbIntensifier;
	public double conjunctionType;
	public double prepositionHadAdverbForm;
	public double quantifierID;
	public double adjectiveID;
	public double irregularPluralForm;
	public double noCompOrSupForm;
	public double mustUseMoreMost;
	public double amustUseMoreMost;
	public double nounBaseForm;
	public double adverbIrreg;
	public double nounanimacy;
	public double adjectiveItensifierID;
	public double nounlocation;
	public double verbBaseForm;
	public double adjectiveBaseForm;
	public double adverbBaseForm;
	public DictionaryAnalyzer (HashMap<String, Noun> nounDictionary,HashMap<String, Verb> verbDictionary,HashMap<String, Adjective> adjectiveDictionary,HashMap<String, Adverb> adverbDictionary,HashMap<String, Conjunction> conjunctionDictionary,HashMap<String, Determiner> determinerDictionary,HashMap<String, Interjection> interjectionDictionary,HashMap<String, Preposition> prepositionDictionary,HashMap<String, Pronoun> pronounDictionary,HashMap<String, Quantifier> quantifierDictionary){
		try {
		    double totalNouns =0;
		    double totalVerbs =0;
		    double totalAdverbs =0;
		    double totalAdjectives =0;
		    double totalDeterminers=0;
		    double totalInterjections=0;
		    double totalPronouns=0;
		    double totalConjunctions=0;
		    double totalPrepositions=0;
		    double totalQuantifier=0;
			
			double definedTrans = 0;
			double definedPosi = 0;
			double definedqual = 0;
			double definedclassifying = 0;
			double definedAnd = 0;
			double definedQuantifier = 0;
			double definedIsProper = 0;
            double definedAdvIntensiferID=0;
            double defineddeterminerTypeID=0;
            double definedinterjectionTypeID=0;
            double definednplurality=0;
            double definedngender =0;
            double definediscompound =0;
            double definediscountable=0;
            double definedispropername=0;
            double definedzarticle=0;
            double definedtense=0;
            double definedaspect=0;
            double definedpplurality =0;
            double definedpgender =0;
            double definedpcase=0;
            double definedptype =0;
            double definedacro =0;
            double definedabb=0;
            double definedfabb=0;
            double definedvtype=0;
            double definedvperson=0;
            double definedphrasal=0;
            double definedisinf=0;
            double definedcomp=0;
            double defineddimsuff=0;
            double definedrel=0;
            double definedacomp=0;
            double definedasup=0;
            double definedintense=0;
            double definedconj=0;
            double definedpadv=0;
            double definedQuantifierID=0;
            double definedAdjectiveID=0;
            double definedirreg=0;
            double definednocomporsup=0;
            double definedmustusemoremost=0;
            double definedamustusemoremost=0;
            double definedbaseform=0;
            double definedairreg=0;
            double definedanimacy=0;
            double definedaintens=0;
            double definedloca=0;
            double vbase = 0;
            double abase = 0;
            double advbase = 0;
            
            double nounmasc=0;
            double nounfem=0;
            double nounneut=0;
            Iterator nounit = nounDictionary.entrySet().iterator();
            while(nounit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)nounit.next();
                    String w = (String) pair.getKey();
                    Noun n = (Noun) pair.getValue();
					    totalNouns++;
					    if(!n.plurality.equals("--")){
					        definednplurality++;
					    }
					    if(!n.gender.equals("--")){
					        definedngender++;
                            if(n.gender.equals("Masculine")){
                                nounmasc++;
                            }
                            else if(n.gender.equals("Feminine")){
                                nounfem++;
                            }
                            else if(n.gender.equals("Neuter")){
                                nounneut++;
                            }
                        }
					    if(n.isCompound != null){
					        definediscompound++;
                        }
					    if(n.isCountable != null){
					        definediscountable++;
                        }
					    if(n.isProperName != null){
					        definedispropername++;
                        }
					    if(n.acceptsZeroArticle != null){
					        definedzarticle++;
                        }
					    if(!n.anAcronymFor.equals("--")){
					        definedacro++;
					    }
					    if(!n.anAbbreviationFor.equals("--")){
					        definedabb++;
                        }
					    if(!n.abbreviatedFrom.equals("--")){
					        definedfabb++;
                        }
					    if(!n.irregularPluralForm.equals("--")){
                            definedirreg++;
                        }
					    if(!n.baseForm.equals("--")){
					        definedbaseform++;
					    }
					    if(n.animacy != null){
					        definedanimacy++;
					    }
					    if(n.location != null){
					        definedloca++;
					    }
					    
            }
            Iterator verbit = verbDictionary.entrySet().iterator();
            while(verbit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)verbit.next();
                    String w = (String) pair.getKey();
                    Verb v = (Verb) pair.getValue();
					    totalVerbs++;
						if (!v.transivity.equals("--")) {
						    definedTrans++;
						}
						if (!v.tense.equals("--")) {
						    definedtense++;
                        }
						if (!v.aspect.equals("--")) {
						    definedaspect++;
                        }
						if (!v.verbType.equals("--")) {
						    definedvtype++;
                        }
						if (!v.person.equals("--")) {
						    definedvperson++;
                        }
						if (!v.phrasal.equals("--")) {
						    definedphrasal++;
                        }
						if (v.isInfinitive != null) {
						    definedisinf++;
                        }
						if(!v.baseForm.equals("--")){
						    vbase++;
						}
					}
            Iterator adjit = adjectiveDictionary.entrySet().iterator();
            while(adjit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)adjit.next();
                    String w = (String) pair.getKey();
                    Adjective a = (Adjective) pair.getValue();
					    totalAdjectives++;
						if(a.worksInPredicativePosition != null){
						    definedPosi++;
						}
						if(a.worksInAttributivePosition != null){
						    definedPosi++;
						}
						if(a.isQualitative != null){
						    definedqual++;
                        }
						if(a.isClassifying != null){
						    definedclassifying++;
                        }
						if(a.commonlyPrecededWithAnd != null){
						    definedAnd++;
                        }
						if(!a.quantifier.equals("--")){
						    definedQuantifier++;
                        }
						if(a.isProper != null){
						    definedIsProper++;
                        }
						if(!a.comparisonType.equals("--")){
						    definedcomp++;
                        }
						if(a.hasDiminutiveSuffix != null){
						    defineddimsuff++;
                        }
						if(a.adjectiveOrderID != -1){
                            definedAdjectiveID++;
                        }
						if(a.mustUseMoreMost != null){
                            definedamustusemoremost++;
                        }
						if(a.adjectiveIntensifierID != -1){
						    definedaintens++;
						}
						if(!a.baseForm.equals("--")){
						    abase++;
						}
					}
            Iterator advit = adverbDictionary.entrySet().iterator();
            while(advit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)advit.next();
                    String w = (String) pair.getKey();
                    Adverb a = (Adverb) pair.getValue();
					    totalAdverbs++;
					    if(a.advIntensiferID != -1){
					        definedAdvIntensiferID++;
                        }
					    if(a.isRelativeAdverb !=null){
					        definedrel++;
                        }
					    if(a.isComparativeAdverb !=null){
					        definedacomp++;
                        }
					    if(a.isSuperlativeAdverb !=null){
					        definedasup++;
                        }
					    if(!a.advIntensifier.equals("--")){
					        definedintense++;
                        }
					    if(a.noCompOrSuperForm != null){
                            definednocomporsup++;
                        }
                        if(a.mustUseMoreMost != null){
                            definedmustusemoremost++;
                        }
                        if(!a.irregularForm.equals("--")){
                            definedairreg++;
                        }
                        if(!a.baseForm.equals("--")){
                            advbase++;
                        }
					}
            Iterator detit = determinerDictionary.entrySet().iterator();
            while(detit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)detit.next();
                    String w = (String) pair.getKey();
                    Determiner d = (Determiner) pair.getValue();
					    totalDeterminers++;
                        if(d.determinerTypeID != -1){
                            defineddeterminerTypeID++;
                        }
                    }
            Iterator intit = interjectionDictionary.entrySet().iterator();
            while(intit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)intit.next();
                    String w = (String) pair.getKey();
                    Interjection i = (Interjection) pair.getValue();
					    totalInterjections++;
                        if(i.interjectionTypeID != -1){
                            definedinterjectionTypeID++;
                        }
                    }
            Iterator proit = pronounDictionary.entrySet().iterator();
            while(proit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)proit.next();
                    String w = (String) pair.getKey();
                    Pronoun p = (Pronoun) pair.getValue();
					    totalPronouns++;
                        if(!p.plurality.equals("--")){
                            definedpplurality++;
                        }
                        if(!p.gender.equals("--")){
                            definedpgender++;
                        }
                        if(!p.pronounCase.equals("--")){
                            definedpcase++;
                        }
                        if(!p.type.equals("--")){
                            definedptype++;
                        }
                    }
            Iterator conjit = conjunctionDictionary.entrySet().iterator();
            while(conjit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)conjit.next();
                    String w = (String) pair.getKey();
                    Conjunction c = (Conjunction) pair.getValue();
					    totalConjunctions++;
					    if(!c.conjunctionType.equals("--")){
					        definedconj++;
					    }
					}
            Iterator prepit = prepositionDictionary.entrySet().iterator();
            while(prepit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)prepit.next();
                    String w = (String) pair.getKey();
                    Preposition p = (Preposition) pair.getValue();
					    totalPrepositions++;
					    if(p.hasAdverbForm != null){
					        definedpadv++;
					    }
					}
            Iterator quantit = quantifierDictionary.entrySet().iterator();
            while(quantit.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)quantit.next();
                    String w = (String) pair.getKey();
                    Quantifier q = (Quantifier) pair.getValue();
					    totalQuantifier++;
					    if(q.quantifierID != -1){
					        definedQuantifierID++;
					    }
					}
				
				
			
			File f = new File("README.md");
			FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter printer = new PrintWriter(bw);
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            
            System.out.println("Check README.md for run details");
            printer.println("## Analysis");
			/*Nouns: */
			printer.println("### Noun Analysis (Total Nouns: "+ NumberFormat.getNumberInstance(Locale.US).format(totalNouns)+ "):");
			/*Noun plurality*/
			nounPlurality = (definednplurality/totalNouns);
			printer.println("- Percentage of Noun Plurality defined: "+df.format((nounPlurality*100))+"%");
			/*Noun gender*/
            nounGender = (definedngender/totalNouns);
            printer.println("- Percentage of Noun Gender defined: "+df.format((nounGender*100)) +"%"+ "("+df.format(((nounmasc/totalNouns)*100))+"% Masculine "+df.format(((nounfem/totalNouns)*100)) + "% Feminine " + df.format(((nounneut/totalNouns)*100)) +"% Neuter)");
            /*Noun iscompound*/
            nounIsCompound = (definediscompound/totalNouns);
            printer.println("- Percentage of Noun isCompound defined: "+df.format((nounIsCompound*100))+"%");
            /*Noun iscountable*/
            nounIsCountable = (definediscountable/totalNouns);
            printer.println("- Percentage of Noun isCountable defined: "+df.format((nounIsCountable*100))+"%");
            /*Noun ispropername*/
            nounIsProperName = (definedispropername/totalNouns);
            printer.println("- Percentage of Noun isProperName defined: "+df.format((nounIsProperName*100))+"%");
            /*Noun acceptsZeroarticle*/
            nounAcceptsZeroArticle = (definedzarticle/totalNouns);
            printer.println("- Percentage of Noun acceptsZeroArticle defined: "+df.format((nounAcceptsZeroArticle*100))+"%");
            /*Noun anAccronymFor*/
            nounAnAcronymFor = (definedacro/totalNouns);
            printer.println("- Percentage of Noun acronymFor defined: "+df.format((nounAnAcronymFor*100))+"%");
            /*Noun anAbbreviationFor*/
            nounAnAbbreviationFor = (definedabb/totalNouns);
            printer.println("- Percentage of Noun abbreviationFor defined: "+df.format((nounAnAbbreviationFor*100))+"%");
            /*Noun abbreviatedFrom*/
            nounAbbreviatedFrom = (definedfabb/totalNouns);
            printer.println("- Percentage of Noun abbreviatedFrom defined: "+df.format((nounAbbreviatedFrom*100))+"%");
            /*Noun irregularPluralForm*/
            irregularPluralForm = (definedirreg/totalNouns);
            printer.println("- Percentage of Noun irregularPluralForm defined: "+df.format((irregularPluralForm*100))+"%");
            /*Noun base form*/
            nounBaseForm = (definedbaseform/totalNouns);
            printer.println("- Percentage of Noun base form defined: "+df.format((nounBaseForm*100))+"%");
            /*Noun animacy*/
            nounanimacy = (definedanimacy/totalNouns);
            printer.println("- Percentage of Noun Animacy defined: "+df.format((nounanimacy*100))+"%");
            /*Noun location*/
            nounlocation = (definedloca/totalNouns);
            printer.println("- Percentage of Noun Location defined: "+df.format((nounlocation*100))+"%");
            
            
			/*Verbs:*/
			printer.println("### Verb analysis (Total Verbs: "+NumberFormat.getNumberInstance(Locale.US).format(totalVerbs)+ "):");
			/*Transitivity percentage*/
			verbTransitivityPercentIdentified = (definedTrans/totalVerbs);
			printer.println("- Percentage of Transivity Defined: "+df.format((verbTransitivityPercentIdentified*100))+"%");
			/*verb tense percentage*/
            verbTense = (definedtense/totalVerbs);
            printer.println("- Percentage of verb Tense Defined: "+df.format((verbTense*100))+"%");
            /*verb aspect percentage*/
            verbAspect = (definedaspect/totalVerbs);
            printer.println("- Percentage of verb aspect Defined: "+df.format((verbAspect*100))+"%");
            /*verb type percentage*/
            verbType = (definedvtype/totalVerbs);
            printer.println("- Percentage of verb type Defined: "+df.format((verbType*100))+"%");
            /*verb person percentage*/
            verbPerson = (definedvperson/totalVerbs);
            printer.println("- Percentage of verb person Defined: "+df.format((verbPerson*100))+"%");
            /*verb phrasal percentage*/
            verbPhrasal = (definedphrasal/totalVerbs);
            printer.println("- Percentage of verb phrasal Defined: "+df.format((verbPhrasal*100))+"%");
            /*verb is infinitive percentage*/
            verbIsInfinitive = (definedisinf/totalVerbs);
            printer.println("- Percentage of verb isInfinitive Defined: "+df.format((verbIsInfinitive*100))+"%");
            /*verb is base form*/
            verbBaseForm = (vbase/totalVerbs);
            printer.println("- Percentage of verb base form Defined: "+df.format((verbBaseForm*100))+"%");
			
			
			/*Adjectives:*/
			printer.println("### Adjective analysis (Total Adjectives: "+NumberFormat.getNumberInstance(Locale.US).format(totalAdjectives)+"):");
			/*Position (Works in predicative and Attributive)*/
			adjectivePercentWorksInPredicativeAndAttributivePositionIdentified = (definedPosi/totalAdjectives);
			printer.println("- Percentage of Position (Works in predicative and Attributive) Defined for Adjectives : "+df.format((adjectivePercentWorksInPredicativeAndAttributivePositionIdentified*100)) + "%");
			/*Is Qualitative*/
			adjectiveIsQualitative = definedqual/totalAdjectives;
			printer.println("- Percentage of isQualitative defined: "+df.format((adjectiveIsQualitative*100))+"%");
			/*Is Classifying*/
			adjectiveIsClassifying = definedclassifying/totalAdjectives;
			printer.println("- Percentage of isClassifying defined: "+df.format((adjectiveIsClassifying*100))+"%");
			/*Is Commonly Preceded with and*/
            adjectiveIsClassifying = definedAnd/totalAdjectives;
            printer.println("- Percentage of commonlyPrecededWithAnd defined: "+df.format((adjectiveCommonlyPrecededWithAnd*100))+"%");
            /*Adjective Quantifier*/
            adjectiveQuantifier = definedQuantifier/totalAdjectives;
            printer.println("- Percentage of Quantifier defined: "+df.format((adjectiveQuantifier*100))+"%");
            /*Adjective IsProper*/
            adjectiveIsProper = definedIsProper/totalAdjectives;
            printer.println("- Percentage of IsProper defined: "+df.format((adjectiveIsProper*100))+"%");
            /*Adjective comparison type*/
            adjectiveComparisonType = definedcomp/totalAdjectives;
            printer.println("- Percentage of comparison type defined: "+df.format((adjectiveComparisonType*100))+"%");
            /*Adjective has diminutive suffix*/
            adjectiveHasDiminutiveSuffix = defineddimsuff/totalAdjectives;
            printer.println("- Percentage of hasDiminutiveSuffix defined: "+df.format((adjectiveHasDiminutiveSuffix*100))+"%");
            /*Adjective ID*/
            adjectiveID = definedAdjectiveID/totalAdjectives;
            printer.println("- Percentage of ajectiveOrderID defined: "+df.format((adjectiveID*100))+"%");
            /*Adjective must use more most*/
            amustUseMoreMost = definedamustusemoremost/totalAdjectives;
            printer.println("- Percentage of mustUseMoreMost defined: "+df.format((amustUseMoreMost*100))+"%");
            /*Adjective intensifier*/
            adjectiveItensifierID = definedaintens/totalAdjectives;
            printer.println("- Percentage of adjectiveItensifierID defined: "+df.format((adjectiveItensifierID*100))+"%");
            /*Adjective base form*/
            adjectiveBaseForm = abase/totalAdjectives;
            printer.println("- Percentage of base form defined: "+df.format((adjectiveBaseForm*100))+"%");
            
            /*Adverbs:*/
            printer.println("### Adverb Analysis (Total Adverbs: "+NumberFormat.getNumberInstance(Locale.US).format(totalAdverbs)+"): ");
            /*Adverb advIdentifier*/
            advIntensiferID = definedAdvIntensiferID/totalAdverbs;
            printer.println("- Percentage of IntensiferId defined: "+df.format((advIntensiferID*100))+"%");
            /*Adverb is relative*/
            adverbIsRelative = definedrel/totalAdverbs;
            printer.println("- Percentage of isRelativeAdverb defined: "+df.format((adverbIsRelative*100))+"%");
            /*Adverb is comparative*/
            adverbIsComparative = definedacomp/totalAdverbs;
            printer.println("- Percentage of isComparativeAdverb defined: "+df.format((adverbIsComparative*100))+"%");
            /*Adverb is superlative*/
            adverbIsSuperlative = definedasup/totalAdverbs;
            printer.println("- Percentage of isSuperlativeAdverb defined: "+df.format((adverbIsSuperlative*100))+"%");
            /*Adverb intensifier*/
            adverbIntensifier = definedintense/totalAdverbs;
            printer.println("- Percentage of advIntensifier defined: "+df.format((adverbIntensifier*100))+"%");
            /*Adverb no comp or sup*/
            noCompOrSupForm = definednocomporsup/totalAdverbs;
            printer.println("- Percentage of noCompOrSuperForm defined: "+df.format((noCompOrSupForm*100))+"%");
            /*Adverb must use more most*/
            mustUseMoreMost = definedmustusemoremost/totalAdverbs;
            printer.println("- Percentage of mustUseMoreMost defined: "+df.format((mustUseMoreMost*100))+"%");
            /*Adverb irregular form*/
            adverbIrreg = definedairreg/totalAdverbs;
            printer.println("- Percentage of Irregular Form defined: "+df.format((adverbIrreg*100))+"%");
            /*Adverb base form*/
            adverbBaseForm = advbase/totalAdverbs;
            printer.println("- Percentage of base form defined: "+df.format((adverbBaseForm*100))+"%");
            
            
            
            /*Determiners:*/
            printer.println("### Determiner Analysis (Total Determiners: "+NumberFormat.getNumberInstance(Locale.US).format(totalDeterminers)+"): ");
            /*Determiner determinerTypeID*/
            determinerTypeID = defineddeterminerTypeID/totalDeterminers;
            printer.println("- Percentage of determinerTypeID defined: "+df.format((determinerTypeID*100))+"%");
            
            
            /*Interjections:*/
            printer.println("### Interjection Analysis (Total Interjections: "+NumberFormat.getNumberInstance(Locale.US).format(totalInterjections)+"): ");
            /*Interjection interjectionTypeID*/
            interjectionTypeID = definedinterjectionTypeID/totalInterjections;
            printer.println("- Percentage of interjectionTypeID defined: "+df.format((interjectionTypeID*100))+"%");
            
           
            /*Pronouns:*/
            printer.println("### Pronoun Analysis (Total Pronouns: "+NumberFormat.getNumberInstance(Locale.US).format(totalPronouns)+"): ");
            /*Pronoun plurality*/
            pronounPlurality = definedpplurality/totalPronouns;
            printer.println("- Percentage of pronounPlurality defined: "+df.format((pronounPlurality*100))+"%");
            /*Pronoun gender*/
            pronounGender = definedpgender/totalPronouns;
            printer.println("- Percentage of pronounGender defined: "+df.format((pronounGender*100))+"%");
            /*Pronoun case*/
            pronounCase = definedpcase/totalPronouns;
            printer.println("- Percentage of pronounCase defined: "+df.format((pronounCase*100))+"%");
            /*Pronoun case*/
            pronounType = definedptype/totalPronouns;
            printer.println("- Percentage of pronounType defined: "+df.format((pronounType*100))+"%");
            
            
            /*Conjunctions:*/
            printer.println("### Conjunction Analysis (Total Conjunctions: "+NumberFormat.getNumberInstance(Locale.US).format(totalConjunctions)+"): ");
            /*Conjunction conjunctionType*/
            conjunctionType = definedconj/totalConjunctions;
            printer.println("- Percentage of conjunctionType defined: "+df.format((conjunctionType*100))+"%");
            
            
            /*Prepositions:*/
            printer.println("### Preposition Analysis (Total Prepositions: "+NumberFormat.getNumberInstance(Locale.US).format(totalPrepositions)+"): ");
            /*Preposition hasAdverbForm*/
            prepositionHadAdverbForm = definedpadv/totalPrepositions;
            printer.println("- Percentage of hadAdverbForm defined: "+df.format((prepositionHadAdverbForm*100)) +"%");
            
            /*Quantifiers:*/
            printer.println("### Quantifier Analysis (Total Quantifiers: "+NumberFormat.getNumberInstance(Locale.US).format(totalQuantifier)+"): ");
            /*Preposition hasAdverbForm*/
            quantifierID = definedQuantifierID/totalQuantifier;
            printer.println("- Percentage of quantifierID defined: "+df.format((quantifierID*100)) +"%");

            bw.close();
            fw.close();
            printer.close();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
