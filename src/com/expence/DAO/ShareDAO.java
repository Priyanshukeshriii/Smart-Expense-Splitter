package PRIYANSHU.Expense.DAO;


import com.expence.DAO.Database;
import com.expence.core.Share;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShareDAO{

    public int addShare(Share share) {
        String sql = "INSERT INTO shares (group_id, member_id, expense_id, amount) VALUES (?, ?, ?, ?)";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, share.getGroupId());
            ps.setInt(2, share.getMemberId());
            ps.setInt(3, share.getExpenseId());
            ps.setDouble(4, share.getShareAmount());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);  // return generated shareId
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    public List<Share> getSharesByGroup(int groupId) {
        List<Share> list = new ArrayList<>();
        String sql = "SELECT * FROM shares WHERE group_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Share s = new Share(
                        rs.getInt("id"),
                        rs.getInt("group_id"),
                        rs.getInt("member_id"),
                        rs.getInt("expense_id"),
                        rs.getDouble("amount")
                );
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<Share> getSharesByExpense(int expenseId) {
        List<Share> list = new ArrayList<>();
        String sql = "SELECT * FROM shares WHERE expense_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expenseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Share s = new Share(
                        rs.getInt("expense_id"),
                        rs.getInt("group_id"),
                        rs.getInt("id"),
                        rs.getInt("member_id"),
                        rs.getDouble("amount")
                );
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public boolean deleteShare(int groupId, int shareId, int currentUserId) {

        String sqlCheck = "SELECT member_id FROM shares WHERE id = ? AND group_id = ?";
        String sqlDelete = "DELETE FROM shares WHERE id = ? AND group_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {

            psCheck.setInt(1, shareId);
            psCheck.setInt(2, groupId);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                System.out.println(" Share not found!");
                return false;
            }

            int ownerId = rs.getInt("member_id");

            if (ownerId != currentUserId) {
                System.out.println("Only the member who added the share can delete it!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlDelete)) {

            ps.setInt(1, shareId);
            ps.setInt(2, groupId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
