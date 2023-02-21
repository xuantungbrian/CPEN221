package cpen221.mp1;
import cpen221.mp1.cryptanalysis.Untangler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Task6TanglerTests {
    @Test
    public void testAreTangled_true() {
        //Took the test from notion
        String x = "110";
        String y = "111";
        String superMessage = "111011111110";
        Assertions.assertEquals(true,Untangler.areTangled(superMessage,x,y));
    }
    @Test
    public void testAreTangled_false() {
        //Took the test from notion
        String m= "voc";
        String n = "dkl";
        String superMessage2 = "vodkldkcvocdkl";
        Assertions.assertEquals(false,Untangler.areTangled(superMessage2,m,n));
    }
    @Test
    public void testAreTangledNoStringExists() {
        //Took the test from notion
        String m= "111";
        String n = "bcd";
        String superMessage2 = "vodkldkcvocdkl";
        Assertions.assertEquals(false,Untangler.areTangled(superMessage2,m,n));
    }

    @Test
    public void testAreTangled_trueSwap() {
        //Took the test from notion
        String x = "111";
        String y = "110";
        String superMessage = "111011111110";
        Assertions.assertEquals(true,Untangler.areTangled(superMessage,x,y));
    }
    @Test
    public void testAreTangledEmpty() {
        //Took the test from notion
        String x = "";
        String y = "";
        String superMessage = "111011111110";
        Assertions.assertEquals(false,Untangler.areTangled(superMessage,x,y));
    }
    @Test
    public void testAreTangled1StringEmpty() {
        //Took the test from notion
        String x = "ab";
        String y = "";
        String superMessage = "111011111110";
        Assertions.assertEquals(false,Untangler.areTangled(superMessage,x,y));
    }
}
