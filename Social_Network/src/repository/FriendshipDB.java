package repository;

import domain.Friendship;

import domain.User;
import domain.validation.Validator;
import repository.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * FriendshipDbRepository repository class
 */

public class FriendshipDB implements Repository<SortedSet<Long>,Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Friendship> validator;
    private  Repository<Long, User> userRepository;

    public FriendshipDB(String url, String username, String password, Validator<Friendship> validator,Repository<Long, User> user) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        this.userRepository=user;
    }

    private Friendship extractFriendship(ResultSet resultSet) throws SQLException{
        Friendship friend=new Friendship();
        if(resultSet.next()){
            Long id1 = resultSet.getLong("user1");
            Long id2 = resultSet.getLong("user2");
            LocalDateTime date = resultSet.getTimestamp("data").toLocalDateTime();
            User u1;
            u1= userRepository.findOne(id1);
            User u2;
            u2= userRepository.findOne(id2);
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
        return null;
    }

    @Override
    public Friendship findOne(SortedSet<Long> id) {
        if(id == null)
            throw new IllegalArgumentException("Id must not be null");
        Friendship friendship;
        String sql = "SELECT * FROM Friendships WHERE user1 = ? AND user2 = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, Math.toIntExact(id.first()));
            statement.setInt(2, Math.toIntExact(id.last()));
            ResultSet resultSet = statement.executeQuery();
            friendship = this.extractFriendship(resultSet);
            if(friendship != null)
                return friendship;

            statement.setInt(2, Math.toIntExact(id.first()));
            statement.setInt(1, Math.toIntExact(id.last()));
            resultSet = statement.executeQuery();
            friendship = this.extractFriendship(resultSet);
            if(friendship != null)
                return friendship;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from Friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("user1");
                Long id2 = resultSet.getLong("user2");
                LocalDateTime date = resultSet.getTimestamp("data").toLocalDateTime();

                Friendship friendship = new Friendship();
                User u1 = userRepository.findOne(id1);
                User u2 = userRepository.findOne(id2);
                SortedSet<Long> s= new TreeSet<>();
                s.add(id1);
                s.add(id2);
                friendship.setID(s);
                friendship.setU1(u1);
                friendship.setU2(u2);
                friendship.setDate(date);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    private void executeStatement(Friendship friendship, String sql){
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, Math.toIntExact(friendship.getID().first()));
            statement.setInt(2, Math.toIntExact(friendship.getID().last()));
            statement.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Friendship save(Friendship friendship) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");

        if(friendship == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        validator.validate(friendship);
        String sql = "INSERT INTO Friendships(user1,user2,data) VALUES (?,?,'"+friendship.getDate().format(formatter)+"')";
        this.executeStatement(friendship,sql);
        return null;
    }

    @Override
    public Friendship delete(SortedSet<Long> id) {
        if(id == null || id.first() == null || id.last() == null)
            throw new IllegalArgumentException("Id must not be null");

        Friendship friendship = this.findOne(id);
        if(friendship != null){
            String sql = "DELETE FROM Friendships WHERE user1 = ? and user2 = ?";
            this.executeStatement(friendship,sql);
        }
        return friendship;
    }

    @Override
    public Friendship update(Friendship friendship) {
        return null;
    }
}