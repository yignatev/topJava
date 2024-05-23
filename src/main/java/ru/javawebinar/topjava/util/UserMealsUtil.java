package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        List <UserMealWithExcess> mealsStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsStream.forEach(x -> System.out.println(x));


//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        int caloriesCont = 0; // счетчик калорий за день.
        for (UserMeal cc : meals) { // считаем калории за день
            caloriesCont = caloriesCont + cc.getCalories();
        }

        List<UserMealWithExcess> list = new ArrayList<>(); // создаем массив его будем заполнять и возвращать.
        for (UserMeal um : meals) { // проверяем что время еды находится между startTime и endTime
            if (startTime.isBefore(um.getDateTime().toLocalTime()) && endTime.isAfter(um.getDateTime().toLocalTime())) {
                list.add(new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), caloriesCont > caloriesPerDay));
            }
        }
        return list;
    }

    public  static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // счетчик калорий за день
        int caloriesDayCont = meals.stream().mapToInt(UserMeal::getCalories).sum();

       List<UserMealWithExcess> list = meals.stream()
               .filter(s ->s.getDateTime().toLocalTime().isAfter(startTime)) // фильтр
               .filter(s-> s.getDateTime().toLocalTime().isBefore(endTime)) // фильтр 2
               .map(c -> new UserMealWithExcess(c.getDateTime(),c.getDescription(),c.getCalories(),caloriesDayCont> caloriesPerDay))// создаем новый элемент
               .collect(Collectors.toList()); // передаем в лист
        return list;
    }
}
