package com.example.haball.Retailor;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haball.Distributor.ui.expandablelist.CustomExpandableListModel;
import com.example.haball.Retailor.ui.Make_Payment.CreatePaymentRequestFragment;
import com.example.haball.Retailor.ui.Network.My_NetworkDashboard;
import com.example.haball.R;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;
import com.example.haball.Retailor.ui.Make_Payment.Payment_Summary;
import com.example.haball.Retailor.ui.Notification.Notification_Fragment;
import com.example.haball.Retailor.ui.Place_Order.PlaceOrderFragment;
import com.example.haball.Retailor.ui.Place_Order.Retailer_Place_Order;
import com.example.haball.Retailor.ui.Profile.ProfileFragment;
import com.example.haball.Retailor.ui.Support.SupportFragment;
import com.techatmosphere.expandablenavigation.model.ChildModel;
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetailorDashboard extends AppCompatActivity  {

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
    private TextView tv_username, tv_user_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailor_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_retailor);
        notification_icon = (ImageView)toolbar.findViewById(R.id.notification_icon_retailer);
        tv_username = toolbar.findViewById(R.id.tv_username);
        tv_user_company = toolbar.findViewById(R.id.tv_user_company);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container_ret, new DashBoardFragment());
        fragmentTransaction.commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(Color.parseColor("#33000000"));

        toggle.syncState();
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        drawer.setDrawerListener(toggle);
        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        companyname = sharedPreferences.getString("CompanyName", "");
        Token = sharedPreferences.getString("Login_Token", "");

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
        navigationExpandableListView
                .init(this)
                .addHeaderModel(new HeaderModel("Dashboard"))
                .addHeaderModel(new HeaderModel("My Network"))
                .addHeaderModel(new HeaderModel("Place Order"))
                .addHeaderModel(new HeaderModel("Make Payment"))
                .addHeaderModel(new HeaderModel("Profile"))
                .addHeaderModel(new HeaderModel("Support"))
                .addHeaderModel(new HeaderModel("Logout"))
                .build()
                .addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition);

                        if (id == 0) {
                            Log.i("Dashboard", "Dashboard Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new DashBoardFragment());
                            fragmentTransaction.commit();

                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 1) {

                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new My_NetworkDashboard());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                            Log.i("My Network", "My Network Activity");

                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 2) {
//                            Log.i("Place Order", "Place Order Activity");
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container_ret, new PlaceOrderFragment());
//                            fragmentTransaction.commit();
//                            drawer.closeDrawer(GravityCompat.START);
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Retailer_Place_Order());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        } else if (id == 3) {
                            Log.i("Make Payment", "Make Payment Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new CreatePaymentRequestFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        } else if (id == 4) {
                            Log.i("Profile", "Profile Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new ProfileFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 5) {
                            Log.i("Support", "Support Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new SupportFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        } else if (id == 6) {
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
                            Intent dashboard = new Intent(RetailorDashboard.this, RetailerLogin.class);
                            startActivity(dashboard);
                            drawer.closeDrawer(GravityCompat.START);
                            finish();
                        }

                        return false;
                    }
                })
                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition, childPosition);
                        if (groupPosition == 3 && childPosition == 0) {
                            Log.i("Payments Summary", "Child");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Payment_Summary());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }  else if (groupPosition == 3 && childPosition == 1) {
                            Log.i("Payment Request", "Child");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new CreatePaymentRequestFragment());
                            fragmentTransaction.commit();
                        }  else if (groupPosition == 2 && childPosition == 0) {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new PlaceOrderFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        return false;
                    }
                });


    }

}
