package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setPassword(rs.getString("password"));
        user.setCaloriesPerDay(rs.getInt("calories_per_day"));
        user.setRegistered(rs.getDate("registered"));
        String role = rs.getString("roles");
        List<Role> roles;
        if(role!=null){
            roles=new ArrayList<>();
            if(role.equals("USER")){
                roles.add(Role.USER);
            }else if(role.equals("ADMIN")){
                roles.add(Role.ADMIN);
            }
        }
        else {
            roles=null;
        }
        user.setRoles(roles);
        return user;
    }
}