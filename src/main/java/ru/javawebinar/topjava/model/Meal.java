package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal u WHERE u.id=:id AND u.user.id=:user_id"),
        @NamedQuery(name = Meal.GET_BETWEEN_HALF_OPEN, query = "SELECT u FROM Meal u WHERE u.user.id=:user_id AND u.dateTime >=:start_date_time AND u.dateTime <:end_date_time ORDER BY u.dateTime DESC"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT u FROM Meal u WHERE u.user.id=:user_id ORDER BY u.dateTime DESC"),
        @NamedQuery(name = Meal.GET, query = "SELECT u FROM Meal u WHERE u.id=:id AND u.user.id=:user_id"),
        @NamedQuery(name = Meal.UPDATE, query = "UPDATE Meal u SET u.dateTime=:date_time, u.calories=:calories, u.description=:description WHERE u.id=:id AND u.user.id=:user_id"),
})
@Entity
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String GET_BETWEEN_HALF_OPEN = "User.GET_BETWEEN_HALF_OPEN";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET = "Meal.get";
    public static final String UPDATE = "Meal.update";

    @Column(name = "date_time", nullable = false)
    //@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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
