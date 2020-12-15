package gson.serializers;

import chess.figure.Figure;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import player.Player;

import java.lang.reflect.Type;
import java.util.Map;

public class PlayerSerializer implements JsonSerializer<Player> {
    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext context) {

        JsonObject result = new JsonObject();
        result.addProperty("name", player.getName());
        result.addProperty("direction", player.getMoveDirection().getDirection());

        Type chessType = new TypeToken<Map<Figure, String>>(){}.getType();
        result.add("chess", context.serialize(player.getCells(), chessType));
        return result;
    }
}
