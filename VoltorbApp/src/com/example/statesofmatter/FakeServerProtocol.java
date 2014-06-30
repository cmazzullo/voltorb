package com.example.statesofmatter;

import java.net.*;
import java.io.*;

public class FakeServerProtocol {
	
	private static final int WAITING = 0;
	
	private int state = WAITING;
	
	public boolean processTie(String s) {
		if (s.equals("tied"))
			return true;
		else
			return false;
	}	
}
