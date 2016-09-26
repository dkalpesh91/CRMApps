package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.SpinnerAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.TransactionCategoryAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleContactListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.FetchBalByCategoryBalance;
import com.sfamobile.dahlia.sfamobile.R;
import com.sfamobile.dahlia.sfamobile.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllExpenseManagementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<FetchBalByCategoryBalance> mWalleBalByCategoryList = null;
    double mTotalAmount = 10000.00;

    ListView mExpenseLV = null;

    private TextView mStartDateTV = null;
    private TextView mEndDateTV = null;
    private TextView mSpentTV = null;
    private TextView mAmountShrinkedTV = null;
    private RelativeLayout rlExpenseDetailsContainer = null;
    private RelativeLayout rlExpenseDetailsContainerShrinked = null;
    private ImageView mFilterIMV = null;
    private int pYear;

    private int pMonth;private int pDay;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;

    ImageView mShareExpenseIMV = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expense_management);
        initializeViews();
        getData();
        TransactionCategoryAdapter transactionCategoryAdapter = new TransactionCategoryAdapter(this,mWalleBalByCategoryList,mTotalAmount);
        mExpenseLV = (ListView) findViewById(R.id.lv_transaction);
        mExpenseLV.setAdapter(transactionCategoryAdapter);

    }


    private void initializeViews() {

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Expense Details");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllExpenseManagementActivity.this,ManagedExpenseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mStartDateTV = (TextView) findViewById(R.id.txt_start_date);
        mEndDateTV = (TextView) findViewById(R.id.txt_end_date);
        mAmountShrinkedTV = (TextView) findViewById(R.id.txt_amount1);
        TextView txtCategoryPercent = (TextView) findViewById(R.id.txt_category_percent);
        TextView txtTotalExpense = (TextView) findViewById(R.id.txt_total_expense);
        TextView txtExpenseCategory = (TextView) findViewById(R.id.txt_expense_category);
        ProgressBar mgcProgress = (ProgressBar) findViewById(R.id.mgc_progress);
        ImageView imgExpenseCategoryIcon = (ImageView) findViewById(R.id.img_expense_category_icon);
        rlExpenseDetailsContainer = (RelativeLayout) findViewById(R.id.rl_expense_details_container);
        rlExpenseDetailsContainerShrinked = (RelativeLayout) findViewById(R.id.rl_expense_details_container_shrinked);


        mFilterIMV = (ImageView) findViewById(R.id.rdb_filter);
        mFilterIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(AllExpenseManagementActivity.this);
                // Include dialog.xml file
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.dialog_expense_filter);
                ImageView cancleIMV = (ImageView) dialog.findViewById(R.id.cancle_img);
                Button startDateButton = (Button) dialog.findViewById(R.id.dsvrc_select_date_button);

                Button submitButton = (Button) dialog.findViewById(R.id.def_save_btn);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                final AutoCompleteTextView startDateATV = (AutoCompleteTextView) dialog.findViewById(R.id.dsvrc_select_date_atv);
                final AutoCompleteTextView endDateATV = (AutoCompleteTextView) dialog.findViewById(R.id.dsvrc_select_date_to_atv);

                startDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cal = Calendar.getInstance();
                        pYear = cal.get(Calendar.YEAR);
                        pMonth = cal.get(Calendar.MONTH);
                        pDay = cal.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog mDatePicker = new DatePickerDialog(AllExpenseManagementActivity.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                startDateATV.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                            }
                        }, pYear, pMonth, pDay);
                        mDatePicker.setTitle("Select Date");
                        mDatePicker.show();
                    }
                });
                Button endDateButton = (Button) dialog.findViewById(R.id.dsvrc_select_date_to_button);
                endDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cal = Calendar.getInstance();
                        pYear = cal.get(Calendar.YEAR);
                        pMonth = cal.get(Calendar.MONTH);
                        pDay = cal.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog mDatePicker = new DatePickerDialog(AllExpenseManagementActivity.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                endDateATV.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                            }
                        }, pYear, pMonth, pDay);
                        mDatePicker.setTitle("Select Date");
                        mDatePicker.show();
                    }
                });

                final RadioButton byDateRdBtn = (RadioButton) dialog.findViewById(R.id.by_date_rd_btn);

                final RadioButton byCategoryRdBtn = (RadioButton) dialog.findViewById(R.id.by_category_rd_btn);

                byDateRdBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        byCategoryRdBtn.setChecked(false);
                    }
                });

                byCategoryRdBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        byDateRdBtn.setChecked(false);
                    }
                });



                Spinner spinner = (Spinner) dialog.findViewById(R.id.def_status_spinner);
                String[] items = {"Travel","Lodging","Boarding","Miscellaneous"};
                SpinnerAdapter adapter=new SpinnerAdapter(AllExpenseManagementActivity.this,
                        R.layout.spinner_status_item,R.id.spinner_value_tv,items);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(AllExpenseManagementActivity.this);
                cancleIMV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


        mShareExpenseIMV = (ImageView) findViewById(R.id.rdb_share);
        mShareExpenseIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Intent> targets = new ArrayList<>();
                Intent template = new Intent(Intent.ACTION_SEND);
                template.setType("text/plain");
                List<ResolveInfo> candidates = AllExpenseManagementActivity.this.getPackageManager().
                        queryIntentActivities(template, 0);
                // remove facebook which has a broken share intent ;)
                for (ResolveInfo candidate : candidates) {
                    String packageName = candidate.activityInfo.packageName;
                    if (!packageName.equals("com.facebook.katana")) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String body= "<p> <b> Hi!<br/><font size=20>How are you </font> <br/>I am fine</b> </p>";
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SFAMobi Expenses");
                       // "Hi,"+"\n"+"Good Morning, Find your daily expenses below"+
                        sharingIntent.setPackage(packageName);
                        targets.add(sharingIntent);
                    }
                }
                Intent chooser = Intent.createChooser(targets.remove(0), "Share Via");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(new Parcelable[targets.size()]));
                startActivity(chooser);

            }
        });


        txtExpenseCategory.setText("Shop");
        mgcProgress.setProgress(70);
        txtCategoryPercent.setText("70" + "%");



        txtTotalExpense.setText(Utils.getIndianCurrencyFormattedWithoutDecimal(
                String.valueOf("10000"), true));


    }



    public String convertToHtml(String htmlString) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<![CDATA[");
        stringBuilder.append(htmlString);
        stringBuilder.append("]]>");
        return stringBuilder.toString();
    }

    private void getData(){

        mWalleBalByCategoryList = new ArrayList<FetchBalByCategoryBalance>();

        FetchBalByCategoryBalance fetchBalByCategoryBalance1 = new FetchBalByCategoryBalance();
        fetchBalByCategoryBalance1.setBalance("3000");
        fetchBalByCategoryBalance1.setCategory("Travel");
        mWalleBalByCategoryList.add(fetchBalByCategoryBalance1);

        FetchBalByCategoryBalance fetchBalByCategoryBalance2 = new FetchBalByCategoryBalance();
        fetchBalByCategoryBalance2.setBalance("4000");
        fetchBalByCategoryBalance2.setCategory("Lodging");
        mWalleBalByCategoryList.add(fetchBalByCategoryBalance2);

        FetchBalByCategoryBalance fetchBalByCategoryBalance3 = new FetchBalByCategoryBalance();
        fetchBalByCategoryBalance3.setBalance("2000");
        fetchBalByCategoryBalance3.setCategory("Boarding");
        mWalleBalByCategoryList.add(fetchBalByCategoryBalance3);

        FetchBalByCategoryBalance fetchBalByCategoryBalance4 = new FetchBalByCategoryBalance();
        fetchBalByCategoryBalance4.setBalance("1000");
        fetchBalByCategoryBalance4.setCategory("Miscellaneous");
        mWalleBalByCategoryList.add(fetchBalByCategoryBalance4);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
