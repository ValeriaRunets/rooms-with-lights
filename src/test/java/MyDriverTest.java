import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyDriverTest {

    @Test
    public void getRoomNot0Id() {
        int id=0;
        MyDriver driver=new MyDriver();
        Room actual=driver.getRoom(id);
        Room expected=null;
        Assert.assertEquals(expected, actual);
    }
}