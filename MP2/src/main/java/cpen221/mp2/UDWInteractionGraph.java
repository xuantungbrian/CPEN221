package cpen221.mp2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UDWInteractionGraph {

    /* ------- Task 1 ------- */
    /* Building the Constructors */
    private List<Integer>[][] graph = new ArrayList[1000][1000];



    /**
     * Creates a new UDWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public UDWInteractionGraph(String fileName) {
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
                if (!parts[0].equals(parts[1])) {
                    graph[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].add(Integer.parseInt(parts[2]));
                    graph[Integer.parseInt(parts[1])][Integer.parseInt(parts[0])].add(Integer.parseInt(parts[2]));
                }
                else {
                    graph[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].add(Integer.parseInt(parts[2]));
                }
            }
            reader.close();
        }
        catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created UDWInteractionGraph
     *                   should only include those emails in the input
     *                   UDWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, int[] timeFilter) {
        for (int i=0; i<inputUDWIG.graph.length; i++) {
            for (int j = 0; j < inputUDWIG.graph.length; j++) {
                graph[i][j] = new ArrayList<>(inputUDWIG.graph[i][j]);
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
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a list of User IDs.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param userFilter a List of User IDs. The created UDWInteractionGraph
     *                   should exclude those emails in the input
     *                   UDWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, List<Integer> userFilter) {
        for (int i=0; i<inputUDWIG.graph.length; i++) {
            for (int j = 0; j < inputUDWIG.graph.length; j++) {
                graph[i][j] = new ArrayList<>(inputUDWIG.graph[i][j]);
            }
        }

        for (int i=0; i<graph.length;i++) {
            for (int j=0; j<graph.length;j++) {
                if (!graph[i][j].isEmpty() && (!userFilter.contains(i) && !userFilter.contains(j))) {
                    graph[i][j].clear();
                }
            }
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a DWInteractionGraph object.
     *
     * @param inputDWIG a DWInteractionGraph object
     */
    //This graph will not be a sorted one
    public UDWInteractionGraph(DWInteractionGraph inputDWIG) {
        int size = maxNumberedUser(inputDWIG.getGraph()) + 1;

        for (int i=0; i< graph.length ;i++) {
            for (int j = 0; j < graph.length; j++) {
                graph[i][j] = new ArrayList<Integer>();
            }
        }

        for (int i=0; i<size;i++) {
            for (int j=0; j<size;j++) {
                if (!inputDWIG.getGraph()[i][j].isEmpty()) {
                    if (i!=j) {
                        graph[i][j].addAll(inputDWIG.getGraph()[i][j]);
                        graph[i][j].addAll(inputDWIG.getGraph()[j][i]);
                    }
                    else {
                        graph[i][j].addAll(inputDWIG.getGraph()[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Creates a new UDWInteractionGraph from using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                  directory containing email interactions
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created UDWInteractionGraph
     *                   should only include those emails in the input
     *                   UDWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    //TODO: May need a test for this constructor (move it in another java file)
    public UDWInteractionGraph(String fileName, int[] timeFilter) {
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
                if (Integer.parseInt(parts[2])>=timeFilter[0] && Integer.parseInt(parts[2])<=timeFilter[1]) {
                    if (!parts[0].equals(parts[1])) {
                        graph[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].add(Integer.parseInt(parts[2]));
                        graph[Integer.parseInt(parts[1])][Integer.parseInt(parts[0])].add(Integer.parseInt(parts[2]));
                    }
                    else {
                        graph[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].add(Integer.parseInt(parts[2]));
                    }
                }
            }
            reader.close();
        }
        catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }
    }

    /**
     * @return a Set of Integers, where every element in the set is a User ID
     * in this UDWInteractionGraph.
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
     * @param user1 the User ID of the first user.
     * @param user2 the User ID of the second user.
     * @return the number of email interactions (send/receive) between user1 and user2
     */
    public int getEmailCount(int user1, int user2) {
        return graph[user1][user2].size();
    }

    /* ------- Task 2 ------- */

    /**
     * @param timeWindow is an int array of size 2 [t0, t1]
     *                   where t0<=t1
     * @return an int array of length 2, with the following structure:
     *  [NumberOfUsers, NumberOfEmailTransactions]
     */
    /* ------- Task 2 ------- */

    /**
     * @param timeWindow is an int array of size 2 [t0, t1]
     *                   where t0<=t1
     * @return an int array of length 2, with the following structure:
     *  [NumberOfUsers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {

        int i, j, k;
        Set <Integer> activeUsers = new <Integer> HashSet ();
        int [] results = new int [2];
        int count = 0;

        for (i = 0; i < graph.length; i++) {
            for (j = 0; j < graph.length; j++) {
                for (k = 0; k < graph[i][j].size(); k++) {

                    if (graph[i][j].isEmpty()) { }
                    else {
                        if (graph[i][j].get(k) < timeWindow[0] || graph[i][j].get(k) > timeWindow[1]) { }

                        else {
                            activeUsers.add(i);
                            activeUsers.add(j);
                            count++;
                        }
                    }
                }
            }
        }
        results[0] = activeUsers.size();
        results[1] = count/2;
        return results;
    }

    /**
     * @param userID the User ID of the user for which
     *               the report will be created
     * @return an int array of length 2 with the following structure:
     *  [NumberOfEmails, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0].
     */
    public int[] ReportOnUser(int userID) {
        int j;
        int emailCount = 0;
        Set <Integer> interactedUsers = new <Integer> HashSet ();
        int [] results = new int [2];

        for (j = 0; j < graph.length; j++) {
            if (!graph[userID][j].isEmpty()) {
                interactedUsers.add(j);
            }
            emailCount += graph[userID][j].size();
        }

        results[0] = emailCount;
        results[1] = interactedUsers.size();
        return results;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @return the User ID for the Nth most active user
     */
    public int NthMostActiveUser(int N) {
        int i;
        int userCount = maxNumberedUser(graph) + 1;
        if (userCount < N) {
            return -1;
        }
        Set <Integer> equalActiveUsers = new <Integer> HashSet ();
        int [] emailCountUsers = new int [userCount];
        int [] sortedEmailCountUsers = new int [userCount];
        Boolean same = false;
        for (i = 0; i < emailCountUsers.length; i++) {
            int [] results = new int [2];
            results = ReportOnUser(i);
            emailCountUsers[i] = results[0];
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
                    return i;
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

        return equalActiveUsers.stream().toList().get(sameValueCount);
    }

    /**
     * @return the largest UserID number
     * @param graph is the Interaction graph
     */
    private int maxNumberedUser (List<Integer>[][] graph) {

        Set<Integer> userSet = new HashSet<Integer>();
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
     * @return the number of completely disjoint graph
     *    components in the UDWInteractionGraph object.
     */
    public int NumberOfComponents() { // need optimization
        List<Integer> Users = new ArrayList<>();
        List<Integer> AllComponent = new ArrayList<>();
        List<Integer> OneComponent;
        int n=0;
        int count=0;
        int i=0,j=0;
        int max=maxNumberedUser(graph)+1;

        for (i=0; i<max;i++) {
            for (j = 0; j < max; j++) {
                if (!graph[i][j].isEmpty()) {
                    Users.add(i);
                    break;
                }
            }
        }

        if (Users.isEmpty()) {return count;}

        i=0;
        j=0;
        Collections.sort(Users);
        n = Users.get(0);

        while (i < Users.size()) {
            OneComponent=new ArrayList<>();
            OneComponent.add(n);
            for (i = 0; i < OneComponent.size(); i++) {
                for (j = 0; j <= max; j++) {
                    if (!graph[OneComponent.get(i)][j].isEmpty() && !OneComponent.contains(j)) {
                        OneComponent.add(j);
                    }
                }
            }

            count++;
            AllComponent.addAll(OneComponent);

            for (i = 0; i < Users.size(); i++) {
                if (!AllComponent.contains(Users.get(i))) {
                    break;
                }
            }

            if (i < Users.size()) {
                n = Users.get(i);
            }
        }

        return count;
    }



    /**
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return whether a path exists between the two users
     */
    public boolean PathExists(int userID1, int userID2) {
        List<Integer> Users = new ArrayList<>();
        List<Integer> AllComponent = new ArrayList<>();
        List<Integer> OneComponent;
        int n=0;
        int i=0,j=0;
        int max=maxNumberedUser(graph)+1;
        int check=0;

        for (i=0; i<max;i++) {
            for (j = 0; j < max; j++) {
                if (!graph[i][j].isEmpty()) {
                    Users.add(i);
                    break;
                }
            }
        }

        if (Users.isEmpty()) {return false;}

        i=0;
        j=0;
        Collections.sort(Users);
        n = Users.get(0);

        while (i < Users.size()) {
            OneComponent=new ArrayList<>();
            OneComponent.add(n);
            for (i = 0; i < OneComponent.size(); i++) {
                for (j = 0; j <= max; j++) {
                    if (!graph[OneComponent.get(i)][j].isEmpty() && !OneComponent.contains(j)) {
                        OneComponent.add(j);
                    }
                }
            }

            if (OneComponent.contains(userID1)&&OneComponent.contains(userID2)){
                check=1;
                break;
            }

            AllComponent.addAll(OneComponent);

            for (i = 0; i < Users.size(); i++) {
                if (!AllComponent.contains(Users.get(i))) {
                    break;
                }
            }

            if (i < Users.size()) {
                n = Users.get(i);
            }
        }

        if (check==1) {
            return true;
        }
        else return false;
    }
}