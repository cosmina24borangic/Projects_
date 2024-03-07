package repository;

import domain.Friendship;
import domain.Pair;
import domain.User;
import domain.validation.Validator;


import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * class which extract a Friendship List from a file
 * extends the AbstractFileRepository
 * uses a Tuple for the Friendship relation
 */

public class FriendshipFile extends AbstractFileRepository<SortedSet<Long>, Friendship>  {

    /**
     * constructor for the class
     * @param fileName - name of the file
     * @param validator - used for corect inputs
     */

    private  Repository<Long, User> userRepository;

    public FriendshipFile(String fileName, Validator<Friendship> validator,Repository<Long, User>  user) {
        super(fileName, validator);
        this.userRepository = user;
        loadData();
    }

    @Override
    public Friendship extractEntity(List<String> attributes) {
        //TODO: implement method
       Friendship friend = new Friendship();
        long id1 = (Long.parseLong(attributes.get(0)));
        long id2 = (Long.parseLong(attributes.get(1)));
        LocalDateTime date=LocalDateTime.parse(attributes.get(2));
        User u1 =new User();
        u1= userRepository.findOne(id1);
        ///System.out.println(u1);
        ///User u1=new User(userRepository.findOne(id1).getFirstName(),userRepository.findOne(id1).getLastName(),null);
        User u2=new User();
        u2= userRepository.findOne(id2);
       /// System.out.println(u2);
        // /User u2=new User(userRepository.findOne(id2).getFirstName(),userRepository.findOne(id2).getLastName(),null);
        if(u1!=null && u2!=null) {
                friend.setU1(u1);
                friend.setU2(u2);
                friend.setDate(date);
            SortedSet<Long> s= new TreeSet<>();
            s.add(u1.getID());
            s.add(u2.getID());
            friend.setID(s);
        }
        return friend;
    }

    @Override
    protected String createEntityAsString(Friendship entity) {
        return +entity.getU1().getID()+";"+entity.getU2().getID()+";"+entity.getDate();
    }
}
