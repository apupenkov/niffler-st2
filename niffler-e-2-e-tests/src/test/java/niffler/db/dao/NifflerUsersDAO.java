package niffler.db.dao;

import java.util.UUID;
import niffler.db.entity.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface NifflerUsersDAO {

  PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  int createUser(UserEntity user);

  String getUserId(String userName);

  int removeUser(UserEntity user);

  UserEntity selectUser(String userName);

  int updateUser(String userName, UserEntity user);

  int deleteUser(String userName);

}
