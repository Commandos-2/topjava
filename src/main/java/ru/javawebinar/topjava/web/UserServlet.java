package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.setUserId;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private AdminRestController adminUserController;

    @Override
    public void init() {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            adminUserController = appCtx.getBean(AdminRestController.class);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String user = request.getParameter("userId");
        if (user != null) {
            int userId = Integer.parseInt(user);
            setUserId(userId);
        }
        request.setAttribute("users", adminUserController.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
