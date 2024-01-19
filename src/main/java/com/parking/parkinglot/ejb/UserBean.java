package com.parking.parkinglot.ejb;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
@Stateless
public class UserBean {
    private static final Logger LOG=Logger.getLogger(UserBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;
    @Inject
    PasswordBean passwordBean;
    public List<UserDto>findAllUsers(){
        LOG.info("findAllUsers");
        try {
            TypedQuery<User>typedQuery=entityManager.createQuery("select u from User u",User.class);
            List<User>users=typedQuery.getResultList();
            return copyUserstoDto(users);
        }catch (Exception ex){
            throw new EJBException(ex);
        }
    }
    public Collection<String> findUsernamesByUserIds(Collection<Long> userIds) {
        List<String> usernames=
                entityManager.createQuery("SELECT u.username from User u where  u.id in :userIds", String.class)
                        .setParameter("userIds",userIds)
                        .getResultList();
        return usernames;
    }
    public UserDto findById(Long userId) {
        LOG.info("findById");
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            return new UserDto(user.getId(), user.getUsername(), user.getEmail());
        }
        return null;
    }
    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }
    public void updateUser(Long userId, String username, String email, String password, Collection<String> groups) {
        LOG.info("updateUser");
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            removeGroupsFromUser(user.getUsername());
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordBean.convertToSha256(password));

            assignGroupsToUser(username, groups);
        }
    }
    private void removeGroupsFromUser(String username) {
        LOG.info("removeGroupsFromUser");
        List<UserGroup> userGroups = entityManager.createQuery("SELECT ug FROM UserGroup ug WHERE ug.username = :username", UserGroup.class)
                .setParameter("username", username)
                .getResultList();

        for (UserGroup userGroup : userGroups) {
            entityManager.remove(userGroup);
        }
    }

    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }
    public void updateUserWithoutPasswordChange(Long userId, String username, String email, List<String> groups) {
        LOG.info("updateUserWithoutPassword");
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            removeGroupsFromUser(user.getUsername());
            user.setUsername(username);
            user.setEmail(email);
            assignGroupsToUser(username, groups);
        }
    }
    private List<UserDto>copyUserstoDto(List<User> users){
        List<UserDto>userDtos=new ArrayList<UserDto>();
        for(User user:users){
            userDtos.add(new UserDto(user.getId(),user.getEmail(),user.getUsername()));
        }
        return userDtos;
    }
}