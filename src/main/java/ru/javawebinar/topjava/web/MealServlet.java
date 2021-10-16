package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealListStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealListStorage mealStorage;
    final int caloriesPerDay = 2000;

    public void init() {
        mealStorage = new MealListStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String calories = request.getParameter("calories");
        String description = request.getParameter("description");
        String dateTime = request.getParameter("datetime");
        if (calories != null && description != null && uuid != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
            Meal newMeal = new Meal(uuid, localDateTime, description, parseInt(calories));
            if (mealStorage.update(newMeal)) {
                log.debug("redirect to meals, edit:" + newMeal);
            } else {
                mealStorage.save(newMeal);
                log.debug("redirect to meals, create:" + newMeal);
            }
        }
        request.setAttribute("mealsTo", filteredByStreams(mealStorage.getStorage(), LocalTime.of(0, 0), LocalTime.of(23, 59, 59, 999999), caloriesPerDay));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");
        if (action != null) {
            switch (action) {
                case "edit":
                    request.setAttribute("meal", mealStorage.get(uuid));
                    log.debug("redirect to editMeal.jsp, edit:" + mealStorage.get(uuid).toString());
                    request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                    return;
                case "add":
                    request.setAttribute("meal", new Meal());
                    log.debug("redirect to editMeal.jsp, create new meal");
                    request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                    return;
                case "delete":
                    log.debug("redirect to meals, delete:" + mealStorage.get(uuid).toString());
                    mealStorage.delete(uuid);
                    response.sendRedirect("meals");
                    return;
            }
        }
        log.debug("redirect to meals, display list meals");
        request.setAttribute("mealsTo", filteredByStreams(mealStorage.getStorage(), LocalTime.of(0, 0), LocalTime.of(23, 59, 59, 999999), caloriesPerDay));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
