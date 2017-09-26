package com.example.contact;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.contact.Util.PermissionUtil;

import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;

/**
 * Content Resolver 사용하기
 * 1. Content Resolver를 불러오기
 * 2. 데이터 URI 정의 <- 일종의 DB에서의 테이블 이름
 * 3. 데이터 URI에서 가져올 컬럼명 정의 (조건절을 정의할 수도 있다.)
 * 4. CR로 쿼리한 데이터를 커서에 담는다.
 * 5. 커서에 담긴 데이터를 반복문을 돌면서 처리한다.
 */

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView = null;
    ArrayList<Contact> list = null;
    static final int REQ_CODE = 100;
    static final String[] PERM = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
    PermissionUtil util = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        util = new PermissionUtil(REQ_CODE, PERM);

        if (util.checkPermission(this)) {
            load();
            setAdapter();
        }
    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (util.afterRequestPermission(REQ_CODE, grantResults)) {
                load();
                setAdapter();
            } else {
                finish();
            }
        }

    private void load() {
        list = new ArrayList<>();
        // 1. Content Resolver 불러오기
        ContentResolver resolver = getContentResolver();
        // 2. 데이터 URI 정의
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 전화번호 URI : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 주소록 URI : ContactsContract.Contacts.CONTENT_URI
        //File의 경로를 URI라는 객체로 만들어서 쓴다. 어떤 URI이냐에 따라 프로토콜이 다름
        String projection[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        }; //Phone이라는 데이터베이스의 테이블의 각 행의 이름
        // 4. 쿼리 결과 -> Cursor
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        // 5. cursor 반복처리
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Contact contact = new Contact();
                int index = cursor.getColumnIndex(projection[1]);
                contact.setName(cursor.getString(index)); //Column의 index를 가져옴, 이름을 가져올 수는 없음'
                contact.setNumber(cursor.getString(cursor.getColumnIndex(projection[2])));
                contact.setId(cursor.getInt(cursor.getColumnIndex(projection[0])));
                list.add(contact);
            }
        }
    }

    private void setAdapter() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, list);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Toast.makeText(this, list.size()+"", Toast.LENGTH_SHORT).show();
    }

}

class Contact {
    private String name;
    private String number;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
