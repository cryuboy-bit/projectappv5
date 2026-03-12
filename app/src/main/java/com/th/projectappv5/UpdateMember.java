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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateMember extends AppCompatActivity {

    private EditText idEdtMemberName, idMemberTel, idMemberEmail;
    private Button idBtnUpdate,idBtnDelete;
    private ProgressBar idPBLoading;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_member);
        idEdtMemberName = findViewById(R.id.idEdtMemberName);
        idMemberTel = findViewById(R.id.idMemberTel);
        idMemberEmail = findViewById(R.id.idMemberEmail);
        idBtnUpdate = findViewById(R.id.idBtnUpdate);
        idBtnDelete = findViewById(R.id.idBtnDelete);
        idPBLoading = findViewById(R.id.idPBLoading);
        if(getIntent().getExtras() != null) {
            member = (Member) getIntent().getSerializableExtra("member");
            idEdtMemberName.setText(member.getNames());
            idMemberTel.setText(member.getTel());
            idMemberEmail.setText(member.getEmail());
        }
        idBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(idEdtMemberName.getText().toString()) &&
                        TextUtils.isEmpty(idMemberTel.getText().toString()) &&
                        TextUtils.isEmpty(idMemberEmail.getText().toString())) {
                    Toast.makeText(UpdateMember.this, "Please enter your data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                member.setNames(idEdtMemberName.getText().toString());
                member.setTel(idMemberTel.getText().toString());
                member.setEmail(idMemberEmail.getText().toString());
                UpdateData(member);
            }
        });

        idBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData(member);
            }
        });

    }

    private void UpdateData(Member member) {
        String url = GBValues.hostname + "mupdate.php";
        idPBLoading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(UpdateMember.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                idPBLoading.setVisibility(View.GONE);
                idEdtMemberName.setText("");
                idMemberTel.setText("");
                idMemberEmail.setText("");

                Toast.makeText(UpdateMember.this, "Data added to API", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UpdateMember.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", member.getId());
                params.put("names", member.getNames());
                params.put("tel", member.getTel());
                params.put("email", member.getEmail());
                return params;
            }
        };
        queue.add(request);
    }

    private void DeleteData(Member member) {
        String url = GBValues.hostname + "mdel.php";
        idPBLoading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(UpdateMember.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                idPBLoading.setVisibility(View.GONE);
                idEdtMemberName.setText("");
                idMemberTel.setText("");
                idMemberEmail.setText("");

                Toast.makeText(UpdateMember.this, "Data added to API", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UpdateMember.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", member.getId());
                return params;
            }
        };
        queue.add(request);
    }


}