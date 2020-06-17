package com.example.haball.Retailor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.DistributorDashboard;
import com.example.haball.Distributor.ui.expandablelist.CustomExpandableListModel;
import com.example.haball.Distributor.ui.terms_and_conditions.TermsAndConditionsFragment;
import com.example.haball.R;
import com.example.haball.Retailor.ui.Dashboard.Dashboard_Tabs;
import com.example.haball.Retailor.ui.Make_Payment.CreatePaymentRequestFragment;
import com.example.haball.Retailor.ui.Make_Payment.PaymentScreen3Fragment_Retailer;
import com.example.haball.Retailor.ui.Make_Payment.Payment_Summary;
import com.example.haball.Retailor.ui.Network.My_NetworkDashboard;
import com.example.haball.Retailor.ui.Network.Select_Tabs.My_Network_Fragment;
import com.example.haball.Retailor.ui.Notification.Notification_Fragment;
import com.example.haball.Retailor.ui.Place_Order.PlaceOrderFragment;
import com.example.haball.Retailor.ui.Place_Order.Retailer_Place_Order;
import com.example.haball.Retailor.ui.Profile.Profile_Tabs;
import com.example.haball.Retailor.ui.Support.SupportFragment;
import com.example.haball.Select_User.Register_Activity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RetailorDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FragmentTransaction fragmentTransaction;
    private ImageView notification_icon;
    private DrawerLayout drawer;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<CustomExpandableListModel> headerList = new ArrayList<>();
    HashMap<CustomExpandableListModel, List<CustomExpandableListModel>> childList = new HashMap<>();
    private ExpandableNavigationListView navigationExpandableListView;
    private String username, companyname, Token;
    private TextView tv_username, tv_user_company, footer_item_1;
    //    private TextView tv_username, tv_user_company;
    boolean doubleBackToExitPressedOnce = false;
    private Socket iSocket;
    private static final String URL = "http://175.107.203.97:4014";
    private String UserId;
    private JSONArray userRights;
    private List<String> NavList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailor_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_retailor);
        notification_icon = (ImageView) toolbar.findViewById(R.id.notification_icon_retailer);
        tv_username = toolbar.findViewById(R.id.tv_username);
        tv_user_company = toolbar.findViewById(R.id.tv_user_company);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
