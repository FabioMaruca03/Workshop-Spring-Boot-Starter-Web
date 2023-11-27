package com.example.web.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RegularRepository<T, ID> {
    protected Map<ID, T> datasource = new ConcurrentHashMap<>();

    public abstract Optional<T> save(T element);

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(datasource.get(id));
    }

    public List<T> findAll() {
        return datasource.values().stream().toList();
    }

    public Optional<T> delete(ID id) {
        return Optional.ofNullable(datasource.remove(id));
    }

    public Optional<T> modify(ID id, T elem) {
        if (!datasource.containsKey(id)) {
            return Optional.empty();
        }

        T original = datasource.get(id);
        Arrays.stream(elem.getClass().getDeclaredFields())
                .filter(field -> !field.getName().equalsIgnoreCase("id"))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);

                        if (field.get(elem) != null) {
                            field.set(original, field.get(elem));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } finally {
                        field.setAccessible(false);
                    }
                });

        return Optional.of(original);
    }

}
