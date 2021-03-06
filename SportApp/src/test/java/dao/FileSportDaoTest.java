
package dao;


import java.io.File;
import java.io.FileWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import domain.FakeUserDao;
import java.util.List;
import sportapp.dao.FileSportDao;
import sportapp.dao.SportDao;
import sportapp.domain.Sport;
import sportapp.domain.User;
import sportapp.dao.UserDao;


/**
 *
 * FileSportDao-luokan testiluokka
 */
public class FileSportDaoTest {
    
    @Rule
    public TemporaryFolder temporaryTestFolder = new TemporaryFolder();
    
    File sportFile;
    SportDao dao;
    
    @Before
    public void setUp() throws Exception {
        sportFile = temporaryTestFolder.newFile("sportsTestFile.txt");
        FakeUserDao userDao = new FakeUserDao();
        userDao.create(new User("maijamallikas"));
        
        try (FileWriter testFile = new FileWriter(sportFile.getAbsolutePath())) {
            testFile.write("1,running,30.0,5.0,150,10,maijamallikas\n");
        }
        dao = new FileSportDao(sportFile.getAbsolutePath(), userDao);
    }
    @Test
    public void readingFromFileIsCorrect() {
        List<Sport> sports = dao.getAll();
        assertEquals(1, sports.size());
        Sport sport = sports.get(0);
        assertEquals("running", sport.getType());
        assertEquals(30.0, sport.getTime(), 0.01);
        assertEquals(5.0, sport.getDistance(), 0.01);
        assertEquals(150, sport.getHeartrate());
        assertEquals(10, sport.getFeeling());
        assertEquals("maijamallikas", sport.getUser().getUsername());
    }
    @Test
    public void addedSportsAreListed() throws Exception {
        dao.create(new Sport("skiing", 35.0, 6.0, 150, 10, new User("maijamallikas")));
        
        List<Sport> sports = dao.getAll();
        assertEquals(2, sports.size());
        Sport sport = sports.get(1);
        assertEquals("skiing", sport.getType());
        assertNotEquals("running", sport.getType());
        assertEquals(35.0, sport.getTime(), 0.01);
        assertNotEquals(30.0, sport.getTime(), 0.01);
        assertEquals(6.0, sport.getDistance(), 0.01);
        assertNotEquals(5.0, sport.getDistance(), 0.01);
        assertEquals("maijamallikas", sport.getUser().getUsername());
    }
    @Test
    public void deletingSportsSucceeds() throws Exception {
        dao.create(new Sport("skiing", 35.0, 6.0, 150, 10, new User("maijamallikas")));
        dao.delete("maijamallikas");
        List<Sport> sports = dao.getAll();
        assertEquals(0, sports.size());
    }
    @Test
    public void deletingSpecificSportSucceeds() throws Exception {
        dao.create(new Sport("skiing", 35.0, 6.0, 150, 10, new User("maijamallikas")));
        dao.create(new Sport("skiing", 50.0, 8.0, 140, 8, new User("maijamallikas")));
        dao.deleteSpecific("skiing", 35.0, 6.0, 150, 10, "maijamallikas");
        List<Sport> sports = dao.getAll();
        assertEquals(2, sports.size());
        Sport sport = sports.get(1);
        assertEquals(8, sport.getFeeling());
        assertNotEquals(35.0, sport.getTime());
    }
   
}
