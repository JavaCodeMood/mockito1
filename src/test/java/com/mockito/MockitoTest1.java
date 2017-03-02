package com.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatcher;  

public class MockitoTest1 {
	//1.验证行为
    @Test  
    public void verify_behaviour(){  
        //模拟创建一个List对象  
        List mock = mock(List.class);  
        //使用mock的对象  
        mock.add(1);  
        mock.clear();  
        //验证add(1)和clear()行为是否发生  
        verify(mock).add(1);  
        verify(mock).clear();  
    }  
    
    //2.模拟我们所期望的结果 
    @Test
    public void when_thenReturn(){
    	//mock一个Iterator类
    	Iterator iterator = mock(Iterator.class);
    	 //预设当iterator调用next()时第一次返回hello，第n次都返回world  
        when(iterator.next()).thenReturn("hello").thenReturn("world");  
        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //验证结果
        assertEquals("hello world world",result);
    	System.out.println(result);
    }
    
    @Test(expected = IOException.class)
    public void when_thenThrow() throws IOException{
    	OutputStream outputStream = mock(OutputStream.class);
    	OutputStreamWriter writer = new OutputStreamWriter(outputStream);
    	//预设当流关闭时抛出异常
    	doThrow(new IOException()).when(outputStream).close();
    	outputStream.close();
    }
    
    //3.参数匹配
    @Test
    public void with_arguments(){
    	Comparable comparable = mock(Comparable.class);
    	//预设根据不同的参数返回不同的结果
    	when(comparable.compareTo("Test1")).thenReturn(1);
    	when(comparable.compareTo("Test2")).thenReturn(2);
    	assertEquals(1,comparable.compareTo("Test1"));
    	//故意写错
    	assertEquals(3,comparable.compareTo("Test2"));
        //对于没有预设的情况会返回默认值
    	assertEquals(0,comparable.compareTo("Not stub"));
    }
    
    //除了匹配制定参数外，还可以匹配自己想要的任意参数
    @Test
    public void with_unspecified_arguments(){
    	List list = mock(List.class);
    	//匹配任意参数
    	when(list.get(anyInt())).thenReturn(1);
    	when(list.contains(argThat(new IsValid()))).thenReturn(true);
    	assertEquals(1,list.get(1));
    	assertEquals(1,list.get(100000));
    	assertTrue(list.contains(1));
    	//assertTrue(!list.contains(100000));
    }
    
    private class IsValid extends ArgumentMatcher<List>{  
        @Override  
        public boolean matches(Object o) {  
            return true;  
        }  
    }  
    
    //需要注意的是如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配 
    @Test
    public void all_arguments_provided_by_matchers(){
    	Comparator comparator = mock(Comparator.class);
    	comparator.compare("你好！", "Hello World!");
    	//如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
    	verify(comparator).compare(anyString(), eq("Hello World!"));
    	//下面的为无效的参数匹配使用
    	verify(comparator).compare(anyString(), "hello");
    }
    
    //4.验证确切的调用次数 
    @Test
    public void verifying_number_of_invocations(){
    	List list = mock(List.class);
    	 list.add(1);  
    	 list.add(2);  
    	 list.add(2);  
    	 list.add(3);  
    	 list.add(3);  
    	 list.add(3);  
    	//验证是否被调用一次，等效于下面的times(1)
    	//verify(list).add("1");
    	verify(list,times(1)).add(1);
    	//验证是否被调用2次
    	verify(list,times(2)).add(2);
    	//验证是否被调用3次
    	verify(list,times(1)).add(1);
    	//验证是否从未被调用过
    	verify(list,never()).add(4);
    	//验证至少调用一次
    	verify(list,atLeastOnce()).add(1);
    	//验证至少调用两次
    	verify(list,atLeast(2)).add(3);
    	//验证至少调用三次
    	verify(list,atMost(3)).add(1);
    	
    }
}
