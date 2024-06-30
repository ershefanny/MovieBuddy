import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MovieBuddy.db";
    private static final int DATABASE_VERSION = 1;

    // Tabel Pengguna
    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_ROLE = "role"; // admin or user

    // Tabel Film
    private static final String TABLE_MOVIES = "movies";
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_YEAR = "year";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query untuk membuat tabel pengguna
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_NAME + " TEXT,"
                + USER_EMAIL + " TEXT,"
                + USER_PASSWORD + " TEXT,"
                + USER_ROLE + " TEXT"
                + ")";

        // Query untuk membuat tabel film
        String createMoviesTable = "CREATE TABLE " + TABLE_MOVIES + "("
                + MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MOVIE_TITLE + " TEXT,"
                + MOVIE_YEAR + " INTEGER"
                + ")";

        // Eksekusi query pembuatan tabel
        db.execSQL(createUsersTable);
        db.execSQL(createMoviesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tabel jika sudah ada versi sebelumnya
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

        // Buat tabel baru
        onCreate(db);
    }

    // Metode CRUD untuk pengelolaan pengguna

    public long addUser(String username, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        values.put(USER_ROLE, role);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
                user.setRole(cursor.getString(cursor.getColumnIndex(USER_ROLE)));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
            user.setRole(cursor.getString(cursor.getColumnIndex(USER_ROLE)));
        }
        cursor.close();
        db.close();
        return user;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getUsername());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_ROLE, user.getRole());
        int rowsAffected = db.update(TABLE_USERS, values, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Metode CRUD untuk pengelolaan film

    public long addMovie(String title, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_TITLE, title);
        values.put(MOVIE_YEAR, year);
        long id = db.insert(TABLE_MOVIES, null, values);
        db.close();
        return id;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(MOVIE_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MOVIE_TITLE)));
                movie.setYear(cursor.getInt(cursor.getColumnIndex(MOVIE_YEAR)));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movieList;
    }

    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_TITLE, movie.getTitle());
        values.put(MOVIE_YEAR, movie.getYear());
        int rowsAffected = db.update(TABLE_MOVIES, values, MOVIE_ID + "=?", new String[]{String.valueOf(movie.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, MOVIE_ID + "=?", new String[]{String.valueOf(movie.getId())});
        db.close();
    }
}
