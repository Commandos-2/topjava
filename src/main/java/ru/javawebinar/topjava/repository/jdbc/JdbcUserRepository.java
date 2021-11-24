package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    //private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final ResultSetExtractor<List<User>> resultSetExtractor = (rs) -> {
        Map<Integer, User> map = new HashMap();
        while (rs.next()) {
            User user = new User();
            Integer id;
            id = rs.getInt("id");
            if (!map.containsKey(id)) {
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setPassword(rs.getString("password"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setRegistered(rs.getDate("registered"));
                user.setRoles(List.of());
            } else {
                user = map.get(id);
            }
            String role = rs.getString("roles");
            if (role != null) {
                user.getRoles().add(Role.valueOf(role));
            }
            map.put(id, user);
        }
        return new ArrayList<>(map.values());
    };

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            int id = newKey.intValue();
            user.setId(id);
            if (insertRoles(user, id) != user.getRoles().size()) {
                return null;
            }

        } else {
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource) == 0 || insertRoles(user, user.getId()) != user.getRoles().size()) {
                return null;
            }
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT u.*,r.role as roles " +
                "FROM users u LEFT JOIN user_roles r ON u.id=r.user_id WHERE id=?", resultSetExtractor, id));
    }

    @Override
    public User getByEmail(String email) {
        return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT u.*,r.role as roles" +
                " FROM users u LEFT JOIN user_roles r ON u.id=r.user_id WHERE email=?", resultSetExtractor, email));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT u.*,r.role as roles FROM users u LEFT JOIN" +
                " user_roles r ON u.id=r.user_id ORDER BY name, email", resultSetExtractor);
    }

    private static String convertRoleToString(Role roles) {

        if (roles != null) {
            return roles.toString();
        }
        return null;
    }

    private int insertRoles(User user, int id) {
        List<Role> list = user.getRoles().stream().toList();
        return jdbcTemplate.batchUpdate(
                "INSERT INTO user_roles (role, user_id) VALUES (?,?)",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, convertRoleToString(list.get(i)));
                        ps.setInt(2, id);
                    }

                    public int getBatchSize() {
                        return list.size();
                    }
                }).length;
    }
}
