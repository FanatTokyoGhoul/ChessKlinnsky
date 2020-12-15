package gson.services;

import chess.board.Board;
import chess.figure.Figure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gson.adapters.ChessAdapter;
import gson.deserializers.BoardDeserializer;
import gson.deserializers.PlayerDeserializer;
import gson.serializers.BoardSerializer;
import gson.serializers.PlayerSerializer;
import player.BotPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonService {
    private final Type chessType = new TypeToken<Map<Figure, String>>(){}.getType();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(BotPlayer.class, new PlayerSerializer())
            .registerTypeAdapter(Board.class, new BoardSerializer())
            .registerTypeAdapter(chessType, new ChessAdapter())
            .registerTypeAdapter(BotPlayer.class, new PlayerDeserializer())
            .registerTypeAdapter(Board.class, new BoardDeserializer())
            .create();

    public void toJson(Board board){
        String json = gson.toJson(board);
        File file = new File("./Games/" + board.getGameName());

        if(!file.exists()){
            while (!file.mkdir()){
                System.out.println("Не получилось создать папку! пробую ещё раз.");
            }
        }

        file = new File("./Games/" + board.getGameName() + "/" + "Move_" + board.getCounter() + ".json");

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Board toBoard(String path){
        String json = "";
        try {
            FileInputStream inFile = new FileInputStream(path);
            byte[] str = new byte[inFile.available()];
            inFile.read(str);
            json = new String(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(json, Board.class);
    }
}
