package domain;

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * class Friendship extends Entity
 */
public class Friendship extends Entity<SortedSet<Long>> {

    private User u1;
    private User u2;
    private LocalDateTime date;


    /**
     * Constructor for friendship
     * @param u1 first user of the friendship
     * @param u2 second user of the friendship
     */
    public Friendship(User u1, User u2) {
        this.u1 = u1;
        this.u2 = u2;
    }

    public Friendship(){}

    public User getU1() {
        return u1;
    }

    public void setU1(User u1) {
        this.u1 = u1;
    }

    public User getU2() {
        return u2;
    }

    public void setU2(User u2) {
        this.u2 = u2;
    }
    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * set the date time when the friendship was created
     * @param Date-LocalDateTime
     */
    public void setDate(LocalDateTime Date){date=Date;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return u1.equals(that.u1) && u2.equals(that.u2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(u1, u2);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Friendship{" +
                "id1=" + u1.getID() +
                ", id2=" + u2.getID() +", date= "+date.format(formatter)+
                '}';
    }
}
