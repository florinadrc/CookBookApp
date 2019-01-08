package cooking.book.repository.user;

import cooking.book.model.user.Role;
import cooking.book.model.user.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);
}
