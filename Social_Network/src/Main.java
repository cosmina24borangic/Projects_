import domain.Friendship;

import domain.Pair;
import domain.User;
import domain.validation.FriendshipValidator;
import domain.validation.UserValidator;

import domain.validation.ValidationException;
import domain.validation.Validator;
import repository.*;


import service.Network;
import service.ServiceFriendship;
import service.ServiceUser;

import java.util.Scanner;
import java.util.SortedSet;


public class Main {
    /**
     * main function of the program

     */

    public static void printCommands() {
        System.out.println("1. Adauga un utilizator");
        System.out.println("2. Sterge un utilizator");
        System.out.println("3. Afiseaza utilizatori");
        System.out.println("4. Adauga o prietenie");
        System.out.println("5. Sterge o prietenie");
        System.out.println("6. Afiseaza prietenii");
        System.out.println("7. Afisare numar de comunitati(numarul de componente conexe din graful retelei)");
        System.out.println("8. Afisare cea mai sociabila comunitate (componenta conexa din retea cu cel mai lung drum)");
        System.out.println("9. Modifica un utilizator");

        System.out.println("0. Exit");
    }

    public static void main(String[] args)
    {
        ///Repository<Long,User> repou = new UserFile("src/data/UserData", new UserValidator());
//        /Repository<Long,User> repou = new InMemoryRepository<>(new UserValidator());
        ///Repository<SortedSet<Long>, Friendship> repof= new InMemoryRepository<>( new FriendshipValidator());
//        Repository <SortedSet<Long>, Friendship> repof= new FriendshipFile("src/data/FriendshipData", new FriendshipValidator(), repou);
        ///User user = new User("Nume", "Prenume");
        ///Friendship friend= new Friendship();
        ///repou.save(user);
        ///user.setLastName("Last");
        ///user.setFirstName("First");
        ///repou.findAll().forEach(System.out::println);
        String username="postgres";
        String pasword="postgres";
        String url="jdbc:postgresql://localhost:5433/academic";
        Repository<Long,User> repou = new UserDB(url, username, pasword, new UserValidator());
        Repository<SortedSet<Long>,Friendship> repof = new FriendshipDB(url, username, pasword, new FriendshipValidator(),repou);
        ServiceUser srvu = new ServiceUser(repou, repof);
        ServiceFriendship srvf = new ServiceFriendship (repou, repof);

        ///srvu.save("Nume", "Prenume");
        ///srvu.save("Numee","Prenumee");
        ///srvu.delete(8L);
        //srvf.addFriend(6L,7L);
        ///srvf.addFriend(9L,10L);
        ///srvf.deleteFriend(6L,7L);

        ///System.out.println(net.connectedComponents());

        ///System.out.println(net.biggestComponent());
        boolean isRunning= true;
        while (isRunning) {
            Main.printCommands();

            System.out.print("Enter command: ");
            Scanner sc=new Scanner(System.in);
            String cmd= sc.nextLine();
            System.out.println();

            switch (cmd) {
                case "1":
                    System.out.print("firstname: ");
                    String  firstname= sc.nextLine();
                    System.out.print("lastname: ");
                    String  lastname= sc.nextLine();

                    srvu.save(firstname,lastname);
                    break;

                case "2":
                    System.out.print("ID: ");
                    long i=sc.nextLong();
                    srvu.delete(i);
                    break;
                case "3":
                    System.out.println(srvu.printUs());
                    break;
                case "4":
                    System.out.print("ID 1: ");
                    long id1= sc.nextLong();
                    System.out.print("ID 2: ");
                    long id2= sc.nextLong();

                    srvf.addFriend(id1,id2);

                    break;
                case "5":
                    System.out.print("ID 1: ");
                    long id11= sc.nextLong();
                    System.out.print("ID 2: ");
                    long id22= sc.nextLong();

                    srvf.deleteFriend(id11,id22);
                    break;
                case "6":
                    System.out.println(srvf.printFr());
                    break;

                case "7":
                    int size =Integer.parseInt(srvu.get_size().toString());
                    Network net= new Network(size);
                    net.addFriendships(srvf.printFr());
                    net.addUsers(srvu.printUs());
                    System.out.println(net.connectedComponents());
                    break;
                case "8":
                    int size2 =Integer.parseInt(srvu.get_size().toString());
                    Network net2= new Network(size2);
                    net2.addFriendships(srvf.printFr());
                    net2.addUsers(srvu.printUs());
                    System.out.println(net2.biggestComponent());
                    break;
                case "9":
                    System.out.print("ID: ");
                    String ids=sc.nextLine();
                    System.out.print("firstname: ");
                    String  firstnameUpdated= sc.nextLine();
                    System.out.print("lastname: ");
                    String  lastnameUpdated= sc.nextLine();
                    long id=Long.parseLong(ids);
                    srvu.update(id,firstnameUpdated,lastnameUpdated);
                    break;

                case "0":
                    System.out.println("Bye");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid command!");
                    break;
            }
            System.out.println("\n--------------");

            if (cmd.equals("0"))
                break;
        }
    }
}
