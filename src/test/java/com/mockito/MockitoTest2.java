package com.mockito;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.awt.List;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;

public class MockitoTest2 {
	//5.模拟方法体抛出异常
    @Test(expected = RuntimeException.class)  
    public void doThrow_when(){  
        List list = mock(List.class);  
        doThrow(new RuntimeException()).when(list).add("小明",1);  
        list.add("小明");  
    }  
    
    //6.验证执行顺序
    @Test
    public void verification_in_order(){  
        List list = mock(List.class);  
        List list2 = mock(List.class);  
        list.add("hello", 1);  
        list2.add("hello");  
        list.add("world",2);  
        list2.add("world");  
        //将需要排序的mock对象放入InOrder  
        InOrder inOrder = Mockito.inOrder(list,list2);  
        //下面的代码不能颠倒顺序，验证执行顺序  
        inOrder.verify(list).add("hello",1);  
        inOrder.verify(list2).add("hello");  
        inOrder.verify(list).add("world",2);  
        inOrder.verify(list2).add("world");  
    }  
    
    //7.确保模拟对象上无互动发生 
    @Test
    public void verify_interaction(){
    	List list = mock(List.class);
    	List list2 = mock(List.class);
    	List list3 = mock(List.class);
    	list.add("beijing");
    	verify(list).add("beijing");
    	//验证是否从未被调用过
    	verify(list,never()).add("shanghai");
    	//验证零互动行为
    	verifyZeroInteractions(list2,list3);
    	
    }
    
    //8.找出冗余的互动(即未被验证到的) 
    @Test(expected = NoInteractionsWanted.class)
    public void find_redundant_interaction(){
    	/*List list = mock(List.class);
    	list.add("hello");
    	list.add("world");
    	 verify(list,times(2)).add("hello world",anyInt());  
    	 //检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过  
        verifyNoMoreInteractions(list);  */
        
        List list2 = mock(List.class);
        list2.add("1");
        list2.add("2");
        verify(list2).add("1");
        //检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常  
        verifyNoMoreInteractions(list2);  
        
    }

	
}
