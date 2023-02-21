package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Task3DWTests {

    private static DWInteractionGraph dwig1;
    private static DWInteractionGraph dwig2;
    private static DWInteractionGraph dwig3;
    private static DWInteractionGraph dwig4;
    private static DWInteractionGraph dwig5;

    @BeforeAll
    public static void setupTests() {
        dwig1 = new DWInteractionGraph("resources/Task3Transactions1.txt");
        dwig2 = new DWInteractionGraph("resources/Task3Transactions2.txt");
        dwig3 = new DWInteractionGraph("resources/Task3Transactions3.txt");
        dwig4 = new DWInteractionGraph("resources/Task3Transactions4.txt");
        dwig5 = new DWInteractionGraph(dwig1, new int[] {4, 5});
    }

    @Test
    public void testBFSGraph1() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 6);
        Assertions.assertEquals(expected, dwig1.BFS(1, 6));
    }

    @Test
    public void testDFSGraph1() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 6);
        Assertions.assertEquals(expected, dwig1.DFS(1, 6));
    }

    @Test
    public void testBFSGraph2() {
        List<Integer> expected = Arrays.asList(1, 3, 5, 6, 4, 8, 7, 2, 9, 10);
        Assertions.assertEquals(expected, dwig2.BFS(1, 10));
    }

    @Test
    public void testDFSGraph2() {
        List<Integer> expected = Arrays.asList(1, 3, 4, 8, 5, 7, 2, 9, 10);
        Assertions.assertEquals(expected, dwig2.DFS(1, 10));
    }

    @Test
    public void testBFSGraph3() {
        List<Integer> expected = Arrays.asList(1,4,7,12,2,6,10,9,13);
        Assertions.assertEquals(expected, dwig3.BFS(1, 13));
    }

    @Test
    public void testDFSGraph3() {
        List<Integer> expected = Arrays.asList(1,4,2,6,9,3,5,7,10,13);
        Assertions.assertEquals(expected, dwig3.DFS(1, 13));
    }

    @Test
    public void testBFSGraph4() {
        List<Integer> expected = Arrays.asList(1, 3, 5, 9, 2, 4, 6, 8, 7, 13);
        Assertions.assertEquals(expected, dwig4.BFS(1, 13));
    }

    @Test
    public void testBFSGraph5() {
        List<Integer> expected = Arrays.asList(1, 3, 5, 9, 2, 4, 6, 8, 7);
        Assertions.assertEquals(expected, dwig4.BFS(1, 7));
    }

    @Test
    public void testDFSGraph4() {
        List<Integer> expected = Arrays.asList(1, 3, 2, 4, 10, 5, 6, 8, 11, 9, 7, 12, 14);
        Assertions.assertEquals(expected, dwig4.DFS(1, 14));
    }

    @Test
    public void testBFSGraphNoPath() {
        Assertions.assertEquals(null, dwig4.BFS(5, 7));
        Assertions.assertEquals(null, dwig4.BFS(5, 9));
        Assertions.assertEquals(null, dwig4.BFS(3, 1));
        Assertions.assertEquals(null, dwig4.BFS(1, 15)); // User 15 doesn't exist
    }

    @Test
    public void testDFSGraphNoPath() {
        Assertions.assertEquals(null, dwig4.BFS(5, 2));
        Assertions.assertEquals(null, dwig4.DFS(5, 7));
        Assertions.assertEquals(null, dwig4.BFS(3, 1));
        Assertions.assertEquals(null, dwig4.DFS(1, 15)); // User 15 doesn't exist
    }

    @Test
    public void TestBFSSameValue () {
        List<Integer> expected = Arrays.asList(1);
        Assertions.assertEquals(expected, dwig4.BFS(1, 1));
        Assertions.assertEquals(null, dwig4.BFS(15, 15));
    }

    @Test
    public void TestDFSSameValue () {
        List<Integer> expected = Arrays.asList(1);
        Assertions.assertEquals(expected, dwig4.BFS(1, 1));
        Assertions.assertEquals(null, dwig4.BFS(15, 15));
    }
}
