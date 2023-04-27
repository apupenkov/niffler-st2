package niffler.db.dao;

import niffler.db.entity.UserEntity;

import java.util.List;

public interface NifflerUsersDAO {

  int createUser(UserEntity user);

  String getUserId(String userName);

  UserEntity selectUser(String userName);

  int updateUser(String userName, UserEntity user);

  int deleteUser(String userName);

}
