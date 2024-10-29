package org.stock.Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.stock.Entities.Category;
import org.stock.Entities.CategoryChangeLog;
import org.stock.Entities.PieceChangeLog;
import org.stock.dto.Categorydto;
import org.stock.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Transactional
    public List<Category> listCategories() {
        return categoryRepository.listAll();
    }

    @Transactional
    public Category saveCategory(Categorydto categorydto) {
        Category category = new Category();
        category.setName(categorydto.getName());
        category.setDescription(categorydto.getDescription());
        categoryRepository.persist(category);

        logChange(category.getId(), "Category Added", null, categorydto.getName());

        return category;
    }

    @Transactional
    public void removeCategory(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new NullPointerException("Category not found !!!");
        }

        logChange(category.getId(), "Category Removed", category.getName(), null);

        categoryRepository.delete(category);
    }

    @Transactional
    public void updateCategory(Long id, Categorydto categorydto) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new NullPointerException("Category not found !!!");
        }

        // Track changes if necessary
        if (categorydto.getName() != null && !categorydto.getName().equals(category.getName())) {
            logChange(category.getId(), "Name Changed", category.getName(), categorydto.getName());
            category.setName(categorydto.getName());
        }

        if (categorydto.getDescription() != null && !categorydto.getDescription().equals(category.getDescription())) {
            logChange(category.getId(), "Description Changed", category.getDescription(), categorydto.getDescription());
            category.setDescription(categorydto.getDescription());
        }

        categoryRepository.persist(category); // Ensure that the updated category is persisted

        logChange(category.getId(), "Category Updated", null, categorydto.getName());
    }

    @Transactional
    public void logChange(Long categoryId, String changeType, String oldValue, String newValue) {
        CategoryChangeLog log = new CategoryChangeLog();
        log.setCategoryId(categoryId);
        log.setChangeType(changeType);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setChangeDate(LocalDateTime.now());

        log.persist();
    }
    @Transactional
    public List<CategoryChangeLog> getChangeLog(Long categoryId) {
        return CategoryChangeLog.list("categoryId", categoryId);
    }
}
