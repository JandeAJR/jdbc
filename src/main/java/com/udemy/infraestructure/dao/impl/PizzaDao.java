package com.udemy.infraestructure.dao.impl;

import com.udemy.infraestructure.dao.Dao;
import com.udemy.infraestructure.database.DataBaseIntegrityException;
import com.udemy.infraestructure.database.Database;
import com.udemy.infraestructure.database.DatabaseException;
import com.udemy.infraestructure.models.Pizza;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDao implements Dao<Pizza> {
	private final Connection connection;

	public PizzaDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
    public void insert(Pizza pizza) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO tb_pizza(name, price) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1,pizza.getName());
            preparedStatement.setDouble(2, pizza.getPrice());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    pizza.setId(resultSet.getLong(1));
                }
            }
            else {
                throw new DatabaseException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Pizza pizza) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE tb_pizza SET name = ?, price = ? WHERE id = ?"
            );
            preparedStatement.setString(1, pizza.getName());
            preparedStatement.setDouble(2,pizza.getPrice());
            preparedStatement.setLong(3, pizza.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM tb_pizza WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataBaseIntegrityException(e.getMessage());
        }
        finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public Pizza findById(Long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT id, name, price FROM tb_pizza WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Pizza pizza = new Pizza();
                pizza.setId(resultSet.getLong("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getDouble("price"));
                return pizza;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(resultSet);
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<Pizza> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT id, name, price FROM tb_pizza ORDER BY name");
            resultSet = preparedStatement.executeQuery();
            List<Pizza> list = new ArrayList<>();
            while (resultSet.next()) {
                Pizza pizza = new Pizza();
                pizza.setId(resultSet.getLong("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setPrice(resultSet.getDouble("price"));
                list.add(pizza);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(resultSet);
            Database.closeStatement(preparedStatement);
        }
    }	
}
