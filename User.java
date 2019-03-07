import java.util.ArrayList;

public class User
{
    private boolean elected;
    private String name;
    private Integer id;
    private int voteFor = 0;
    private int voteNeutral = 0;
    private int voteAgainst = 0;
    ArrayList<User> nominators = new ArrayList<User>();
    
    private int votedFor = 0;
    private int votedNeutral = 0;
    private int votedAgainst = 0;
    
    
    
    public User (String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    
    public void addVote(Integer i) {
        int num = i.intValue();
        if (num == 1) {
            voteFor++;
        } else if (num == -1) {
            voteAgainst++;
        } else {
            voteNeutral++;
        }
    }
    
    public void totalVotes() {
        if (voteFor != 0 || voteAgainst != 0 || voteNeutral != 0) {
            System.out.println(voteFor + " votes for\n" +
            voteNeutral + " votes neutral\n" +
            voteAgainst + " votes against");
        } else {
            System.out.println(name + " has not voted yet.");
        }
    }
    
    public void recievedVote(Integer i) {
        int num = i.intValue();
        if (num == 1) {
            votedFor++;
        } else if (num == -1) {
            votedAgainst++;
        } else {
            votedNeutral++;
        }
    }
    
    public void totalVotesRecieved() {
        if (votedFor != 0 || votedAgainst != 0 || votedNeutral != 0) {
            System.out.println(votedFor + " times voted for\n" +
            votedNeutral + " times voted neutral\n" +
            votedAgainst + " times voted against");
        } else {
            System.out.println(name + " has not been voted on yet.");
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void addNominator(User u) {
        nominators.add(u);
    }
    
    public void getNominators() {
        if (nominators.size() == 0) {
            System.out.println("This user has not been nominated");
        } else {
            for (User u : nominators) {
                System.out.println(u.getName());
            }
        }
    }
    
    public void electionResult(Integer i) {
        if (i.intValue() == 1) {
            elected = true;
        } else {
            elected = false;
        }
    }
}
