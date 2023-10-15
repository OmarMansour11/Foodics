import org.testng.annotations.Test;


public class TestCase {

    Base base = new Base();
    //TC-1
    @Test(priority = 1)
    public void lunch() {
        base.home();
    }
}
