package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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
    @Transactional
    @Modifying
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(crudUserRepository.getReferenceById(userId));
            return crudRepository.save(meal);
        } else if (crudRepository.findByIdAndByUserId(meal.getId(), userId).isEmpty()) {
            throw new NotFoundException("Not found entity with id=" + meal.getId());
        } else {
            meal.setCalories(meal.getCalories());
            meal.setDescription(meal.getDescription());
            meal.setDateTime(meal.getDateTime());
            meal.setUser(crudUserRepository.getReferenceById(userId));
            return crudRepository.save(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndByUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getMealByUserIdAndId(userId, id);
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
