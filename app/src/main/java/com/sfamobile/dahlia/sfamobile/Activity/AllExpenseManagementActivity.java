package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
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
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CFFFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.sfamobile.dahlia.sfamobile.Adapter.SpinnerAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.TransactionCategoryAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleContactListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.FetchBalByCategoryBalance;
import com.sfamobile.dahlia.sfamobile.R;
import com.sfamobile.dahlia.sfamobile.Utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

    private int pMonth;
    private int pDay;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;

    ImageView mShareExpenseIMV = null;

    private static String FILE = Environment.getExternalStorageDirectory() + "/HelloWorld.pdf";

    private Button b;
    private PdfPCell cell;
    private String textAnswer;
    private Image bgImage;
    ListView list;
    private String path;
    private File dir;
    private File file;

    BaseColor myColor = WebColors.getRGBColor("#9E9E9E");
    BaseColor myColor1 = WebColors.getRGBColor("#757575");

    int weeklyTotalExpense = 0;
    int dailyTotalExpense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expense_management);
        initializeViews();
        getData();
        TransactionCategoryAdapter transactionCategoryAdapter = new TransactionCategoryAdapter(this,
                mWalleBalByCategoryList, mTotalAmount);
        mExpenseLV = (ListView) findViewById(R.id.lv_transaction);
        mExpenseLV.setAdapter(transactionCategoryAdapter);

        // creating new file path
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SFAMobi1/Expense PDF Files1";
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // try {
        //
        // ArrayList<String> FilesInFolder = GetFiles("/sdcard/Trinity/PDF
        // Files");
        // if (FilesInFolder.size() != 0)
        // list.setAdapter(new ArrayAdapter<String>(this,
        // android.R.layout.simple_list_item_1, FilesInFolder));
        //
        // list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        // public void onItemClick(AdapterView<?> parent, View v, int position,
        // long id) {
        // // Clicking on items
        // }
        // });
        // } catch (NullPointerException e) {
        // e.printStackTrace();
        // }
    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i = 0; i < files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }

    public Document createPDF() throws FileNotFoundException, DocumentException {

        // create document file
        Document doc = new Document();
        String weekDays[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String weekDate[] = {"26/Sept/2016", "27/Sept/2016", "28/Sept/2016", "29/Sept/2016", "30/Sept/2016", "01/Oct/2016", "02/Oct/2016"};

        try {

            Log.e("PDFCreator", "PDF Path: " + path);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            file = new File(dir, "Expenses PDF" + sdf.format(Calendar.getInstance().getTime()) + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(doc, fOut);

            String FONT1 = "resources/fonts/PlayfairDisplay-Regular.ttf";
            String FONT2 = "resources/fonts/PT_Sans-Web-Regular.ttf";
            String FONT3 = "resources/fonts/FreeSans.ttf";


            Font f1 = FontFactory.getFont(FONT1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            Font f2 = FontFactory.getFont(FONT2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            Font f3 = FontFactory.getFont(FONT3, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            Font f4 = FontFactory.getFont(FONT3, BaseFont.WINANSI, BaseFont.EMBEDDED, 12);

            // open the document

            doc.open();
            // create table


            PdfPTable pt = new PdfPTable(3);
            pt.setWidthPercentage(100);
            float[] fl = new float[]{20, 45, 35};
            pt.setWidths(fl);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            // set drawable in cell
            Drawable myImage = AllExpenseManagementActivity.this.getResources().getDrawable(R.drawable.sfamobi_icon);
            Bitmap bitmap = ((BitmapDrawable) myImage).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            try {
                bgImage = Image.getInstance(bitmapdata);
                bgImage.setAbsolutePosition(330f, 642f);
                cell.addElement(bgImage);
                pt.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.addElement(new Paragraph("Weekly Expenses"));

                cell.addElement(new Paragraph("Employee ID  :"+"SR121"));
                cell.addElement(new Paragraph("Employee Name:"+"Ravi Verma"));
                pt.addCell(cell);

                cell = new PdfPCell(new Paragraph(""));
                cell.setBorder(Rectangle.NO_BORDER);
                pt.addCell(cell);



                PdfPTable pTable = new PdfPTable(1);
                pTable.setWidthPercentage(100);
                cell = new PdfPCell();
                cell.setColspan(1);
                cell.addElement(pt);
                pTable.addCell(cell);

                PdfPTable table = new PdfPTable(5);


                //float[] columnWidth = new float[] { 6, 30, 30, 20, 20, 30 };
                float[] columnWidth = new float[]{8, 30, 25, 40, 40};
                table.setWidths(columnWidth);


                cell = new PdfPCell();

                cell.setBackgroundColor(myColor);
                cell.setColspan(6);
                cell.addElement(pTable);
                table.addCell(cell);


                String expensesType[] = {"Travel", "Lodging", "Boarding", "Miscellaneous"};
                List<Integer[]> myList = new ArrayList<Integer[]>();
                Integer expenseSpend1[] = {3000, 4000, 2000, 1000};
                myList.add(expenseSpend1);
                Integer expenseSpend2[] = {1000, 8000, 7000, 7000};
                myList.add(expenseSpend2);
                Integer expenseSpend3[] = {5000, 3000, 1000, 3000};
                myList.add(expenseSpend3);
                Integer expenseSpend4[] = {1000, 3000, 500, 900};
                myList.add(expenseSpend4);
                Integer expenseSpend5[] = {500, 600, 600, 900};
                myList.add(expenseSpend5);
                Integer expenseSpend6[] = {300, 300, 900, 600};
                myList.add(expenseSpend6);
                Integer expenseSpend7[] = {00, 00, 00, 00};
                myList.add(expenseSpend7);
                String expensesRemarks[] = {"Travel", "Lodging", "Boarding", "Miscellaneous"};


                for (int j = 0; j < weekDate.length; j++) {
                    cell = new PdfPCell(new Phrase("Date:" + weekDays[j] + " " + weekDate[j]));
                    cell.setColspan(6);
                    table.addCell(cell);
                    cell = new PdfPCell();
                    cell.setColspan(6);

                    cell.setBackgroundColor(myColor1);

                    cell = new PdfPCell(new Phrase("No."));
                    cell.setBackgroundColor(myColor1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Expenses Type"));
                    cell.setBackgroundColor(myColor1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(getString(R.string.spend_rupees).toString(), f1));
                    cell.setBackgroundColor(myColor1);
                    table.addCell(cell);
//					cell = new PdfPCell(new Phrase("Date"));
//					cell.setBackgroundColor(myColor1);
//					table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Client"));
                    cell.setBackgroundColor(myColor1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Remarks"));
                    cell.setBackgroundColor(myColor1);
                    table.addCell(cell);

                    // table.setHeaderRows(3);
                    cell = new PdfPCell();
                    cell.setColspan(6);

                    Integer expenseSpendTemp[] = myList.get(j);
                    for (int i = 0; i <= 3; i++) {
                        dailyTotalExpense = dailyTotalExpense + expenseSpendTemp[i];

                        table.addCell(String.valueOf(i + 1));
                        table.addCell(expensesType[i]);
                        table.addCell(String.valueOf(expenseSpendTemp[i]).toString());
                        table.addCell("Sales call with Dahlia tech");
                        table.addCell("It's big lead");

                    }
                    weeklyTotalExpense = weeklyTotalExpense + dailyTotalExpense;


                    PdfPTable ftable = new PdfPTable(6);
                    ftable.setWidthPercentage(100);
                    float[] columnWidthaa = new float[]{40, 5, 20, 20, 20, 25};
                    ftable.setWidths(columnWidthaa);
                    cell = new PdfPCell();
                    cell.setColspan(6);
                    cell.setBackgroundColor(myColor1);
                    cell = new PdfPCell(new Phrase("Total Expenses"));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setBackgroundColor(myColor1);
                    ftable.addCell(cell);
                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setBackgroundColor(myColor1);
                    ftable.addCell(cell);
                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setBackgroundColor(myColor1);
                    ftable.addCell(cell);
                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setBackgroundColor(myColor1);
                    ftable.addCell(cell);
                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setBackgroundColor(myColor1);
                    ftable.addCell(cell);
                    cell = new PdfPCell(new Phrase(Utils.getIndianCurrencyFormattedWithoutDecimal(String.valueOf(dailyTotalExpense), false), f2));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setBackgroundColor(myColor);
                    ftable.addCell(cell);
                    cell = new PdfPCell(new Paragraph("'"));
                    cell.setColspan(6);
                    ftable.addCell(cell);
                    cell = new PdfPCell();
                    cell.setColspan(6);
                    cell.addElement(ftable);
                    table.addCell(cell);

                    dailyTotalExpense = 0;

                }

                PdfPTable ftable = new PdfPTable(6);
                ftable.setWidthPercentage(100);
                float[] columnWidthaa = new float[]{50, 5, 20, 20, 20, 25};
                ftable.setWidths(columnWidthaa);
                cell = new PdfPCell();
                cell.setColspan(6);
                cell = new PdfPCell(new Phrase("Weekly Total Expenses"));
                cell.setBorder(Rectangle.NO_BORDER);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(Utils.getIndianCurrencyFormattedWithoutDecimal(String.valueOf(weeklyTotalExpense), true), f3));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor);
                ftable.addCell(cell);
                cell = new PdfPCell();
                cell.setColspan(6);
                cell.addElement(ftable);
                table.addCell(cell);
                doc.add(table);


                // Toast.makeText(getApplicationContext(), "created PDF",
                // Toast.LENGTH_LONG).show();
            } catch (DocumentException de) {
                Log.e("PDFCreator", "DocumentException:" + de);
            } catch (IOException e) {
                Log.e("PDFCreator", "ioException:" + e);
            } finally {
                doc.close();
                weeklyTotalExpense = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doc;
    }

    private void initializeViews() {

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Expense Details");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllExpenseManagementActivity.this, ManagedExpenseActivity.class);
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

                final AutoCompleteTextView startDateATV = (AutoCompleteTextView) dialog
                        .findViewById(R.id.dsvrc_select_date_atv);
                final AutoCompleteTextView endDateATV = (AutoCompleteTextView) dialog
                        .findViewById(R.id.def_select_date_to_atv);

                startDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cal = Calendar.getInstance();
                        pYear = cal.get(Calendar.YEAR);
                        pMonth = cal.get(Calendar.MONTH);
                        pDay = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker = new DatePickerDialog(AllExpenseManagementActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth,
                                                          int selectedday) {
                                        startDateATV.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                                    }
                                }, pYear, pMonth, pDay);
                        mDatePicker.setTitle("Select Date");
                        mDatePicker.show();
                    }
                });
                Button endDateButton = (Button) dialog.findViewById(R.id.def_select_date_to_button);
                endDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cal = Calendar.getInstance();
                        pYear = cal.get(Calendar.YEAR);
                        pMonth = cal.get(Calendar.MONTH);
                        pDay = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker = new DatePickerDialog(AllExpenseManagementActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth,
                                                          int selectedday) {
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
                String[] items = {"Travel", "Lodging", "Boarding", "Miscellaneous"};
                SpinnerAdapter adapter = new SpinnerAdapter(AllExpenseManagementActivity.this,
                        R.layout.spinner_status_item, R.id.spinner_value_tv, items);
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

                Document doc = null;
                try {
                    doc = createPDF();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                // List<Intent> targets = new ArrayList<>();
                // Intent template = new Intent(Intent.ACTION_SEND);
                // template.setType("text/plain");
                // List<ResolveInfo> candidates =
                // AllExpenseManagementActivity.this.getPackageManager().
                // queryIntentActivities(template, 0);
                // // remove facebook which has a broken share intent ;)
                // for (ResolveInfo candidate : candidates) {
                // String packageName = candidate.activityInfo.packageName;
                // if (packageName.equals("com.google.android.gm")) {
                // Intent sharingIntent = new
                // Intent(android.content.Intent.ACTION_SEND);
                // sharingIntent.setType("text/plain");
                //
                //
                // Uri uri = Uri.fromFile(file);
                //
                //
                // String formattedText = getString(R.string.my_text);
                // String body= "<p> <b> Hi!<br/><font size=20>How are you
                // </font> <br/>I am fine</b> </p>";
                // sharingIntent.putExtra(Intent.EXTRA_TEXT,
                // Html.fromHtml(formattedText));
                // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                // "SFAMobi Expenses");
                // sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                // // "Hi,"+"\n"+"Good Morning, Find your daily expenses below"+
                // sharingIntent.setPackage(packageName);
                // targets.add(sharingIntent);
                // }else if (packageName.equals("com.whatsapp")) {
                // Intent sharingIntent = new
                // Intent(android.content.Intent.ACTION_SEND);
                // sharingIntent.setType("text/plain");
                //
                //
                // Uri uri = Uri.fromFile(file);
                //
                //
                // String formattedText = getString(R.string.my_text);
                // String body= "<p> <b> Hi!<br/><font size=20>How are you
                // </font> <br/>I am fine</b> </p>";
                // sharingIntent.putExtra(Intent.EXTRA_TEXT, formattedText);
                // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                // "SFAMobi Expenses");
                // // "Hi,"+"\n"+"Good Morning, Find your daily expenses below"+
                // sharingIntent.setPackage(packageName);
                // targets.add(sharingIntent);
                // }
                // }
                // Intent chooser = Intent.createChooser(targets.remove(0),
                // "Share Via");
                // chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                // targets.toArray(new Parcelable[targets.size()]));
                // startActivity(chooser);

                final Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                // i.putExtra(Intent.EXTRA_TEXT,"text");

                final List<ResolveInfo> activities = getPackageManager().queryIntentActivities(sharingIntent, 0);

                List<String> appNames = new ArrayList<String>();
                for (ResolveInfo info : activities) {
                    appNames.add(info.loadLabel(getPackageManager()).toString());
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AllExpenseManagementActivity.this);
                builder.setTitle("Complete Action using...");
                builder.setItems(appNames.toArray(new CharSequence[appNames.size()]),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                ResolveInfo info = activities.get(item);
                                if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                                    // Gmail was chosen

                                    Uri uri = Uri.fromFile(file);

                                    String formattedText = getString(R.string.my_text);
                                    String body = "<p> <b> Hi!<br/><font size=20>How are you </font> <br/>I am fine</b> </p>";

                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(formattedText));
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SFAMobi Expenses");
                                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                } else if (info.activityInfo.packageName.equals("com.whatsapp")) {
                                    // whatsapp was chosen

                                    Uri uri = Uri.fromFile(file);
                                    String formattedText = getString(R.string.my_text);
                                    String body = "<p> <b> Hi!<br/><font size=20>How are you </font> <br/>I am fine</b> </p>";
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(formattedText));
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SFAMobi Expenses");
                                    //sharingIntent.putExtra(Intent.ACTION_ATTACH_DATA, uri);
//									sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
//									sharingIntent.setData(uri);
                                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                }

                                // start the selected activity
                                sharingIntent.setPackage(info.activityInfo.packageName);
                                startActivity(sharingIntent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        txtExpenseCategory.setText("Shop");
        mgcProgress.setProgress(70);
        txtCategoryPercent.setText("70" + "%");

        txtTotalExpense.setText(Utils.getIndianCurrencyFormattedWithoutDecimal(String.valueOf("10000"), true));

    }

    public String convertToHtml(String htmlString) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<![CDATA[");
        stringBuilder.append(htmlString);
        stringBuilder.append("]]>");
        return stringBuilder.toString();
    }

    private void getData() {

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
