package com.example.haball.Retailor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

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
    private TextView tv_username, tv_user_company, footer_item_1;
//    private TextView tv_username, tv_user_company;
    boolean doubleBackToExitPressedOnce = false;

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
        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
        fragmentTransaction.commit();

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
                drawer.closeDrawer(GravityCompat.START);

            }
        });
        navigationExpandableListView
                .init(this)
                .addHeaderModel(new HeaderModel("Dashboard"))
                .addHeaderModel(new HeaderModel("My Network"))
                .addHeaderModel(new HeaderModel("Place Order"))
                .addHeaderModel(new HeaderModel("Make Payment"))
                .addHeaderModel(new HeaderModel("Profile"))
                .addHeaderModel(new HeaderModel("Support"))
                .addHeaderModel(new HeaderModel("Logout"))
//                .addHeaderModel(new HeaderModel("\n\n\n\nTerms And Conditions"))
                .build()
                .addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition);

                        if (id == 0) {
                            Log.i("Dashboard", "Dashboard Activity");

                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Dashboard_Tabs());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 1) {


                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container_ret, new My_NetworkDashboard());
                            fragmentTransaction.replace(R.id.main_container_ret, new My_Network_Fragment()).addToBackStack("tag");;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                            Log.i("My Network", "My Network Activity");
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 2) {
//
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("tag");;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        } else if (id == 3) {
                            Log.i("Make Payment", "Make Payment Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new CreatePaymentRequestFragment()).addToBackStack("tag1");;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        } else if (id == 4) {
                            Log.i("Profile", "Profile Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Profile_Tabs()).addToBackStack("tag");;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 5) {
                            Log.i("Support", "Support Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new SupportFragment()).addToBackStack("tag");;
                            fragmentTransaction.commit();
                            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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
                })
                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition, childPosition);

                        if (groupPosition == 3 && childPosition == 0) {
                            Log.i("Payments Summary", "Child");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new Payment_Summary()).addToBackStack("tag");;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);

                        }  else if (groupPosition == 3 && childPosition == 1) {
                            Log.i("Payment Request", "Child");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new CreatePaymentRequestFragment()).addToBackStack(null);;
                            fragmentTransaction.commit();
                        }  else if (groupPosition == 2 && childPosition == 0) {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container_ret, new PlaceOrderFragment()).addToBackStack("tag");;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        return false;
                    }
                });


    }
    @Override
    public void onBackPressed() {

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            super.onBackPressed();
        if(drawer.isDrawerOpen(Gravity.LEFT)){
            drawer.closeDrawer(Gravity.LEFT);
        }else{
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
}
