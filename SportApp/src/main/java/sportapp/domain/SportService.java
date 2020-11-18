
package sportapp.domain;

import java.util.List;
import java.util.stream.Collectors;
import sportapp.dao.UserDao;
import sportapp.dao.SportDao;
/**
 * Sovelluslogiikasta vastaava luokka
 */
public class SportService {
    private UserDao userDao;
    private SportDao sportDao;
    private User loggedIn;
    
    public SportService (UserDao userDao, SportDao sportDao) {
        this.userDao = userDao;
        this.sportDao = sportDao;
       
    }
    /**
     * sisäänkirjautuminen
     * @param username
     * @return true jos käyttäjätunnus on olemassa, jos ei ole niin false
     */
    
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }
    /**
     * uloskirjautuminen
     */
    
    public void logout() {
        loggedIn = null;
    }
    /**
     * uuden käyttäjän rekisteröinti
     * @param username käyttäjätunnus
     * 
     * @return true jos käyttäjän rekisteröinti onnistuu, jos ei niin false
     */
    
    public boolean createUser(String username) {
        if (userDao.findByUsername(username) == null) {
            User user = new User(username);
            try {
                userDao.create(user);
            } catch (Exception e) {
                return false;
            }
            return true;
    }
    return false;
    }
    /**
     * Uuden urheilusuorituksen lisääminen käyttäjälle
     * @param contentType lisättävän urheilusuorituksen laji
     * @param contentTime lisättävän urheilusuorituksen aika
     * @param contentDistance lisättävän urheilusuorituksen matka
     */
    public boolean addSport(String contentType, double contentTime, double contentDistance) {
        Sport sport = new Sport (contentType, contentTime, contentDistance, loggedIn);
        try {
            sportDao.create(sport);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Sport> getSport() {
        return sportDao.getAll()
                .stream()
                .filter(u -> u.getUser().equals(loggedIn))
                .collect(Collectors.toList());
    }
}
