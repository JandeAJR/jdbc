package com.udemy.application.services;

import com.udemy.infraestructure.dao.Dao;
import com.udemy.infraestructure.dao.DaoFactory;
import com.udemy.infraestructure.database.Database;
import com.udemy.infraestructure.models.Pizza;
import java.util.List;

public class PizzaService {
	private Dao<Pizza> dao = DaoFactory.createPizzaDao();

    public Pizza pizzaRegistration(Pizza pizza) {
        dao.insert(pizza);
        Database.commit();
        return  pizza;
    }

    public Pizza findById(Long id) { return dao.findById(id); }

    public List<Pizza> findAll() {
        return dao.findAll();
    }

    public Pizza update(Pizza pizza) {
        dao.update(pizza);
        Database.commit();
        return pizza;
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
        Database.commit();
        //DataBase.rollback();
    }
}
