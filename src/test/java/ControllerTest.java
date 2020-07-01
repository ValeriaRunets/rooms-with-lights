import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ControllerTest {
    Controller controller;
    @Before
    public void init(){
        controller=new Controller();
    }
    @Test
    public void checkIpCountry() {
        String address="5.254.124.240";
        Room room=new Room("name", "Germany");
        Room room1=new Room("name", "Belarus");
        boolean buf=controller.checkIpCountry(room, address);
        boolean buf1=controller.checkIpCountry(room1, address);
        Assert.assertTrue(buf);
        Assert.assertFalse(buf1);
    }
}