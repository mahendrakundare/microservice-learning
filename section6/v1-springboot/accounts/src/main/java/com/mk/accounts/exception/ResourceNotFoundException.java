package com.mk.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param resourceName the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param fieldName the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param fieldValue the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
