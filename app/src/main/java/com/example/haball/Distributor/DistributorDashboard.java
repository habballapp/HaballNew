package com.example.haball.Distributor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.ui.Fragment_Notification.FragmentNotification;
import com.example.haball.Distributor.ui.home.HomeFragment;
import com.example.haball.Distributor.ui.orders.OrdersTabsLayout.Orders_Dashboard;
import com.example.haball.Distributor.ui.payments.ConsolidatedPaymentsFragment;
import com.example.haball.Distributor.ui.payments.CreatePaymentRequestFragment;
import com.example.haball.Distributor.ui.payments.PaymentRequestDashboard;
import com.example.haball.Distributor.ui.payments.PaymentsSummaryFragment;
import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.Distributor.ui.payments.ProofOfPaymentsDashboardFragment;
import com.example.haball.Distributor.ui.profile.ProfileFragment;
import com.example.haball.Distributor.ui.retailer.RetailerFragment;
import com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrderDashboard;
import com.example.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.example.haball.Distributor.ui.support.SupportFragment;
import com.example.haball.Distributor.ui.terms_and_conditions.TermsAndConditionsFragment;
import com.example.haball.R;

import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.techatmosphere.expandablenavigation.model.ChildModel;
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DistributorDashboard extends AppCompatActivity {

    private TextView tv_username, tv_user_company;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;
    private ExpandableNavigationListView navigationExpandableListView;
    private String username, companyname, Token;
    private ImageButton notification_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        companyname = sharedPreferences.getString("CompanyName", "");
        Token = sharedPreferences.getString("Login_Token", "");

        tv_username = toolbar.findViewById(R.id.tv_username);
        tv_user_company = toolbar.findViewById(R.id.tv_user_company);
        notification_icon = toolbar.findViewById(R.id.notification_icon);

        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new FragmentNotification());
                fragmentTransaction.commit();
            }
        });

        tv_username.setText("Hi, " + username);
        tv_user_company.setText(companyname);

        drawer = findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new HomeFragment());
        fragmentTransaction.commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(Color.parseColor("#33000000"));


        toggle.syncState();
        navigationExpandableListView = findViewById(R.id.expandable_navigation);
        navigationExpandableListView
                .init(this)
                .addHeaderModel(new HeaderModel("Dashboard"))
                .addHeaderModel(new HeaderModel("Orders")
                        .addChildModel(new ChildModel("\tPlace Order")))
                .addHeaderModel(
                        new HeaderModel("Payment")
//                                  .addChildModel(new ChildModel("\tPayments Summary"))
                                .addChildModel(new ChildModel("\tConsolidate Payments"))
                                .addChildModel(new ChildModel("\tMake Payment"))
                                .addChildModel(new ChildModel("\tPayment Ledger"))
                                .addChildModel(new ChildModel("\tProof of Payments"))

                )
                .addHeaderModel(new HeaderModel("Shipment"))
                .addHeaderModel(new HeaderModel("Retailer")
                        .addChildModel(new ChildModel("\tRetailer Management"))
                        .addChildModel(new ChildModel("\t Retailer Order"))
                        .addChildModel(new ChildModel("\tRetailer Payment")))
                .addHeaderModel(new HeaderModel("Profile"))
                .addHeaderModel(new HeaderModel("Support"))
                .addHeaderModel(new HeaderModel("Logout"))
                .addHeaderModel(new HeaderModel("\n\n\n\nTerms And Conditions"))
                .build()
                .addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition);

                        if (id == 0) {
                            Log.i("Dashboard", "Dashboard Activity"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 1) {
                            Log.i("Orders", "Orders Activity");
                        } else if (id == 2) {
                            Log.i("Payments", "Payments Activity");//DONE
                            navigationView.setItemTextColor(ColorStateList.valueOf(Color.RED));
                        } else if (id == 3) {
                            Log.i("Shipment", "Shipment Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new Shipments_Fragments());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 4) {
                            Log.i("Retailer", "Retailer Activity");

                        } else if (id == 5) {
                            Log.i("Profile", "Profile Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new ProfileFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 6) {
                            Log.i("Suppport", "Support Activity"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new SupportFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 7) {
                            Log.i("Logout", "Logout Activity");
                            if (Token != null){
                                SharedPreferences login_token = getSharedPreferences("LoginToken",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = login_token.edit();
                                editor.remove("Login_Token");
                                editor.commit();
                                Intent login = new Intent(DistributorDashboard.this, Distribution_Login.class);
                                startActivity(login);
                                finish();
                            }
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        else if (id == 8) {
                            Log.i("terms and conditions", "terms and conditions");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new TermsAndConditionsFragment());
                            fragmentTransaction.commit();

                            drawer.closeDrawer(GravityCompat.START);
                        }

                        return false;
                    }
                })
                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition, childPosition);

//                        if (groupPosition == 2 && childPosition == ) {
//                            Log.i("Payments Summary", "Child"); //DONE
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.replace(R.id.main_container, new PaymentsSummaryFragment());
//                            fragmentTransaction.commit();
//                            drawer.closeDrawer(GravityCompat.START);
//                        } else
                        if (groupPosition == 2 && childPosition == 0) {
                            Log.i("Consolidate Payments", "Child");//DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new ConsolidatedPaymentsFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 2 && childPosition == 1) {
                            Log.i("Make Payment", "Child");//DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new CreatePaymentRequestFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 2 && childPosition == 2) {
                            Log.i("Payment Ledger", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new Payments_Fragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 2 && childPosition == 3) {
                            Log.i("Proof of Payments", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new ProofOfPaymentsDashboardFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 1 && childPosition == 0) {
                            Log.i("Place order", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new Orders_Dashboard());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        else if (groupPosition == 4 && childPosition ==0)
                        {
                            Toast.makeText(DistributorDashboard.this, "retialer Managment", Toast.LENGTH_SHORT).show();
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new RetailerFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        else  if (groupPosition ==4 && childPosition == 1)
                        {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new RetailerOrderDashboard());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        else if (groupPosition ==4 && childPosition ==2)
                        {
                            Toast.makeText(DistributorDashboard.this, "Retailer Payment", Toast.LENGTH_SHORT).show();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        return false;
                    }
                });
        navigationExpandableListView.expandGroup(1);

    }
}
