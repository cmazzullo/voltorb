package com.example.statesofmatter;

import java.net.*;
import java.io.*;

public class FakeServerProtocol {
	
	private static int numPlayers = 0;
	
	
	
	public boolean readyBattle() throws Exception {
		if (numPlayers < 2) {
			numPlayers++;
			return false;
		} else if(numPlayers == 2)
			return true;
		else
			throw new Exception("Too many players.");
	}
	
	public boolean processTie(String s) {
		if (s.equals("tied"))
			return true;
		else
			return false;
	}	
}
