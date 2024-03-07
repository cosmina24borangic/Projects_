package com.example.ofertevacantebun.utils;

public interface Observable<E extends Events> {
    public void addObserver(Observer<E> e);
    public void removeObserver(Observer<E> e);
    public void notifyObservers(E t);
}
