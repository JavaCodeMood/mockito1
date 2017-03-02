package com.mockito;

import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest4 {
	@Mock
	private List mockList;

	@Test
	public void shorthand() {
		mockList.add(1);
		verify(mockList).add(1);
	}

}
