package service;

import domain.Friendship;
import domain.User;

import java.util.*;

/**
 * Network is a class for community operation
 * mat - matrix with integer values
 * size-integer(size of matrix)
 *ind-Set that contains Long keys
 */
public class Network {
    private Integer[][] mat;
    private Integer size;
    private Set<Long> ind;

    /**
     * create a matrix of friends connections
     *
     * @param size of matrix, number of users
     */
    public Network(int size) {
        this.ind = new HashSet<>();
        this.mat = new Integer[size][size];
        this.size = size;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.mat[i][j] = 0;
    }

    /**
     * add a friendships to the network
     *
     * @param list-list of Friendships
     */
    public void addFriendships(Iterable<Friendship> list) {
        list.forEach(f ->
                this.mat[(int) (f.getU1().getID() - 1)][(int) (f.getU2().getID() - 1)] = 1);
        list.forEach(f ->
                this.mat[(int) (f.getU2().getID() - 1)][(int) (f.getU1().getID() - 1)] = 1);

    }

    /**
     * add users to the network
     *
     * @param list- list of Users
     */
    public void addUsers(Iterable<User> list) {
        list.forEach(x -> ind.add(x.getID() - 1));
    }

    /**
     * dfs algorithm
     *
     * @param v-integer
     * @param visited-boolean array
     */
    private void DFSUtils(int v, boolean[] visited) {
        visited[v] = true;
        for (int i = 0; i < size; i++)
            if (mat[v][i] == 1 && !visited[i])
                DFSUtils(i, visited);
    }

    /**
     * check how many connected Components we have
     *
     * @return nr-integer (the result)
     */
    public int connectedComponents() {
        int nr = 0;
        boolean[] visited = new boolean[size];

        for (int i = 0; i < size; i++) {
            if (!visited[i] && ind.contains(Long.valueOf(i))) {
                DFSUtils(i, visited);
                nr++;
            }
        }
        return nr;
    }

    public int maxi = 0, nod1;


    private void DFSUtil(int v, boolean[] visited,int dist){
        if(maxi<dist){
            maxi = dist;
            nod1 = v;
        }
        visited[v] = true;
        for(int i=0;i<size;i++)
            if(mat[v][i]==1&& !visited[i]) {
                DFSUtil(i, visited, dist + 1);
            }
    }


    /**
     * find the biggest Component
     *
     * @return list of Integer(the result)
     */
    public List<Long> biggestComponent() {
        boolean[] visited = new boolean[size];
        boolean[] viz = new boolean[size];
        List<Long> listRez=new ArrayList<>();
        int maxim=0;
        for(int i=0;i<size;i++){
            if(!viz[i] && ind.contains((long) i)) {
                maxi=0;
                nod1=0;
//                for (int j = 0; j < size; j++) {
//                    visited[j] = false;
//                }
//                DFSUtil(i, visited, 0);
                for (int j = 0; j < size; j++) {
                    visited[j] = false;
                }
                DFSUtil(i, visited, 0);

                if (maxim < maxi) {
                    listRez.clear();
                    maxim = maxi;
                    for(int j = 0;j<size;j++){
                        if(visited[j]!=viz[j]&& !viz[j])
                        {
                            viz[j] = visited[j];
                            listRez.add((long) j+1);
                        }
                    }
                }
            }

        }
        return listRez;
    }
}

