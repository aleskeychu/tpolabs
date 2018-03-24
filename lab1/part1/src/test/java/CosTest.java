import org.junit.Assert;
import org.junit.Test;

public class CosTest {

    // -pi/2, pi/2, 0, -pi/6, pi/6, -pi/3, pi/3

    private Double eps = 1e-5;

    @Test
    public void testLeftEdge() {
        double val = - Math.PI / 2;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testRightEdge() {
        double val = Math.PI / 2;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testMiddle() {
        double val = 0;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testLeftBottom() {
        double val = - Math.PI / 3;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testRightBottom() {
        double val = Math.PI / 3;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testLeftTop() {
        double val = - Math.PI / 6;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testRightTop() {
        double val = Math.PI / 6;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }
}
