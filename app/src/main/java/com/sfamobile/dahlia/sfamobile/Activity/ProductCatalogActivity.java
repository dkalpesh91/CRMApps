package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleProductCatalogAdapter;
import com.sfamobile.dahlia.sfamobile.Model.ProductCatalogModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

public class ProductCatalogActivity extends AppCompatActivity implements View.OnClickListener{


    //,android.widget.CompoundButton.OnCheckedChangeListener

    private ListView mShowProductLV = null;

    private Button mCreateQuotationButton = null;

    private ArrayList<ProductCatalogModel> mProductList = null;

    private ArrayList<ProductCatalogModel> mSelectedProductList = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;

    boolean isAddProduct = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isAddProduct = bundle.getBoolean("isAddProduct");
        }

        setData();
        mCreateQuotationButton = (Button) findViewById(R.id.create_quotation_btn);
        mShowProductLV = (ListView)findViewById(R.id.product_catalog_lv);
        mShowProductLV.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        ViewMultipleProductCatalogAdapter viewMultipleLeadListAdapter = new ViewMultipleProductCatalogAdapter(this,mProductList);
        mShowProductLV.setAdapter(viewMultipleLeadListAdapter);
        mCreateQuotationButton.setOnClickListener(this);
        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Product Catalog");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
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

        ProductCatalogModel productCatalogModel2 = new ProductCatalogModel();
        productCatalogModel2.setName("Full Sleeve Cotton T-Shirt");
        productCatalogModel2.setPrice("900.00");
        productCatalogModel2.setProductDesc("Wash with similar colors in cold water, Do not iron on Label");
        productCatalogModel2.setProductId("A3111");
        productCatalogModel2.setQuantity("50");
        mProductList.add(productCatalogModel2);


        ProductCatalogModel productCatalogModel3 = new ProductCatalogModel();
        productCatalogModel3.setName("Analog Black Dial Men's Watch");
        productCatalogModel3.setPrice("1399.00");
        productCatalogModel3.setProductDesc("Dial colour-Black ,Case shape-Round.");
        productCatalogModel3.setProductId("C4231");
        productCatalogModel3.setQuantity("50");
        mProductList.add(productCatalogModel3);


        ProductCatalogModel productCatalogModel4 = new ProductCatalogModel();
        productCatalogModel4.setName("Aux cable for Iphone, Android");
        productCatalogModel4.setPrice("199.00");
        productCatalogModel4.setProductDesc("No need to charge, no hassle of connecting through");
        productCatalogModel4.setProductId("D3012");
        productCatalogModel4.setQuantity("50");
        mProductList.add(productCatalogModel4);

        ProductCatalogModel productCatalogModel5 = new ProductCatalogModel();
        productCatalogModel5.setName("Sonic Power Tooth Brush");
        productCatalogModel5.setPrice("99.00");
        productCatalogModel5.setProductDesc("Deep Cleaning , Gum Protection");
        productCatalogModel5.setProductId("A3201");
        productCatalogModel5.setQuantity("50");
        mProductList.add(productCatalogModel5);


    }

    @Override
    public void onClick(View v) {



        switch (v.getId()) {



            case R.id.create_quotation_btn:

                mSelectedProductList = new ArrayList<ProductCatalogModel>();
                ProductCatalogModel productCatalogModel = new ProductCatalogModel();
                for(int i = 0; i<mProductList.size() ; i++){
                    productCatalogModel = mProductList.get(i);
                    if(productCatalogModel.isChecked() == true){
                        mSelectedProductList.add(productCatalogModel);
                    }
                }
                if (mSelectedProductList.size() != 0) {
                    Intent i = new Intent(ProductCatalogActivity.this, CreateQuotationActivity.class);
                    i.putExtra("isAddProduct", isAddProduct);
                    i.putExtra("productList", mSelectedProductList);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(ProductCatalogActivity.this,"Please, add product",Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.back_arrow_img:

                Intent intent1 = new Intent(ProductCatalogActivity.this,DashBoardActivity.class);
                startActivity(intent1);
                finish();

                break;



            default:
                break;
        }

    }




//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//    }
}
