package com.expense.DAO;

import com.expense.core.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    public int addGroup(Group group){
        Connection con = null;
        String sql = "insert into trip_group (name ,admin_id) values (? , ?)";
        try{
            con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,group.getName());
            ps.setInt(2, group.getAdmin_id());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            return -1;
        } catch (SQLException | ClassNotFoundException e ) {
            e.printStackTrace();
            return -1;
        }
        finally {
            Database.closeConnection(con);
        }

    }

    public Group getGroupById(int id) {
        String sql = "SELECT * FROM trip_group WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Group g = new Group();
                g.setId(rs.getInt("id"));
                g.setName(rs.getString("name"));
                g.setAdmin_id(rs.getInt("admin_id"));
                return g;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Group> getAllGroupsOfUser(int memberId) {

        List<Group> list = new ArrayList<>();

        String sql = "SELECT g.id, g.name, g.admin_id " +
                "FROM trip_group g JOIN members m ON g.id = m.group_id " +
                "WHERE m.id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Group(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("admin_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public boolean deleteGroup(int id) {
        String sql = "DELETE FROM trip_group WHERE id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateAdmin(int groupId, int adminId) {

        String sql = "UPDATE groups SET admin_id = ? WHERE id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adminId);
            ps.setInt(2, groupId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
