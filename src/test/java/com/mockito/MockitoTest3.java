package com.mockito;

import java.awt.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//使用@Mock注解进行测试    另一种MockitoTest4
public class MockitoTest3 {
	@Mock
	private List mockList;
	
	//初始化mock，必须初始化，否则报错
	public MockitoTest3(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shorthand(){
		mockList.add("1");
		verify(mockList).add("1");
	}
	
	//使用回调生成期望值 
    @Test  
    public void answer_with_callback(){  
        //使用Answer来生成我们我们期望的返回  
        when(mockList.getItem(anyInt())).thenAnswer(new Answer<Object>() {  
            @Override  
            public Object answer(InvocationOnMock invocation) throws Throwable {  
                Object[] args = invocation.getArguments();  
                return "hello world:"+args[0];  
            }  
        });  
        assertEquals("hello world:0",mockList.getItem(1));  
        assertEquals("hello world:999",mockList.getItem(999));  
    }  
	
	//监控真实对象  使用spy来监控真实的对象，需要注意的是此时我们需要谨慎的使用when-then语句，而改用do-when语句
	
	//修改对未预设的调用返回默认期望值 
    @Test  
    public void unstubbed_invocations(){  
        //mock对象使用Answer来对未预设的调用返回默认期望值  
        List mock = mock(List.class,new Answer() {  
            @Override  
            public Object answer(InvocationOnMock invocation) throws Throwable {  
                return 999;  
            }  
        });  
        //下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值  
        assertEquals(999, mock.getSelectedIndex());  
        //下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值  
        assertEquals(999,mock.size());  
    }  
    
    //重置mock
    @Test  
    public void reset_mock(){  
        List list = mock(List.class);  
        when(list.getItemCount()).thenReturn(10);  
        list.add("1");  
        assertEquals(10,list.getItemCount());  
        //重置mock，清除所有的互动和预设  
        reset(list);  
        assertEquals(0,list.getItemCount());  
    }  

}
