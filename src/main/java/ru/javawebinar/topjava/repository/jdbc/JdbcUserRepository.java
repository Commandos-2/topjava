package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        Map<Integer, User> map = new java.util.HashMap<>(Map.of());
        Integer id;
        while (rs.next()) {
            User user = new User();
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
            // System.out.println(user);
            String role = rs.getString("roles");
            if (role != null) {
                if (role.equals("USER")) {
                    user.getRoles().add(Role.USER);
                } else if (role.equals("ADMIN")) {
                    user.getRoles().add(Role.ADMIN);
                }
            }
            map.put(id, user);
        }
        return map.values().stream().toList();
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
        if (user.isNew()) {
            List<Role> list = user.getRoles().stream().toList();
            if (jdbcTemplate.batchUpdate("INSERT INTO users (name, email,password,registered,enabled,calories_per_day) VALUES (?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setDate(4, new java.sql.Date(user.getRegistered().getTime()));
                    ps.setBoolean(5, user.isEnabled());
                    ps.setInt(6, user.getCaloriesPerDay());
                }

                public int getBatchSize() {
                    return 1;
                }
            }).length == 1 && jdbcTemplate.batchUpdate(
                    "INSERT INTO user_roles (role, user_id) VALUES (?,?)",
                    new BatchPreparedStatementSetter() {

                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, convertRoleToString(list.get(i)));
                            ps.setInt(2, getByEmail(user.getEmail()).getId());
                        }

                        public int getBatchSize() {
                            return list.size();
                        }
                    }).length == user.getRoles().size()) {
                return getByEmail(user.getEmail());
            } else {
                return null;
            }
        } else {
            if (jdbcTemplate.batchUpdate(
                    "UPDATE users SET name=?, email=?,password=?,registered=?,enabled=?,calories_per_day=? WHERE id=?",
                    new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, user.getName());
                            ps.setString(2, user.getEmail());
                            ps.setString(3, user.getPassword());
                            ps.setDate(4, new java.sql.Date(user.getRegistered().getTime()));
                            ps.setBoolean(5, user.isEnabled());
                            ps.setInt(6, user.getCaloriesPerDay());
                            ps.setInt(7, user.getId());
                        }

                        public int getBatchSize() {
                            return 1;
                        }
                    }).length == 1 && jdbcTemplate.batchUpdate(
                    "UPDATE user_roles SET role=? WHERE user_id=?", user.getRoles(), user.getRoles().size(),
                    (ps, role) -> {
                        ps.setString(1, convertRoleToString(role));
                        ps.setInt(2, user.getId());
                    }).length == user.getRoles().size()) {
                return user;
            } else {
                return null;
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT u.*,r.role as roles FROM users u LEFT JOIN user_roles r ON u.id=r.user_id WHERE id=?", resultSetExtractor, id));
    }

    @Override
    public User getByEmail(String email) {
        return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT u.*,r.role as roles FROM users u LEFT JOIN user_roles r ON u.id=r.user_id WHERE email=?", resultSetExtractor, email));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT u.*,r.role as roles FROM users u LEFT JOIN user_roles r ON u.id=r.user_id ORDER BY name, email", resultSetExtractor);
    }

    private static String convertRoleToString(Role roles) {

        if (roles != null) {
            if (roles.equals(Role.USER)) {
                return "USER";
            } else if (roles.equals(Role.ADMIN)) {
                return "ADMIN";
            }
        }
        return null;
    }
}
