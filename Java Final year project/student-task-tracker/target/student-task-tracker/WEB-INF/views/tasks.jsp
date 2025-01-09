<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.studenttracker.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="com.studenttracker.model.Task.TaskStatus" %>
<%@ page import="com.studenttracker.model.Task.TaskType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tasks - Student Task Tracker</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" />
</head>
<body>
    <div class="container">
        <h2>Your Tasks</h2>
        <p><a href="<%= request.getContextPath() %>/auth/logout">Logout</a></p>

        <h3>Create New Task</h3>
        <form action="<%= request.getContextPath() %>/tasks/create" method="post">
            <div class="form-group">
                <label>Title:</label>
                <input type="text" name="title" required />
            </div>
            <div class="form-group">
                <label>Description:</label>
                <textarea name="description"></textarea>
            </div>
            <div class="form-group">
                <label>Date (YYYY-MM-DD):</label>
                <input type="date" name="date" required />
            </div>
            <div class="form-group">
                <label>Type:</label>
                <select name="type">
                    <option value="ASSIGNMENT">Assignment</option>
                    <option value="EXAM">Exam</option>
                    <option value="HOMEWORK">Homework</option>
                    <option value="HOLIDAY">Holiday</option>
                </select>
            </div>
            <button type="submit">Add Task</button>
        </form>

        <hr />

        <h3>Existing Tasks</h3>
        <c:if test="${empty tasks}">
            <p>No tasks found.</p>
        </c:if>
        <c:if test="${not empty tasks}">
            <table border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                <c:forEach items="${tasks}" var="task">
                    <tr>
                        <td>${task.title}</td>
                        <td>${task.description}</td>
                        <td>${task.taskDate}</td>
                        <td>${task.taskType}</td>
                        <td>${task.status}</td>
                        <td>
                            <c:choose>
                                <c:when test="${task.status == 'PENDING'}">
                                    <form action="<%= request.getContextPath() %>/tasks/complete" method="post" style="display:inline;">
                                        <input type="hidden" name="taskId" value="${task.id}" />
                                        <button type="submit">Mark Completed</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    Completed
                                </c:otherwise>
                            </c:choose>

                            <!-- Delete -->
                            <form action="<%= request.getContextPath() %>/tasks/delete" method="post" style="display:inline;">
                                <input type="hidden" name="taskId" value="${task.id}" />
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</body>
</html>
