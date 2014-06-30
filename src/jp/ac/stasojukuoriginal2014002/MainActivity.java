package jp.ac.stasojukuoriginal2014002;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements View.OnClickListener{

	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();

		// 登録ボタンに変数リスナーを登録する
		Button btnENTRY = (Button)findViewById(R.id.btnENTRY);
		btnENTRY.setOnClickListener(this);

		// メンテボタンに変数リスナーを登録する
		Button btnMAINTE = (Button)findViewById(R.id.btnMAINTE);
		btnMAINTE.setOnClickListener(this);

		// 登録ボタンに変数リスナーを登録する
		Button btnCHECK = (Button)findViewById(R.id.btnCHECK);
		btnCHECK.setOnClickListener(this);

		//クラスのフィールド変数がNULLなら、データベース空間オープン
		if(sdb == null) {
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}
		try {
			sdb = helper.getWritableDatabase();
		}catch(SQLiteException e){
			//異常終了
			return;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ

		//生成して代入用のIntentインスタンス変数を用意
		Intent intent = null;
		switch(v.getId())  { //どのボタンが押されたか判定
			case R.id.btnENTRY://登録ボタンが押された
				//エディットテキストからの入力内容を取り出す
				EditText etv = (EditText)findViewById(R.id.edtMsg);
				String inputMsg = etv.getText().toString();

				// inputMsgがnullでない、かつからでない場合のみ、if文内を実行
				if(inputMsg!=null && !inputMsg.isEmpty()){
					//MySQLiteOpenHelperのインサートメソッドを呼び出し
					helper.insertHitokoto(sdb, inputMsg);
				}

				//入力欄をクリア
				etv.setText("");
				break;

			case R.id.btnMAINTE://メンテボタンが押された
				//インテントのインスタンス作成
				intent = new Intent(MainActivity.this, MaintenanceActivity.class);
				// 次画面のアクティビティ起動
				startActivity(intent);
				break;

			case R.id.btnCHECK://一言チェックボタンが押された
			//MySQLiteOpenHelperのセレクト一言メソッドを呼び出して一言をランダムに取得
			String strHitokoto = helper.selectRandomHitokoto(sdb);

			//インテントノインスタンス生成
			intent = new Intent(MainActivity.this, HitokotoActivity.class);
			//インテントに一言を混入
			intent.putExtra("hitokoto", strHitokoto);
			// 次画面のアクティビティ起動
			startActivity(intent);
			break;
		}
	}



}
