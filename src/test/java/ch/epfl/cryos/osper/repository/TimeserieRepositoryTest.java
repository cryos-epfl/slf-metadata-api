package ch.epfl.cryos.osper.repository;

import ch.epfl.cryos.osper.Application;
import ch.epfl.cryos.osper.model.Timeserie;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by kryvych on 09/11/16.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@TestPropertySource(locations = { "classpath:test.properties" })
@ActiveProfiles({ "insecure", "test" })
public class TimeserieRepositoryTest extends TestCase {

    @Autowired
    private TimeserieRepository repository;


    @Test
    public void testGetAll() {
        List<Timeserie> allTimeseries = repository.getAllTimeseries();

    }
}