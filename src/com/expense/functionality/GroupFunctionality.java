package com.expense.functionality;

import com.expense.DAO.GroupDAO;
import com.expense.DAO.MemberDAO;
import com.expense.core.Group;
import com.expense.core.Member;

import java.util.ArrayList;
import java.util.List;

public class GroupFunctionality {


    private GroupDAO groupDAO = new GroupDAO();
    private MemberDAO memberDAO = new MemberDAO();

    public int createGroup(String groupName, String adminName) {


            Member admin = new Member();
            admin.setName(adminName);
            admin.setGroupId(0);


            Group group = new Group();
            group.setName(groupName);
            group.setAdmin_id(0);

            int groupId = groupDAO.addGroup(group);


            admin.setGroupId(groupId);
            int adminId = memberDAO.addMember(admin);


            groupDAO.updateAdmin(groupId, adminId);

            return groupId;
        }



        public List<Group> getGroupsOfUser(int memberId) {


            Member member = memberDAO.getMemberById(memberId);

            if (member == null) {
                System.out.println("Member not found!");
                return new ArrayList<>();
            }


            return groupDAO.getAllGroupsOfUser(memberId);
        }


        public int addMember(int groupId, String memberName, int currentUserId) {

            Group group = groupDAO.getGroupById(groupId);

            if (group == null) {
                System.out.println("Group does not exist!");
                return -1;
            }

            if (group.getAdmin_id() != currentUserId) {
                System.out.println("Only admin can add members!");
                return -1;
            }

            Member newMember = new Member();
            newMember.setGroupId(groupId);
            newMember.setName(memberName);

            return memberDAO.addMember(newMember);
        }

        public boolean removeMember(int groupId, int memberIdToRemove, int currentUserId) {

            Group group = groupDAO.getGroupById(groupId);

            if (group == null) {
                System.out.println("Group not found!");
                return false;
            }

            if (group.getAdmin_id() != currentUserId) {
                System.out.println("Only admin can remove members!");
                return false;
            }

            if (group.getAdmin_id() == memberIdToRemove) {
                System.out.println(" Admin cannot remove himself!");
                return false;
            }

            return memberDAO.deleteMember(groupId, memberIdToRemove);
        }
}


