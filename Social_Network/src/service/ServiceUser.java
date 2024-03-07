package service;

import domain.Entity;
import domain.Friendship;

import domain.Pair;
import domain.User;
import domain.validation.ValidationException;
import repository.Repository;
import java.util.*;
import service.ServiceFriendship;

/**
 * class Service
 * repoUser-Repository for Users
 * repoFriends-Repository for Friendships
 */
public class ServiceUser {
    private final Repository<Long, User> repoUser;
    private final Repository<SortedSet<Long>, Friendship> repoFriends;

    /**
     * constructor for the service
     * @param RepoUser UserRepository
     * @param RepoFriends FriendsRepository
     */
    public ServiceUser(Repository<Long, User> RepoUser, Repository<SortedSet<Long>, Friendship> RepoFriends) {
        repoFriends = RepoFriends;
        repoUser = RepoUser;

    }



    /**
     * save a user, call the repo function
     * if is not valid throw ValidationException
     * @param firstname - String
     * @param lastname - String
     */
    public void save(String firstname, String lastname) {
        User user = new User(firstname, lastname, null);
        long id = get_size();
        id++;
        user.setID(id);
        User save = repoUser.save(user);
        if (save != null)
            throw new ValidationException(" id already used");
    }


    /**
     * delete a user, call the repo function
     * @param id-Long
     * @return the deleted user
     * otherwise, throw ValidationException
     */
    public Entity delete(Long id) {

        User deleted = repoUser.delete(id);

        if (deleted == null)
            throw new ValidationException(" id invalid!");
        ///return deleted;
        Iterable<User> FriendList= deleted.getFriends();
        for(User friend: FriendList)
        {
            SortedSet<Long> s = new TreeSet<Long>();
            s.add(friend.getID());
            s.add(deleted.getID());
            repoFriends.delete(s);
            friend.deleteFriend(deleted);

        }
        return deleted;
    }

    public void update(Long id, String firstname, String lastname) {
        User userUpdate=findOne(id);
        User user = new User(firstname, lastname,userUpdate.getFriends());
        user.setID(id);
        User save = repoUser.update(user);
        if (save != null)
            throw new ValidationException(" id invalid!");
    }

    /**
     * get the maximum id
     * @return the result-Long
     */
    public Long get_size() {
        Long maxim = 0L;
        for (User ur : repoUser.findAll())
            if (ur.getID() > maxim)
                maxim = ur.getID();
        return maxim;
    }

    /**
     * Display friends for a given user
     * @param id integer id of a posible user
     * @return the list of users friends with the one given
     * @throws ValidationException if the id for user given is invalid
     */
    public Iterable<User> getFriends(Long id) throws ValidationException{
        try{
            Set<User> users= new HashSet<>();
            User response = repoUser.findOne(id);
            if(response == null)
                throw new ValidationException(" id invalid!");
            else
                for (Friendship fr : repoFriends.findAll()){
                    if(Objects.equals(fr.getU1().getID(), id))
                        users.add(repoUser.findOne(fr.getU2().getID()));
                    if(Objects.equals(fr.getU2().getID(), id))
                        users.add(repoUser.findOne(fr.getU1().getID()));
                }
            return users;
        }
        catch (ValidationException exception){
            throw new ValidationException(exception);
        }
    }

    /** Function which returns all the users from list
     * @return all the users
     */
    public Iterable<User> printUs() {
        return repoUser.findAll();
    }

    /**Display friends for a given user
     * @param nr integer id of a posible user
     * @return the user with the given id
     * @throws ValidationException if the id for user given is invalid
     */
    public User findOne(Long nr) {
        if(repoUser.findOne(nr) != null)
            return repoUser.findOne(nr);
        else
            throw new ValidationException("id invalid!");
    }
}