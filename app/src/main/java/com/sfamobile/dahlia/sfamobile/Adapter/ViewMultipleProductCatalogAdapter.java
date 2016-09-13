package com.sfamobile.dahlia.sfamobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Model.ProductCatalogModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

/**
 * Created by Admin on 05-08-2016.
 */
public class ViewMultipleProductCatalogAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Context activity;
    private ArrayList<ProductCatalogModel> data;
    private static LayoutInflater inflater=null;
    int i=0;
    private TextView mShowPrice = null;

    public ArrayList myItems = new ArrayList();

    /*************  CustomAdapter Constructor *****************/
    public ViewMultipleProductCatalogAdapter(Activity a, ArrayList<ProductCatalogModel> d) {

        /********** Take passed values **********/
        activity = a;
        data = d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
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
        public TextView productId;
        public TextView productDesc;
        public LinearLayout showPriceLL;
        public TextView showPriceTV;
        public EditText quantity;
        public CheckBox selectItemCheckBox;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        final ProductCatalogModel productCatalogModel = data.get(position);

        View vi = convertView;
        final ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.product_catalog_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.productId = (TextView) vi.findViewById(R.id.product_id_value_tv);
            holder.name = (TextView) vi.findViewById(R.id.product_name_value_tv);
            holder.productDesc=(TextView)vi.findViewById(R.id.product_desc_value_tv);
            holder.showPriceLL = (LinearLayout) vi.findViewById(R.id.layout_btn_show_price);
            holder.showPriceTV=(TextView)vi.findViewById(R.id.show_price_tv);

            holder.showPriceLL.setTag(position);
            holder.quantity=(EditText) vi.findViewById(R.id.available_quantitiy_value_et);
            holder.selectItemCheckBox = (CheckBox)vi.findViewById(R.id.select_item_checkBox);



            holder.showPriceLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LinearLayout linearLayout = (LinearLayout) v;

//                    final Dialog dialog = new Dialog(activity);
//                    // Include dialog.xml file
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.setContentView(R.layout.show_price_dialog);
//
//                    mShowPrice = (TextView) dialog.findViewById(R.id.show_price_value_tv);
//
//                    mShowPrice.setText(productCatalogModel.getPrice());
//
//                    WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
//                    wmlp.x = (int) linearLayout.getX();
//                    wmlp.y = (int) linearLayout.getY();
//
//                    dialog.show();

                    holder.showPriceTV.setText(productCatalogModel.getPrice());


                }
            });



            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.name.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/

            /************  Set Model values in Holder elements ***********/



            holder.productId.setText(productCatalogModel.getProductId());
            holder.name.setText( productCatalogModel.getName() );
            holder.productDesc.setText(productCatalogModel.getProductDesc());
            holder.quantity.setText(productCatalogModel.getQuantity());
            holder.quantity.setId(position);
            holder.selectItemCheckBox.setChecked(productCatalogModel.isChecked());
            holder.selectItemCheckBox.setId(position);
            holder.selectItemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = v.getId();
                    final CheckBox Caption = (CheckBox) v;
                    if(data.get(position).isChecked() != true) {
                        data.get(position).setChecked(true);
                        Caption.setChecked(true);
                    } else {
                        data.get(position).setChecked(false);
                        Caption.setChecked(false);
                    }
                }
            });
            holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        data.get(position).setQuantity( Caption.getText().toString());
                    }
                }
            });



            /******** Set Item Click Listner for LayoutInflater for each row *******/

            //vi.setOnClickListener(new OnItemClickListener( position ));
            vi.setId(position);
            vi.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    holder.selectItemCheckBox.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
//    private class OnItemClickListener  implements View.OnClickListener {
//        private int mPosition;
//
//        OnItemClickListener(int position){
//            mPosition = position;
//        }
//
//        @Override
//        public void onClick(View arg0) {
//
//            arg0.
//
//
//        }
//    }
}
