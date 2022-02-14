package com.galeeva.project.dao;

import com.galeeva.project.dto.OrderDataFilter;
import com.galeeva.project.entity.OrderDataEntity;
import com.galeeva.project.entity.OrderDelivery;
import com.galeeva.project.entity.OrderPaperType;
import com.galeeva.project.entity.OrderStatus;
import com.galeeva.project.exeption.DaoException;
import com.galeeva.project.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDataDao implements Dao<Long, OrderDataEntity> {

    private static final OrderDataDao INSTANCE = new OrderDataDao();

    private static final String DELETE_SQL = """
            DELETE FROM order_data
            WHERE id = ?
             """;

    private static final String SAVE_SQL = """
            INSERT INTO order_data(users_id, service_id, file, paper_type, quantity, machine_id, status, total_price, 
            created_at, delivered_at, delivery) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE order_data
            SET  users_id = ?,
                 service_id = ?,
                 file=?,
                 paper_type= ?,
                 quantity = ?,
                 machine_id = ?,
                 status= ?,
                 total_price = ?, 
                 created_at = ?,
                 delivered_at = ?,
                 delivery = ?  
            WHERE id = ?  
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id,
                   users_id,
                   service_id,
                   file,
                   paper_type,
                   quantity, 
                   machine_id,
                   status,
                   total_price,
                   created_at, 
                   delivered_at,
                   delivery
            FROM order_data
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String FIND_ALL_LIMIT_OFFSET_SQL = FIND_ALL_SQL + """
            LIMIT ?
            OFFSET ?
            """;

    private final UserDao userDao = UserDao.getInstance();

    private final MachineDao machineDao = MachineDao.getInstance();

    private final ServiceDao serviceDao = ServiceDao.getInstance();

    private OrderDataDao() {
    }

    public List<OrderDataEntity> findAll(OrderDataFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL_LIMIT_OFFSET_SQL);) {
            for (int i = 0; i < parameters.size(); i++) {
                prepareStatement.setObject(i + 1, parameters.get(i));
            }
            var resultSet = prepareStatement.executeQuery();
            List<OrderDataEntity> orderDataEntities = new ArrayList<>();
            while (resultSet.next()) {
                orderDataEntities.add(buildOrderData(resultSet));
            }
            return orderDataEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<OrderDataEntity> findAllByServiceId(Long serviceId) {
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setObject(1, serviceId);

            ResultSet resultSet = prepareStatement.executeQuery();
            List<OrderDataEntity> orderDataEntities = new ArrayList<>();
            while (resultSet.next()) {
                orderDataEntities.add(buildOrderData(resultSet));
            }
            return orderDataEntities;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
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
    public OrderDataEntity save(OrderDataEntity orderData) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL)) {
            prepareStatement.setLong(1, orderData.getUsersId().getId());
            prepareStatement.setInt(2, orderData.getServiceId().getId());
            prepareStatement.setString(3, orderData.getFile());
            prepareStatement.setString(4, String.valueOf(orderData.getPaperType()));
            prepareStatement.setInt(5, orderData.getQuantity());
            prepareStatement.setObject(6, orderData.getMachineId().getId());
            prepareStatement.setString(7, String.valueOf(orderData.getStatus()));
            prepareStatement.setBigDecimal(8, orderData.getTotalPrice());
            prepareStatement.setTimestamp(9, Timestamp.valueOf(orderData.getCreatedAt()));
            prepareStatement.setObject(10, Timestamp.class);
            prepareStatement.setString(11, String.valueOf(orderData.getDelivery()));

            prepareStatement.executeUpdate();

            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderData.setId(generatedKeys.getLong("id"));
            }
            return orderData;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public void update(OrderDataEntity orderData) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setLong(1, orderData.getUsersId().getId());
            prepareStatement.setInt(2, orderData.getServiceId().getId());
            prepareStatement.setString(3, orderData.getFile());
            prepareStatement.setString(4, String.valueOf(orderData.getPaperType()));
            prepareStatement.setInt(5, orderData.getQuantity());
            prepareStatement.setObject(6, orderData.getMachineId());
            prepareStatement.setString(7, String.valueOf(orderData.getStatus()));
            prepareStatement.setBigDecimal(8, orderData.getTotalPrice());
            prepareStatement.setTimestamp(9, Timestamp.valueOf(orderData.getCreatedAt()));
            prepareStatement.setObject(10, orderData.getDeliveredAt());
            prepareStatement.setString(11, String.valueOf(orderData.getDelivery()));
            prepareStatement.setLong(12, orderData.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<OrderDataEntity> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    public Optional<OrderDataEntity> findById(Long id, Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setObject(1, id);
            var resultSet = prepareStatement.executeQuery();
            OrderDataEntity orderData = null;
            if (resultSet.next()) {
                orderData = buildOrderData(resultSet);
            }
            return Optional.ofNullable(orderData);
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public List<OrderDataEntity> findAll() {
        try (var connection = ConnectionManager.get()) {
            return findAll(connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<OrderDataEntity> findAll(Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = prepareStatement.executeQuery();
            List<OrderDataEntity> orderDataEntities = new ArrayList<>();
            while (resultSet.next()) {
                orderDataEntities.add(buildOrderData(resultSet));
            }
            return orderDataEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private OrderDataEntity buildOrderData(ResultSet resultSet) throws SQLException {
        return new OrderDataEntity(
                resultSet.getObject("id", Long.class),
                userDao.findById(resultSet.getObject("users_id", Long.class)).orElse(null),
                serviceDao.findById(resultSet.getObject("service_id", Integer.class)).orElse(null),
                resultSet.getObject("file", String.class),
                OrderPaperType.valueOf(resultSet.getObject("paper_type", String.class)),
                resultSet.getObject("quantity", Integer.class),
                machineDao.findById(resultSet.getObject("machine_id", Integer.class)).orElse(null),
                OrderStatus.valueOf(resultSet.getObject("status", String.class)),
                resultSet.getObject("total_price", BigDecimal.class),
                resultSet.getObject("created_at", Timestamp.class).toLocalDateTime(),
                Optional.ofNullable(resultSet.getObject("delivered_at", Timestamp.class).toLocalDateTime()),
                OrderDelivery.valueOf(resultSet.getObject("delivery", String.class))
        );
    }

    public static OrderDataDao getInstance() {
        return INSTANCE;
    }
}
