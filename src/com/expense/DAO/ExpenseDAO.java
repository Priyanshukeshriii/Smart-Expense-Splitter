package com.expense.DAO;

import com.expense.core.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public int addExpense(Expense expense) {

        String sql = "INSERT INTO expenses (group_id, paid_by, amount, description, date) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, expense.getGroupId());
            ps.setInt(2, expense.getPaidByMember());
            ps.setDouble(3, expense.getAmount());
            ps.setString(4, expense.getDescription());
            ps.setDate(5, Date.valueOf(expense.getDate()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;  // failed
    }

    public List<Expense> getExpensesByGroup(int groupId) {

        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE group_id = ? ORDER BY date DESC";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Expense e = new Expense();
                e.setId(rs.getInt("id"));
                e.setGroupId(rs.getInt("group_id"));
                e.setPaidByMember(rs.getInt("paid_by"));
                e.setAmount(rs.getDouble("amount"));
                e.setDescription(rs.getString("description"));
                e.setDate(rs.getDate("date").toLocalDate());

                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public boolean deleteExpense(int groupId, int expenseId, int currentUserId) {

        // Step 1: Verify that current user added this expense
        String checkSql = "SELECT paid_by FROM expenses WHERE id = ? AND group_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement checkPs = con.prepareStatement(checkSql)) {

            checkPs.setInt(1, expenseId);
            checkPs.setInt(2, groupId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                int paidBy = rs.getInt("paid_by");

                if (paidBy != currentUserId) {
                    System.out.println("Only the member who added this expense can delete it!");
                    return false;
                }

            } else {
                System.out.println("Expense not found for this group!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Step 2: If allowed → delete the expense
        String deleteSql = "DELETE FROM expenses WHERE id = ? AND group_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteSql)) {

            ps.setInt(1, expenseId);
            ps.setInt(2, groupId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
