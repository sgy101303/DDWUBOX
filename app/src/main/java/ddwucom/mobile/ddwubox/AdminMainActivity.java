package ddwucom.mobile.ddwubox;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    final int ADD_CODE = 100;
    final int UPDATE_CODE = 200;

    ListView listView;
    MyAdapter myAdapter;
    ArrayList<Movie> movieList;
    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();

        listView = findViewById(R.id.customListView); //adapter 지정 전에 데이터 준비돼있어야함
        movieDBManager = new MovieDBManager(this);
        movieList = movieDBManager.getAllMovie();
//        movieList.addAll(movieDBManager.getAllMovie());
        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, movieList);
        //둘을 결합
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieList.get(position);
                Intent intent = new Intent(AdminMainActivity.this, UpdateActivity.class);
                intent.putExtra("movie", movie);
                startActivityForResult(intent, UPDATE_CODE);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
                builder.setTitle("영화 삭제")
                        .setMessage(movieList.get(pos).getMovie() + "을(를) 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (movieDBManager.removeMovie(movieList.get(pos).get_id())) {
                                    Toast.makeText(AdminMainActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    movieList.clear();
                                    movieList.addAll(movieDBManager.getAllMovie());
                                    myAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(AdminMainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CODE) {  // AddActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    String movie = data.getStringExtra("movie");
                    Toast.makeText(this, movie + " 추가 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "영화 추가 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == UPDATE_CODE) {    // UpdateActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "영화 수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "영화 수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final ConstraintLayout searchTitle = (ConstraintLayout) View.inflate(this, R.layout.search_title, null);
        final ConstraintLayout searchGenre = (ConstraintLayout) View.inflate(this, R.layout.search_genre, null);
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(AdminMainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_CODE);
                break;
            case R.id.menu_list :
                movieList.clear();
                movieList.addAll(movieDBManager.getAllMovie());
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.menu_exit:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(AdminMainActivity.this);
                builder2.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("취소", null);
                Dialog dlg2 = builder2.create();
                dlg2.setCanceledOnTouchOutside(false);
                dlg2.show();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieList.clear();
        movieList.addAll(movieDBManager.getAllMovie());
        myAdapter.notifyDataSetChanged();
    }

}