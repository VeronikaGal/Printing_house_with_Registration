package com.galeeva.project.dao;

import com.galeeva.project.entity.MachineEntity;
import com.galeeva.project.entity.MachineModel;
import com.galeeva.project.entity.MachineType;
import com.galeeva.project.exeption.DaoException;
import com.galeeva.project.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MachineDao implements Dao<Integer, MachineEntity> {

    private static final MachineDao INSTANCE = new MachineDao();

    private static final String FIND_ALL_SQL = """
            SELECT id,
                   model,
                   type
            FROM machine
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM machine
            WHERE id = ?
             """;

    private static final String SAVE_SQL = """
            INSERT INTO machine(model, type) 
            VALUES (?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE machine
            SET  model = ?,
                 type = ?
            WHERE id = ?  
            """;

    private MachineDao() {
    }

    public static MachineDao getInstance() {
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
    public MachineEntity save(MachineEntity machine) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL)) {
            prepareStatement.setString(1, String.valueOf(machine.getModel()));
            prepareStatement.setString(2, String.valueOf(machine.getType()));

            prepareStatement.executeUpdate();

            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                machine.setId(generatedKeys.getInt("id"));
            }
            return machine;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public void update(MachineEntity machine) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, String.valueOf(machine.getModel()));
            prepareStatement.setString(2, String.valueOf(machine.getType()));
            prepareStatement.setInt(3, machine.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<MachineEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<MachineEntity> findById(Integer id, Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();
            MachineEntity machine = null;
            if (resultSet.next()) {
                machine = buildMachine(resultSet);
            }
            return Optional.ofNullable(machine);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<MachineEntity> findAll() {
        try (var connection = ConnectionManager.get()) {
            return findAll(connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<MachineEntity> findAll(Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = prepareStatement.executeQuery();
            List<MachineEntity> machineEntities = new ArrayList<>();
            while (resultSet.next()) {
                machineEntities.add(buildMachine(resultSet));
            }
            return machineEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private MachineEntity buildMachine(ResultSet resultSet) throws SQLException {
        return new MachineEntity(
                resultSet.getObject("id", Integer.class),
                MachineModel.valueOf(resultSet.getObject("model", String.class)),
                MachineType.valueOf(resultSet.getObject("type", String.class))
        );
    }
}
