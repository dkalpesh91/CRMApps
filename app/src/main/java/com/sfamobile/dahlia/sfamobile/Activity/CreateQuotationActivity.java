package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleProductCatalogAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewQuotationAdapter;
import com.sfamobile.dahlia.sfamobile.Model.ProductCatalogModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

public class CreateQuotationActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ProductCatalogModel> mSelectedProductList = null;

    private ListView mShowProductLV = null;

    private TextView mShowTOtalCostTV = null;

    private LinearLayout mTotalPriceLL = null;
    private LinearLayout mDiscountLL = null;

    private TextInputLayout mClientNameTIL = null;

    private AutoCompleteTextView mClientNameATV = null;

    private EditText mPercentageET = null;

    private Button mCreateOrderButton = null;
    private Button mSendForAprrovalButton = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    int discount = 5;

    boolean isAddProduct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quotation);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isAddProduct = bundle.getBoolean("isAddProduct");
        }

        mCreateOrderButton = (Button) findViewById(R.id.create_order_btn);
        mPercentageET = (EditText) findViewById(R.id.percentage_et);
        mSendForAprrovalButton = (Button) findViewById(R.id.send_approval_btn);
        mShowProductLV = (ListView)findViewById(R.id.product_catalog_lv);
        mShowTOtalCostTV = (TextView)findViewById(R.id.total_cost_value_tv);
        mDiscountLL = (LinearLayout) findViewById(R.id.add_percentage_ll);
        mTotalPriceLL = (LinearLayout)findViewById(R.id.total_cost_ll);
        mClientNameTIL = (TextInputLayout)findViewById(R.id.cqa_client_name_til);
        mClientNameATV = (AutoCompleteTextView)findViewById(R.id.cqa_client_name_atv);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Create Quotation");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);
        mActivityAddIMV.setOnClickListener(this);




        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            mSelectedProductList = (ArrayList<ProductCatalogModel>) b.get("productList");
            mCreateOrderButton.setVisibility(View.VISIBLE);
            mDiscountLL.setVisibility(View.VISIBLE);
            mTotalPriceLL.setVisibility(View.VISIBLE);
            mShowProductLV.setVisibility(View.VISIBLE);
            mClientNameTIL.setVisibility(View.VISIBLE);
            mClientNameATV.setVisibility(View.VISIBLE);

            mShowTOtalCostTV.setText(getTotalPrice(mSelectedProductList));


            mShowProductLV.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);


            ViewMultipleProductCatalogAdapter viewMultipleLeadListAdapter = new ViewMultipleProductCatalogAdapter(this,mSelectedProductList);
            mShowProductLV.setAdapter(viewMultipleLeadListAdapter);
            mCreateOrderButton.setOnClickListener(this);
            mSendForAprrovalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CreateQuotationActivity.this,"Send for next approval",Toast.LENGTH_LONG).show();
                }
            });


            mPercentageET.addTextChangedListener(passwordWatcher);
        }else {
            mCreateOrderButton.setVisibility(View.GONE);
            mDiscountLL.setVisibility(View.GONE);
            mTotalPriceLL.setVisibility(View.GONE);
            mShowProductLV.setVisibility(View.GONE);
            mClientNameTIL.setVisibility(View.GONE);
            mClientNameATV.setVisibility(View.GONE);
        }





    }





    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                mShowTOtalCostTV.setText(getTotalPrice(mSelectedProductList));
                mSendForAprrovalButton.setVisibility(View.GONE);
            } else{
                if(Integer.parseInt(mPercentageET.getText().toString()) > 5) {
                    mSendForAprrovalButton.setVisibility(View.VISIBLE);
                }else {
                    mSendForAprrovalButton.setVisibility(View.GONE);
                }
                mShowTOtalCostTV.setText(getTotalPrice(String.valueOf(mPercentageET.getText()),String.valueOf(mShowTOtalCostTV.getText())));
            }
        }
    };


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(CreateQuotationActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.add_item_img:
                Intent intent1 = new Intent(CreateQuotationActivity.this,ProductCatalogActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.create_order_btn:
                final Dialog dialog = new Dialog(CreateQuotationActivity.this);
                // Include dialog.xml file
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.dialog_show_quatation);

                mShowProductLV = (ListView) dialog.findViewById(R.id.dsq_product_list_lv);
                TextView totalCostTV = (TextView) dialog.findViewById(R.id.total_cost_tv);
                totalCostTV.setText(getTotalPrice(mSelectedProductList));

                ViewQuotationAdapter viewMultipleLeadListAdapter = new ViewQuotationAdapter(this,mSelectedProductList);
                mShowProductLV.setAdapter(viewMultipleLeadListAdapter);

                dialog.show();

                break;







            default:
                break;
        }

    }

    private String getTotalPrice(String percentage,String previousPrice){
        String totalPrice = "";
        float percentagePrice = ((Float.parseFloat(previousPrice)) * (Integer.parseInt(percentage))/100);
        percentagePrice = Float.parseFloat(previousPrice) - percentagePrice ;
        totalPrice = String.valueOf(percentagePrice);
        return  totalPrice;
    }

    private String getTotalPrice(ArrayList<ProductCatalogModel> selectedProductList){
        String totalPrice = "";
        float price = 0;
        int quantity = 1;
        ProductCatalogModel productCatalogModel = new ProductCatalogModel();
        for(int i = 0; i<selectedProductList.size() ; i++){
            productCatalogModel = selectedProductList.get(i);
            quantity = Integer.parseInt(productCatalogModel.getQuantity());
            price = price + (Float.parseFloat(productCatalogModel.getPrice()) * quantity);
        }
        totalPrice = String.valueOf(price);
        return  totalPrice;
    }

}
