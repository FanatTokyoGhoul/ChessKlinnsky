package gson.deserializers;

import chess.board.Direction;
import chess.figure.Figure;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import player.BotPlayer;
import player.Player;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PlayerDeserializer implements JsonDeserializer<Player> {
    @Override
    public Player deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        Direction direction = new Direction(jsonObject.get("direction").getAsInt());
        String name = jsonObject.get("name").getAsString();

        Type chessType = new TypeToken<Map<Figure, String>>(){}.getType();
        Map<Figure, String> cells = context.deserialize(jsonObject.getAsJsonArray("chess"), chessType);

        Map<String, Figure> figures = new HashMap<>();
        for(Map.Entry<Figure, String> keyAndValue : cells.entrySet()){
            figures.put(keyAndValue.getValue(), keyAndValue.getKey());
        }

        return new BotPlayer(name, direction, cells, figures);
    }
}
