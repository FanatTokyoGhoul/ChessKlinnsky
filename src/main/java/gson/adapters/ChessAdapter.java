package gson.adapters;

import chess.figure.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChessAdapter extends TypeAdapter<Map<Figure, String>> {
    @Override
    public void write(JsonWriter out, Map<Figure, String> figureStringMap) throws IOException {
        out.beginArray();

        for (Map.Entry<Figure, String> keyAndValue : figureStringMap.entrySet()) {
            out.value(keyAndValue.getKey().getNameClass() + "-" + keyAndValue.getValue());
        }

        out.endArray();
    }

    @Override
    public Map<Figure, String> read(JsonReader in) throws IOException {
        Map<Figure, String> map = new HashMap<>();

        in.beginArray();
        while (in.hasNext()){
            String[] keyAndValue = in.nextString().split("-");
            Figure figure = createFFigure(keyAndValue[0]);
            map.put(figure, keyAndValue[1]);
        }
        return map;
    }

    private Figure createFFigure(String figure){
        switch (figure){
            case "Bishop":
                return new Bishop();
            case "King":
                return new King();
            case "Knight":
                return new Knight();
            case "Pawn":
                return new Pawn();
            case "Queen":
                return new Queen();
            case "Rook":
                return new Rook();
            default:
                return null;
        }
    }
}
