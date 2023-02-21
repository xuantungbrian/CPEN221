package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

public class Task3SentimentTests {
    private static cpen221.mp1.Document testDocument1;
    private static cpen221.mp1.Document testDocument2;
    private static cpen221.mp1.Document testDocument3;


    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new cpen221.mp1.Document("The Ant and The Cricket", "resources/antcrick.txt");
        testDocument2 = new cpen221.mp1.Document("negative", "resources/negative.txt");
        testDocument3 = new cpen221.mp1.Document("positive", "resources/positive.txt");
    }



    @Test
    public void testPositiveSentence() throws NoSuitableSentenceException {
        Assertions.assertEquals("\"Well, try dancing now!\"", testDocument1.getMostPositiveSentence());
    }

    @Test
    public void testNegativeSentence() throws NoSuitableSentenceException {
        Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.", testDocument1.getMostNegativeSentence());
    }

    @Test
    public void testNoPositiveSentences() throws NoSuitableSentenceException {
        Assertions.assertEquals("ERROR.No positive sentences.", testDocument2.getMostPositiveSentence());
    }

    @Test
    public void testNoNegativeSentences() throws NoSuitableSentenceException {
        Assertions.assertEquals("ERROR.No negative sentences.", testDocument3.getMostNegativeSentence());
    }
}