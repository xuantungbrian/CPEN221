package cpen221.mp3.wikimediator;
import cpen221.mp3.fsftbuffer.Bufferable;
import cpen221.mp3.fsftbuffer.FSFTBuffer;
import org.fastily.jwiki.core.Wiki;
import java.util.*;

public class WikiMediator {
    private final Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();
    private final FSFTBuffer <Bufferable> wikiFSFT;
    private final HashMap <String, Integer> recentlyUsed;
    private final HashMap <String, Long> timeCalled;
    private ArrayList <Long> requestTime;

    public WikiMediator (int capacity, int stalenessInterval) {
        wikiFSFT = new FSFTBuffer<> (capacity, stalenessInterval);
        recentlyUsed = new HashMap<>();  // adding the operation to this hashmap everytime some operation is called
        requestTime = new ArrayList<>();  // to record time at which operations are called
        timeCalled = new HashMap<>(); // key = operation, value = time at which it is called
    }

    private List <String> search (String query, int limit) {
        requestTime.add(System.currentTimeMillis()*1000);  // recording time at which it was called
        ArrayList<String> searchList = wiki.search(query, limit);
        if (!recentlyUsed.containsKey(query)) {
            recentlyUsed.put(query, 1);
        }
        else {
            recentlyUsed.put(query, (recentlyUsed.get(query))+1);
        }
        timeCalled.put(query, (System.currentTimeMillis())*1000);
        return searchList;
    }

    private String getPage (String pageTitle) {
        requestTime.add((System.currentTimeMillis())*1000);
        PageObj pageObj = new PageObj(pageTitle);
        if (Objects.equals(wikiFSFT.get(pageTitle), pageObj)) {
            return pageObj.getPageText();
        }
        else {
            wikiFSFT.put(pageObj);
        }
        if (!recentlyUsed.containsKey(pageTitle)) {
            recentlyUsed.put(pageTitle, 1);
        }
        else {
            recentlyUsed.put(pageTitle, (recentlyUsed.get(pageTitle))+1);
        }
        timeCalled.put(pageTitle, (System.currentTimeMillis())*1000);
        return pageObj.getPageText();
    }

    private List <String> zeitgeist(int limit) {
        requestTime.add((System.currentTimeMillis())*1000);
        Map <String, Integer> recentlyUsedSorted = sortBasedOnValue(recentlyUsed);
        List <String> recentlyCalledOperation = new ArrayList<>();
        for (String key : recentlyUsedSorted.keySet()) {
            if (recentlyCalledOperation.size() <= limit) {
                recentlyCalledOperation.add(key);
            }
        }
        return recentlyCalledOperation;
    }

    private List <String> trending (int timeLimitInSeconds, int maxItems) {
        requestTime.add((System.currentTimeMillis())*1000);
        ArrayList <String> keys = new ArrayList<>();
        List<String> frequentRequest = new ArrayList<>();

        for (String key : timeCalled.keySet()) {
            if (timeCalled.get(key) <= timeLimitInSeconds) {
                keys.add(key);
            }
        }
        // Now we create a copy of the map as we need that to remove elements from the initial map that contain
        // the operations in the timeCalled hashmap

        HashMap <String, Integer> mapCopy = new HashMap<>();
        mapCopy.putAll(recentlyUsed);

        for (String i : keys) {
            for (String j : mapCopy.keySet()) {
                if (j != i) {
                    mapCopy.remove(j);
                }
            }
        }

        Map <String, Integer> recentlyUsedSorted = sortBasedOnValue(mapCopy);
        for (String key : recentlyUsedSorted.keySet()) {
            if (frequentRequest.size() <= maxItems) {
                frequentRequest.add(key);
            }
        }
        return frequentRequest;
    }

    public int windowedPeakLoad (int timeWindowInSeconds) {
        requestTime.add((System.currentTimeMillis())*1000);
        int peakLoad = peakLoadFinder(timeWindowInSeconds);
        return peakLoad;
    }

    public int windowedPeakLoad() {
        requestTime.add((System.currentTimeMillis())*1000);
        int peakLoad = peakLoadFinder(30);  // default time = 30s
        return peakLoad;
    }

    // HELPER METHODS

    // Helper for sorting the values of a hashmap
    public static HashMap <String, Integer> sortBasedOnValue (HashMap <String, Integer> recentlyUsed) {
        // To create a list containing elements of a HashMap
        List <Map.Entry<String, Integer>> list = new LinkedList <Map.Entry<String, Integer>> (recentlyUsed.entrySet());

        // sorting the list created in the previous step
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry <String, Integer> operation1,
                               Map.Entry <String, Integer> operation2)
            {
                return (operation2.getValue()).compareTo(operation1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap <String, Integer> sortedMap = new LinkedHashMap <String, Integer>();
        for (Map.Entry <String, Integer> x : list) {
            sortedMap.put(x.getKey(), x.getValue());
        }
        return sortedMap;  // sorted hashmap is returned
    }

    // Helper for peak load
    private int peakLoadFinder (int timeWindowInSeconds) {
        int peakLoad = 0;
        int count;
        for (int i = 0; i < requestTime.size(); i++) {
            long initial = requestTime.get(i);  // initial time
            count = 0;
            for (int j = i; requestTime.get(j) < initial+timeWindowInSeconds; j++) {
                count++;
            }
            if (count > peakLoad) {
                peakLoad = count;
            }
        }
        return peakLoad;
    }
}
