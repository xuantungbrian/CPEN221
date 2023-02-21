package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import cpen221.mp1.sentiments.SentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.*;
import java.net.URL;

public class Document {

    /* ------- Task 0 ------- */
    /*  all the basic things  */
    private List<String> sentenceList;
    private List<String> wordList;
    private String documentId;
    private String mostPosSentence;
    private String mostNegSentence;

    /**
     * Create a new document using a URL.
     *
     * @param docId  the document identifier
     * @param docURL the URL with the contents of the document
     */
    public Document(String docId, URL docURL) {
        documentId = docId;
        String document = "";

        try {
            String documentURL = docURL.toString();
            Scanner urlScanner = new Scanner(new URL(documentURL).openStream());
            while (urlScanner.hasNext()) {

                String fileLine = urlScanner.nextLine();
                fileLine = fileLine.trim();
                document = document.concat(" ");
                document = document.concat(fileLine);
                sentenceList = sentenceListConverter(document);
                wordList = wordListConverter(sentenceList);
            }
        } catch (IOException ioe) {
            System.out.println("Problem reading from URL!");
        }
    }

    /**
     * @param docId    the document identifier
     * @param fileName the name of the file with the contents of
     *                 the document
     */
    public Document(String docId, String fileName) {
        try {
            documentId = docId;
            String document = "";

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            for (String fileLine = reader.readLine();
                 fileLine != null;
                 fileLine = reader.readLine()) {

                // Replaces white spaces with normal space
                fileLine = fileLine.trim();

                document = document.concat(" ");
                document = document.concat(fileLine);

                sentenceList = sentenceListConverter(document);
                wordList = wordListConverter(sentenceList);
            }
            reader.close();
        } catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }

    }

    /**
     * Obtain the identifier for this document.
     *
     * @return the identifier for this document
     */
    public String getDocId() {
        return documentId;
    }

    /**
     * @param docString /String containing the document.
     *
     * @return sentenceList    the list</String> containing each sentence.
     */
    //The code here was implemented from Notion.
    private static List<String> sentenceListConverter(String docString) {
        List<String> sentenceList = new ArrayList();

        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(docString);

        int start = iterator.first();

        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {

            String sentence = docString.substring(start, end);
            sentence = sentence.trim();
            sentenceList.add(sentence);
        }

        return sentenceList;
    }

    /**
     * @param sentenceL the list</String> containing sentences.
     *
     * @return list2    the list</String> containing individual words.
     */


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

    /* ------- Task 1 ------- */

    /**
     * Calculates the average word length.
     *
     * @return sum of all the characters in the word list / the number of words
     */
    public double averageWordLength() {
        int sum = 0;
        for (String index : wordList) {
            sum += index.length();
        }
  
        return (double) sum / wordList.size();
    }

    /**
     * Calculates the unique word ratio.
     *
     * @return the total number of unique words (multiples not included) / the total number of words
     */
    public double uniqueWordRatio() {
        List<String> unique = new ArrayList<>();
        for (String index : wordList) {
            if (!unique.contains(index)) {
                unique.add(index);
            }
        }

        return (double) unique.size() / wordList.size();
    }

    /**
     * Calculates the hapax legomana ratio.
     *
     * @return the total number of unique words  / the total number of words
     */
    public double hapaxLegomanaRatio() {
        int unique = 0;
        for (int i = 0; i < wordList.size(); i++) {
            int sum = 0;
            for (int j = 0; j < wordList.size(); j++) {
                String str1 = wordList.get(i);
                String str2 = wordList.get(j);
                if (str1.equals(str2)) {
                    sum++;
                }
            }
            if (sum == 1) {
                unique++;
            }
        }

        return (double) unique / wordList.size();
    }

    /* ------- Task 2 ------- */

    /**
     * Obtain the number of sentences in the document.
     *
     * @return the number of sentences in the document
     */
    public int numSentences() {
        return sentenceList.size();
    }

    /**
     * Obtain a specific sentence from the document.
     * Sentences are numbered starting from 1.
     *
     * @param sentence_number the position of the sentence to retrieve,
     *                        {@code 1 <= sentence_number <= this.getSentenceCount()}
     * @return the sentence indexed by {@code sentence_number}
     */
    public String getSentence(int sentence_number) {
        return sentenceList.get(sentence_number - 1);
    }

    /**
     * Obtain the average sentence length of a document.
     *
     * @return the total number of words / the total number of sentences
     */
    public double averageSentenceLength() {
        return (double) wordList.size() / sentenceList.size();
    }

    /**
     * Obtain the average sentence complexity.
     *
     * @return the total number of phrases / the total number of sentences
     */
    public double averageSentenceComplexity() {
        int phrases = 0;
        for (String str : sentenceList) {
            for (char c : str.toCharArray()) {
                if (c == ';' || c == ':' || c == ',') {
                    phrases++;
                }
            }
        }

        return (double) (phrases + sentenceList.size()) / sentenceList.size();
    }

    /* ------- Task 3 ------- */

//    To implement these methods while keeping the class
//    small in terms of number of lines of code,
//    implement the methods fully in sentiments.SentimentAnalysis
//    and call those methods here. Use the getSentence() method
//    implemented in this class when you implement the methods
//    in the SentimentAnalysis class.

//    Further, avoid using the Google Cloud AI multiple times for
//    the same document. Save the results (cache them) for
//    reuse in this class.

//    This approach illustrates how to keep classes small and yet
//    highly functional.

    /**
     * Obtain the sentence with the most positive sentiment in the document
     *
     * @return the sentence with the most positive sentiment in the
     * document; when multiple sentences share the same sentiment value
     * returns the sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a positive sentiment
     */
    public String getMostPositiveSentence() throws NoSuitableSentenceException {
        try {
            if (mostPosSentence != null) {
                return mostPosSentence;
            }
            if (SentimentAnalysis.getMostPositiveSentence(this) != null) {
                mostPosSentence = SentimentAnalysis.getMostPositiveSentence(this);
                return mostPosSentence;
            } else {
                throw new NoSuitableSentenceException();
            }
        }
        catch (NoSuitableSentenceException e){
            String statement="ERROR.No positive sentences.";
            return statement;
        }
    }

    /**
     * Obtain the sentence with the most negative sentiment in the document
     *
     * @return the sentence with the most negative sentiment in the document;
     * when multiple sentences share the same sentiment value, returns the
     * sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a negative sentiment
     */
    public String getMostNegativeSentence() throws NoSuitableSentenceException {
        try {
            if (mostNegSentence != null) {
                return mostNegSentence;
            }
            if (SentimentAnalysis.getMostNegativeSentence(this) != null) {
                mostNegSentence = SentimentAnalysis.getMostNegativeSentence(this);
                return mostNegSentence;
            } else {
                throw new NoSuitableSentenceException();
            }
        }
        catch (NoSuitableSentenceException e){
            String statement="ERROR.No negative sentences.";
            return statement;
        }
    }
}

