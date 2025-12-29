package com.udemy.application.services;

import com.udemy.infraestructure.dao.Dao;
import com.udemy.infraestructure.dao.DaoFactory;
import com.udemy.infraestructure.database.Database;
import com.udemy.infraestructure.models.Request;
import java.util.List;

public class RequestService {
	private Dao<Request> dao = DaoFactory.createRequestDao();

    public Request requestRegistration(Request request) {
        dao.insert(request);
        Database.commit();
        return  request;
    }

    public Request findById(Long id) { return dao.findById(id); }

    public List<Request> findAll() {
        return dao.findAll();
    }

    public Request update(Request request) {
        dao.update(request);
        Database.commit();
        return request;
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
        Database.commit();
    }
}
