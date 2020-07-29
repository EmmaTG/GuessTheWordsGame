package Game.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDataSource {
    private static final String CURRENT_DIRECTORY = System.getProperty("user.dir") + "/Words.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + CURRENT_DIRECTORY;

    private static final String TABLE_NAME = "words";
    private static final String COLUMN_WORDS_ID = "_id";
    private static final String COLUMN_WORDS_WORD = "word";

    private List<Integer> listOfIDs = new ArrayList<>();

    private Connection conn;

    private static WordDataSource ourInstance = new WordDataSource();

    public static WordDataSource getInstance() {
        return ourInstance;
    }

    private WordDataSource() {
    }

    public boolean open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            String sqlLine = "SELECT " + COLUMN_WORDS_ID + " FROM " + TABLE_NAME;
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sqlLine);
            while (result.next()){
                listOfIDs.add(result.getInt(COLUMN_WORDS_ID));
            }
            statement.close();
            result.close();
            return true;
        } catch (SQLException se){
            System.out.println("Fatal error could not open database: " + se.getMessage());
            return false;
        }
    }

    public List<Integer> getIds(){
        return listOfIDs;
    }

    public boolean close(){
        try {
            if (conn != null) {
                conn.close();
            }
            return true;
        } catch (SQLException se){
            System.out.println("Fatal error could not close database: " + se.getMessage());
            return false;
        }
    }

    public List<String> queryWords(List<Integer> ids){
        List<String> listofWords = new ArrayList<>();
        try(Statement statement = conn.createStatement()){
            for (int i : ids){
                String sqlString = "SELECT " + COLUMN_WORDS_WORD + " FROM " + TABLE_NAME + " WHERE " + COLUMN_WORDS_ID + "=" + i;
                ResultSet result = statement.executeQuery(sqlString);
                if (result != null){
                    listofWords.add(result.getString(COLUMN_WORDS_WORD));
                    result.close();
                } else {
                    System.out.println("Error getting row with id: " + i);
                }
            }

        } catch ( SQLException se){
            System.out.println(" Error getting words with ids: " + se.getMessage());
        }
        return listofWords;
    }

}
