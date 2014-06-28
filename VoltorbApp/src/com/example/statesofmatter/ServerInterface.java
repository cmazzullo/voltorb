package com.example.statesofmatter;

public interface ServerInterface {
     
    PlayerAction sendAction();

    Monster sendLead();

    Attack sendAttack();

    State sendState();

    PlayerAction getAction();

    Turn getTurn(Player player);

    Monster getLead();

    Attack getAttack();

    State getState();

    boolean bothFainted(Player p1, Player p2);

    Player getPlayer(String ID);
}
