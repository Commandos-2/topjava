package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MealToList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealToList mealsToList;

    public void init() {
        mealsToList = new MealToList(LocalTime.of(0, 0), LocalTime.of(23, 59));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");
        if (action != null) {
            switch (action) {
                case "delete":
                    mealsToList.delete(uuid);
                    mealsToList.RecalculateСaloriesPerDay();
                    response.sendRedirect("meals");
                    return;
                case "edit":
                    request.setAttribute("mealTo", mealsToList.get(uuid));
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
                    return;
                case "add":
                    request.setAttribute("mealTo", createTo(new Meal(), false));
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
                    return;
            }
        } else {
            String calories = request.getParameter("calories");
            String description = request.getParameter("description");
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            if (calories != null && description != null && uuid != null) {
                LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
                for (MealTo mealTo : mealsToList.getMealToList()) {
                    if (mealTo.getUuid().equals(uuid)) {
                        mealTo.setDateTime(localDateTime);
                        mealTo.setCalories(parseInt(calories));
                        mealTo.setDescription(description);
                        mealsToList.RecalculateСaloriesPerDay();
                        request.setAttribute("mealsTo", mealsToList.getMealToList());
                        request.getRequestDispatcher("meals.jsp").forward(request, response);
                        return;
                    }
                }
                mealsToList.add(new MealTo(localDateTime,description,parseInt(calories),false,uuid ));
            }
            mealsToList.RecalculateСaloriesPerDay();
            request.setAttribute("mealsTo", mealsToList.getMealToList());
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
    }
}
