package com.expence.DAO;

import com.expence.core.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public int addMember(Member member) {
        String sql = "INSERT INTO members (group_id, name) VALUES (?, ?)";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, member.getGroupId());
            ps.setString(2, member.getName());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // generated memberId
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // failed
    }

    public List<Member> getMembersByGroup(int groupId) {
        List<Member> list = new ArrayList<>();

        String sql = "SELECT * FROM members WHERE group_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setGroupId(rs.getInt("group_id"));
                m.setName(rs.getString("name"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



    public boolean deleteMember(int groupId, int memberId) {

        String sql = "DELETE FROM members WHERE id = ? AND group_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ps.setInt(2, groupId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
