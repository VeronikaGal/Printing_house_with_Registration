package com.galeeva.project.dao;

import com.galeeva.project.entity.ServiceEntity;
import com.galeeva.project.entity.ServiceName;
import com.galeeva.project.exeption.DaoException;
import com.galeeva.project.util.ConnectionManager;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDao implements Dao<Integer, ServiceEntity> {

    private static final ServiceDao INSTANCE = new ServiceDao();

    private static final String FIND_ALL_SQL = """
            SELECT id,
                   name,
                   description,
                   price
            FROM service
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM service
            WHERE id = ?
             """;

    private static final String SAVE_SQL = """
            INSERT INTO service(name, description, price) 
            VALUES (?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE service
            SET  name = ?,
                 description = ?,
                 price = ?
            WHERE id = ?  
            """;

    private ServiceDao() {
    }

    public static ServiceDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public ServiceEntity save(ServiceEntity service) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL)) {
            prepareStatement.setString(1, String.valueOf(service.getName()));
            prepareStatement.setString(2, service.getDescription());
            prepareStatement.setBigDecimal(3, service.getPrice());

            prepareStatement.executeUpdate();

            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                service.setId(generatedKeys.getInt("id"));
            }
            return service;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public void update(ServiceEntity service) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, String.valueOf(service.getName()));
            prepareStatement.setString(2, service.getDescription());
            prepareStatement.setBigDecimal(3, service.getPrice());
            prepareStatement.setInt(4, service.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<ServiceEntity> findAll() {
        try (var connection = ConnectionManager.get()) {
            return findAll(connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<ServiceEntity> findAll(Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = prepareStatement.executeQuery();
            List<ServiceEntity> serviceEntities = new ArrayList<>();
            while (resultSet.next()) {
                serviceEntities.add(buildService(resultSet));
            }
            return serviceEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<ServiceEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<ServiceEntity> findById(Integer id, Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            ServiceEntity service = null;
            if (resultSet.next()) {
                service = buildService(resultSet);
            }
            return Optional.ofNullable(service);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private ServiceEntity buildService(ResultSet resultSet) throws SQLException {
        return new ServiceEntity(
                resultSet.getObject("id", Integer.class),
                ServiceName.valueOf(resultSet.getObject("name", String.class)),
                resultSet.getObject("description", String.class),
                resultSet.getObject("price", BigDecimal.class)
        );
    }
}