//        drawer.setScrimColor(Color.parseColor("#33000000"));
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));

        toggle.syncState();
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        drawer.setDrawerListener(toggle);
        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        companyname = sharedPreferences.getString("CompanyName", "");
        Token = sharedPreferences.getString("Login_Token", "");
        UserId = sharedPreferences.getString("UserId", "");
        try {
            userRights = new JSONArray(sharedPreferences.getString("UserRights", ""));
            Log.i("userRights", String.valueOf(userRights));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        boolean UserAlert = false;
        boolean Distributor_Preferences = false;
        boolean Retailer_Profile = false;
        boolean Order_Add_Update = false;
        boolean Order_Export = false;
        boolean Order_View = false;
        boolean Kyc_add_update = false;
        boolean User_Change_Password = false;
        boolean Payment_Add_Update = false;
        boolean Payment_View = false;

//
        UserAlert = true;
        Distributor_Preferences = true;
        Retailer_Profile = true;
        Order_Add_Update = true;
        Order_Export = true;
        Order_View = true;
        Kyc_add_update = true;
        User_Change_Password = true;
        Payment_Add_Update = true;
        Payment_View = true;
//
//
//        for (int i = 0; i < userRights.length(); i++) {
//            try {
//                JSONObject userRightsData = new JSONObject(String.valueOf(userRights.get(i)));
//                if (userRightsData.get("Title").equals("Distributor Preferences")) {
//                    Distributor_Preferences = true;
//                }
//                if (userRightsData.get("Title").equals("UserAlert")) {
//                    UserAlert = true;
//                }
//                if (userRightsData.get("Title").equals("Kyc add/update")) {
//                    Kyc_add_update = true;
//                }
//                if (userRightsData.get("Title").equals("Order Add/Update")) {
//                    Order_Add_Update = true;
//                }
//                if (userRightsData.get("Title").equals("Payment Add/Update")) {
//                    Payment_Add_Update = true;
//                }
//                if (userRightsData.get("Title").equals("Retailer Profile")) {
//                    Retailer_Profile = true;
//                }
//                if (userRightsData.get("Title").equals("User Change Password")) {
//                    User_Change_Password = true;
//                }
//                if (userRightsData.get("Title").equals("Payment View")) {
//                    Payment_View = true;
//                }
//                if (userRightsData.get("Title").equals("Order Export")) {
//                    Order_Export = true;
//                }
//                if (userRightsData.get("Title").equals("Order View")) {
//                    Order_View = true;
//                }
////                Log.i("userRightsData", String.valueOf(userRights.get(i)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        SharedPreferences retailerInfo = getSharedPreferences("Retailer_UserRights",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
        retailerInfo_editor.putString("UserAlert", String.valueOf(UserAlert));
        retailerInfo_editor.putString("Distributor_Preferences", String.valueOf(Distributor_Preferences));
        retailerInfo_editor.putString("Retailer_Profile", String.valueOf(Retailer_Profile));
        retailerInfo_editor.putString("Order_Add_Update", String.valueOf(Order_Add_Update));
        retailerInfo_editor.putString("Order_Export", String.valueOf(Order_Export));
        retailerInfo_editor.putString("Order_View", String.valueOf(Order_View));
        retailerInfo_editor.putString("Kyc_add_update", String.valueOf(Kyc_add_update));
        retailerInfo_editor.putString("User_Change_Password", String.valueOf(User_Change_Password));
        retailerInfo_editor.putString("Payment_Add_Update", String.valueOf(Payment_Add_Update));
        retailerInfo_editor.putString("Payment_View", String.valueOf(Payment_View));
        retailerInfo_editor.apply();

        if (!UserAlert)
            notification_icon.setVisibility(View.GONE);

        if (Payment_View || Order_View) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
            fragmentTransaction.commit();
            NavList.add("Dashboard");
        } else {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_container_ret, new SupportFragment());
            fragmentTransaction.commit();
        }
        if (Kyc_add_update)
            NavList.add("My Network");
        if (Order_Add_Update)
            NavList.add("Place Order");
        if (Payment_Add_Update)
            NavList.add("Make Payment");
        if (Retailer_Profile)
            NavList.add("Profile");
        NavList.add("Support");
        NavList.add("Logout");

        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container_ret, new Notification_Fragment());
                fragmentTransaction.commit();
            }
        });

        tv_username.setText("Hi, " + username);
        tv_user_company.setText(companyname);

        navigationExpandableListView = findViewById(R.id.expandable_navigation);
        footer_item_1 = findViewById(R.id.footer_item_1);
        footer_item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.main_container, new TermsAndConditionsFragment());
//                fragmentTransaction.commit();

                drawer.closeDrawer(GravityCompat.START);

            }
        });
        navigationExpandableListView.init(this);
        if (Payment_View || Order_View)
            navigationExpandableListView.addHeaderModel(new HeaderModel("Dashboard"));
        if (Kyc_add_update)
            navigationExpandableListView.addHeaderModel(new HeaderModel("My Network"));
        if (Order_Add_Update)
            navigationExpandableListView.addHeaderModel(new HeaderModel("Place Order"));
        if (Payment_Add_Update)
            navigationExpandableListView.addHeaderModel(new HeaderModel("Make Payment"));
        if (Retailer_Profile)
            navigationExpandableListView.addHeaderModel(new HeaderModel("Profile"));
        navigationExpandableListView.addHeaderModel(new HeaderModel("Support"));
        navigationExpandableListView.addHeaderModel(new HeaderModel("Logout"));
