package org.taboola.question2.solution;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import org.junit.runner.RunWith;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

public class MyClassTest {

    @Test
    public void testCreateMyClassEmptyNumbers() throws IllegalAccessException {
        Date date = new Date();
        MyClass myClass = new MyClass(date, "A_NAME");
        Field name = Whitebox.getField(MyClass.class, "m_name");
        Field time = Whitebox.getField(MyClass.class, "m_time");
        Field numbers = Whitebox.getField(MyClass.class, "m_numbers");
        String cName = (String) name.get(myClass);
        Date cDate = (Date) time.get(myClass);
        Object cNumbers = numbers.get(myClass);

        Assert.assertEquals(date, cDate);
        Assert.assertEquals("A_NAME", cName);
        Assert.assertEquals(Optional.empty(), cNumbers);
    }

    @Test
    public void testCreateMyClassNumbers() throws IllegalAccessException {
        Date date = new Date();

        List<Long> nullValue = new ArrayList<>();
        nullValue.add(null);

        List<Long> empty = new ArrayList<>();

        List<Long> singleValue = new ArrayList<>();
        singleValue.add(1L);

        List<Long> nullAndValues = new ArrayList<>();
        nullAndValues.add(10L);
        nullAndValues.add(null);
        nullAndValues.add(101L);
        nullAndValues.add(201L);
        nullAndValues.add(303L);

        MyClass myClass = new MyClass(date, "A_NAME", nullValue, null);
        Field numbers = Whitebox.getField(MyClass.class, "m_numbers");
        Object cNumbers = numbers.get(myClass);

        Assert.assertEquals(Optional.of(nullValue), cNumbers);

        myClass = new MyClass(date, "A_NAME", empty, null);
        numbers = Whitebox.getField(MyClass.class, "m_numbers");
        cNumbers = numbers.get(myClass);

        Assert.assertEquals(Optional.of(empty), cNumbers);

        myClass = new MyClass(date, "A_NAME", singleValue, null);
        numbers = Whitebox.getField(MyClass.class, "m_numbers");
        cNumbers = numbers.get(myClass);

        Assert.assertEquals(Optional.of(singleValue), cNumbers);

        myClass = new MyClass(date, "A_NAME", nullAndValues, null);
        numbers = Whitebox.getField(MyClass.class, "m_numbers");
        cNumbers = numbers.get(myClass);

        Assert.assertEquals(Optional.of(nullAndValues), cNumbers);
    }

    @Test
    public void testContainsNumber() {
        Date date = new Date();

        List<Long> empty = new ArrayList<>();

        List<Long> nullValue = new ArrayList<>();
        nullValue.add(null);

        List<Long> singleValue = new ArrayList<>();
        singleValue.add(10L);

        List<Long> nullAndValues = new ArrayList<>();
        nullAndValues.add(10L);
        nullAndValues.add(null);
        nullAndValues.add(101L);
        nullAndValues.add(201L);
        nullAndValues.add(303L);

        // Test empty (actually Optional.empty() ) List
        MyClass myClass = new MyClass(date, "A_NAME");
        Assert.assertFalse(myClass.containsNumber(1L));

        myClass = new MyClass(date, "A_NAME", empty, null);
        Assert.assertFalse(myClass.containsNumber(1L));

        myClass = new MyClass(date, "A_NAME", nullValue, null);
        Assert.assertFalse(myClass.containsNumber(1L));

        myClass = new MyClass(date, "A_NAME", singleValue, null);
        Assert.assertFalse(myClass.containsNumber(1L));
        Assert.assertTrue(myClass.containsNumber(10L));

        myClass = new MyClass(date, "A_NAME", nullAndValues, null);
        Assert.assertFalse(myClass.containsNumber(1L));
        Assert.assertTrue(myClass.containsNumber(10L));
        Assert.assertTrue(myClass.containsNumber(101L));
        Assert.assertTrue(myClass.containsNumber(201L));
        Assert.assertTrue(myClass.containsNumber(303L));

    }

    @Test
    public void testToString() {
        Date date = new Date();

        List<Long> empty = new ArrayList<>();

        List<Long> nullValue = new ArrayList<>();
        nullValue.add(null);

        List<Long> singleValue = new ArrayList<>();
        singleValue.add(10L);

        List<Long> nullAndValues = new ArrayList<>();
        nullAndValues.add(10L);
        nullAndValues.add(null);
        nullAndValues.add(101L);
        nullAndValues.add(201L);
        nullAndValues.add(303L);

        MyClass myClass = new MyClass(date, "A_NAME");
        String myClassStr = myClass.toString();
        Assert.assertEquals("A_NAME", myClassStr);

        myClass = new MyClass(date, "A_NAME", empty, null);
        myClassStr = myClass.toString();
        Assert.assertEquals("A_NAME", myClassStr);

        myClass = new MyClass(date, "A_NAME", nullValue, null);
        myClassStr = myClass.toString();
        Assert.assertEquals("A_NAME null", myClassStr);

        myClass = new MyClass(date, "A_NAME", singleValue, null);
        myClassStr = myClass.toString();
        Assert.assertEquals("A_NAME 10", myClassStr);

        myClass = new MyClass(date, "A_NAME", nullAndValues, null);
        myClassStr = myClass.toString();
        Assert.assertEquals("A_NAME 10 null 101 201 303", myClassStr);

    }

