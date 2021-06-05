package com.pseudonova.saverod.interfaces;

public interface IRepository<K, V>
{
    void addOrUpdate(V value);
    boolean containsKey(K key);
    V getValue(K key);
}
