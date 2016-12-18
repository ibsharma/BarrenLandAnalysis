import org.junit.Assert;
import org.junit.Test;

public class BarrenLandTest {
    BarrenLand barrenLand = new BarrenLand();

    public BarrenLandTest() {
    }

    @Test
    public void testInput1() throws Exception {
        String expected = "116800 116800";
        String input = "{“0 292 399 307”}";
        barrenLand.barrenLandAnalysis(input);
        barrenLand.calculateFertileArea();
        String actual = barrenLand.printToSTDOUT();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInput2() throws Exception {
        String expected = "22816 192608";
        String input = "{“48 192 351 207”, “48 392 351 407”, “120 52 135 547”, “260 52 275 547”}";
        barrenLand.barrenLandAnalysis(input);
        barrenLand.calculateFertileArea();
        String actual = barrenLand.printToSTDOUT();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInput3() throws Exception {
        String expected = "240000";
        String input = "{\"\"}";
        barrenLand.barrenLandAnalysis(input);
        barrenLand.calculateFertileArea();
        String actual = barrenLand.printToSTDOUT();
        Assert.assertEquals(expected, actual);
    }
}
