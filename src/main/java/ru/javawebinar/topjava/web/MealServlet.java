package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealMemoryStorage;

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
    private MealMemoryStorage mealStorage;
    static final int caloriesPerDay = 2000;

    @Override
    public void init() {
        mealStorage = new MealMemoryStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Integer uuid = Integer.valueOf(request.getParameter("uuid"));
        String calories = request.getParameter("calories");
        String description = request.getParameter("description");
        String dateTime = request.getParameter("datetime");
        if (calories != null && description != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
            Meal newMeal = new Meal(uuid, localDateTime, description, parseInt(calories));
            try {
                mealStorage.save(newMeal);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.debug("redirect to meals, save:{}", newMeal);
        }
        request.setAttribute("mealsTo", filteredByStreams(mealStorage.getStorage(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "edit":
                    Integer uuidEdit = Integer.valueOf(request.getParameter("uuid"));
                    request.setAttribute("meal", mealStorage.get(uuidEdit));
                    log.debug("redirect to editMeal.jsp, edit uuid:{}", uuidEdit);
                    request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                    return;
                case "add":
                    Meal newMeal = new Meal();
                    newMeal.setUuid(0);
                    request.setAttribute("meal", newMeal);
                    log.debug("redirect to editMeal.jsp, create new meal");
                    request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                    return;
                case "delete":
                    Integer uuidDelete = Integer.valueOf(request.getParameter("uuid"));
                    log.debug("redirect to meals, delete uuid:{}", uuidDelete);
                    mealStorage.delete(uuidDelete);
                    response.sendRedirect("meals");
                    return;
            }
        }
        log.debug("redirect to meals, display list meals");
        request.setAttribute("mealsTo", filteredByStreams(mealStorage.getStorage(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
