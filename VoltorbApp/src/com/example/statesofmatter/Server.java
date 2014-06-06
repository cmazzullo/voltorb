package com.example.statesofmatter;

public interface Server {
    
    enum PlayerAction {
        SWITCH, ATTACK, PHASE_SHIFT
    }
    
    PlayerAction sendMyAction();

    Monster sendMyLead();

    Attack sendMyAttack();

    Monster.State sendMyState();

    PlayerAction getOpponentAction();

    Monster getOpponentLead();

    Attack getOpponentAttack();

    Monster.State getOpponentState();
}
