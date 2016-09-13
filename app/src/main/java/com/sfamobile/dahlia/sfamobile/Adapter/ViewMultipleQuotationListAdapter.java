package com.sfamobile.dahlia.sfamobile.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Model.ProductCatalogModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

/**
 * Created by Admin on 05-08-2016.
 */
public class ViewMultipleQuotationListAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;

    ListView mShowProductLV = null;

    private ArrayList<ProductCatalogModel> mProductList = null;
    int i=0;
    String[] idStr ={"1101","1102","1103","1104","1105"};
    /*************  CustomAdapter Constructor *****************/
    public ViewMultipleQuotationListAdapter(Activity a, String[] d) {

        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {


        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public TextView cid;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.company_details_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.comapny_name_tv);
            holder.cid = (TextView) vi.findViewById(R.id.comapny_id_tv);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.length<=0)
        {
            holder.name.setText("No Data");


        }
        else
        {
            /***** Get each Model object from Arraylist ********/

            /************  Set Model values in Holder elements ***********/


            holder.name.setText(data[position]);
            holder.cid.setText("Quotation Id -"+idStr[position]);


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

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


            final Dialog dialog = new Dialog(activity);
            // Include dialog.xml file
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.dialog_show_quatation);

            mShowProductLV = (ListView) dialog.findViewById(R.id.dsq_product_list_lv);
            TextView totalCostTV = (TextView) dialog.findViewById(R.id.total_cost_tv);
            totalCostTV.setText("90250.0");
            ViewQuotationAdapter viewMultipleLeadListAdapter = new ViewQuotationAdapter(activity,mProductList);
            mShowProductLV.setAdapter(viewMultipleLeadListAdapter);

            dialog.show();

        }
    }
}
