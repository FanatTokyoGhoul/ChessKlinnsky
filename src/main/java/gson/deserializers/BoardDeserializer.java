package gson.deserializers;

import chess.board.Board;
import com.google.gson.*;
import player.BotPlayer;
import player.Player;

import java.lang.reflect.Type;

public class BoardDeserializer implements JsonDeserializer<Board> {
    @Override
    public Board deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String name = jsonObject.get("nameGame").getAsString();
        int counter = jsonObject.get("counter").getAsInt();
        Player firstPlayer = context.deserialize(jsonObject.get("firstPlayer"), BotPlayer.class);
        Player secondPlayer = context.deserialize(jsonObject.get("secondPlayer"), BotPlayer.class);
        return new Board(firstPlayer, secondPlayer, counter, name);
    }
}
