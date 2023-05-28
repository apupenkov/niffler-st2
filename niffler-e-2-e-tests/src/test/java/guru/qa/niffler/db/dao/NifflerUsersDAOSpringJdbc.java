package guru.qa.niffler.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import javax.sql.DataSource;
import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.entity.Authority;
import guru.qa.niffler.db.entity.AuthorityEntity;
import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class NifflerUsersDAOSpringJdbc implements NifflerUsersDAO {

  private final TransactionTemplate transactionTemplate;
  private final JdbcTemplate jdbcTemplate;

  public NifflerUsersDAOSpringJdbc() {
    DataSourceTransactionManager transactionManager = new JdbcTransactionManager(
        DataSourceProvider.INSTANCE.getDataSource(ServiceDB.NIFFLER_AUTH));
    this.transactionTemplate = new TransactionTemplate(transactionManager);
    this.jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
  }

  @Override
  public int createUser(UserEntity user) {

    final String CREATE_USER_QUERY = "INSERT INTO users "
            + "(id, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) "
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_AUTHORITY_QUERY = "INSERT INTO authorities (user_id, authority) VALUES (?, ?)";

      return transactionTemplate.execute(transactionStatus -> {
          user.setId(UUID.randomUUID());
          jdbcTemplate.update(CREATE_USER_QUERY,
                  user.getId(), user.getUsername(), pe.encode(user.getPassword()), user.getEnabled()
                  , user.getAccountNonExpired(), user.getAccountNonLocked(), user.getCredentialsNonExpired());
          for (AuthorityEntity authority : user.getAuthorities()) {
              jdbcTemplate.update(INSERT_AUTHORITY_QUERY, user.getId(), authority.getAuthority().name());
          }
          return 1;
      });
  }

  @Override
  public String getUserId(String userName) {
    return jdbcTemplate.query("SELECT * FROM users WHERE username = ?",
        rs -> {return rs.getString(1);},
        userName
    );
  }

  @Override
  public int removeUser(UserEntity user) {
    return transactionTemplate.execute(transactionStatus -> {
      jdbcTemplate.update("DELETE FROM authorities WHERE user_id = ?", user.getId());
      return jdbcTemplate.update("DELETE FROM users WHERE id = ?", user.getId());
    });
  }

  @Override
  public UserEntity getUser(String userName) {
      return jdbcTemplate.query("SELECT * FROM users WHERE username = ?",
              rs -> {
                UserEntity user = new UserEntity();
                user.setId(UUID.fromString(rs.getString(1)));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEnabled(rs.getBoolean(4));
                user.setAccountNonExpired(rs.getBoolean(5));
                user.setAccountNonLocked(rs.getBoolean(6));
                user.setAccountNonExpired(rs.getBoolean(7));
              return user;
      },
    userName
    );
  }

  @Override
  public int updateUser(UserEntity user) {
    String query = "UPDATE public.users " +
            "SET username=?, password=?, enabled=?, account_non_expired=?, " +
            "account_non_locked=?, credentials_non_expired=? " +
            "WHERE username=?";
    return jdbcTemplate.update(query,
            user.getUsername(),
            pe.encode(user.getPassword()),
            user.getEnabled(),
            user.getAccountNonExpired(),
            user.getAccountNonLocked(),
            user.getCredentialsNonExpired(),
            user.getUsername()
    );
  }
}
