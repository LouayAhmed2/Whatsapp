package com.example.whatsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    RecyclerView mUserList;
    RecyclerView.Adapter mUserLisrAdapter;
    RecyclerView.LayoutManager mUserListLayoutManger;
    ArrayList<UserObject> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        userList = new ArrayList<>();
        initalizeRecyclerview();
        getContactList();
    }

    public void getContactList() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            UserObject mContact = new UserObject(name, phone);
            userList.add(mContact);
            mUserLisrAdapter.notifyDataSetChanged();

        }
    }

    private void initalizeRecyclerview() {
        mUserList = findViewById(R.id.userlist);
        mUserList.setNestedScrollingEnabled(false);
        mUserListLayoutManger = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mUserList.setLayoutManager(mUserListLayoutManger);
        mUserLisrAdapter = new UserListAdapter(userList);

        mUserList.setAdapter(mUserLisrAdapter);
    }
}
