package ddwucom.mobile.ddwubox;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class CustomerMainActivity extends AppCompatActivity {
    final int SHOW_CODE = 100;

    ListView listView;
    CustomerAdapter customerAdapter;
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
        customerAdapter = new CustomerAdapter(this, R.layout.customer_custom_adapter_view, movieList);
        //둘을 결합
        listView.setAdapter(customerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieList.get(position);
                if(movieList.get(position).getReservation().equals("TRUE")) {
                    Intent intent = new Intent(CustomerMainActivity.this, CustomerShowActivity.class);
                    intent.putExtra("movie", movie);
                    startActivityForResult(intent, SHOW_CODE);
                }
                else {
                    Intent intent = new Intent(CustomerMainActivity.this, CustomerReservExptActivity.class);
                    intent.putExtra("movie", movie);
                    startActivityForResult(intent, SHOW_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOW_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                   Toast.makeText(this, "영화 보여주기 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "영화 보여주기 취소", Toast.LENGTH_SHORT).show();
                   break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final ConstraintLayout searchTitle = (ConstraintLayout) View.inflate(this, R.layout.search_title, null);
        final ConstraintLayout searchGenre = (ConstraintLayout) View.inflate(this, R.layout.search_genre, null);
        switch (item.getItemId()) {
            case R.id.menu_list :
                movieList.clear();
                movieList.addAll(movieDBManager.getAllMovie());
                customerAdapter.notifyDataSetChanged();
                break;

            case R.id.menu_rate:
                Intent intent = new Intent(CustomerMainActivity.this, RatingActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_title :
                AlertDialog.Builder builderTitleSearch = new AlertDialog.Builder(this)
                        .setTitle("영화 제목 검색")
                        .setView(searchTitle)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText etSearchTitle = searchTitle.findViewById(R.id.etSearchTitle);
                                movieDBManager.getMoviesByTitle(etSearchTitle.getText().toString());
                                if(etSearchTitle.getText().toString().getBytes().length <= 0)
                                    Toast.makeText(CustomerMainActivity.this, "검색할 내용을 입력하지 않아 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                else {
                                    movieList.clear();
                                    movieList.addAll(movieDBManager.getMoviesByTitle(etSearchTitle.getText().toString()));
                                    if (movieDBManager.getMoviesByTitle(etSearchTitle.getText().toString()).size() == 0)
                                        Toast.makeText(CustomerMainActivity.this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(CustomerMainActivity.this, "검색 결과입니다.", Toast.LENGTH_SHORT).show();
                                    customerAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CustomerMainActivity.this, "검색이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Dialog dlgT = builderTitleSearch.create();
                dlgT.setCanceledOnTouchOutside(false);
                dlgT.show();
                break;
            case R.id.menu_genre :
                AlertDialog.Builder builderGenreSearch = new AlertDialog.Builder(this)
                        .setTitle("영화 장르 검색")
                        .setView(searchGenre)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText etSearchGenre = searchGenre.findViewById(R.id.etSearchGenre);
                                if(etSearchGenre.getText().toString().getBytes().length <= 0)
                                    Toast.makeText(CustomerMainActivity.this, "검색할 내용을 입력하지 않아 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                else {
                                    movieList.clear();
                                    movieList.addAll(movieDBManager.getMoviesByGenre(etSearchGenre.getText().toString()));
                                    if (movieDBManager.getMoviesByGenre(etSearchGenre.getText().toString()).size() == 0)
                                        Toast.makeText(CustomerMainActivity.this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(CustomerMainActivity.this, "검색 결과입니다.", Toast.LENGTH_SHORT).show();
                                    customerAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CustomerMainActivity.this, "검색이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Dialog dglG = builderGenreSearch.create();
                dglG.setCanceledOnTouchOutside(false);
                dglG.show();
                break;
            case R.id.menu_introduce:
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerMainActivity.this);

                builder.setTitle("관리자 정보")
                        .setIcon(R.mipmap.somsom)
                        .setMessage("심가연 : 010-2018-0997\n" + "이승혜 : 010-2018-0994\n" + "문제 발생 시 연락주시기 바랍니다.")
                        .setPositiveButton("확인", null);
                Dialog dlg = builder.create();
                dlg.setCanceledOnTouchOutside(false);
                dlg.show();

                break;
            case R.id.menu_exit:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(CustomerMainActivity.this);
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

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        switch (v.getId()) {
//            case R.id.customListView :
//                getMenuInflater().inflate(R.menu.menu_main, menu);
//                break;
//        }
//    }

//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_add:
//                Intent intent = new Intent(MainActivity.this, AddActivity.class);
//                startActivityForResult(intent, ADD_CODE);
//                break;
//            case R.id.menu_introduce:
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//                builder.setTitle("개발자 소개")
//                        .setIcon(R.mipmap.me)
//                        .setMessage("모바일 소프트웨어 02분반 수강생\n" + "20180977 컴퓨터학과 심가연입니다.\n" + "한 학기동안 수준 높은 강의 해주셔서 감사합니다.")
//                        .setPositiveButton("확인", null);
//                Dialog dlg = builder.create();
//                dlg.setCanceledOnTouchOutside(false);
//                dlg.show();
//
//                break;
//            case R.id.menu_exit:
//                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
//                builder2.setTitle("앱 종료")
//                        .setMessage("앱을 종료하시겠습니까?")
//                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                            }
//                        })
//                        .setNegativeButton("취소", null);
//                Dialog dlg2 = builder2.create();
//                dlg2.setCanceledOnTouchOutside(false);
//                dlg2.show();
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        movieList.clear();
        movieList.addAll(movieDBManager.getAllMovie());
        customerAdapter.notifyDataSetChanged();
    }

}