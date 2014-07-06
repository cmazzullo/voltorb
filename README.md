voltorb
=======

**Voltorb** is a platform on which to build turn-based games on
  Android. Or maybe it's a Pokemon clone.

  Update: Right now we really need a UserInterface interface!
  
  Get rattata from jepriebe for the user interface (in progress).
  
  BUGS:
  
  1) Same-named monsters refer to same object, even when on opposing teams. 
	 Most recent update to object overrides previous update within same turn.
	 
  2) When one player switches in the same turn that the other attacks, 
	 change is made and damage applied to correct monster, but both players
	 end up with attacking monster as THEIR lead, and the switched monster
	 as their opponent. (Probably should check FakeServerProtocol)
	 (Is actually happening when both players switch too. This didn't used to happen.)