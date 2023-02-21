package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import cpen221.mp1.similarity.DocumentSimilarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

public class Task4Js_DocumentTests {
    private static Document testDocument1;
    private static Document testDocument2;

    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new cpen221.mp1.Document("The Ant and The Cricket", "resources/antcrick.txt");
        testDocument2 = new cpen221.mp1.Document("brazen", "resources/brazen.txt");
    }

    @Test
    public void testSameDocJsDivergence() {
        DocumentSimilarity demo = new DocumentSimilarity();
        Assertions.assertEquals(0, demo.jsDivergence(testDocument1, testDocument1));
    }

    @Test
    public void testSameDocDivergence() {
        DocumentSimilarity demo = new DocumentSimilarity();
        Assertions.assertEquals(0, demo.documentDivergence(testDocument2, testDocument2));
    }

    @Test
    public void testJsDivergence() throws NoSuitableSentenceException {
        DocumentSimilarity demo = new DocumentSimilarity();
        Assertions.assertEquals(0.6171589276056652, demo.jsDivergence(testDocument1, testDocument2));
    }

    @Test
    public void testDocDivergence() {
        DocumentSimilarity demo = new DocumentSimilarity();
        Assertions.assertEquals(40.114422114607095, demo.documentDivergence(testDocument1, testDocument2));
    }


}
