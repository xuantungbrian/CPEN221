package cpen221.mp2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DWInteractionGraph {

    /* ------- Task 1 ------- */
    /* Building the Constructors */

    private List<Integer>[][] graph = new ArrayList[1000][1000];

    /**
     * Creates a new DWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public DWInteractionGraph(String fileName) {
        for (int i=0; i<graph.length;i++) {
            for (int j = 0; j < graph.length; j++) {
                graph[i][j] = new ArrayList<Integer>();
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            for (String fileLine = reader.readLine();
                 fileLine != null;
                 fileLine = reader.readLine()) {
                String[] parts= fileLine.split(" ");
                graph[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].add(Integer.parseInt(parts[2]));
            }
            reader.close();
        }
        catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }
    }

    /**
     * Creates a new DWInteractionGraph from a DWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputDWIG a DWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created DWInteractionGraph
     *                   should only include those emails in the input
     *                   DWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, int[] timeFilter) {
        for (int i=0; i<inputDWIG.graph.length; i++) {
            for (int j = 0; j < inputDWIG.graph.length; j++) {
                graph[i][j] = new ArrayList<>(inputDWIG.graph[i][j]);
            }
        }

        for (int i=0; i<graph.length;i++) {
            for (int j=0; j<graph.length;j++) {
                if (!graph[i][j].isEmpty()) {
                    for (int k=0; k<graph[i][j].size();k++) {
                        if (graph[i][j].get(k)<timeFilter[0] || graph[i][j].get(k)>timeFilter[1]) {
                            graph[i][j].remove(k);
                            k--;
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a new DWInteractionGraph from a DWInteractionGraph object
     * and considering a list of User IDs.
     *
     * @param inputDWIG a DWInteractionGraph object
     * @param userFilter a List of User IDs. The created DWInteractionGraph
     *                   should exclude those emails in the input
     *                   DWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, List<Integer> userFilter) {
        for (int i=0; i<inputDWIG.graph.length; i++) {
            for (int j = 0; j < inputDWIG.graph.length; j++) {
                graph[i][j] = new ArrayList<>(inputDWIG.graph[i][j]);
            }
        }

        for (int i=0; i<graph.length;i++) {
            for (int j=0; j<graph.length;j++) {
                if (!graph[i][j].isEmpty() &&((!userFilter.contains(i) && !userFilter.contains(j)))) {
                    graph[i][j].clear();
                }
            }
        }
    }

    /**
     * @return a Set of Integers, where every element in the set is a User ID
     * in this DWInteractionGraph.
     */
    public Set<Integer> getUserIDs() {
        Set <Integer> setID = new HashSet<>();
        for (int i=0; i<graph.length;i++) {
            for (int j=0; j<graph.length;j++) {
                if (!graph[i][j].isEmpty()) {
                    setID.add(i);
                    setID.add(j);
                }
            }
        }
        return setID;
    }

    /**
     * @param sender the User ID of the sender in the email transaction.
     * @param receiver the User ID of the receiver in the email transaction.
     * @return the number of emails sent from the specified sender to the specified
     * receiver in this DWInteractionGraph.
     */
    public int getEmailCount(int sender, int receiver) { return graph[sender][receiver].size();}

    List<Integer>[][] getGraph() {
        List<Integer>[][] newGraph= new ArrayList[1000][1000];
        for (int i=0; i<graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                newGraph[i][j] = new ArrayList<>(graph[i][j]);
            }
        }
        return newGraph;
    }

    /* ------- Task 2 ------- */

    /**
     * Given an int array, [t0, t1], reports email transaction details.
     * Suppose an email in this graph is sent at time t, then all emails
     * sent where t0 <= t <= t1 are included in this report.
     * @param timeWindow is an int array of size 2 [t0, t1] where t0<=t1.
     * @return an int array of length 3, with the following structure:
     * [NumberOfSenders, NumberOfReceivers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        int i, j, k;
        Set <Integer> senders = new <Integer>HashSet();
        Set <Integer> receivers = new <Integer>HashSet();
        int [] results = new int [3];
        int count = 0;

        for (i = 0; i < graph.length; i++) {
            for (j = 0; j < graph.length; j++) {
                for (k = 0; k < graph[i][j].size(); k++) {

                    if (graph[i][j].isEmpty()) { }
                    else {
                        if (graph[i][j].get(k) < timeWindow[0] || graph[i][j].get(k) > timeWindow[1]) { }

                        else {
                            senders.add(i);
                            receivers.add(j);
                            count++;
                        }
                    }
                }
            }
        }
        results[0] = senders.size();
        results[1] = receivers.size();
        results[2] = count;
        return results;
    }

    /**
     * Given a User ID, reports the specified User's email transaction history.
     * @param userID the User ID of the user for which the report will be
     *               created.
     * @return an int array of length 3 with the following structure:
     * [NumberOfEmailsSent, NumberOfEmailsReceived, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0, 0].
     */
    public int[] ReportOnUser(int userID) {
        int j;
        int emailSentCount = 0;
        int emailReceivedCount = 0;
        Set <Integer> interactedUsers = new <Integer> HashSet ();
        int [] results = new int [3];

        for (j = 0; j < graph.length; j++) {
            if (!graph[userID][j].isEmpty()) {
                interactedUsers.add(j);
            }
            emailSentCount += graph[userID][j].size();
        }

        for (j = 0; j < graph.length; j++) {
            if (!graph[j][userID].isEmpty()) {
                interactedUsers.add(j);
            }
            emailReceivedCount += graph[j][userID].size();
        }

        results[0] = emailSentCount;
        results[1] = emailReceivedCount;
        results[2] = interactedUsers.size();
        return results;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @param interactionType Represent the type of interaction to calculate the rank for
     *                        Can be SendOrReceive.Send or SendOrReceive.RECEIVE
     * @return the User ID for the Nth most active user in specified interaction type.
     * Sorts User IDs by their number of sent or received emails first. In the case of a
     * tie, secondarily sorts the tied User IDs in ascending order.
     */
    public int NthMostActiveUser(int N, SendOrReceive interactionType) {

        int i;
        int userCount = maxNumberedUser(graph) + 1;
        int [] sentEmailCountUsers = new int [userCount];
        int [] receivedEmailCountUsers = new int [userCount];

        Set <Integer> equalActiveUsers = new <Integer> HashSet ();
        int [] emailCountUsers = new int [userCount];
        int [] sortedEmailCountUsers = new int [userCount];
        Boolean same = false;

        for (i = 0; i < emailCountUsers.length; i++) {
            int [] results = new int [3];
            results = ReportOnUser(i);
            sentEmailCountUsers[i] = results[0];
            receivedEmailCountUsers[i] = results[1];
        }

        if (interactionType == SendOrReceive.SEND) {
            for (i = 0; i < sentEmailCountUsers.length; i++) {
                emailCountUsers[i] = sentEmailCountUsers[i];
            }
        }

        else if (interactionType == SendOrReceive.RECEIVE) {
            for (i = 0; i < sentEmailCountUsers.length; i++) {
                emailCountUsers[i] = receivedEmailCountUsers[i];
            }
        }


        for (i = 0; i < emailCountUsers.length; i++) {
            sortedEmailCountUsers[i] = emailCountUsers[i];
        }

        Arrays.sort(sortedEmailCountUsers);
        for (i = 0; i < sortedEmailCountUsers.length; i++) {
            if (i == sortedEmailCountUsers.length - N) {}
            else if (sortedEmailCountUsers[sortedEmailCountUsers.length - N] == sortedEmailCountUsers[i] && !same) {
                same = true;
            }
        }
        int sameValueCount = -1;
        if (same == false) {
            for (i = 0; i < emailCountUsers.length; i++) {
                if (sortedEmailCountUsers[sortedEmailCountUsers.length - N] == emailCountUsers[i]) {
                    if (sortedEmailCountUsers[sortedEmailCountUsers.length - N] == -1) {
                        return -1;
                    }
                    else {
                        return i;
                    }
                }
            }
        }

        else {
            for (i = sortedEmailCountUsers.length - N; i < sortedEmailCountUsers.length; i++) {
                if (sortedEmailCountUsers[sortedEmailCountUsers.length - N] == sortedEmailCountUsers[i]) {
                    sameValueCount++;
                }
            }
        }
        for (i = 0; i < emailCountUsers.length; i++) {
            if (sortedEmailCountUsers[sortedEmailCountUsers.length - N] == emailCountUsers[i]) {
                equalActiveUsers.add(i);
            }
        }

        if (sortedEmailCountUsers[sortedEmailCountUsers.length - N] == 0) {
            return -1;
        }

        return equalActiveUsers.stream().toList().get(sameValueCount);
    }

    /**
     * @return the largest UserID number
     * @param graph is the Interaction graph
     */
    private int maxNumberedUser (List<Integer>[][] graph) {

        Set <Integer> userSet  = new HashSet <Integer> ();
        int i, j;
        int max = -1;

        for (i = 0; i < graph.length; i++) {
            for (j = 0; j < graph.length; j++) {
                if (graph[i][j].size() != 0) {
                    if (i > max) {
                        max = i;
                    }

                    if (j > max) {
                        max = j;
                    }
                }
            }
        }

        return max;
    }

    /* ------- Task 3 ------- */

    /**
     * performs breadth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> BFS(int userID1, int userID2) {

        int userCount = maxNumberedUser(graph) + 1;
        List <Integer> path = new ArrayList <Integer>();
        path.add(userID1);

        if (userID1 == userID2) {
            for (int i = 0; i < userCount; i++) {
                if (!graph[userID1][i].isEmpty() || !graph[userID1][i].isEmpty()) {
                    return path;
                }
            }
            return null;
        }
        BFSTree(userID1, userID2, path, 0);

        if (path.contains(userID2)) {
            return path;
        }

        else {
            return null;
        }

    }

    /**
     * performs breadth first search by recursion
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @param path is the path of users that the program has researched
     * @param n is a number that helps the recursion to run
     */
    private void BFSTree (int userID1, int userID2, List <Integer> path, int n) {

        Set <Integer> users = new HashSet <Integer> ();
        users = interactedUsers(graph, userID1);

        for (int i = 0; i < users.size() && !path.contains(userID2); i++) {
            addNumToPath(path, users.stream().toList().get(i));
        }

        n++;
        while (!path.contains(userID2) && n < path.size()) {
            if (n < path.size())
            BFSTree(path.get(n), userID2, path, n);
            n = path.size(); // This is done to exit the while loop after the research is completed
        }
    }

    /**
     * performs depth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> DFS(int userID1, int userID2) {
        List <Integer> path = new ArrayList <Integer>();
        Set <Integer> previousPaths = new HashSet ();
        path.add(userID1);
        int userCount = maxNumberedUser(graph)+1;
        if (userID1 == userID2) {
            for (int i = 0; i < userCount; i++) {
                if (!graph[userID1][i].isEmpty() || !graph[userID1][i].isEmpty()) {
                    return path;
                }
            }
            return null;
        }

        DFSTree(userID1, userID2, path, previousPaths);

        if (path.contains(userID2)) {
            return path;
        }

        else {
            return null;
        }
    }

    /**
     * performs depth first search by recursion
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @param path is the path of users that the program has researched
     * @param previousPaths is the path of the previous step in recursion
     */
    private void DFSTree (int userID1, int userID2, List <Integer> path, Set <Integer>previousPaths) {
        int i, j;
        Set<Integer> users = new HashSet<Integer>();
        users = interactedUsers(graph, userID1);

        previousPaths.add(userID1);

        for (i = 0; i < users.size(); i++) {

            if (!path.contains(userID2) && !previousPaths.contains(users.stream().toList().get(i))) {
                addNumToPath(path, users.stream().toList().get(i));
            }

            if (!previousPaths.contains(users.stream().toList().get(i)) && !path.contains(userID2)) {
                DFSTree(users.stream().toList().get(i), userID2, path, previousPaths);
            }
        }
    }

    /**
     * Adds a number to a list if the number is not alredy in that list
     * @param path is the path of users that the program has researched
     * @param num is the number that may get added to path
     */
    private void addNumToPath (List <Integer> path, int num) {
        if (!path.contains(num)) {
            path.add(num);
        }
    }

    /**
     * @returns a list of users that a user has sent an email to
     * @param graph is the interaction graph
     * @param user is the user that the program is checking the sent emails of
     */
    private Set<Integer> interactedUsers (List[][] graph, int user) {

        int i, j;
        Set <Integer> users = new HashSet <Integer> ();

        for (i = 0; i < maxNumberedUser(graph) + 1; i++) {
            if (!graph[user][i].isEmpty()) {
                users.add(i);
            }
        }
        return users;
    }

    /* ------- Task 4 ------- */
    /**
     * Read the MP README file carefully to understand
     * what is required from this method.
     * @param hours
     * @return the maximum number of users that can be polluted in N hours
     */
    public int MaxBreachedUserCount(int hours) {

        int i, j, k;
        int userCount = maxNumberedUser(graph)+1;
        int max = 0;
        List <Integer> graph2 [][] = new ArrayList [userCount][userCount];

        for (i=0; i< userCount; i++) {
            for (j = 0; j < userCount; j++) {
                graph2[i][j] = new ArrayList<Integer> ();
                graph2[i][j] = graph[i][j];
            }
        }

        for (i = 0; i < userCount; i++) {
            for (j = 0; j < userCount; j++) {
                for (k = 0; k < graph2[i][j].size(); k++) {
                    int time = graph2[i][j].get(k);
                    int [] timeFilter = {time, time+3600*hours};
                    List <Integer> [][] newGraph = new ArrayList [userCount][userCount];
                    newGraph = filterGraph(graph2, timeFilter);
                    List <Integer> path = new ArrayList ();
                    path.add(i);
                    TreeVirus(newGraph, i, path, 0);
                    if (path.size() > max) {
                        max = path.size();
                    }
                }
            }
        }
        return max;
    }

    /**
     * performs breadth first search by recursion
     * @param newGraph is an interaction graph
     * @param userID1 the user ID for the first user
     * @param path is the path of users that the program has researched
     * @param n is a number that helps the recursion to run
     */
    private void TreeVirus (List <Integer> [][] newGraph, int userID1, List <Integer> path, int n) {

        Set <Integer> users = new HashSet <Integer> ();
        users = interactedUsers(newGraph, userID1);

        for (int i = 0; i < users.size(); i++) {
            addNumToPath(path, users.stream().toList().get(i));
        }

        n++;
        while (n < path.size()) {
            TreeVirus(newGraph, path.get(n), path, n);
            n = path.size();
        }
    }

    /**
     * @returns a graph that is filtered with a period of time
     * @param graph2 is an interaction graph
     * @param timeFilter is the timefilter that is being performed on a given graph
     */
    private List <Integer>[][] filterGraph (List <Integer> [][] graph2, int[] timeFilter) {

        int userCount = maxNumberedUser(graph2) + 1;
        List <Integer> newGraph [][] = new ArrayList [userCount][userCount];

        for (int i=0; i< userCount; i++) {
            for (int j = 0; j < userCount; j++) {
                newGraph[i][j] = new ArrayList<Integer>();
                for (int k = 0; k < graph2[i][j].size(); k++) {
                    newGraph[i][j].add(graph2[i][j].stream().toList().get(k));
                }
            }
        }

        for (int i = 0 ; i < userCount ; i++) {
            for (int j = 0; j < userCount ; j++) {
                for (int k=0; k<newGraph[i][j].size();k++) {
                    if (newGraph[i][j].get(k) < timeFilter[0] || newGraph[i][j].get(k) >= timeFilter[1]) {
                        newGraph[i][j].remove(k);
                        k--;
                    }
                }
            }
        }
        return newGraph;
    }
}