    @Test
    public void testContainsAll() throws Exception {
        Date date = new Date();
        MyClass myClass = new MyClass(date, "A_NAME");

        List<Long> empty = new ArrayList<>();
        boolean b = Whitebox.invokeMethod(myClass, "containsAll", empty, empty);
        Assert.assertTrue( b );

        List<Long> nullValue = new ArrayList<>();
        nullValue.add(null);
        b = Whitebox.invokeMethod(myClass, "containsAll", nullValue, nullValue);
        Assert.assertTrue( b );

        List<Long> singleValue = new ArrayList<>();
        singleValue.add(10L);
        b = Whitebox.invokeMethod(myClass, "containsAll", singleValue, singleValue);
        Assert.assertTrue( b );

        List<Long> doubleValue = new ArrayList<>();
        doubleValue.add(10L);
        doubleValue.add(10L);

        b = Whitebox.invokeMethod(myClass, "containsAll", singleValue, doubleValue);
        Assert.assertFalse( b );

        b = Whitebox.invokeMethod(myClass, "containsAll", doubleValue, singleValue );
        Assert.assertFalse( b );

        List<Long> twoValue = new ArrayList<>();
        twoValue.add(10L);
        twoValue.add(11L);

        b = Whitebox.invokeMethod(myClass, "containsAll", doubleValue, twoValue );
        Assert.assertFalse( b );
        b = Whitebox.invokeMethod(myClass, "containsAll", twoValue, doubleValue );
        Assert.assertFalse( b );

        List<Long> tArrayR = new ArrayList<>();
        tArrayR.add(10L);
        tArrayR.add(null);
        tArrayR.add(11L);
        tArrayR.add(null);
        tArrayR.add(null);
        tArrayR.add(11L);
        tArrayR.add(101L);
        tArrayR.add(11L);
        tArrayR.add(11L);
        tArrayR.add(11L);
        tArrayR.add(101L);
        tArrayR.add(300L);

        List<Long> tArrayL = new ArrayList<>();
        tArrayL.add(11L);
        tArrayL.add(null);
        tArrayL.add(101L);
        tArrayL.add(null);
        tArrayL.add(11L);
        tArrayL.add(11L);
        tArrayL.add(null);
        tArrayL.add(11L);
        tArrayL.add(11L);
        tArrayL.add(300L);
        tArrayL.add(10L);
        tArrayL.add(101L);

        b = Whitebox.invokeMethod(myClass, "containsAll", tArrayR, tArrayL );
        Assert.assertTrue( b );

        tArrayR.add(10L);
        tArrayR.add(300L);

        tArrayL.add(11L);
        tArrayL.add(101L);

        b = Whitebox.invokeMethod(myClass, "containsAll", tArrayR, tArrayL );
        Assert.assertFalse( b );

        b = Whitebox.invokeMethod(myClass, "containsAll", tArrayL, tArrayR );
        Assert.assertFalse( b );
    }


    @Test
    public void testEquals() {
        Date date = new Date();
        MyClass myClassL = new MyClass(date, "A_NAME");
        MyClass myClassR = new MyClass(date, "A_NAME");

        boolean b = myClassL.equals(myClassR);
        Assert.assertTrue( b );

        b = myClassL.equals(myClassL);
        Assert.assertTrue( b );

        b = myClassL.equals(new MyClass(date, "B_NAME"));
        Assert.assertFalse( b );

        b = myClassL.equals(new MyClass(new Date(), "A_NAME"));
        Assert.assertFalse( b );

        List<Long> tArrayR = new ArrayList<>();
        tArrayR.add(10L);
        tArrayR.add(null);
        tArrayR.add(11L);
        tArrayR.add(null);
        tArrayR.add(null);
        tArrayR.add(11L);
        tArrayR.add(101L);
        tArrayR.add(11L);
        tArrayR.add(11L);
        tArrayR.add(11L);
        tArrayR.add(101L);
        tArrayR.add(300L);

        List<Long> tArrayL = new ArrayList<>();
        tArrayL.add(11L);
        tArrayL.add(null);
        tArrayL.add(101L);
        tArrayL.add(null);
        tArrayL.add(11L);
        tArrayL.add(11L);
        tArrayL.add(null);
        tArrayL.add(11L);
        tArrayL.add(11L);
        tArrayL.add(300L);
        tArrayL.add(10L);
        tArrayL.add(101L);

        myClassL = new MyClass(date, "A_NAME", tArrayL, null);
        myClassR = new MyClass(date, "A_NAME", tArrayR, null);
        b = myClassL.equals(myClassR);
        Assert.assertTrue( b );

        tArrayR.add(10L);
        tArrayR.add(300L);

        tArrayL.add(11L);
        tArrayL.add(101L);

        myClassL = new MyClass(date, "A_NAME", tArrayL, null);
        myClassR = new MyClass(date, "A_NAME", tArrayR, null);
        b = myClassL.equals(myClassR);
        Assert.assertFalse( b );

    }

    @Test
    public void isHistoric() {
        Date date = new Date();
        MyClass myClass = new MyClass(date, "A_NAME");

        boolean b = myClass.isHistoric();
        Assert.assertTrue( b );

        myClass = new MyClass(new Date( date.getTime() * 2 ), "A_NAME");
        b = myClass.isHistoric();
        Assert.assertFalse( b );
    }
}