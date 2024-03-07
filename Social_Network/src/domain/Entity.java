package domain;

import java.io.Serializable;


/**
 * Entity for extending other classes
 * @param <ID>
 */
public class Entity <ID> implements Serializable {
    private ID id;

    /**
     * getter id
     * @return id
     */
    public ID getID(){
        return id;
    }

    /**
     * setter id
     * @param id_new the new value of id
     */
    public void setID(ID id_new){
        id=id_new;
    }
}
