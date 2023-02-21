package cpen221.mp1.sentiments;

import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import cpen221.mp1.exceptions.NoSuitableSentenceException;

import java.io.IOException;

public class SentimentAnalysis {
    /**
     * Obtain the sentence with the most positive sentiment in the document
     *
     * @return the sentence with the most positive sentiment in the
     * document; when multiple sentences share the same sentiment value
     * returns the sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a positive sentiment
     */
    public static String getMostPositiveSentence(cpen221.mp1.Document doc1)throws NoSuitableSentenceException{

        int numberOfSentences=doc1.numSentences();
        double mostPositive = -2;
        int indexTracker=0;
        int positiveIndex=0;

        try {
            for (int i = 1; i <= numberOfSentences; i++) {
                String text = doc1.getSentence(i);
                try (LanguageServiceClient language = LanguageServiceClient.create()) {
                    Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
                    AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
                    Sentiment sentiment = response.getDocumentSentiment();
                    if (sentiment != null) {
                        if (mostPositive <= sentiment.getScore()) {
                            mostPositive = sentiment.getScore();
                            indexTracker = i;
                        }
                    }
                } catch (IOException ioe) {
                    System.out.println(ioe);
                    throw new RuntimeException("Unable to communicate with Sentiment Analyzer!");
                }
                if (mostPositive >= 0.3) {
                    positiveIndex++;
                }
            }
            if (positiveIndex == 0) {
                throw new NoSuitableSentenceException();
            }
            return doc1.getSentence(indexTracker);
        }
        catch (NoSuitableSentenceException e){
            return null;
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
    public static String getMostNegativeSentence(cpen221.mp1.Document doc1)
            throws NoSuitableSentenceException {
        int numberOfSentences=doc1.numSentences();
        double mostNegative = 2;
        int indexTracker=0;
        int negativeIndex=0;

        try {
            for (int i = 1; i <= numberOfSentences; i++) {
                String text = doc1.getSentence(i);
                try (LanguageServiceClient language = LanguageServiceClient.create()) {
                    Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
                    AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
                    Sentiment sentiment = response.getDocumentSentiment();
                    if (sentiment != null) {
                        if (mostNegative >= sentiment.getScore()) {
                            mostNegative = sentiment.getScore();
                            indexTracker = i;
                        }
                    }
                } catch (IOException ioe) {
                    System.out.println(ioe);
                    throw new RuntimeException("Unable to communicate with Sentiment Analyzer!");
                }
                if (mostNegative <= -0.3) {
                    negativeIndex++;
                }
            }
            if (negativeIndex == 0) {
                throw new NoSuitableSentenceException();
            }
            return doc1.getSentence(indexTracker);
        }
        catch (NoSuitableSentenceException e){
            return null;
        }
    }
}

