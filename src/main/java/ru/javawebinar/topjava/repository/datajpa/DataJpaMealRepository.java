package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository,
                                 CrudUserRepository crudUserRepository) {
        this.crudRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(crudUserRepository.getReferenceById(userId));
            return crudRepository.save(meal);
        }
        if (crudRepository.getByUserIdAndById(userId, meal.getId()) == null) {
            return null;
        } else {
            meal.setUser(crudUserRepository.getReferenceById(userId));
            return crudRepository.save(meal);
        }
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return crudRepository.deleteByUserIdAndById(userId, mealId) != 0;
    }

    @Override
    public Meal get(int mealId, int userId) {
        return crudRepository.getByUserIdAndById(userId, mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(userId, startDateTime, endDateTime);
    }
}
