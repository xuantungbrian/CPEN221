package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentSimilarity {

    /* DO NOT CHANGE THESE WEIGHTS */
    private final int WT_AVG_WORD_LENGTH = 5;
    private final int WT_UNIQUE_WORD_RATIO = 15;
    private final int WT_HAPAX_LEGOMANA_RATIO = 25;
    private final int WT_AVG_SENTENCE_LENGTH = 1;
    private final int WT_AVG_SENTENCE_CPLXTY = 4;
    private final int WT_JS_DIVERGENCE = 50;
    /* ---- END OF WEIGHTS ------ */

    /* ------- Task 4 ------- */

    /**
     * Compute the Jensen-Shannon Divergence between the given documents
     *
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Jensen-Shannon Divergence between the given documents
     */
    public double jsDivergence(Document doc1, Document doc2) {
        List<String> containsWord = new ArrayList();
        List<String> sentenceList1 = new ArrayList();
        List<String> sentenceList2 = new ArrayList();
        List<String> wordList1 = new ArrayList();
        List<String> wordList2 = new ArrayList();
        for (int i = 0; i < doc1.numSentences(); i++) {
            sentenceList1.add(doc1.getSentence(i+1));
        }
        for (int i = 0; i < doc2.numSentences(); i++) {
            sentenceList2.add(doc2.getSentence(i+1));
        }
        wordList1 = wordListConverter(sentenceList1);
        wordList2 = wordListConverter(sentenceList2);
        double sum = 0;

        for (String words1 : wordList1) {
            if (containsWord.contains(words1) != true) {

                double probability1 = (double) wordCounter(wordList1, words1) / wordList1.size();
                double probability2 = (double) wordCounter(wordList2, words1) / wordList2.size();
                double mTotal = (probability1 + probability2) / 2;

                if (probability2 != 0) {
                    sum += (double) (probability1 * Math.log(probability1 / mTotal) / Math.log(2)) + (double) (probability2 * Math.log(probability2 / mTotal) / Math.log(2));
                    containsWord.add(words1);
                } else {
                    sum += (double) (probability1 * Math.log(probability1 / mTotal) / Math.log(2));
                    containsWord.add(words1);
                }
            }
        }
        for (String words2 : wordList2) {
            if (containsWord.contains(words2) != true) {

                double probability1 = (double) wordCounter(wordList1, words2) / wordList1.size();
                double probability2 = (double) wordCounter(wordList2, words2) / wordList2.size();
                double mTotal = (probability1 + probability2) / 2.0;

                if (probability1 != 0) {
                    sum += (double) (probability1 * Math.log(probability1 / mTotal) / Math.log(2)) + (double) (probability2 * Math.log(probability2 / mTotal) / Math.log(2));
                    containsWord.add(words2);
                } else {
                    sum += (double) (probability2 * Math.log(probability2 / mTotal) / Math.log(2));
                    containsWord.add(words2);
                }
            }
        }

        return sum / 2.0;
    }

    /**
     * Compute the Document Divergence between the given documents
     *
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Document Divergence between the given documents
     */
    public double documentDivergence(Document doc1, Document doc2) {

        double sentenceLength = (WT_AVG_SENTENCE_LENGTH * Math.abs(doc1.averageSentenceLength() - doc2.averageSentenceLength()));
        double sentenceComplexity = (WT_AVG_SENTENCE_CPLXTY * Math.abs(doc1.averageSentenceComplexity() - doc2.averageSentenceComplexity()));
        double averageWordLength = (WT_AVG_WORD_LENGTH * Math.abs(doc1.averageWordLength() - doc2.averageWordLength()));
        double uniqueWordRatio = (WT_UNIQUE_WORD_RATIO * Math.abs(doc1.uniqueWordRatio() - doc2.uniqueWordRatio()));
        double hapaxLegomana = (WT_HAPAX_LEGOMANA_RATIO * Math.abs(doc1.hapaxLegomanaRatio() - doc2.hapaxLegomanaRatio()));
        double divergence = sentenceLength + sentenceComplexity + averageWordLength + uniqueWordRatio + hapaxLegomana + WT_JS_DIVERGENCE * jsDivergence(doc1, doc2);

        return divergence;
    }

    private int wordCounter(List<String> wordList, String word) {
        int index = 0;
        for (String i : wordList) {
            if (i.equals(word)) {
                index++;
            }
        }
        return index;
    }

    private static List<String> wordListConverter(List<String> sentenceL) {
        List<String> list2 = new ArrayList();

        for (String sentenceStr : sentenceL) {
            sentenceStr = sentenceStr.toLowerCase();
            String[] indWord = sentenceStr.split("\\s+");

            for (int i = 0; i < indWord.length; i++) {
                if (checkLower(indWord[i])) {
                    indWord[i] = indWord[i].replaceAll("[^\\w-'`#]", "");
                    indWord[i] = indWord[i].trim();
                    list2.add(indWord[i]);
                }
            }
        }

        return list2;
    }

    /**
     * Checks if the word contains a lower case letter.
     *
     * @return true or false
     */
    private static boolean checkLower(String check) {
        for (char c : check.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }

        return false;
    }
}