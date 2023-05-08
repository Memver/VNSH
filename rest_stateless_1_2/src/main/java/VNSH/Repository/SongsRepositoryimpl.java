package VNSH.Repository;

import VNSH.Classes.Artist;
import VNSH.Classes.ArtistSongs;
import VNSH.Classes.Song;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class SongsRepositoryimpl implements SongsRepository {
    private static final String className = "org.h2.Driver";
    private static final String url = "jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE";

    @Override
    public ArtistSongs save(Song song) throws SQLException, ClassNotFoundException {
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Если добавляют нового исполнителя, то нового исполнителя надо добавить в таблицу artists
        // Получение артиста по id
        PreparedStatement prepStat =
                connection.prepareStatement("Select id from artists WHERE name = ?");
        prepStat.setString(1, song.getArtistName());
        ResultSet rs = prepStat.executeQuery();

        // Если не находит артиста в таблице artists, то создает его
        if(!rs.next()){
            prepStat = connection.prepareStatement("INSERT INTO artists ( name ) VALUES (?)");
            prepStat.setString(1, song.getArtistName());
            prepStat.execute();
        }

        // Добавление песни в БД
        prepStat = connection.prepareStatement("INSERT INTO songs ( artistName, name, auditions) VALUES (?,?,?)");
        prepStat.setString(1, song.getArtistName());
        prepStat.setString(2, song.getName());
        prepStat.setInt(3, song.getAuditions());
        prepStat.execute();

        // Получение загруженной песни из БД
        prepStat = connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions\n" +
                "FROM songs\n" +
                "INNER JOIN artists ON songs.artistname = artists.name\n" +
                "WHERE songs.id = (SELECT MAX(id) FROM songs);");
        rs = prepStat.executeQuery();
        rs.next();
        ArtistSongs artistSongs = new ArtistSongs(rs.getInt(1), rs.getInt(2),
                rs.getString(3), rs.getString(4), rs.getInt(5));

        connection.close();
        return artistSongs;
    }

    @Override
    public ArrayList<ArtistSongs> findAll() throws SQLException, ClassNotFoundException{
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Получение всех песен
        PreparedStatement prepStat = connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions \n" +
                "FROM songs\n" +
                "INNER JOIN artists ON songs.artistname = artists.name;\n");
        ResultSet rs = prepStat.executeQuery();
        ArrayList<ArtistSongs> list = new ArrayList<>();
        while (rs.next()){
            list.add(new ArtistSongs(rs.getInt(1), rs.getInt(2),
                    rs.getString(3), rs.getString(4), rs.getInt(5)));
        }

        connection.close();
        return list;
    }

    @Override
    public ArtistSongs getById(Integer id)throws SQLException, ClassNotFoundException {
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Получение песни по id
        PreparedStatement prepStat =
                connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions\n" +
                        "FROM songs\n" +
                        "INNER JOIN artists ON songs.artistname = artists.name\n" +
                        "WHERE songs.id = ?;");
        prepStat.setInt(1, id);
        ResultSet rs = prepStat.executeQuery();

        // Если не находит песню по id, то возвращает null
        if(!rs.next()){
            connection.close();
            return null;
        }
        ArtistSongs artistSongs = new ArtistSongs(rs.getInt(1), rs.getInt(2),
                rs.getString(3), rs.getString(4), rs.getInt(5));

        connection.close();
        return artistSongs;
    }

    @Override
    public ArtistSongs updateById(Integer id, Song song)throws SQLException, ClassNotFoundException {
        // Присваивает поступившей песне поступивший id
        song.setId(id);

        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // УДАЛЕНИЕ ПЕСНИ ПО id
        // Получение песни по id
        PreparedStatement prepStat =
                connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions\n" +
                        "FROM songs\n" +
                        "INNER JOIN artists ON songs.artistname = artists.name\n" +
                        "WHERE songs.id = ?;");
        prepStat.setInt(1, id);
        ResultSet rs = prepStat.executeQuery();

        rs.next();
        ArtistSongs artistSongs = new ArtistSongs(rs.getInt(1), rs.getInt(2),
                rs.getString(3), rs.getString(4), rs.getInt(5));

        // Удаление песни из БД по id
        prepStat = connection.prepareStatement("Delete from songs where id = ?");
        prepStat.setInt(1, id);
        prepStat.execute();

        // Если нет больше песен со старым артистом, то удаляем артиста из таблицы артистов
        prepStat =
                connection.prepareStatement("SELECT *\n" +
                        "FROM songs\n" +
                        "WHERE artistName = ?;");
        prepStat.setString(1, artistSongs.getArtistName());
        rs = prepStat.executeQuery();

        if(!rs.next()){
            prepStat = connection.prepareStatement("DELETE FROM artists where name = ?;");
            prepStat.setString(1, artistSongs.getArtistName());
            prepStat.execute();
        }


        // ДОБАВЛЕНИЕ НОВОГО АРТИСТА
        // Если добавляют нового исполнителя, то нового исполнителя надо добавить в таблицу artists
        prepStat =
                connection.prepareStatement("Select * from artists WHERE name = ?");
        prepStat.setString(1, song.getArtistName());
        rs = prepStat.executeQuery();

        // Если не находит артиста в таблице artists, то создает его
        if(!rs.next()){
            prepStat = connection.prepareStatement("INSERT INTO artists ( name ) VALUES (?)");
            prepStat.setString(1, song.getArtistName());
            prepStat.execute();
        }

        // Добавление песни в БД
        prepStat = connection.prepareStatement("INSERT INTO songs ( id, artistName, name, auditions) VALUES (?,?,?,?)");
        prepStat.setInt(1,song.getId());
        prepStat.setString(2, song.getArtistName());
        prepStat.setString(3, song.getName());
        prepStat.setInt(4, song.getAuditions());
        prepStat.execute();

        // Получение загруженной песни из БД
        prepStat = connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions\n" +
                "FROM songs\n" +
                "INNER JOIN artists ON songs.artistname = artists.name\n" +
                "WHERE songs.id = ?;");
        prepStat.setInt(1,song.getId());
        rs = prepStat.executeQuery();
        rs.next();
        ArtistSongs result = new ArtistSongs(rs.getInt(1), rs.getInt(2),
                rs.getString(3), rs.getString(4), rs.getInt(5));
        connection.close();
        return result;
    }

    @Override
    public ArtistSongs deleteById(Integer id)throws SQLException, ClassNotFoundException {
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Получение песни по id
        PreparedStatement prepStat =
                connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions\n" +
                        "FROM songs\n" +
                        "INNER JOIN artists ON songs.artistname = artists.name\n" +
                        "WHERE songs.id = ?;");
        prepStat.setInt(1, id);
        ResultSet rs = prepStat.executeQuery();

        rs.next();
        ArtistSongs artistSongs = new ArtistSongs(rs.getInt(1), rs.getInt(2),
                rs.getString(3), rs.getString(4), rs.getInt(5));

        // Удаление песни из БД по id
        prepStat = connection.prepareStatement("Delete from songs where id = ?");
        prepStat.setInt(1, id);
        prepStat.execute();

        // Если нет больше песен со старым артистом, то удаляем артиста из таблицы артистов
        prepStat =
                connection.prepareStatement("SELECT *\n" +
                        "FROM songs\n" +
                        "WHERE artistName = ?;");
        prepStat.setString(1, artistSongs.getArtistName());
        rs = prepStat.executeQuery();

        if(!rs.next()){
            prepStat = connection.prepareStatement("DELETE FROM artists where name = ?;");
            prepStat.setString(1, artistSongs.getArtistName());
            prepStat.execute();
        }
        connection.close();
        return artistSongs;
    }


    @Override
    public ArtistSongs listenSongById(Integer id, Integer count)throws SQLException, ClassNotFoundException{
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Меняем количество прослушиваний в БД
        PreparedStatement prepStat = connection.prepareStatement("UPDATE songs SET auditions = auditions + ? WHERE ID=?");
        prepStat.setInt(1, count);
        prepStat.setInt(2, id);
        prepStat.execute();

        // Получаем результат
        prepStat = connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions \n" +
                "FROM songs\n" +
                "INNER JOIN artists ON songs.artistname = artists.name\n" +
                "WHERE songs.id = ?;");
        prepStat.setInt(1, id);
        ResultSet rs = prepStat.executeQuery();
        rs.next();
        ArtistSongs artistSongs = new ArtistSongs(rs.getInt(1), rs.getInt(2),
                rs.getString(3), rs.getString(4), rs.getInt(5));
        connection.close();
        return artistSongs;
    }

    @Override
    public ArrayList<ArtistSongs> getSortedSongsByAuditions(int limit)throws SQLException, ClassNotFoundException{
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Добавление прослушиваний каждой песне в БД
        PreparedStatement prepStat = connection.prepareStatement("UPDATE songs SET auditions = auditions + 1\n" +
                "ORDER BY auditions DESC\n" +
                "LIMIT ?;");
        prepStat.setInt(1, limit);
        prepStat.execute();

        // Отправляем БД запрос на получение отсортированных песен
        prepStat = connection.prepareStatement("SELECT songs.id, artists.id as artistId, songs.artistName, songs.name, songs.auditions \n" +
                "FROM songs\n" +
                "INNER JOIN artists ON songs.artistname = artists.name\n" +
                "ORDER by auditions DESC\n" +
                "LIMIT ?;");
        prepStat.setInt(1, limit);
        ResultSet rs = prepStat.executeQuery();
        ArrayList<ArtistSongs> list = new ArrayList<>();
        while (rs.next()){
            list.add(new ArtistSongs(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
        }
        connection.close();
        return list;
    }

    @Override
    public ArrayList<Artist> getArtists() throws SQLException, ClassNotFoundException{
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Получение всех артистов
        PreparedStatement prepStat = connection.prepareStatement("SELECT id, name FROM artists;");
        ResultSet rs = prepStat.executeQuery();
        ArrayList<Artist> list = new ArrayList<>();
        while (rs.next()){
            list.add(new Artist(rs.getInt(1), rs.getString(2)));
        }
        // Возврат всех артистов
        connection.close();
        return list;
    }

    @Override
    public Artist getArtistById(Integer id)throws SQLException, ClassNotFoundException {
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Получение артиста по id
        PreparedStatement prepStat =
                connection.prepareStatement("Select id, name from artists WHERE id = ?");
        prepStat.setInt(1, id);
        ResultSet rs = prepStat.executeQuery();

        // Если не находит артиста по id, то возвращает null
        if(!rs.next()){
            connection.close();
            return null;
        }
        Artist artist = new Artist(rs.getInt(1), rs.getString(2));
        connection.close();
        return artist;
    }

    @Override
    public ArrayList<ArtistSongs> getArtistSongsById(Integer id)throws SQLException, ClassNotFoundException{
        // Подключение к БД
        Class.forName(className);
        Connection connection = DriverManager.getConnection(url, "sa", "");

        // Получение имени артиста по id
        PreparedStatement prepStat =
                connection.prepareStatement("Select name from artists WHERE id = ?");
        prepStat.setInt(1, id);
        ResultSet rs = prepStat.executeQuery();

        // Если не находит артиста по id, то возвращает null
        if(!rs.next()){
            connection.close();
            return null;
        }

        String artistName = rs.getString(1);

        // Получение песен из БД по имени артиста
        prepStat = connection.prepareStatement("Select * from songs WHERE artistName = ?");
        prepStat.setString(1, artistName);
        rs = prepStat.executeQuery();

        ArrayList<ArtistSongs> list = new ArrayList<>();
        while (rs.next()){
            list.add(new ArtistSongs(rs.getInt(1), id , rs.getString(2),rs.getString(3),rs.getInt(4)));
        }
        connection.close();
        return list;
    }
}
