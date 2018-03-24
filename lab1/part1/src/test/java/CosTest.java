import org.junit.Assert;
import org.junit.Test;

public class CosTest {

    // -pi, pi, 0, -pi/3, pi/3, -2pi/3, 2pi/3

    private Double eps = 1e-5;

    @Test
    public void testLeftEdge() {
        double val = - Math.PI;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testSecondLeftEdge() {
        double val = - Math.PI / 2;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testRightEdge() {
        double val = Math.PI;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testSecondRightEdge() {
        double val = Math.PI;
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
        double val = -2 * Math.PI / 3;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testRightTop() {
        double val = 2 * Math.PI / 3;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testPeriodLeft() {
        double val = -10 * Math.PI;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }

    @Test
    public void testPeriodRight() {
        double val = 13 * Math.PI;
        Assert.assertEquals(Cos.calc(val), Math.cos(val), eps);
    }
}
