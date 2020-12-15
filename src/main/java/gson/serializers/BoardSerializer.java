package gson.serializers;

import chess.board.Board;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BoardSerializer implements JsonSerializer<Board> {
    @Override
    public JsonElement serialize(Board board, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("nameGame", board.getGameName());
        result.addProperty("counter", board.getCounter());

        result.add("firstPlayer", context.serialize(board.getFirstPlayer()));
        result.add("secondPlayer", context.serialize(board.getSecondPlayer()));
        return result;
    }
}
