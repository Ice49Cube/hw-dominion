package dominion.model;

class Guard {

    static void validateAffected(int expected, int count, String methodName) {
        validateFalse(expected != count, "Database error - Guard.validateAffected - " + methodName);
    }

    static void validateState(Game game, String state) {
        validateNotNull(game.getState(), "Operation not allowed while the current state is '(null)'.");
        validateTrue(game.getState().equals(state), "Operation not allowed while the current state is '" + game.getState() + "'.");
    }

    static void validateNotNull(Object instance, String message) {
        validateFalse(instance == null, message);
    }

    static void validateNull(Object instance, String message) {
        validateFalse(instance != null, message);
    }

    static void validateTrue(boolean expression, String message) {
        validateFalse(!expression, message);
    }

    static void validateFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalStateException(message);
        }
    }

    /*
    static void validateCurrentPlayer(Player player, String methodName) {
        if (!player.getIsCurrent()) {
            throw new IllegalStateException("Method only allowed for the current player. - " + methodName);
        }
    }
 
    static void validateInsert(int id, String message) {
        
    }
    */
}
