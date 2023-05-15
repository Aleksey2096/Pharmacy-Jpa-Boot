package by.academy.pharmacy2.service.database;

import java.util.Set;

public interface DBService<D, I> {
    D create(final D dto);

    D readById(final I id);

    void update(final D dto);

    void deleteById(final I id);

    Set<D> readAll();
}
