package com.expence.DAO;

import com.expence.core.Group;

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

    public List<Group> getAllGroupsOfUser(int user_id) {
        List<Group> list = new ArrayList<>();
        String sql = "SELECT * FROM trip_group";

        try (Connection con = Database.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Group g = new Group();
                g.setId(rs.getInt("id"));
                g.setName(rs.getString("name"));
                g.setAdmin_id(rs.getInt("admin_id"));
                list.add(g);
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

}
