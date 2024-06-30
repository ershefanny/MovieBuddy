import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Inisialisasi komponen dari layout
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewGenre = findViewById(R.id.textViewGenre);
        TextView textViewRating = findViewById(R.id.textViewRating);
        TextView textViewOverview = findViewById(R.id.textViewOverview);
        LinearLayout layoutRecommendations = findViewById(R.id.layoutRecommendations);

        // Set data film
        textViewTitle.setText("Lovely Runner");
        textViewGenre.setText("Fantasy, Romance, Comedy");
        textViewRating.setText("Rating: 9.8");
        textViewOverview.setText("Overview:\nDi dunia gemerlap bintang, Ryu Seon Jae bersinar sebagai selebriti papan atas, memikat perhatian sejak debutnya. Terlepas dari kehidupannya yang sempurna, sifatnya yang sebenarnya disembunyikan di balik layar.");

        // Tambahkan daftar rekomendasi film
        addRecommendation("Ipar Adalah Maut", "Drama", "Rating: 8.6");
        addRecommendation("How to Make M..", "Drama", "Rating: 7.5");
        addRecommendation("Haikyu!! The", "Animation", "Rating: 9.8");
    }

    private void addRecommendation(String title, String genre, String rating) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(title + "\nGenre: " + genre + "\n" + rating);
        LinearLayout layoutRecommendations = findViewById(R.id.layoutRecommendations);
        layoutRecommendations.addView(textView);
    }
}
