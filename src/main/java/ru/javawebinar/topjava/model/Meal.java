package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = "Meal.DELETE", query = "DELETE FROM Meal m WHERE m.id=:id AND m.user=:userid"),
        @NamedQuery(name = "Meal.GET_MEAL", query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user=:userid"),
        @NamedQuery(name = "Meal.GET_BY_USER_SORTED",query = "SELECT m FROM Meal m WHERE m.user=:userid ORDER BY m.dateTime"),
        @NamedQuery(name ="Meal.GET_BETWEEN_TIME", query = "SELECT m FROM Meal m WHERE m.user=:userid AND m.dateTime BETWEEN :startDateTime AND :endDateTime")
})

@Entity
@Table(name = "meal")
public class Meal extends AbstractBaseEntity {

    public static final String DELETE ="Meal.delete";
    public static final String GET_BY_USER_SORTED ="Meal.getAll";
    public static final String GET_MEAL ="Meal.get";
    public static final String GET_BETWEEN_TIME = "Meal.getBetweenHalfOpen";

    @Column(name = "date_time")
    @NotBlank
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description")
    @NotBlank
    @NotNull
    @Size(max = 128)
    private String description;

    @Column(name = "calories")
    @NotBlank
    @NotNull
    private int calories;

    @NotBlank
    @NotNull
    @JoinColumn(name="user_id")
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
