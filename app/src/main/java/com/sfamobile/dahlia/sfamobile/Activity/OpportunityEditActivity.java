package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleContactListAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleProductCatalogAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewQuotationAdapter;
import com.sfamobile.dahlia.sfamobile.Model.ProductCatalogModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

public class OpportunityEditActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityEditIMV = null;

    Button mShowQuotatation = null;
    ListView mShowProductLV = null;

    private ArrayList<ProductCatalogModel> mProductList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_edit);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Opportunity Details");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
        mActivityEditIMV = (ImageView) findViewById(R.id.edit_item_img);
        mActivityEditIMV.setOnClickListener(this);
        mShowQuotatation = (Button) findViewById(R.id.moli_show_quotation_btn);
        mShowQuotatation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(OpportunityEditActivity.this,ManagedOpportunityActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.edit_item_img:
                Intent intent1 = new Intent(OpportunityEditActivity.this,ConvertLeadToOpportunityActivity.class);
                intent1.putExtra("clientName","Dahlia Tech");
                intent1.putExtra("clientEmail","sales@dahlia-tech.com");
                intent1.putExtra("clientPhone","9503636188");
                intent1.putExtra("clientAddress","Amenora Chember,Pune");
                intent1.putExtra("contactName","Ravi V");
                intent1.putExtra("requirement","This opportunity related to lead");
                intent1.putExtra("amount","1,00,000");
                intent1.putExtra("OpportunityStage","Lost 30%");
                intent1.putExtra("ClosureDate","6/8/2016");
                intent1.putExtra("StatusType","Lost");
                intent1.putExtra("isFromLeadEdit",true);
                intent1.putExtra("Reason","This opportunity is Lost because of product specification not suitable");

                startActivity(intent1);
                finish();
                break;



            case R.id.moli_show_quotation_btn:


            final Dialog dialog = new Dialog(OpportunityEditActivity.this);
            // Include dialog.xml file
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.dialog_show_quatation);

                mShowProductLV = (ListView) dialog.findViewById(R.id.dsq_product_list_lv);
                TextView totalCostTV = (TextView) dialog.findViewById(R.id.total_cost_tv);
                totalCostTV.setText("90250.0");

                setData();
                ViewQuotationAdapter viewMultipleLeadListAdapter = new ViewQuotationAdapter(this,mProductList);
                mShowProductLV.setAdapter(viewMultipleLeadListAdapter);

            dialog.show();

                break;


            default:
                break;
        }
    }


    private void setData(){

        mProductList = new ArrayList<ProductCatalogModel>();

        ProductCatalogModel productCatalogModel = new ProductCatalogModel();
        productCatalogModel.setName("Fashion Women's Handbag");
        productCatalogModel.setPrice("1200.00");
        productCatalogModel.setProductDesc("Sold and fulfilled by bags beautys (3.4 out of 5 | 97 ratings).");
        productCatalogModel.setProductId("A3201");
        productCatalogModel.setQuantity("50");
        mProductList.add(productCatalogModel);

        ProductCatalogModel productCatalogModel1 = new ProductCatalogModel();
        productCatalogModel1.setName("Digital Blue Dial Men's Watch");
        productCatalogModel1.setPrice("700.00");
        productCatalogModel1.setProductDesc("Dial Color: Blue, Case Shape: Round, Dial Glass Material: Mineral");
        productCatalogModel1.setProductId("B3221");
        productCatalogModel1.setQuantity("50");
        mProductList.add(productCatalogModel1);




    }

}

