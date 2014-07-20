package com.example.server;

import com.example.statesofmatter.Attack;
import com.example.statesofmatter.Monster;
import com.example.statesofmatter.Player;
import com.example.statesofmatter.PlayerAction;
import com.example.statesofmatter.State;
import com.example.statesofmatter.Turn;

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
