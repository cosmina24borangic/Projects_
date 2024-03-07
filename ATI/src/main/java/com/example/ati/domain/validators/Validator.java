package com.example.ati.domain.validators;

import com.example.ati.domain.exceptions.EmptyStringException;
import com.example.ati.domain.exceptions.NegativeNumberException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException, EmptyStringException, NegativeNumberException;
}