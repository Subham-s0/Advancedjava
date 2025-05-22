package com.Advancedjava.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.PaymentModel;
import com.Advancedjava.model.PaymentModel.PaymentMethod;
import com.Advancedjava.model.PaymentModel.PaymentStatus;

public class PaymentDao {

	public int savePayment(PaymentModel payment) throws SQLException {
		String checkSql = "SELECT COUNT(*) FROM payment WHERE booking_id = ? AND payment_status = 'PAID'";

		try (Connection connection = DBconnection.getDbConnection();
				PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {

			checkStmt.setInt(1, payment.getBookingId());

			try (ResultSet rs = checkStmt.executeQuery()) {
				if (rs.next() && rs.getInt(1) > 0) {
					throw new SQLException("Payment has already been made for this booking.");
				}
			}
			String insertSql = "INSERT INTO payment (booking_id, payment_amount, payment_method, payment_status, payment_date) "
					+ "VALUES (?, ?, ?, ?, ?)";

			try (PreparedStatement statement = connection.prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS)) {

				statement.setInt(1, payment.getBookingId());
				statement.setBigDecimal(2, payment.getPaymentAmount());
				statement.setString(3, payment.getPaymentMethod().toString());
				statement.setString(4, payment.getPaymentStatus().toString());
				statement.setDate(5, payment.getPaymentDate());

				int affectedRows = statement.executeUpdate();

				if (affectedRows == 0) {
					throw new SQLException("Creating payment failed, no rows affected.");
				}

				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int transactionId = generatedKeys.getInt(1);
						payment.setTransactionId(transactionId);
						return transactionId;
					} else {
						throw new SQLException("Creating payment failed, no ID obtained.");
					}
				}
			}
		}
	}

	public PaymentModel findById(int transactionId) throws SQLException {
		String sql = "SELECT * FROM payment WHERE transaction_id = ?";

		try (Connection connection = DBconnection.getDbConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, transactionId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return extractPaymentFromResultSet(resultSet);
				}
			}
		}

		return null;
	}

	public List<PaymentModel> findByBookingId(int bookingId) throws SQLException {
		String sql = "SELECT * FROM payment WHERE booking_id = ?";
		List<PaymentModel> payments = new ArrayList<>();

		try (Connection connection = DBconnection.getDbConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, bookingId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					payments.add(extractPaymentFromResultSet(resultSet));
				}
			}
		}

		return payments;
	}

	public boolean updatePayment(PaymentModel payment) throws SQLException {
		String sql = "UPDATE payment SET payment_status = ?, payment_method = ?, payment_amount = ?, payment_date = ? "
				+ "WHERE transaction_id = ?";

		try (Connection connection = DBconnection.getDbConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, payment.getPaymentStatus().toString());
			statement.setString(2, payment.getPaymentMethod().toString());
			statement.setBigDecimal(3, payment.getPaymentAmount());
			statement.setDate(4, payment.getPaymentDate());
			statement.setInt(5, payment.getTransactionId());

			return statement.executeUpdate() > 0;
		}
	}

	private PaymentModel extractPaymentFromResultSet(ResultSet rs) throws SQLException {
		return new PaymentModel(rs.getInt("transaction_id"), rs.getBigDecimal("payment_amount"),
				PaymentMethod.valueOf(rs.getString("payment_method")),
				PaymentStatus.valueOf(rs.getString("payment_status")), rs.getDate("payment_date"),
				rs.getInt("booking_id"));
	}

	public BigDecimal getTotalPaidAmount() throws DataAccessException {
		String sql = "SELECT COALESCE(SUM(payment_amount), 0) FROM payment WHERE payment_status = 'paid'";

		try (Connection connection = DBconnection.getDbConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {

			if (rs.next()) {
				return rs.getBigDecimal(1); // total sum of paid payments
			} else {
				return BigDecimal.ZERO;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error calculating total paid amount", e);
		}
	}

}
