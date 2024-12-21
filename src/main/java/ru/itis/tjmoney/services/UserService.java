package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IAvatarDAO;
import ru.itis.tjmoney.dao.interfaces.IGroupDAO;
import ru.itis.tjmoney.dao.interfaces.IGroupMemberDAO;
import ru.itis.tjmoney.dao.interfaces.IUserDAO;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.exceptions.UserNotFoundException;
import ru.itis.tjmoney.models.Group;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.interfaces.IUserService;
import ru.itis.tjmoney.util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    private final IUserDAO userDAO;
    private final IAvatarDAO avatarDAO;
    private final IGroupMemberDAO groupMemberDAO;
    private final IGroupDAO groupDAO;

    public UserService(IUserDAO userDAO, IAvatarDAO avatarDAO, IGroupMemberDAO groupMemberDAO, IGroupDAO groupDAO) {
        this.userDAO = userDAO;
        this.avatarDAO = avatarDAO;
        this.groupMemberDAO = groupMemberDAO;
        this.groupDAO = groupDAO;
    }

    @Override
    public User getUserById(int id) {
        User user = userDAO.findById(id);
        if (user == null) throw new UserNotFoundException("Пользователь с таким id не найден");
        return user;
    }

    @Override
    public User getByUsername(String username) {
        User user = userDAO.findByUsername(username);
        if (user == null) throw new UserNotFoundException("Пользователь с таким именем не найден");
        return user;
    }

    @Override
    public void update(int userId, String username, String telegramId, boolean sendingToTelegram, boolean sendingToEmail) {
        User oldUser = getUserById(userId);
        if (userDAO.findByUsername(username) != null && !oldUser.getUsername().equals(username)) throw new UpdateException("Пользователь с таким именем уже существует");

        if (telegramId.isEmpty()) userDAO.update(new User(userId, username, null, null, null, sendingToTelegram, sendingToEmail));
        else userDAO.update(new User(userId, username, null, null, telegramId, sendingToTelegram, sendingToEmail));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String repeatPassword, int userId) {
        String currentPassword = getUserById(userId).getPassword();

        if (!PasswordUtil.encrypt(oldPassword).equalsIgnoreCase(currentPassword)) throw new UpdateException("Неверный старый пароль");
        if (!newPassword.equals(repeatPassword)) throw new UpdateException("Новые пароли не совпадают");

        userDAO.updatePassword(PasswordUtil.encrypt(newPassword), userId);
    }

    @Override
    public void delete(int userId) {
        List<GroupMember> groupMembers = groupMemberDAO.findByUserIdWhereAdmin(userId);
        for (GroupMember groupMember : groupMembers) {
            groupDAO.delete(groupMember.getGroupId());
        }
        userDAO.delete(userId);
    }

    @Override
    public void uploadPhoto(int userId, String url) {
        if (avatarDAO.findUrl(userId) == null) avatarDAO.save(userId, url);
        else avatarDAO.update(userId, url);
    }

    @Override
    public String getPhotoUrl(int userId) {
        String url = avatarDAO.findUrl(userId);
        if (url == null) return "/static/defaultAvatar.png";
        return url;
    }

    @Override
    public void deletePhoto(int userId) {
        avatarDAO.delete(userId);
    }
}
