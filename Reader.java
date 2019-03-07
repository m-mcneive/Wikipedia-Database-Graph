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

                        /*
                        *This is the first part of the election. When the fileScanner comes across the letter E,
                        * we know that this is the beginning of a new election. The next value will be 1 or 0 that
                        * determines whether the user was elected or not respectively
                        *
                        * (For the purpose of this project, the next line involves the time of the election, but this
                        * information was not necessary for us)
                         */
                        case "E":
                            elected = new Integer(fileScanner.next());
                            break;

                        /*
                        *The next line after E is U which is the user that is up for election. After thr U, the next 2 lines
                        * will be the user id and the username
                         */
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

                        /*
                        *After the user, the nominator (or nominators) will be listed
                         */
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

                        /*
                        * Finally, all of the users will cast their votes. This continues until the fileScanner comes across
                        * the letter E again, signaling that the next election is beginning
                         */
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
