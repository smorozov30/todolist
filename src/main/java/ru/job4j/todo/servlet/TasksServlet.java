package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.HibernateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class TasksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        boolean checkbox = Boolean.parseBoolean(req.getParameter("showAll"));
        Predicate<Task> condition = checkbox ? task -> true : task -> !task.isDone();
        List<Task> tasks = HibernateService.instOf().getTasks(condition);
        String jsonResp = gson.toJson(tasks);
        resp.getWriter().write(jsonResp);
    }
}
