voltorb
=======

**Voltorb** is a platform on which to build turn-based games on
  Android. Or maybe it's a Pokemon clone.

  Update: Right now we really need a UserInterface interface!
  
  Get rattata from jepriebe for the user interface (in progress).
  
  TODO: FakeLobbies and UserThreads do not handle player disconnection yet.
	If one player disconnects, (for now) lobby should be destroyed and connected
	player set up with a new lobby for the next connected player to join.
	
  BUGS:
	2. When player 2 faints and switches a new monster in, the leads get swapped
		so player 2's lead is now player 1's lead and presumably vice versa. May 
		happen in reverse instace. Check FakeServerProtocol (should be continueTurns
		method) for leads being swapped/not when they shouldn't/should be.