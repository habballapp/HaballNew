package com.example.haball.Retailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.DistributorDashboard;
import com.example.haball.Distributor.ui.expandablelist.CustomExpandableListModel;
import com.example.haball.Distributor.ui.home.HomeFragment;
import com.example.haball.Distributor.ui.orders.Orders_Fragment;
import com.example.haball.Distributor.ui.payments.Payments_Fragment;
import com.example.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.example.haball.Distributor.ui.support.SupportFragment;
import com.example.haball.Payment.PaymentLedger;
import com.example.haball.Payment.Proof_Of_Payment_Form;
import com.example.haball.R;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Retailor.ui.Dashboard.DashBoardFragment;
import com.example.haball.Retailor.ui.Make_Payment.MakePaymentFragment;
import com.example.haball.Retailor.ui.Make_Payment.Payment_Summary;
import com.example.haball.Retailor.ui.Place_Order.PlaceOrderFragment;
import com.example.haball.Retailor.ui.Profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.techatmosphere.expandablenavigation.model.ChildModel;
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetailorDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private TextView tv_username;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<CustomExpandableListModel> headerList = new ArrayList<>();
    HashMap<CustomExpandableListModel, List<CustomExpandableListModel>> childList = new HashMap<>();
    private ExpandableNavigationListView navigationExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setContentView(R.layout.activity_retailor_dashboard);


        drawer = findViewById(R.id.drawer_layout_retailor);

        final NavigationView navigationView = findViewById(R.id.nav_view_retailor);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container_ret, new DashBoardFragment());
        fragmentTransaction.commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationExpandableListView = findViewById(R.id.expandable_navigation);
        navigationExpandableListView
                .init(this)
                .addHeaderModel(new HeaderModel("Dashboard"))
                .addHeaderModel(new HeaderModel("My Network"))
                .addHeaderModel(new HeaderModel("Place Order")
                        .addChildModel(new ChildModel("Place Order")))
                .addHeaderModel(
                        new HeaderModel("Make Payment")
                                .addChildModel(new ChildModel("Payments Summary"))
                                .addChildModel(new ChildModel("Payment Request"))
                                .addChildModel(new ChildModel("Payment Ledger"))

                )
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
                            Log.i("My Network", "My Network Activity");
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 2) {
                            Log.i("Place Order", "Place Order Activity");

                        } else if (id == 3) {
                            Log.i("Make Payment", "Make Payment Activity");

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
                            Intent dashboard = new Intent(RetailorDashboard.this, RetailerLogin.class);
                            startActivity(dashboard);
                            drawer.closeDrawer(GravityCompat.START);
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
                            fragmentTransaction.replace(R.id.main_container_ret, new Payments_Fragment());
                            fragmentTransaction.commit();
                        } else if (groupPosition == 3 && childPosition == 2) {
                            Log.i("Payment Ledger", "Child");
                            Intent payment_ledger = new Intent(RetailorDashboard.this, PaymentLedger.class);
                            startActivity(payment_ledger);
                        } else if (groupPosition == 3 && childPosition == 4) {
                            Log.i("Proof of Payments", "Child");
                            Intent proof_p = new Intent(RetailorDashboard.this, Proof_Of_Payment_Form.class);
                            startActivity(proof_p);
                        } else if (groupPosition == 2 && childPosition == 0) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.retailor_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_ret);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int position = menuItem.getItemId();

        drawer = findViewById(R.id.drawer_layout_retailor);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}