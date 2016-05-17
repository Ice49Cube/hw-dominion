package dominion.model;


class Guard {
   
    static void validateAffected(int expected, int count, String message) {
        if (expected != count) {
            throw new IllegalStateException("Database error - " + message);
        }
    }

    static void validateState(Game game, String state) {
        if (game.getState() == null) {
            throw new IllegalStateException("Operation not allowed in while the current state is '(null)'.");
        } else if (!game.getState().equals(state)) {
            throw new IllegalStateException("Operation not allowed in while the current state is '" + game.getState() + "'.");
        }
    }
    
    static void validateCurrentPlayer(Player player, String methodName) {
        if (!player.getIsCurrent()){
            throw new IllegalStateException("Method allowed for the current player. - " + methodName);
        }
    }
    
    /*static void validateInsert(int id, String message) {
        
    }*/
   
}
