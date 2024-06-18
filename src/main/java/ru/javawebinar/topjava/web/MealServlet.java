package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getMeals;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    final static int CALORIES_PER_DAY = 2000;
    final static LocalTime START_TIME = LocalTime.of(0, 0);
    final static LocalTime END_TIME = LocalTime.of(23, 59);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealsList = MealsUtil.filteredByStreams(getMeals(), START_TIME, END_TIME, CALORIES_PER_DAY);

        request.setAttribute("mealsList", mealsList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
