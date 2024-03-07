package service;

import domain.Entity;
import domain.Friendship;
import domain.Pair;
import domain.User;
import domain.validation.ValidationException;
import repository.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Math.max;
import static java.lang.Math.min;


/**
 * class Service
 * repoUser-Repository for Users
 * repoFriends-Repository for Friendships
 */
public class ServiceFriendship {
    private Repository<Long, User> repoUser;
    private Repository<SortedSet<Long>, Friendship> repoFriends;

    /**
     * constructor for the service
     * @param RepoUser UserRepository
     * @param RepoFriends FriendsRepository
     */
    public ServiceFriendship(Repository<Long, User> RepoUser, Repository<SortedSet<Long>, Friendship> RepoFriends){
        repoFriends = RepoFriends;
        repoUser = RepoUser;
    }

    /**
     * add a friendship
     * if is not valid throw ValidationException
     * @param id1-Long
     * @param id2-Long
     */
    public void addFriend(Long id1, Long id2) throws ValidationException {
        User user1 = repoUser.findOne(id1);
        User user2 = repoUser.findOne(id2);

        try{
            if(user1 != null && user2!= null) {
                SortedSet<Long> s = new TreeSet<Long>();
                s.add(user1.getID());
                s.add(user2.getID());
                ///System.out.println(repoFriends.findOne(s));
                if(repoFriends.findOne(s) == null) {
                    Friendship friendship=new Friendship(user1,user2);
                    friendship.setID(s);
                    friendship.setDate(LocalDateTime.now());
                    Friendship response = repoFriends.save(friendship);
                    user1.addFriend(user2);
                    user2.addFriend(user1);
                    if (response != null)
                        throw new ValidationException("Friendship already made!");
                }
                else
                    throw new ValidationException("Friendship already made!");
            }
            else{
                String errors = "";
                if(user1 == null)
                    errors += "First id does not exist!";
                if(user2 == null)
                    errors += "Second id does not exist!";
                throw new ValidationException(errors);
            }
        }
        catch (ValidationException e){
            throw new ValidationException(e);
        }
    }

    /**
     * delete a friendship
     * if is not valid throw ValidationException
     * @param id1-Long
     * @param id2-Long
     */
    public void deleteFriend(Long id1, Long id2) throws ValidationException {
        User user1 = repoUser.findOne(id1);
        User user2 = repoUser.findOne(id2);
        if (user1 == null)
            throw new ValidationException(" first id does not exist");
        if ( user2 == null)
            throw new ValidationException(" second id does not exist");
        SortedSet<Long> s = new TreeSet<Long>();
        s.add(user1.getID());
        s.add(user2.getID());
        Entity save = repoFriends.delete(s);
        if (save == null)
            throw new ValidationException(" ids are not used in a friendship");
        user1.deleteFriend(user2);
        user2.deleteFriend(user1);
    }

    /**
     * @return all the friendships
     */
    public Iterable<Friendship> printFr() {
        return repoFriends.findAll();
    }
}