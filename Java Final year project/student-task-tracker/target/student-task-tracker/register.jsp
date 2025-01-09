<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Student Task Tracker</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" />
</head>
<body>
    <div class="container">
        <h2>Register</h2>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="error" style="color: red;"><%= error %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/auth/register" method="post">
            <div class="form-group">
                <label>Username: </label>
                <input type="text" name="username" required />
            </div>
            <div class="form-group">
                <label>Email: </label>
                <input type="email" name="email" required />
            </div>
            <div class="form-group">
                <label>Password: </label>
                <input type="password" name="password" required />
            </div>
            <button type="submit">Register</button>
        </form>

        <p>Already have an account? 
            <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
        </p>
    </div>
</body>
</html>
