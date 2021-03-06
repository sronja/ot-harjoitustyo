
package sportapp.dao;

import java.util.List;
import sportapp.domain.User;

public interface UserDao {
    
    User create(User user) throws Exception;
    
    List<User> getAll();

    User findByUsername(String username);
    
    User findByUsernameAndPassword(String username, String password);
    
    User delete(String username) throws Exception;
}
