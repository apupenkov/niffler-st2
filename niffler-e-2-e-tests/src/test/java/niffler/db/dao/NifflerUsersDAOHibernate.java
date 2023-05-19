package niffler.db.dao;

import niffler.db.ServiceDB;
import niffler.db.entity.UserEntity;
import niffler.db.jpa.EmfProvider;
import niffler.db.jpa.JpaTransactionManager;

public class NifflerUsersDAOHibernate extends JpaTransactionManager implements NifflerUsersDAO {

  public NifflerUsersDAOHibernate() {
    super(EmfProvider.INSTANCE.getEmf(ServiceDB.NIFFLER_AUTH).createEntityManager());
  }

  @Override
  public int createUser(UserEntity user) {
//    user.setPassword(pe.encode(user.getPassword()));
    persist(user);
    return 0;
  }

  @Override
  public String getUserId(String userName) {
    return getUser(userName)
        .getId()
        .toString();
  }

  @Override
  public UserEntity getUser(String userName) {
    return em.createQuery("select u from UserEntity u where username=:username", UserEntity.class)
            .setParameter("username", userName).getSingleResult();
  }

  @Override
  public int updateUser(UserEntity user) {
//    String query = "UPDATE public.users " +
//            "SET username=?, password=?, enabled=?, account_non_expired=?, " +
//            "account_non_locked=?, credentials_non_expired=? " +
//            "WHERE username=?";
//    return em.createQuery("UPDATE public.users SET username=:username, password=:password, enabled=:enabled, " +
//            "account_non_expired=:account_non_expired, account_non_locked=:account_non_locked, " +
//            "credentials_non_expired=:credentials_non_expired WHERE username=:username", UserEntity.class)
//            .setParameter("username", user.getUsername())
//            .setParameter("password", user.getPassword())
//            .setParameter("enabled", user.getEnabled())
//            .setParameter("account_non_expired", user.getAccountNonExpired())
//            .setParameter("account_non_locked", user.getAccountNonLocked())
//            .setParameter("credentials_non_expired", user.getCredentialsNonExpired())
//            .executeUpdate();
    merge(user);
    return 1;
  }

  @Override
  public int removeUser(UserEntity user) {
    remove(user);
    return 0;
  }
}
