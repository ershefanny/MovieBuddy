import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // Contoh penggunaan DatabaseHelper untuk CRUD pengguna dan film
        // Silakan sesuaikan dengan kebutuhan aplikasi Anda
    }
}
