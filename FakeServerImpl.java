public class FakeServerImpl {

    private BufferedReader reader = 
        new BufferedReader(new InputStreamReader(System.in));

    PlayerAction sendMyAction() {
        System.out.println("What action would you like to take? [1, 2, 3]");
        String input = reader.readLine();
        System.out.println(input);
    }

    Monster sendMyLead();

    Attack sendMyAttack();

    Monster.State sendMyState();

    PlayerAction getOpponentAction();

    Monster getOpponentLead();

    Attack getOpponentAttack();

    Monster.State getOpponentState();

}
