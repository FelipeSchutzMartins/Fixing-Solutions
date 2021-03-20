package com.fixingsolutions.repository;

import com.fixingsolutions.domain.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public interface Dao<T> {

		Connection connection = new Connection();

	    T get(int id) throws SQLException;

	    List<T> getAll() throws SQLException;

	    void save(T t) throws SQLException;

	    void update(T t) throws SQLException;

	    void delete(Integer id) throws SQLException;

	    default void executarUpdate(PreparedStatement statement) throws SQLException{

	    	statement.executeUpdate();

		}

	}
