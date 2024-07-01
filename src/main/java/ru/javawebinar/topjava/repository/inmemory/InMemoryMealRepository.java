package ru.javawebinar.topjava.repository.inmemory;

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
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        return meal.getUserId() == userId ? repository.remove(id) != null : null;
        //return repository.remove(id) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        return meal.getUserId() == userId ? meal : null;
        //return repository.get(mealId);
    }

    @Override
    public Collection<Meal> getAll() {
        Comparator<Meal> compareByDate = Comparator.comparing(Meal::getDate).thenComparing(Meal::getDate);
        return repository.values().stream().sorted(compareByDate.reversed()).collect(Collectors.toList());
    }
    @Override
    public Collection<Meal> getAllUsersMeal(int userId){
        return getAll().stream().filter(s-> s.getUserId() == 1).collect(Collectors.toList());
    }

}

