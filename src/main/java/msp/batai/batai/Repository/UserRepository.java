package msp.batai.batai.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import msp.batai.batai.Model.User;

public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(@Param("username") String username);
}
