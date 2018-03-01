package io.github.areguig.jonad;

interface FailableSupplier<T, E extends Exception> {

    T get() throws E;
}
