package com.studenttracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.studenttracker.model.User;
import com.studenttracker.util.DatabaseUtil;
import com.studenttracker.util.SecurityUtil;

public class UserDAO {

    // 1) Check if user exists (by username or email)
    public boolean userExists(String username, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // 2) Create a new user with hashed password
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password, salt) VALUES (?, ?, ?, ?)";
        String salt = SecurityUtil.generateSalt();
        String hashedPassword = SecurityUtil.hashPassword(user.getPassword(), salt);

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, salt);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            user.setSalt(salt);
            user.setPassword(hashedPassword);
        }
    }

    // 3) Authenticate a user by verifying hashed password
    public User authenticate(String username, String plainPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dbPassword = rs.getString("password");
                String dbSalt = rs.getString("salt");

                // Hash the incoming password with the stored salt
                String hashedInput = SecurityUtil.hashPassword(plainPassword, dbSalt);

                if (hashedInput.equals(dbPassword)) {
                    // success
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setSalt(dbSalt);
                    user.setPassword(dbPassword);
                    return user;
                }
            }
        }
        return null; // invalid credentials
    }
}
