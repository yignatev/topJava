package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId)); // мы назначаем пришедшей еде id пришедего пользователя
        if (meal.isNew()) {
            em.persist(meal);// сохраняем еду без проверки на пользователя т.е. можно создать еду другому пользователю
            return meal;
        } else if (get(meal.id(), userId) == null) {
            return null;
        }
        return em.merge(meal); // а править еду можно только свою почему ?
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userid", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.GET_MEAL, Meal.class)
                .setParameter("id", id)
                .setParameter("userid", userId)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_BY_USER_SORTED, Meal.class)
                .setParameter("userid", userId)
                .getResultList();

    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery("Meal.GET_BETWEEN_TIME", Meal.class)
                .setParameter("userid", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}
