package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = "Meal.DELETE", query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userid"),
        @NamedQuery(name = "Meal.GET_MEAL", query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userid"),
        @NamedQuery(name = "Meal.GET_BY_USER_SORTED", query = "SELECT m FROM Meal m WHERE m.user.id = :userid ORDER BY m.dateTime DESC"),
        @NamedQuery(name = "Meal.GET_BETWEEN_TIME", query = "SELECT m FROM Meal m WHERE m.user.id=:userid " +
                "AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC")
})
@Entity
@Table(name = "meal", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date_time"}))
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.DELETE";
    public static final String GET_BY_USER_SORTED = "Meal.GET_BY_USER_SORTED";
    public static final String GET_MEAL = "Meal.GET_MEAL";
    public static final String GET_BETWEEN_TIME = "Meal.GET_BETWEEN_TIME";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Length(min = 3, max = 128)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 1, max = 10000)
    private int calories;

    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
