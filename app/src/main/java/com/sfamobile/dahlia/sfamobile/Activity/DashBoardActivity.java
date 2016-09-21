package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.DashBoardViewPagerAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.TabPagerAdapter;
import com.sfamobile.dahlia.sfamobile.Fragments.LeadsFragment;
import com.sfamobile.dahlia.sfamobile.Fragments.MeetingsFragment;
import com.sfamobile.dahlia.sfamobile.R;

import az.plainpie.PieView;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    TextView mMyLeadsTV = null;
    TextView mMyMeetingsTV = null;

    View mMyLeadsView = null;
    View mMyMeetingsView = null;

    private static final int TAB_MY_LEADS = 201;
    private static final int TAB_MY_MEETINGS = 202;
    private int TAB_SELECTED = TAB_MY_LEADS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMyLeadsTV = (TextView)findViewById(R.id.my_leads_tv) ;
        mMyLeadsTV.setOnClickListener(this);

        mMyMeetingsTV = (TextView)findViewById(R.id.my_meetings_tv) ;
        mMyMeetingsTV.setOnClickListener(this);

        mMyLeadsView = (View)findViewById(R.id.my_leads_view) ;

        mMyMeetingsView = (View)findViewById(R.id.my_meetings_view) ;


        Fragment fragment = new LeadsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_expense_container, fragment)
                .commit();


//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        //Initializing the tablayout
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//
//        //Adding the tabs using addTab() method
//        tabLayout.addTab(tabLayout.newTab().setText("My Leads"));
//        tabLayout.addTab(tabLayout.newTab().setText("My Meetings"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        //Initializing viewPager
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//        //Creating our pager adapter
//        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//
//        //Adding adapter to pager
//        viewPager.setAdapter(adapter);
//
//        //Adding onTabSelectedListener to swipe views
//        tabLayout.setOnTabSelectedListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Welcome in SFAMobi World.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PieView pieView = (PieView) findViewById(R.id.pieView);

// Change the color fill of the bar representing the current percentage
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.colorAccent));

// Change the color fill of the background of the widget, by default is transparent
        pieView.setMainBackgroundColor(getResources().getColor(R.color.colorPink));

// Change the color of the text of the widget
        pieView.setTextColor(getResources().getColor(R.color.colorWhite));


        pieView.setInnerTextVisibility(View.VISIBLE);

// Change the text of the widget
        pieView.setInnerText("Won\n"+75+"%");

// Change the text size of the widget
        pieView.setPercentageTextSize(25);


        PieView pieView1 = (PieView) findViewById(R.id.pieView2);

// Change the color fill of the bar representing the current percentage
        pieView1.setPercentageBackgroundColor(getResources().getColor(R.color.colorPink));
        pieView1.setMainBackgroundColor(getResources().getColor(R.color.colorAccent));

        pieView1.setTextColor(getResources().getColor(R.color.colorWhite));


        pieView1.setInnerTextVisibility(View.VISIBLE);

// Change the text of the widget
        pieView1.setInnerText("Lost\n"+25+"%");

// Change the text size of the widget
        pieView1.setPercentageTextSize(25);



//        BarChart barChart = (BarChart) findViewById(R.id.chart);
//        barChart.setDrawBarShadow(false);
//        barChart.setDrawGridBackground(false);
//
//
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(4f, 0));
//        entries.add(new BarEntry(8f, 1));
//        entries.add(new BarEntry(6f, 2));
//        entries.add(new BarEntry(12f, 3));
//        entries.add(new BarEntry(18f, 4));
//        entries.add(new BarEntry(9f, 5));
//
//
//        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
//
//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//
//        BarData data = new BarData(labels, dataset);
//        barChart.setData(data); // set the data and list of lables into chart
//
//        barChart.setDescription("Description");  // set the description



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }

        finish();


    }

    private void setupViewPager(ViewPager viewPager) {
        DashBoardViewPagerAdapter adapter = new DashBoardViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LeadsFragment(), "My Leads");
        adapter.addFragment(new MeetingsFragment(), "My Meetings");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daily_report) {
            Intent intent = new Intent(this,DailySalesVisitReportActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_lead) {

            Intent intent = new Intent(this,ManageLeadActivity.class);
            startActivity(intent);
            finish();


        } else if (id == R.id.nav_opportunity) {
            Intent intent = new Intent(this,ManagedOpportunityActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this,ManageCompanyContactActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_product_catalog) {
            Intent intent = new Intent(this,ProductCatalogActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_quotation) {
            Intent intent = new Intent(this,ManagedQuotationActivity.class);
            startActivity(intent);
            finish();

        }else if (id == R.id.nav_meeting) {
            Intent intent = new Intent(this,ShowMeetingActivity.class);
            startActivity(intent);
            finish();

        }else if (id == R.id.nav_expense) {
            Intent intent = new Intent(this,ManagedExpenseActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        switch (v.getId()) {


            case R.id.my_leads_tv:
                if (TAB_SELECTED != TAB_MY_LEADS) {
                    TAB_SELECTED = TAB_MY_LEADS;
                    mMyLeadsView.setVisibility(View.VISIBLE);
                    mMyMeetingsView.setVisibility(View.GONE);


                    fragment = new LeadsFragment();
                } else {
                    return;
                }
                break;

            case R.id.my_meetings_tv:
                if (TAB_SELECTED != TAB_MY_MEETINGS) {
                    TAB_SELECTED = TAB_MY_MEETINGS;


                    mMyLeadsView.setVisibility(View.GONE);
                    mMyMeetingsView.setVisibility(View.VISIBLE);

                    fragment = new MeetingsFragment();
                } else {
                    return;
                }
                break;
            default:
                break;
        }


        if (fragment != null) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_expense_container, fragment).commit();
        }

    }
}
