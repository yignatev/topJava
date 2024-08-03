package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDateTime AND" +
            " m.dateTime < :endDateTime ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("userId") int userId, @Param("startDateTime") LocalDateTime startDateTime,
                                  @Param("endDateTime") LocalDateTime endDateTime);

    Meal getMealByUserIdAndId(int userId, int mealId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAllByUserId(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m where m.id = :id AND m.user.id=:userId")
    int deleteByIdAndByUserId(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM Meal m where m.id = :id AND m.user.id=:userId")
    Optional<Integer> findByIdAndByUserId(@Param("id") int id, @Param("userId") int userId);


}
