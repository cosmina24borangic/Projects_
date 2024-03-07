package repository;

import domain.Entity;
import domain.validation.Validator;
import repository.InMemoryRepository;

import java.io.*;

import java.util.Arrays;
import java.util.List;

/**
 * class AbstractFileRepository extends InMemoryRepository
 * @param <ID> type E must have an attribute of type ID
 * @param <E> type of entities saved in repository
 */
///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;

    /**
     * Constructor for the file repository
     * @param fileName name of the file
     * @param validator the validator which is used later
     */
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
    }

    /**
     * load the data from a file
     * @catch exceptions if its needed
     */
    protected void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null){
                List<String> attr=Arrays.asList(linie.split(";"));
                E e=extractEntity(attr);
                super.save(e);
                System.out.println(linie);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected abstract String createEntityAsString(E entity);

    protected void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            super.findAll().forEach(entity -> {
                try {
                    bw.write(createEntityAsString(entity));
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E save(E entity){
        E e=super.save(entity);
        if (e==null)
        {
            writeToFile(entity);
        }
        return e;

    }

    @Override
    public E update(E entity) {
        E updated_entity = super.update(entity);
        if (updated_entity == null) {
            writeToFile();
            return null;
        }
        return entity;
    }

    @Override
    public E delete(ID id) {
        E deleted_entity = super.delete(id);
        if (deleted_entity == null) {

            return null;
        }
        writeToFile();
        return deleted_entity;
    }

    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

