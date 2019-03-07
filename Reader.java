import java.util.*;
import java.io.*;

public class Reader
{
    Hashtable<Integer, User> set = new Hashtable<Integer, User>(7220);
    Hashtable<String, User> stringSet = new Hashtable<String, User>(7220);
    Integer id = 0;
    User unknown = new User("UNKNOWN", 0);
    String name = "";
    public void run()
    {
        Scanner fileScanner;
        try
        {
            fileScanner = new Scanner (new File ("wikiElec.txt"));
            Integer elected = 0;
            /*
             * O(n) to iterate through the whole file
             */
            while (fileScanner.hasNext()) {
                if (!fileScanner.hasNext()) {
                    break;
                }
                User u;
                String s = fileScanner.next();
                try {
                    switch (s) {
                        case "E":
                            elected = new Integer(fileScanner.next());
                            break;

                        case "U":
                            id = new Integer(fileScanner.next());
                            if (!(set.containsKey(id))) {
                                name = fileScanner.next();
                                User use = new User (name, id);
                                set.put(id, use);
                                stringSet.put(name, set.get(id));
                            }
                            set.get(id).electionResult(elected);
                            break;

                        case "N":
                            Integer nominatorId = new Integer(fileScanner.next());
                            if (nominatorId.intValue() == -1) {
                                set.get(id).addNominator(unknown);
                            } else if (set.containsKey(nominatorId)) {
                                set.get(id).addNominator(set.get(nominatorId));
                            } else {
                                String nominatorName = fileScanner.next();
                                User use = new User(nominatorName, nominatorId);
                                set.put(nominatorId, use);
                                set.get(id).addNominator(set.get(nominatorId));
                            }
                            break;

                        case "V":   
                            Integer vote = new Integer(fileScanner.next());
                            Integer voterId = new Integer(fileScanner.next());
                            fileScanner.next();
                            fileScanner.next();
                            String voterName = fileScanner.next();
                            if (!set.containsKey(voterId)) {
                                User use = new User(voterName, voterId);
                                set.put(voterId, use);
                                stringSet.put(voterName, set.get(voterId));
                            }
                            set.get(voterId).addVote(vote);
                            set.get(id).recievedVote(vote);
                            break;
                        
                        default:
                            break;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println(e);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public void totalVotesById(Integer id) {
        set.get(id).totalVotes();
    }

    public void totalVotesByName(String screenName) {
        stringSet.get(screenName).totalVotes();
    }

    public void nominatorsById(Integer id) {
        set.get(id).getNominators();
    }

    public void votesRecievedByName(String screenName) {
        stringSet.get(screenName).totalVotesRecieved();
    }
}
