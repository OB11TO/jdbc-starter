package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;


//blob -> bytea
//clob -> TEXT
public class BlobRunner {
    public static void main(String[] args) throws SQLException {
        //saveImage();
        getImage();
    }


    private static void getImage() throws SQLException {
        String sql = """
                SELECT image
                FROM task26.aircraft
                WHERE id = ?
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1,1);
            var resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                var image = resultSet.getBytes("image");
                try {
                    Files.write(Path.of("resources", "бехелит_2.jpg"),image, StandardOpenOption.CREATE);
                } catch (IOException e) {
                    throw new RuntimeException("Ooops, не можем загрузить фотку");
                }
            }
        }
    }


    private static void saveImage() throws SQLException, IOException {
        String sql = """
                UPDATE task26.aircraft
                SET image = ?
                WHERE id = 1
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {

            //замена blob в postgres (bytea)
            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "бехелит.jpg")));
            preparedStatement.executeUpdate();

        }

    }

//    Method org.postgresql.jdbc.PgConnection.createBlob() is not yet implemented.
//    private static void saveImage() throws SQLException, IOException {
//        String sql = """
//                UPDATE task26.aircraft
//                SET image = ?
//                WHERE id = 1
//                """;
//        try (var connection = ConnectionManager.open();
//             var preparedStatement = connection.prepareStatement(sql)) {
//
//            connection.setAutoCommit(false);
//            var blob = connection.createBlob();// создали blob
//            blob.setBytes(1, Files.readAllBytes(Path.of("resources", "бехелит.jpg"))); // читаем
//
//            preparedStatement.setBlob(1, blob);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }
//    }
}
