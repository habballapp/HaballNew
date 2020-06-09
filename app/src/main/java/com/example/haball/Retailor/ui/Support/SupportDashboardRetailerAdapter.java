package com.example.haball.Retailor.ui.Support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.StatusKVP;
import com.example.haball.Distributor.ui.support.DeleteSupport;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Support.SupportDashboardRetailerModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SupportDashboardRetailerAdapter extends RecyclerView.Adapter<SupportDashboardRetailerAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    Context mContxt;
    Context activity;
    String dashboard, id, pending, createdDate;
    List<SupportDashboardRetailerModel> supportList;
    private String URL_SUPPORT_VIEW = "http://175.107.203.97:4014/api/support/TicketById/";

    public SupportDashboardRetailerAdapter(Activity activity, Context applicationContext, String dashboard, String id, String pending, String createdDate) {
//        this.mContxt = applicationContext;
//        this.activity = activity;
//        this.dashboard = dashboard;
//        this.id = id;
//        this.pending = pending;
//        this.createdDate = createdDate;
    }

    public SupportDashboardRetailerAdapter(Context context, List<SupportDashboardRetailerModel> supportList) {
        this.mContxt = context;
        this.supportList = supportList;
        this.recyclerView = recyclerView;
    }

    @Override
    public SupportDashboardRetailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_support_rv, parent, false);
        return new SupportDashboardRetailerAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final SupportDashboardRetailerAdapter.ViewHolder holder, final int position) {
        holder.heading.setText(supportList.get(position).getIssueType());
        holder.ticket_id_value.setText(supportList.get(position).getTicketNumber());
        holder.status_value.setText(supportList.get(position).getStatus());
        holder.created_date_value.setText(supportList.get(position).getCreatedDate().split("T")[0]);

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContxt, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_items, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_view:
                                supportView(position);
                                break;
//                            case R.id.menu_edit:
//                                //handle menu2 click
//                                Toast.makeText(mContxt,"Edit Clicked",Toast.LENGTH_LONG).show();
//
//                                break;
                            case R.id.menu_delete:
                                //handle menu3 click
//                                Toast.makeText(mContxt,"Delete Clicked",Toast.LENGTH_LONG).show();
                                final AlertDialog deleteAlert = new AlertDialog.Builder(mContxt).create();
                                LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
                                View delete_alert = delete_inflater.inflate(R.layout.delete_alert, null);
                                deleteAlert.setView(delete_alert);
                                Button btn_delete = (Button) delete_alert.findViewById(R.id.btn_delete);
                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        try {
                                            deleteAlert.dismiss();
                                            DeleteSupportTicket(supportList.get(position).getID());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

//                                        final AlertDialog delete_successAlert = new AlertDialog.Builder(mContxt).create();
//                                        LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
//                                        View delete_success_alert = delete_inflater.inflate(R.layout.delete_success, null);
//                                        delete_successAlert.setView(delete_success_alert);
//
//                                        ImageButton img_delete = (ImageButton) delete_success_alert.findViewById(R.id.btn_close_success);
//                                        img_delete.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                delete_successAlert.dismiss();
//                                            }
//                                        });
//                                        delete_successAlert.show();
                                    }
                                });
                                ImageButton img_delete_alert = (ImageButton) delete_alert.findViewById(R.id.btn_close);
                                img_delete_alert.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        deleteAlert.dismiss();
                                    }
                                });
                                deleteAlert.show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    private void supportView(final int position) {
        SharedPreferences SupportId = ((FragmentActivity)mContxt).getSharedPreferences("SupportId",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SupportId.edit();
        editor.putString("SupportId", supportList.get(position).getID());
        editor.commit();

        FragmentTransaction fragmentTransaction= ((FragmentActivity)mContxt).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container_ret,new Retailer_Support_Ticket_View());
        fragmentTransaction.commit();

//        SharedPreferences sharedPreferences = mContxt.getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        final String Token = sharedPreferences.getString("Login_Token", "");
//        Log.i("Token  ", Token);
//        StatusKVP statusKVP = new StatusKVP(mContxt, Token);
//        final HashMap<String, String> RetailerIssueTypePrivateKVP = statusKVP.getRetailerIssueTypePrivateKVP();
//        final HashMap<String, String> RetailerCriticalityPrivateKVP = statusKVP.getRetailerCriticalityPrivateKVP();
//        final HashMap<String, String> RetailerContactingMethodKVP = statusKVP.getRetailerContactingMethodKVP();
//
//        if (!URL_SUPPORT_VIEW.contains("/" + supportList.get(position).getID()))
//            URL_SUPPORT_VIEW = URL_SUPPORT_VIEW + supportList.get(position).getID();
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_SUPPORT_VIEW, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                TextView tv_username, et_email, et_phone, et_issue_type, et_criticality, et_preffered_contact, et_status, et_comments;
////                                        Toast.makeText(mContxt, "View Clicked", Toast.LENGTH_LONG).show();
//                final AlertDialog alertDialog = new AlertDialog.Builder(mContxt).create();
//                LayoutInflater inflater = LayoutInflater.from(mContxt);
//                View view_popup = inflater.inflate(R.layout.view_popup, null);
//                alertDialog.setView(view_popup);
//
//                tv_username = view_popup.findViewById(R.id.tv_username);
//                et_email = view_popup.findViewById(R.id.et_email);
//                et_phone = view_popup.findViewById(R.id.et_phone);
//                et_issue_type = view_popup.findViewById(R.id.et_issue_type);
//                et_criticality = view_popup.findViewById(R.id.et_criticality);
//                et_preffered_contact = view_popup.findViewById(R.id.et_preffered_contact);
//                et_status = view_popup.findViewById(R.id.et_status);
//                et_comments = view_popup.findViewById(R.id.et_comments);
//                String issue_type = "", criticality = "", preffered_contact = "";
//
//                try {
//                    for (Map.Entry<String, String> entry : RetailerIssueTypePrivateKVP.entrySet()) {
//                        if(entry.getKey().equals(String.valueOf(response.get("IssueType"))))
//                            issue_type = entry.getValue();
//                    }
//                    for (Map.Entry<String, String> entry : RetailerCriticalityPrivateKVP.entrySet()) {
//                        if(entry.getKey().equals(String.valueOf(response.get("Criticality"))))
//                            criticality = entry.getValue();
//                    }
//                    for (Map.Entry<String, String> entry : RetailerContactingMethodKVP.entrySet()) {
//                        if(entry.getKey().equals(String.valueOf(response.get("PreferredContactMethod"))))
//                            preffered_contact = entry.getValue();
//                    }
//
//                    tv_username.setText(String.valueOf(response.get("ContactName")));
//                    et_email.setText("Email Address: " + String.valueOf(response.get("Email")));
//                    et_phone.setText("Phone: " + String.valueOf(response.get("MobileNumber")));
//                    et_issue_type.setText("Issue Type: " + issue_type);
//                    et_criticality.setText("Criticality: " + criticality);
//                    et_preffered_contact.setText("Preferred Contact Method: " + preffered_contact);
//                    et_status.setText("Status: " + supportList.get(position).getStatus());
//                    et_comments.setText("Message: " + String.valueOf(response.get("Description")));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
//                img_email.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                printErrorMessage(error);
//
//                error.printStackTrace();
//                Log.i("onErrorResponse", "Error");
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("Content-Type", "application/json");
//
//                return params;
//            }
//        };
//        Volley.newRequestQueue(mContxt).add(request);
    }

    private void DeleteSupportTicket(String ID) throws JSONException {
        DeleteSupportTicket deleteSupport = new DeleteSupportTicket();
        String response = deleteSupport.DeleteSupportTicket(mContxt, ID);

    }

    @Override
    public int getItemCount() {
        return supportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView heading, ticket_id_value, status_value, created_date_value;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            ticket_id_value = itemView.findViewById(R.id.ticket_id_value);
            status_value = itemView.findViewById(R.id.status_value);
            created_date_value = itemView.findViewById(R.id.created_date_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }


    private void printErrorMessage(VolleyError error) {
        if (mContxt != null) {
            if (error instanceof NetworkError) {
                Toast.makeText(mContxt, "Network Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(mContxt, "Server Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(mContxt, "Auth Failure Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(mContxt, "Parse Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(mContxt, "No Connection Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(mContxt, "Timeout Error !", Toast.LENGTH_LONG).show();
            }

            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = "";
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.i("responseBody", responseBody);
                    JSONObject data = new JSONObject(responseBody);
                    Log.i("data", String.valueOf(data));
                    Iterator<String> keys = data.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        message = message + data.get(key) + "\n";
                    }
                    Toast.makeText(mContxt, message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
