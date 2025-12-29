package com.udemy.application.services;

import com.udemy.infraestructure.dao.Dao;
import com.udemy.infraestructure.dao.DaoFactory;
import com.udemy.infraestructure.database.Database;
import com.udemy.infraestructure.models.Customer;
import java.util.List;

public class CustomerService {
	private Dao<Customer> dao = DaoFactory.createCustomerDao();

    public Customer customerRegistration(Customer customer) {
        dao.insert(customer);
        Database.commit();
        return  customer;
    }

    public Customer findById(String id) { return dao.findById(id); }

    public List<Customer> findAll() {
        return dao.findAll();
    }

    public Customer update(Customer customer) {
        dao.update(customer);
        Database.commit();
        return customer;
    }

    public void deleteById(String id) {
        dao.deleteById(id);
        Database.commit();
    }
}
