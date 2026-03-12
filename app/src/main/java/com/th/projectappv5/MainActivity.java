package com.th.projectappv5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MemberAdapter.OnMemberListener {

    private RecyclerView memberRV;
    private MemberAdapter adapter;
    private ArrayList<Member> memberArrayList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memberRV = findViewById(R.id.idRVMember);
        progressBar = findViewById(R.id.idPB);

        memberArrayList = new ArrayList<>();
        getData();

        Button idBtnInsertMember = findViewById(R.id.idBtnInsertMember);
        idBtnInsertMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateMember.class));
            }
        });

    }

    @Override
    public void onMemberClick(int position) {
        Intent intent = new Intent(MainActivity.this, UpdateMember.class);
        intent.putExtra("member",memberArrayList.get(position));
        startActivity(intent);
    }

    private void getData() {
        String url = GBValues.hostname + "mget.php" + "?id_user=000000";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                memberRV.setVisibility(View.VISIBLE);
                memberArrayList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String memberid = responseObj.getString("id");
                        String memberName = responseObj.getString("names");
                        String memberTel = responseObj.getString("tel");
                        String memberEmail = responseObj.getString("email");
                        Member member = new Member(memberName, memberTel, memberEmail);
                        member.setId(memberid);
                        memberArrayList.add(member);
                        buildRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void buildRecyclerView() {

        adapter = new MemberAdapter(memberArrayList, MainActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        memberRV.setHasFixedSize(true);
        memberRV.setLayoutManager(manager);
        memberRV.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }



}