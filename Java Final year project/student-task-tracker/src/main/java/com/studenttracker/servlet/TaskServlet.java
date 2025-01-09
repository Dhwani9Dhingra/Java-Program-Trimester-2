package com.studenttracker.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.studenttracker.dao.TaskDAO;
import com.studenttracker.model.Task;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {

    private TaskDAO taskDAO = new TaskDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Check session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String path = req.getPathInfo();
        if (path == null || "/".equals(path)) {
            // Display tasks page
            int userId = (int) session.getAttribute("userId");
            try {
                List<Task> tasks = taskDAO.getTasksByUserId(userId);
                req.setAttribute("tasks", tasks);
                req.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else {
            // other GET actions (not implemented in minimal approach)
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Check session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String path = req.getPathInfo();
        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        switch (path) {
            case "/create":
                handleCreateTask(req, resp, (int) session.getAttribute("userId"));
                break;
            case "/complete":
                handleMarkComplete(req, resp);
                break;
            case "/delete":
                handleDeleteTask(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleCreateTask(HttpServletRequest req, HttpServletResponse resp, int userId)
            throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dateStr = req.getParameter("date");
        String typeStr = req.getParameter("type");

        try {
            Date taskDate = sdf.parse(dateStr);

            Task task = new Task();
            task.setUserId(userId);
            task.setTitle(title);
            task.setDescription(description);
            task.setTaskDate(taskDate);
            task.setTaskType(Task.TaskType.valueOf(typeStr));
            task.setStatus(Task.TaskStatus.PENDING);

            taskDAO.createTask(task);

            resp.sendRedirect(req.getContextPath() + "/tasks");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void handleMarkComplete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            taskDAO.markTaskCompleted(taskId);
            resp.sendRedirect(req.getContextPath() + "/tasks");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void handleDeleteTask(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            taskDAO.deleteTask(taskId);
            resp.sendRedirect(req.getContextPath() + "/tasks");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
