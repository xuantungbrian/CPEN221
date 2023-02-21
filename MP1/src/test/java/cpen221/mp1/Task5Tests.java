package cpen221.mp1;

import cpen221.mp1.similarity.DocumentSimilarity;
import cpen221.mp1.similarity.GroupingDocuments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Task5Tests {
    private static Document testDocument1;
    private static Document testDocument2;
    private static Document testDocument3;
    private static Document testDocument4;
    private static Document testDocument5;

    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new Document("The Ant and The Cricket", "resources/antcrick.txt");
        testDocument2 = new Document("brazen", "resources/brazen.txt");
        testDocument3 = new Document("WaitingForHer", "resources/waitingforher.txt");
        testDocument4 = new Document("momoadventure", "resources/momoadventure.txt");
        testDocument5 = new Document("therunner", "resources/therunner.txt");
    }

    @Test
    public void test1() {

        Set<Document> testSet = new HashSet<>();

        testSet.add(testDocument1);
        testSet.add(testDocument2);

        Set<Set<Document>> expectedSet = new HashSet<>();
        Set<Set<Document>> resultSet = new HashSet<>();

        GroupingDocuments demo = new GroupingDocuments();
        resultSet = demo.groupBySimilarity(testSet, 2);
        // We have 2 documents and are expected to have 2 groups,
        // so resultSet should be [[testDocument1],[testDocument2]]

        Set<Document> set1 = new HashSet<>();
        Set<Document> set2 = new HashSet<>();

        set1.add(testDocument1);
        set2.add(testDocument2);

        expectedSet.add(set1);
        expectedSet.add(set2);

        Assertions.assertEquals(resultSet, expectedSet);

    }

    @Test
    public void test2() {
        Set<Document> testSet = new HashSet<>();

        testSet.add(testDocument1);
        testSet.add(testDocument2);
        testSet.add(testDocument4);
        testSet.add(testDocument5);

        Set<Set<Document>> expectedSet = new HashSet<>();
        Set<Set<Document>> resultSet = new HashSet<>();

        GroupingDocuments demo = new GroupingDocuments();
        resultSet = demo.groupBySimilarity(testSet, 1);
        // We have to only have 1 group, so all documents will be in 1 set like
        // [[testDocument1, testDocument2, testDocument4, testDocument5]]

        Set<Document> set1 = new HashSet<>();

        set1.add(testDocument1);
        set1.add(testDocument2);
        set1.add(testDocument4);
        set1.add(testDocument5);

        expectedSet.add(set1);

        Assertions.assertEquals(resultSet, expectedSet);
        // resultSet should be [[testDocument1],[testDocument2], [testDocument4], [testDocument5]]
    }

    @Test
    public void test4() {

        Set<Document> testSet = new HashSet<>();

        testSet.add(testDocument1);
        testSet.add(testDocument2);
        testSet.add(testDocument3);
        testSet.add(testDocument4);
        testSet.add(testDocument5);

        Set<Set<Document>> expectedSet = new HashSet<>();
        Set<Set<Document>> resultSet = new HashSet<>();

        GroupingDocuments demo = new GroupingDocuments();
        resultSet = demo.groupBySimilarity(testSet, 2);

        // We have 5 documents, and are expected to have 2 groups, so 3 of the most
        // common pairs should be merged together
        // Their divergence values:
        // testDocument 1-2: 40.22169928340534
        // testDocument 1-3: 41.18629766450205
        // testDocument 1-4: 41.229240858645234
        // testDocument 1-5: 44.48311082067882
        // testDocument 2-3: 53.115104132403346
        // testDocument 2-4: 50.561644615778235
        // testDocument 2-5: 53.42828720660056
        // testDocument 3-4: 39.10748126950453
        // testDocument 3-5: 45.11509930004616
        // testDocument 4-5: 40.19445943790598
        // The 3 smallest divergence pair values are for: 3-4, 4-5, 1-2 respectively. So this is how program should work:
        // Step 0: [[1],[2],[3],[4],[5]]
        // Step 1: [[1], [2], [3, 4], [5]]
        // Step 2: [[1], [2], [3, 4, 5]]
        // Step 3: [[1, 2], [3, 4, 5]]

        Set<Document> set1 = new HashSet<>();
        Set<Document> set2 = new HashSet<>();

        set1.add(testDocument1);
        set1.add(testDocument2);
        set2.add(testDocument3);
        set2.add(testDocument4);
        set2.add(testDocument5);

        expectedSet.add(set1);
        expectedSet.add(set2);

        Assertions.assertEquals(resultSet, expectedSet);
    }
}