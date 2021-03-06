package sportapp.dao;

import sportapp.domain.User;
import sportapp.domain.Sport;
import java.util.List;

public interface SportDao {
    
    Sport create(Sport sport) throws Exception;
    
    List<Sport> getAll();
    
    void delete(String username) throws Exception;
    
    void deleteSpecific(String type, double time, double distance, int heartrate, int feeling, String username) throws Exception;
}

