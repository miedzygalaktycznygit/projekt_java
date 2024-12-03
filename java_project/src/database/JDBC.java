package database;
import java.sql.*;
import java.sql.Driver;
import java.sql.DriverManager;

public class JDBC {
    //MySQL
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/quiz_gui_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1234";

    public static boolean saveQuestionCategoryAndAnswersToDatabase(String question, String category, String[] answers, int correctIndex){
        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );
            // dodaje jeśli jest nowe inacze odbiera to z bazy danych

            Category categoryObj = getCategory(category);
            if(categoryObj == null){
                categoryObj = insertCategory(category);
            }

            //dodanie pytania do bazy danych
            Question questionObj = insertQuestion(categoryObj, question);

            return insertAnswers(questionObj, answers, correctIndex);

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    //metoda dla pytania
    private static Question insertQuestion(Category category, String questionText){
        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement insertQuestionQuery = connection.prepareStatement(
                    "INSERT INTO QUESTION(CATEGORY_ID, QUESTION_TEXT)" + "VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            insertQuestionQuery.setInt(1, category.getCategoryId());
            insertQuestionQuery.setString(2,questionText);
            insertQuestionQuery.executeUpdate();

            //sprawdzenie po id pytania
            ResultSet resultSet = insertQuestionQuery.getGeneratedKeys();
            if(resultSet.next()){
                int questionId = resultSet.getInt(1);
                return new Question(questionId, category.getCategoryId(), questionText);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        //zwraca 0 jak nie znajdzie bazy danych

        return null;
    }


    //metody dal kategorij
    private static Category getCategory(String category){
        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement getCategoryQuery = connection.prepareStatement(
                    "SELECT * FROM CATEGORY WHERE CATEGORY_NAME + ?"
            );

            getCategoryQuery.setString(1, category);

            ResultSet resultSet = getCategoryQuery.executeQuery();
            if(resultSet.next()){
                int categoryId = resultSet.getInt("category_id");
                return new Category(categoryId, category);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        //zwraca 0 jak nie znajdzie bazy danych

        return null;

    }
    private static Category insertCategory(String category){
        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement insertCategoryQuery = connection.prepareStatement(
                    "INSERT INTO CATEGORY(CATEGORY_NAME)" + "VALUES(?)", Statement.RETURN_GENERATED_KEYS
            );

            insertCategoryQuery.setString(1, category);
            insertCategoryQuery.executeUpdate();
            //inkremenctacja za każdym razem jak mamy nowe dane dla category id
            ResultSet resultSet = insertCategoryQuery.getGeneratedKeys();
            if(resultSet.next()){
                int categoryId = resultSet.getInt(1);
                return new Category(categoryId, category);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        //zwraca 0 jak nie znajdzie bazy danych

        return null;

    }
    //metoda dla odpowiedzi
    private static boolean insertAnswers(Question question, String[] answers, int correctInsdex){
        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement insertAnswerQuery = connection.prepareStatement(
                    "INSERT INTO ANSWER(QUESTION_ID, ANSWER_TEXT, IS_CORRECT)" + "VALUES (?, ?, ?)"
            );
            insertAnswerQuery.setInt(1, question.getQuestionId());
            for(int i = 0; i < answers.length; i++){
                insertAnswerQuery.setString(2, answers[i]);
                if(i == correctInsdex){
                    insertAnswerQuery.setBoolean(3, true);
                }else{
                    insertAnswerQuery.setBoolean(3, false);
                }
                insertAnswerQuery.executeUpdate();
            }

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
