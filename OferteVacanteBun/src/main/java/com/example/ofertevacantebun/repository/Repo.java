package com.example.ofertevacantebun.repository;

import java.util.List;

public interface Repo <T>{
    Iterable<T> findAll();
    //T findOne(Double id);
    T save(T entity);
}
