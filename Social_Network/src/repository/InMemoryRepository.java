package repository;

import domain.Entity;
import domain.User;
import domain.validation.ValidationException;
import domain.validation.Validator;
import repository.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * InMemoryRepository where are store the objects
 * implements the interface of Repository
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> -  type of entities saved in repository
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;


    /**
     * constructor for merory repository
     * @param validator - validation
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getID()) != null) {
            return entity;
        }
        else entities.put(entity.getID(),entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        if(entities.get(id)==null)
            return null;
        E entity=entities.get(id);
        entities.remove(id);
        return entity;
    }

    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException(" entity must be not null!");
        validator.validate(entity);
        if(entities.get(entity.getID()) != null) {
            entities.put(entity.getID(),entity);
            return null;
        }
        return entity;
    }

}
