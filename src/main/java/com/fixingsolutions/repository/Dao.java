package com.fixingsolutions.repository;

import com.fixingsolutions.domain.Funcionario;

import java.sql.SQLException;
import java.util.List;


public interface Dao<T> {

	    Funcionario get(int id);

	    List<T> getAll();

	    void save(T t);

	    void update(T t);

	    void delete(T t);
	}
