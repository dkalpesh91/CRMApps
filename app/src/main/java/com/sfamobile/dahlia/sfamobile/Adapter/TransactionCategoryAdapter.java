package com.sfamobile.dahlia.sfamobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Model.FetchBalByCategoryBalance;
import com.sfamobile.dahlia.sfamobile.R;
import com.sfamobile.dahlia.sfamobile.Utils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Admin on 17-09-2016.
 */
public class TransactionCategoryAdapter extends BaseAdapter {
    private Activity context;

    private List<FetchBalByCategoryBalance> walleBalByCategoryList;

    private double totalAmount;

    String pattern = "##.#";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);

    public TransactionCategoryAdapter(Activity context,
                                      List<FetchBalByCategoryBalance> walleBalByCategoryList, double totalAmount) {
        super();
        this.totalAmount = totalAmount;
        this.context = context;
        this.walleBalByCategoryList = walleBalByCategoryList;
    }

    @Override
    public int getCount() {

        return walleBalByCategoryList.size();
    }

    @Override
    public Object getItem(int position) {

        return walleBalByCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        final ViewHolderItem viewHolder;

        Log.e("Inside adapter", " inside get view");
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_expense_manager_category, arg2,false);
            viewHolder = new ViewHolderItem();
            viewHolder.img_transaction_category = (ImageView) convertView
                    .findViewById(R.id.img_transaction_category);
            viewHolder.txtTransactionCategory = (TextView) convertView
                    .findViewById(R.id.txt_transaction_category);
            viewHolder.txt_transaction_amount = (TextView) convertView
                    .findViewById(R.id.txt_transaction_amount);
            viewHolder.txt_category_percent = (TextView) convertView
                    .findViewById(R.id.txt_category_percent);
            viewHolder.mgc_progress = (ProgressBar) convertView.findViewById(R.id.mgc_progress);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        final FetchBalByCategoryBalance walletBalanceByCategoryItem = walleBalByCategoryList
                .get(position);

        int pos = position;
        setValuesToView(viewHolder, walletBalanceByCategoryItem,pos);
        viewHolder.img_transaction_category.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Intent in = new Intent(context, OneviewSelectCategoryActivity.class);
//                context.startActivityForResult(in, REQUEST_ADD_CATEGORY);
            }
        });

        return convertView;
    }

    private void setValuesToView(final ViewHolderItem viewHolder,
                                 final FetchBalByCategoryBalance walletBalanceByCategoryItem,int pos) {
        String transactionType = walletBalanceByCategoryItem.getCategory().trim();

        String balanaceAmount = walletBalanceByCategoryItem.getBalance().trim();

        viewHolder.txtTransactionCategory.setText(transactionType);
        viewHolder.txt_transaction_amount.setText("- "
                + Utils.getIndianCurrencyFormattedWithoutDecimal(balanaceAmount,true));
        viewHolder.txt_transaction_amount.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        viewHolder.txt_transaction_amount.setSelected(true);
        viewHolder.txt_transaction_amount.setMarqueeRepeatLimit(-1);
        double balancePercent = 0;
        try {
            balancePercent = (Double.parseDouble(balanaceAmount) / totalAmount * 100.0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String reqVal = decimalFormat.format(balancePercent);

        viewHolder.txt_category_percent.setText(reqVal + "%");
        viewHolder.mgc_progress.setProgress((int) balancePercent);


        int imageIconResource = 0;

        switch (pos){
            case 0:
                imageIconResource = R.drawable.icn_travel_vector;
                break;
            case 1:
                imageIconResource = R.drawable.icn_dining;
                break;
            case 2:
                imageIconResource = R.drawable.icn_others_vector;
                break;
            case 3:
                imageIconResource = R.drawable.icn_grocery_vector;
                break;


            default:

                imageIconResource = R.drawable.icn_travel_vector;
                break;
        }
            viewHolder.img_transaction_category.setImageResource(imageIconResource);

//        viewHolder.img_transaction_category.setBackgroundResource(0);
//
//                viewHolder.mgc_progress.setProgressDrawable(context.getResources().getDrawable(
//                        R.drawable.progress_background_expense_manager_yellow));





    }

    static class ViewHolderItem {
        TextView txtTransactionCategory, txt_transaction_amount, txt_category_percent;

        ImageView img_transaction_category;

        ProgressBar mgc_progress;

    }

}

