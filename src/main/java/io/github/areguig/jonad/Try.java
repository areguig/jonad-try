package io.github.areguig.jonad;

import java.util.function.Function;

abstract class Try<T> {


    static <T, E extends Exception> Try<T> with(FailableSupplier<T, E> v) {
        try {
            return new Success<T>(v.get());
        } catch (Exception e) {
            return new Failure<T>(e);
        }
    }

    abstract T get();

    abstract <U> Try<U> flatMap(Function<T, Try<U>> f);

    abstract <U> Try<U> recoverWith(Function<Exception, Try<U>> fe);
}

class Success<T> extends Try<T> {

    private final T value;

    Success(T value) {
        this.value = value;
    }

    T get() {
        return value;
    }

    <U> Try<U> flatMap(Function<T, Try<U>> f) {
        try {
            return f.apply(this.value);
        } catch (Exception e) {
            return new Failure<U>(e);
        }
    }

    <U> Try<U > recoverWith(Function<Exception, Try<U>> fe) {
        return (Try<U>) this;
    }
}

class Failure<T> extends Try<T> {

    private final Exception e;

    Failure(Exception e) {
        this.e = e;
    }

    T get() {
        throw new RuntimeException("");
    }

    <U> Try<U> flatMap(Function<T, Try<U>> f) {
        return (Try<U>) this;
    }

    <U> Try<U> recoverWith(Function<Exception, Try<U>> fe) {
        try {
            return fe.apply(e);
        } catch (Exception e){
            return new Failure<U>(e);
        }
    }
}