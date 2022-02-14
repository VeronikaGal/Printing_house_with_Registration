package com.galeeva.project.dao;

import com.galeeva.project.dto.UserFilter;
import com.galeeva.project.entity.Role;
import com.galeeva.project.entity.UserEntity;
import com.galeeva.project.exeption.DaoException;
import com.galeeva.project.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, UserEntity> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String FIND_ALL_SQL = """
            SELECT id,
                   name,
                   phone_number,
                   address,
                   role,
                   email,
                   password
            FROM users
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
             """;

    private static final String SAVE_SQL = """
            INSERT INTO users(name, phone_number, address, role, email, password) 
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE users
            SET  name = ?,
                 phone_number = ?,
                 address=?,
                 role = ?,
                 email = ?,
                 password = ?  
            WHERE id = ?  
            """;

    private static final String FIND_ALL_LIMIT_OFFSET_SQL = FIND_ALL_SQL + """
            LIMIT ?
            OFFSET ?
            """;

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    @SneakyThrows
    public UserEntity save(UserEntity user) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL)) {
            prepareStatement.setObject(1, user.getName());
            prepareStatement.setObject(2, user.getPhoneNumber());
            prepareStatement.setObject(3, user.getAddress());
            prepareStatement.setObject(4, user.getRole().name());
            prepareStatement.setObject(5, user.getEmail());
            prepareStatement.setObject(6, user.getPassword());

            prepareStatement.executeUpdate();

            var generatedKeys = prepareStatement.getGeneratedKeys();
            generatedKeys.next();
            user.setId(generatedKeys.getObject("id", Long.class));

            return user;
        }
    }

    @Override
    public void update(UserEntity user) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setObject(1, user.getName());
            prepareStatement.setObject(2, user.getPhoneNumber());
            prepareStatement.setObject(3, user.getAddress());
            prepareStatement.setObject(4, user.getRole().name());
            prepareStatement.setObject(5, user.getEmail());
            prepareStatement.setObject(6, user.getPassword());
            prepareStatement.setObject(7, user.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<UserEntity> findById(Long id, Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setObject(1, id);
            var resultSet = prepareStatement.executeQuery();
            UserEntity user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet, connection);
            }
            return Optional.ofNullable(user);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (var connection = ConnectionManager.get()) {
            return findAll(connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<UserEntity> findAll(Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = prepareStatement.executeQuery();
            List<UserEntity> userEntities = new ArrayList<>();
            while (resultSet.next()) {
                userEntities.add(buildUser(resultSet, connection));
            }
            return userEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<UserEntity> findAll(UserFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL_LIMIT_OFFSET_SQL)) {
            for (int i = 0; i < parameters.size(); i++) {
                prepareStatement.setObject(i + 1, parameters.get(i));
            }
            var resultSet = prepareStatement.executeQuery();
            List<UserEntity> userEntities = new ArrayList<>();
            while (resultSet.next()) {
                userEntities.add(buildUser(resultSet, connection));
            }
            return userEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private UserEntity buildUser(ResultSet resultSet, Connection connection) throws SQLException {
        return new UserEntity(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("name", String.class),
                resultSet.getObject("phone_number", String.class),
                resultSet.getObject("address", String.class),
                Role.valueOf(resultSet.getObject("role", String.class)),
                resultSet.getObject("email", String.class),
                resultSet.getObject("password", String.class)
        );
    }
}
