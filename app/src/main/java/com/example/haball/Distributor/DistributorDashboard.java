package com.example.haball.Distributor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.haball.Distributor.ui.expandablelist.CustomExpandableListModel;
import com.example.haball.Distributor.ui.expandablelist.CustomExpandableListViewAdapter;
import com.example.haball.Distributor.ui.home.HomeFragment;
import com.example.haball.Distributor.ui.main.OrdersFragment;
import com.example.haball.Distributor.ui.main.PaymentsFragment;
import com.example.haball.Distributor.ui.orders.Orders_Fragment;
import com.example.haball.Distributor.ui.payments.ConsolidatedPaymentsFragment;
import com.example.haball.Distributor.ui.payments.PaymentRequestDashboard;
import com.example.haball.Distributor.ui.payments.PaymentsSummaryFragment;
import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.Distributor.ui.payments.ProofOfPaymentsDashboardFragment;
import com.example.haball.Distributor.ui.profile.ProfileFragment;
import com.example.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.example.haball.Distributor.ui.support.SupportFragment;
import com.example.haball.R;

import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.haball.Retailor.ui.Place_Order.PlaceOrderFragment;
import com.google.android.material.navigation.NavigationView;
import com.techatmosphere.expandablenavigation.model.ChildModel;
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DistributorDashboard extends AppCompatActivity {

    private TextView tv_username, tv_user_company;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;
    private ExpandableNavigationListView navigationExpandableListView;
    private String username, companyname;

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

        tv_username = toolbar.findViewById(R.id.tv_username);
        tv_user_company = toolbar.findViewById(R.id.tv_user_company);

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

        toggle.syncState();
        navigationExpandableListView = findViewById(R.id.expandable_navigation);
        navigationExpandableListView
                .init(this)
                .addHeaderModel(new HeaderModel("Dashboard"))
                .addHeaderModel(new HeaderModel("Orders")
                        .addChildModel(new ChildModel("\t\t\tPlace Order")))
                .addHeaderModel(
                        new HeaderModel("Payment")
                                .addChildModel(new ChildModel("\t\t\tPayments Summary"))
                                .addChildModel(new ChildModel("\t\t\tConsolidate Payments"))
                                .addChildModel(new ChildModel("\t\t\tPayment Request"))
                                .addChildModel(new ChildModel("\t\t\tPayment Ledger"))
                                .addChildModel(new ChildModel("\t\t\tProof of Payments"))

                )
                .addHeaderModel(new HeaderModel("Shipment"))
                .addHeaderModel(new HeaderModel("Retailer"))
                .addHeaderModel(new HeaderModel("Profile"))
                .addHeaderModel(new HeaderModel("Support"))
                .addHeaderModel(new HeaderModel("Logout"))
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
                            drawer.closeDrawer(GravityCompat.START);
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
                            drawer.closeDrawer(GravityCompat.START);
                        }

                        return false;
                    }
                })
                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition, childPosition);

                        if (groupPosition == 2 && childPosition == 0) {
                            Log.i("Payments Summary", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new PaymentsSummaryFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 2 && childPosition == 1) {
                            Log.i("Consolidate Payments", "Child");//DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new ConsolidatedPaymentsFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 2 && childPosition == 2) {
                            Log.i("Payment Request", "Child");//DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new PaymentRequestDashboard());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 2 && childPosition == 3) {
                            Log.i("Payment Ledger", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new Payments_Fragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 2 && childPosition == 4) {
                            Log.i("Proof of Payments", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new ProofOfPaymentsDashboardFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (groupPosition == 1 && childPosition == 0) {
                            Log.i("Place order", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new Orders_Fragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        return false;
                    }
                });
        navigationExpandableListView.expandGroup(1);

    }
}