//                .addHeaderModel(new HeaderModel("\n\n\n\nTerms And Conditions"))
        navigationExpandableListView.build()
                .addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition);

                        if (NavList.contains("Dashboard") && NavList.indexOf("Dashboard") == id) {
//                        if (id == 0) {
                            Log.i("Dashboard", "Dashboard Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Dashboard_Tabs());
                            fragmentTransaction.commit();

                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains("My Network") && NavList.indexOf("My Network") == id) {
//                        } else if (id == 1) {

                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container_ret, new My_NetworkDashboard());
                            fragmentTransaction.replace(R.id.main_container_ret, new My_Network_Fragment()).addToBackStack("tag");
                            ;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                            Log.i("My Network", "My Network Activity");

                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains("Place Order") && NavList.indexOf("Place Order") == id) {
//                        } else if (id == 2) {
//                            Log.i("Place Order", "Place Order Activity");
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container_ret, new PlaceOrderFragment());
//                            fragmentTransaction.commit();
//                            drawer.closeDrawer(GravityCompat.START);
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        } else if (NavList.contains("Make Payment") && NavList.indexOf("Make Payment") == id) {
//                        } else if (id == 3) {
                            Log.i("Make Payment", "Make Payment Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new CreatePaymentRequestFragment()).addToBackStack("tag1");
                            ;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        } else if (NavList.contains("Profile") && NavList.indexOf("Profile") == id) {
//                        } else if (id == 4) {
                            Log.i("Profile", "Profile Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Profile_Tabs()).addToBackStack("tag");
                            ;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains("Support") && NavList.indexOf("Support") == id) {
//                        } else if (id == 5) {
                            Log.i("Support", "Support Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new SupportFragment()).addToBackStack("tag");
                            ;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains("Logout") && NavList.indexOf("Logout") == id) {

//                        } else if (id == 6) {
                            Log.i("Logout", "Logout Activity");
                            SharedPreferences login_token = getSharedPreferences("LoginToken",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = login_token.edit();
                            editor.remove("Login_Token");
                            editor.remove("User_Type");
                            editor.remove("Retailer_Id");
                            editor.remove("username");
                            editor.remove("CompanyName");
                            editor.remove("UserId");
                            editor.commit();
//                            Intent dashboard = new Intent(RetailorDashboard.this, RetailerLogin.class);
//                            startActivity(dashboard);
                            Intent intent = new Intent(RetailorDashboard.this, Register_Activity.class);
                            startActivity(intent);
//                            finish();
                            drawer.closeDrawer(GravityCompat.START);
                            finish();
                        }

                        return false;
                    }
                });
//        navigationExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                navigationExpandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.group_indicator));
//            }
//        });
//        navigationExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                navigationExpandableListView.setGroupIndicator(null);
//            }
//        });

//                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                    @Override
//                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                        navigationExpandableListView.setSelected(groupPosition, childPosition);
//                        if (groupPosition == 3 && childPosition == 0) {
//                            Log.i("Payments Summary", "Child");
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container_ret, new Payment_Summary()).addToBackStack("tag");
//                            ;
//                            fragmentTransaction.commit();
//                            drawer.closeDrawer(GravityCompat.START);
//                        } else if (groupPosition == 3 && childPosition == 1) {
//                            Log.i("Payment Request", "Child");
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container_ret, new CreatePaymentRequestFragment()).addToBackStack(null);
//                            ;
//                            fragmentTransaction.commit();
//                        } else if (groupPosition == 2 && childPosition == 0) {
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container_ret, new PlaceOrderFragment()).addToBackStack("tag");
//                            ;
//                            fragmentTransaction.commit();
//                            drawer.closeDrawer(GravityCompat.START);
//                        }
//                        drawer.closeDrawer(GravityCompat.START);
//                        return false;
//                    }
//                });


    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            super.onBackPressed();
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() == 0) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    finishAffinity();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 1500);
            } else {
//            super.onBackPressed();
                fm.popBackStack();
            }

        }
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2500);
                getNotificationCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new MyAsyncTask().execute();
        }


        private void getNotificationCount() {
            try {
                IO.Options opts = new IO.Options();
//            opts.query = "userId=" + UserId;
                iSocket = IO.socket(URL, opts);
                iSocket.connect();

                iSocket.emit("userId", UserId);

                if (iSocket.connected()) {
                    iSocket.emit("userId", UserId);
                    iSocket.on("userId" + UserId, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject) args[0];
//                    Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();
//                    Log.i("notificationTest", String.valueOf(data.getJSONArray("data"));
                            try {
                                if (Integer.parseInt(data.getString("count")) == 0) {
                                    notification_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_notifications_black_24dp));
                                } else {
                                    notification_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
                                }
                                Log.i("notificationTest12", String.valueOf(data.getString("count")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
