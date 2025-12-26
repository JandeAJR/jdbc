package com.udemy.infraestructure.dao;

import com.udemy.infraestructure.dao.impl.*;
import com.udemy.infraestructure.database.Database;

/*
	Padrão Factory que é uma classe que cria objetos.
	O objetivo é facilitar a criação de objetos
*/

public class DaoFactory {
	public static Dao createPizzaDao() {
        return new PizzaDao(Database.getConnection());
    }

    public static Dao createCustomerDao() { 
    	return new CustomerDao(Database.getConnection()); 
    }

    public static Dao createRequestDao() {
        return new RequestDao(Database.getConnection());
    }
}
