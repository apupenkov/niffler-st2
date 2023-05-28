package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.entity.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface NifflerUsersDAO {

  PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  int createUser(UserEntity user);

  int updateUser(UserEntity user);

  String getUserId(String userName);

  int removeUser(UserEntity user);

  UserEntity getUser(String userName);

}
