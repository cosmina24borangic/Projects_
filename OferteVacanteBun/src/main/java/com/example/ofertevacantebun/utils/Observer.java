package com.example.ofertevacantebun.utils;

public interface Observer<E extends Events> {
    void update(E e);
}
