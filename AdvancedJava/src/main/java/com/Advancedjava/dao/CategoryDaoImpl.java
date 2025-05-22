package com.Advancedjava.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public boolean existsByCategoryName(String categoryName) throws DataAccessException {
		String query = "SELECT COUNT(*) FROM category WHERE category_name = ?";

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, categoryName);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database error checking category existence", e);
		}
	}

	@Override
	public Categorymodel findById(int categoryId) throws DataAccessException {
		String sql = "SELECT * FROM category WHERE category_id = ?";
		Categorymodel category = null;

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, categoryId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					category = new Categorymodel(rs.getInt("category_id"), rs.getString("category_name"),
							rs.getString("category_icon"));
				}
				return category;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching category", e);
		}
	}

	public List<Categorymodel> findAllcategories() throws DataAccessException {
		String sql = "SELECT * FROM category";
		List<Categorymodel> categories = new ArrayList<>();

		try (Connection conn = DBconnection.getDbConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				Categorymodel category = new Categorymodel(rs.getInt("category_id"), rs.getString("category_name"),
						rs.getString("category_icon"));
				categories.add(category);
			}
			return categories;
		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching all categories", e);
		}
	}
}