package com.example.statesofmatter;

public interface Server {
    
    enum PlayerAction {
        SWITCH, ATTACK, PHASE_SHIFT
    }
    
    PlayerAction sendMyAction();

    Monster sendMyLead();

    Attack sendMyAttack();

    State sendMyState();

    PlayerAction getOpponentAction();

    Turn getOpponentTurn();

    Monster getOpponentLead();

    Attack getOpponentAttack();

    State getOpponentState();

    Boolean opponentAllFainted();

    Player getPlayer(String ID);

    Player getOpponent();
}
