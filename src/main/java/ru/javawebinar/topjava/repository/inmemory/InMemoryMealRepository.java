package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal, meal.getUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            log.info("save {}", meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (meal.getUserId().equals(userId)) {
            log.info("edit {}", meal);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        log.info("this Meal {} is not yours {}", meal, userId);
        return null;

    }

    @Override
    public boolean delete(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        if (meal.getUserId() == userId) {
            log.info("deleted {}", meal);
            return repository.remove(mealId) != null;
        }
        log.info("noting to delete");
        return false;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        if (meal.getUserId() == userId) {
            log.info("got {}", meal);
            return meal;
        }
        log.info("noting to get");
        return null;
    }

    @Override
    public Collection<Meal> getAllUsersMeal(int userId) {
        log.info("getAllUsersMeal by user {}", userId);
        Comparator<Meal> compareByDateAndTime = Comparator.comparing(Meal::getDate).thenComparing(Meal::getTime);
        return repository.values().stream().
                filter(m -> m.getUserId().equals(userId)).
                sorted(compareByDateAndTime).
                collect(Collectors.toList());
    }

}

