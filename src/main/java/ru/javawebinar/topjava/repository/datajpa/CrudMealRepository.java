package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDateTime AND" +
            " m.dateTime < :endDateTime ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("userId") int userId, @Param("startDateTime") LocalDateTime startDateTime,
                                  @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAllByUserId(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m where m.user.id=:userId AND m.id = :mealId")
    int deleteByUserIdAndById(@Param("userId") int userId, @Param("mealId") int mealId);

    @Query("SELECT m FROM Meal m where m.user.id=:userId AND m.id = :mealId")
    Meal getByUserIdAndById(@Param("userId") int userId, @Param("mealId") int mealId);
}
