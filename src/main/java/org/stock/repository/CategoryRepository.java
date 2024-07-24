package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.stock.Entities.Category;

@ApplicationScoped
public class CategoryRepository implements PanacheRepositoryBase<Category,Long> {
}
