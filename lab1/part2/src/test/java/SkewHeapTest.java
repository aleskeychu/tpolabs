import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SkewHeapTest {

    @Test
    public void testInsert() {
        Method getState = null;
        SkewHeap heap = new SkewHeap();
        try {
            getState = heap.getClass().getDeclaredMethod("getState");
            getState.setAccessible(true);
        } catch (NoSuchMethodException e) {
            System.out.println("Error: no getState method");
            Assert.fail();
        }
        try {
            Assert.assertEquals(getState.invoke(heap), "");
            heap.insert(5);
            Assert.assertEquals(getState.invoke(heap), "[5;n;n]");
            heap.insert(3);
            Assert.assertEquals(getState.invoke(heap), "[3;5;n][5;n;n]");
            heap.insert(7);
            Assert.assertEquals(getState.invoke(heap), "[3;7;5][7;n;n][5;n;n]");
            heap.insert(6);
            Assert.assertEquals(getState.invoke(heap), "[3;5;7][5;6;n][6;n;n][7;n;n]");
            heap.insert(4);
            Assert.assertEquals(getState.invoke(heap), "[3;4;5][4;7;n][7;n;n][5;6;n][6;n;n]");

        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error: invocation getState");
            Assert.fail();
        }
    }

    @Test
    public void testGetSize() {
        SkewHeap heap = new SkewHeap();
        Assert.assertEquals(heap.getSize(), 0);
        heap.insert(1);
        Assert.assertEquals(heap.getSize(), 1);
        heap.insert(5);
        heap.insert(10);
        heap.insert(8);
        Assert.assertEquals(heap.getSize(), 4);
        heap.removeSmallest();
        Assert.assertEquals(heap.getSize(), 3);
    }

    @Test
    public void testRemoveSmallest() {
        Method getState = null;
        SkewHeap heap = new SkewHeap();
        try {
            getState = heap.getClass().getDeclaredMethod("getState");
            getState.setAccessible(true);
        } catch (NoSuchMethodException e) {
            System.out.println("Error: no getState method");
            Assert.fail();
        }
        try {
            Assert.assertEquals(getState.invoke(heap), "");
            heap.insert(1);
            heap.insert(3);
            heap.insert(5);
            heap.insert(2);
            heap.insert(4);
            Assert.assertEquals(getState.invoke(heap),
                    "[1;4;2][4;5;n][5;n;n][2;3;n][3;n;n]");
            int value = heap.removeSmallest();
            Assert.assertEquals(value, 1);
            Assert.assertEquals(getState.invoke(heap),
                    "[2;4;3][4;5;n][5;n;n][3;n;n]");
            heap.removeSmallest();
            heap.removeSmallest();
            heap.removeSmallest();
            heap.removeSmallest();
            heap.removeSmallest();
            Assert.assertEquals(getState.invoke(heap), "");
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error: invocation getState");
            Assert.fail();
        }
    }


}
