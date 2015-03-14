package com.winterbe.java8;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Simplified elvis-like operator. You can achieve the same with Optional but Take has simpler syntax.
 * 
 * @see Optional2
 * @author Benjamin Winterberg
 */
public class Take<T> {

    private static final Take<?> EMPTY = new Take<>(null);

    private T value;

    private Take(T value) {
        this.value = value;
    }

    public static <T> Optional<T> of(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        }
        catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public static <T> Take<T> of(T something) {
        return new Take<>(something);
    }

    public <S> Take<S> take(Function<? super T, S> resolver) {
        if (!isPresent()) {
            return empty();
        }
        S result = resolver.apply(value);
        return new Take<>(result);
    }

    public Optional<T> get() {
        return Optional.ofNullable(value);
    }

    public T orElse(T fallback) {
        if (isPresent()) {
            return value;
        }
        return fallback;
    }

    public boolean isPresent() {
        return value != null;
    }

    @SuppressWarnings("unchecked")
    private static <T> Take<T> empty() {
        return (Take<T>) EMPTY;
    }

}