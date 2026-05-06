package com.freightflow.exception;

/**
 * Exception thrown when a resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s с ID %d не найден", resourceName, id));
    }
}
