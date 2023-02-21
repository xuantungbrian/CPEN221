package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupingDocuments {

    /* ------- Task 5 ------- */

    /**
     * Group documents by similarity
     *
     * @param allDocuments   the set of all documents to be considered,
     *                       is not null
     * @param numberOfGroups the number of document groups to be generated
     * @return groups of documents, where each group (set) contains similar
     * documents following this rule: if D_i is in P_x, and P_x contains at
     * least one other document, then P_x contains some other D_j such that
     * the divergence between D_i and D_j is smaller than (or at most equal
     * to) the divergence between D_i and any document that is not in P_x.
     */
    public static Set<Set<Document>> groupBySimilarity(Set<Document> allDocuments, int numberOfGroups) {

        int i, j, k;
        k = 0;
        int totalComparisons = 0;

        // totalComparisons = total number of comparisons depending on how many documents there are
        for (i = 1; i < allDocuments.size(); i++) {
            totalComparisons += i;
        }

        double[] comparisonValues = new double[totalComparisons];

        for (i = 0; i < allDocuments.size(); i++) {

            for (j = i + 1; j < allDocuments.size(); j++) {

                Document doc1 = allDocuments.stream().toList().get(i);
                Document doc2 = allDocuments.stream().toList().get(j);
                DocumentSimilarity demo = new DocumentSimilarity();
                comparisonValues[k] = demo.documentDivergence(doc1, doc2);

                k++;
            }
        }

        Set<Set<Document>> partDocs = new HashSet<Set<Document>>();

        int[] pairs = new int[2];

        // Each document from allDocuments is stored in set, and then that set is added to partDocs
        // until all documents are inside partDocs.
        for(Document doc : allDocuments) {
            partDocs.add(Set.of(doc));
        }

        int l, m;
        for (j = 1; partDocs.size() > numberOfGroups; ) {
            k = nthSmallestValueArray(comparisonValues, j);
            pairs = docNumFinder(k, allDocuments.size());

            for (l = 0; l < partDocs.size(); l++) {
                for (m = 0; m < partDocs.size(); m++) {
                    if (l == m) {  }

                    else if (partDocs.stream().toList().get(l).contains(allDocuments.stream().toList().get(pairs[0]))
                            && partDocs.stream().toList().get(m).contains(allDocuments.stream().toList().get(pairs[1]))) {

                        mergeSets(partDocs, l, m);
                        j++;
                        m = partDocs.size();
                        l = partDocs.size();
                    }
                }
            }
        }

        return partDocs;
    }

    /**
     * Merge two sets, which are contained inside a set
     * @param partDocs a set that contains sets containing documents, is not null
     * @param l the place of the searched set inside partDocs
     * @param m the place of the searched set inside partDocs
     * @return the new set of partDocs
     */
    public static Set<Set<Document>> mergeSets (Set<Set<Document>> partDocs, int l, int m) {

        Set<Document> setone = new HashSet<Document>();
        Set<Document> settwo = new HashSet<Document>();

        setone = partDocs.stream().toList().get(l);
        settwo = partDocs.stream().toList().get(m);

        partDocs.remove(setone);
        partDocs.remove(settwo);

        Set<Document> combined = Stream.concat(setone.stream(), settwo.stream()).collect(Collectors.toSet());
        partDocs.add(combined);

        return partDocs;
    }

    /**
     * Find which two documents are compared to each other.
     * @param k is a number we're trying to find
     * @param setSize is the size of the set at the beginning
     * @return the pair of compared documents
     */
    public static int[] docNumFinder(int k, int setSize) {
        int[] pairs = new int[2];
        int i, j, c;
        c = 0;

        for (i = 0; i < setSize; i++) {
            for (j = i + 1; j < setSize; j++) {
                if (c == k) {
                    pairs[0] = i;
                    pairs[1] = j;
                    return pairs;
                }
                c++;
            }
        }
        return pairs;
    }


    /**
     * Find the nth smallest number in an array
     * @param value contains the divergence values of document pairs
     * @param n is an integer, n is smaller than the length of array value
     * @return the nth smallest number in the array value
     */
    public static int nthSmallestValueArray(double[] value, int n) {

        double sortedArray [] = new double[value.length];

        for (int i = 0; i < value.length; i++) {
            sortedArray[i] = value[i];
        }

        Arrays.sort(sortedArray);

        // We are assuming that no pair of documents have the same Divergence, as it is
        // a very unlikely possibility.
        for (int i = 0; i < value.length; i++) {
            if (sortedArray[n - 1] == value[i]) {
                return i;
            }
        }
        return 0;
    }
}