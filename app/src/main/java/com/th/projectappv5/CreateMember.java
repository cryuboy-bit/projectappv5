package com.th.projectappv5;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateMember extends AppCompatActivity {

    private EditText idEdtMemberName, idMemberTel, idMemberEmail;
    private Button idBtnInsert;
    private ProgressBar idPBLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_member);
        idEdtMemberName = findViewById(R.id.idEdtMemberName);
        idMemberTel = findViewById(R.id.idMemberTel);
        idMemberEmail = findViewById(R.id.idMemberEmail);
        idBtnInsert = findViewById(R.id.idBtnInsert);
        idPBLoading = findViewById(R.id.idPBLoading);

        idBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(idEdtMemberName.getText().toString()) &&
                        TextUtils.isEmpty(idMemberTel.getText().toString()) &&
                        TextUtils.isEmpty(idMemberEmail.getText().toString())) {
                    Toast.makeText(CreateMember.this, "Please enter your data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                Member member = new Member(idEdtMemberName.getText().toString(),idMemberTel.getText().toString(),
                        idMemberEmail.getText().toString());
                InsertData(member);
            }
        });


    }

    private void InsertData(Member member) {
        String url = GBValues.hostname + "minsert.php";
        idPBLoading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(CreateMember.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                idPBLoading.setVisibility(View.GONE);
                idEdtMemberName.setText("");
                idMemberTel.setText("");
                idMemberEmail.setText("");
                Toast.makeText(CreateMember.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);
                    String output = respObj.getString("success_msg");
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreateMember.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("names", member.getNames());
                params.put("tel", member.getTel());
                params.put("email", member.getEmail());
                return params;
            }
        };
        queue.add(request);
    }

}