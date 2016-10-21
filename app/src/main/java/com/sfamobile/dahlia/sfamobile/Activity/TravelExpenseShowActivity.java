package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sfamobile.dahlia.sfamobile.R;
import com.sfamobile.dahlia.sfamobile.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TravelExpenseShowActivity extends AppCompatActivity implements Html.ImageGetter {
    
    ImageView mShareIV = null;
    private String path;
    private File dir;
    private File file;

    static Font.FontFamily ff = Font.FontFamily.HELVETICA;
    static float size = 10;
    static float size1 = 8;
    private TextView mActivityNameTV;
    private ImageView mActivityBackIMV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense_show);
        initViews();
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SFAMobi/Expense PDF Files";
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        
        
    }

    @Override
    public Drawable getDrawable(String arg0) {
        // TODO Auto-generated method stub
        int id = 0;

        if(arg0.equals("addbutton.png")){
            id = R.drawable.map_test;
        }

        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(id);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        return d;
    }


    private void initViews() {


        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Travel Expense");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelExpenseShowActivity.this,TravelExpensesListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
        mShareIV = (ImageView) findViewById(R.id.img_expense_share_icon);
        mShareIV.setOnClickListener(new View.OnClickListener() {
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


                final Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                // i.putExtra(Intent.EXTRA_TEXT,"text");

                final List<ResolveInfo> activities = getPackageManager().queryIntentActivities(sharingIntent, 0);

                List<String> appNames = new ArrayList<String>();
                for (ResolveInfo info : activities) {
                    appNames.add(info.loadLabel(getPackageManager()).toString());
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TravelExpenseShowActivity.this);
                builder.setTitle("Complete Action using...");
                builder.setItems(appNames.toArray(new CharSequence[appNames.size()]),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                ResolveInfo info = activities.get(item);
                                if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                                    // Gmail was chosen

                                    Uri uri = Uri.fromFile(file);

                                    String formattedText = getString(R.string.travel_expense_text);
                                    String body = "<p> <b> Hi!<br/><font size=20>How are you </font> <br/>I am fine</b> </p>";

                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(formattedText));
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SFAMobi Travel Expenses");
                                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                } else if (info.activityInfo.packageName.equals("com.whatsapp")) {
                                    // whatsapp was chosen

                                    Uri uri = Uri.fromFile(file);
                                    String formattedText = getString(R.string.travel_expense_text);
                                    String body = "<p> <b> Hi!<br/><font size=20>How are you </font> <br/>I am fine</b> </p>";
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(formattedText));
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SFAMobi Travel Expenses");
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
        
    }


    public Document createPDF() throws FileNotFoundException, DocumentException {

        Font bold = new Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD);
        Font boldLarge = new Font(Font.FontFamily.HELVETICA, 28f, Font.BOLD);
        Font boldMeddium = new Font(Font.FontFamily.HELVETICA, 18f, Font.NORMAL);
        Font normal = new Font(Font.FontFamily.HELVETICA, 14f, Font.NORMAL);


        String FONT1 = "assets/fonts/PlayfairDisplay-Regular.ttf";
        String FONT2 = "assets/fonts/PlayfairDisplay-Bold.ttf";
        String FONT3 = "assets/fonts/PlayfairDisplay-Bold.ttf";
        String RUPEE = "The Rupee character \u20B9 and the Rupee symbol \u20A8";


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


            // open the document

            doc.open();
            // create table

            Font f1 = FontFactory.getFont(FONT1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            Font f2 = FontFactory.getFont(FONT2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 18);
            Font f3 = FontFactory.getFont(FONT3, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            Font f4 = FontFactory.getFont(FONT3, BaseFont.WINANSI, BaseFont.EMBEDDED, 12);




            Paragraph p1 = new Paragraph();
            Chunk travelExpenseMgtchunk = new Chunk("Travel Expense Management \n\n", boldLarge);
            Phrase travelExpenseMgtPhrase = new Phrase(travelExpenseMgtchunk);
            p1.add(travelExpenseMgtPhrase);
            p1.setAlignment(Paragraph.ALIGN_CENTER);





            //add paragraph to document
            doc.add(p1);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.demo_map);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);

            //add image to document
            doc.add(myImg);




            Paragraph p2 = new Paragraph();
            Chunk travelExpenseMgtchunk1 = new Chunk("\n Monday 10 OCT 2016 09:30 AM\n\n", boldMeddium);
            Phrase travelExpenseMgtPhrase1 = new Phrase(travelExpenseMgtchunk1);
            p2.add(travelExpenseMgtPhrase1);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p2);


            PdfPTable tabletmp2 = new PdfPTable(1);
            BaseColor headingColor8 = WebColors.getRGBColor("#F0F0F0");
            tabletmp2.setWidthPercentage(100);
            PdfPTable table8 = new PdfPTable(2);
            table8.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table8.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table8.getDefaultCell().setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            float[] colWidths8 = { 50, 50 };
            table8.setWidths(colWidths8);
            PdfPCell cellPaymentSummry = new PdfPCell();
            cellPaymentSummry.setBackgroundColor(headingColor8);
            cellPaymentSummry.setBorder(Rectangle.NO_BORDER);

            Chunk chunkPaymentInvoiceLabal = new Chunk("Mode of travel -", normal);
            Phrase phPaymentInvoiceLabal = new Phrase(chunkPaymentInvoiceLabal);

            Paragraph phPaymentSummry = new Paragraph();
            phPaymentSummry.add(phPaymentInvoiceLabal);
            cellPaymentSummry.addElement(phPaymentSummry);
            table8.addCell(cellPaymentSummry);

            PdfPCell cellPaymentSummry1 = new PdfPCell();
            cellPaymentSummry1.setBackgroundColor(headingColor8);
            cellPaymentSummry1.getBorderWidthRight();
            cellPaymentSummry1.setBorder(Rectangle.NO_BORDER);
            String invoice = "Two-wheeler(Self-Own)";

            Chunk chunkPaymentInvoicel = new Chunk(invoice, bold);
            Phrase phPaymentInvoice = new Phrase(chunkPaymentInvoicel);



            Paragraph phPaymentSummry1 = new Paragraph();
            phPaymentSummry1.add(phPaymentInvoice);
            phPaymentSummry1.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            cellPaymentSummry1.addElement(phPaymentSummry1);
            table8.addCell(cellPaymentSummry1);




            tabletmp2.addCell(table8);
            doc.add(tabletmp2);



            PdfPTable myTable = new PdfPTable(2);

//create a 3 cells and add them to the table

            PdfPCell cellTwo = new PdfPCell(new Paragraph("\n09:30 AM\n"));
            PdfPCell cellThree = new PdfPCell(new Paragraph("\nAmenora Park Town,MagarPatta,Pune"));
//            PdfPCell cellFive = new PdfPCell(new Paragraph(""));
//            PdfPCell cellSix = new PdfPCell(new Paragraph(""));

            cellTwo.setBorder(Rectangle.NO_BORDER);
            cellThree.setBorder(Rectangle.LEFT);

            myTable.addCell(cellTwo);
            myTable.addCell(cellThree);
//            myTable.addCell(cellFive);
//            myTable.addCell(cellSix);
            doc.add(myTable);


            Chunk chunkPaymentInvoice2 = new Chunk("to\n\n", bold);
            Phrase phPaymentInvoice2 = new Phrase(chunkPaymentInvoice2);



            Paragraph phPaymentSummry2 = new Paragraph();
            phPaymentSummry2.add(phPaymentInvoice2);
            phPaymentSummry2.setAlignment(Element.ALIGN_CENTER);
            doc.add(phPaymentSummry2);


            PdfPTable myTable1 = new PdfPTable(2);

            PdfPCell cellTwo1 = new PdfPCell(new Paragraph("\n10:30 AM"));
            PdfPCell cellThree1 = new PdfPCell(new Paragraph("\nBramha Suncity,Kharadi,Pune"));
//            PdfPCell cellFive = new PdfPCell(new Paragraph(""));
//            PdfPCell cellSix = new PdfPCell(new Paragraph(""));

            cellTwo1.setBorder(Rectangle.NO_BORDER);
            cellThree1.setBorder(Rectangle.LEFT);

            myTable1.addCell(cellTwo1);
            myTable1.addCell(cellThree1);
//            myTable.addCell(cellFive);
//            myTable.addCell(cellSix);
            doc.add(myTable1);

            Paragraph p3 = new Paragraph();
            Chunk travelExpenseMgtchunk2 = new Chunk("\n Bill Details\n\n", boldMeddium);
            Phrase travelExpenseMgtPhrase2 = new Phrase(travelExpenseMgtchunk2);
            p3.add(travelExpenseMgtPhrase2);
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p3);


            PdfPTable tabletmp3 = new PdfPTable(1);
            BaseColor headingColor9 = WebColors.getRGBColor("#F0F0F0");
            tabletmp3.setWidthPercentage(100);
            PdfPTable table9 = new PdfPTable(2);
            table9.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table9.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table9.getDefaultCell().setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            float[] colWidths9 = { 50, 50 };
            table8.setWidths(colWidths9);
            PdfPCell cellPaymentSummry2 = new PdfPCell();
            cellPaymentSummry2.setBackgroundColor(headingColor9);
            cellPaymentSummry2.setBorder(Rectangle.NO_BORDER);



            Chunk chunkPaymentInvoiceLabal1 = new Chunk("Base Fare(per KM)", normal);
            Phrase phPaymentInvoiceLabal1 = new Phrase(chunkPaymentInvoiceLabal1);

            Chunk chunkPaymentModeLabal1 = new Chunk("\n\nTotal Distance", normal);
            Phrase phPaymentModeLabal1 = new Phrase(chunkPaymentModeLabal1);
            Chunk chunkAmountReceivedLabal1 = new Chunk("\n\nTotal Bill(rounded off)", normal);

            Phrase phAmountReceivedLabal1 = new Phrase(chunkAmountReceivedLabal1);
            Paragraph phPaymentSummry4 = new Paragraph();
            phPaymentSummry4.add(phPaymentInvoiceLabal1);
            phPaymentSummry4.add(phPaymentModeLabal1);
            phPaymentSummry4.add(phAmountReceivedLabal1);
            cellPaymentSummry2.addElement(phPaymentSummry4);
            table9.addCell(cellPaymentSummry2);

            PdfPCell cellPaymentSummry3 = new PdfPCell();
            cellPaymentSummry3.setBackgroundColor(headingColor8);
            cellPaymentSummry3.getBorderWidthRight();
            cellPaymentSummry3.setBorder(Rectangle.NO_BORDER);
            String PaymentMode = "8 KM";
            String amountReceived = getString(R.string.amount_70);
            String invoice1 = getString(R.string.amount_45);

            Chunk chunkPaymentInvoicel2 = new Chunk(invoice1, f2);
            Phrase phPaymentInvoice4 = new Phrase(chunkPaymentInvoicel2);

            Chunk chunkPaymentMode = new Chunk("\n\n" + PaymentMode, bold);
            Phrase phPaymentMode = new Phrase(chunkPaymentMode);

            Chunk chunkAmountReceived = new Chunk("\n\n" + amountReceived, f2);
            Phrase phAmountReceived = new Phrase(chunkAmountReceived);


            Paragraph phPaymentSummry5 = new Paragraph();
            phPaymentSummry5.add(phPaymentInvoice4);
            phPaymentSummry5.add(phPaymentMode);
            phPaymentSummry5.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

            phPaymentSummry5.add(phAmountReceived);
            cellPaymentSummry3.addElement(phPaymentSummry5);
            table9.addCell(cellPaymentSummry3);




            tabletmp3.addCell(table9);
            doc.add(tabletmp3);

            Paragraph p4 = new Paragraph();
            Chunk travelExpenseMgtchunk3 = new Chunk("\n Payment\n\n", boldMeddium);
            Phrase travelExpenseMgtPhrase3 = new Phrase(travelExpenseMgtchunk3);
            p4.add(travelExpenseMgtPhrase3);
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p4);



            PdfPTable tabletmp4 = new PdfPTable(1);
            BaseColor headingColor10 = WebColors.getRGBColor("#F0F0F0");
            tabletmp4.setWidthPercentage(100);
            PdfPTable table10 = new PdfPTable(2);
            table10.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table10.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table10.getDefaultCell().setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            float[] colWidths10 = { 50, 50 };
            table10.setWidths(colWidths10);
            PdfPCell cellPaymentSummry5 = new PdfPCell();
            cellPaymentSummry5.setBackgroundColor(headingColor10);
            cellPaymentSummry5.setBorder(Rectangle.NO_BORDER);

            Chunk chunkPaymentInvoiceLabal5 = new Chunk("Paid By Cash -", normal);
            Phrase phPaymentInvoiceLabal5 = new Phrase(chunkPaymentInvoiceLabal5);

            Paragraph phPaymentSummry6 = new Paragraph();
            phPaymentSummry6.add(phPaymentInvoiceLabal5);
            cellPaymentSummry5.addElement(phPaymentSummry6);
            table10.addCell(cellPaymentSummry5);

            PdfPCell cellPaymentSummry6 = new PdfPCell();
            cellPaymentSummry6.setBackgroundColor(headingColor8);
            cellPaymentSummry6.getBorderWidthRight();
            cellPaymentSummry6.setBorder(Rectangle.NO_BORDER);
            String invoice3 = getString(R.string.amount_70);

            Chunk chunkPaymentInvoicel3 = new Chunk(invoice3, f2);
            Phrase phPaymentInvoice3 = new Phrase(chunkPaymentInvoicel3);



            Paragraph phPaymentSummry3 = new Paragraph();
            phPaymentSummry3.add(phPaymentInvoice3);
            phPaymentSummry3.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            cellPaymentSummry6.addElement(phPaymentSummry3);
            table10.addCell(cellPaymentSummry6);




            tabletmp4.addCell(table10);
            doc.add(tabletmp4);



//            Paragraph billDetails = new Paragraph();
//            Chunk billDetailsChunk = new Chunk("Bill Details \n", boldMeddium);
//            Phrase billDetailsPhrase = new Phrase(billDetailsChunk);
//            billDetails.add(billDetailsPhrase);
//            billDetails.setAlignment(Paragraph.ALIGN_CENTER);
//
//
//
//
//
//            //add paragraph to document
//            doc.add(billDetails);






//            PdfPTable tabletmp = new PdfPTable(1);
//            tabletmp.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//            tabletmp.setWidthPercentage(100);
//            PdfPTable table = new PdfPTable(2);
//            float[] colWidths = { 45, 55 };
//            table.setWidths(colWidths);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.share_icon);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
//            Image image2 = Image.getInstance(stream.toByteArray());
//            image2.setWidthPercentage(60);
//            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
//            PdfPCell cell = new PdfPCell();
//            cell.setBorder(Rectangle.NO_BORDER);
//            cell.addElement(image2);
//            table.addCell(cell);
//            String receiptNo = "123455555555555555";
//            String collectionDate = "09/09/09";
//            Chunk chunk1 = new Chunk("Date: ", normal);
//            Phrase ph1 = new Phrase(chunk1);
//
//            Chunk chunk2 = new Chunk(collectionDate, bold);
//            Phrase ph2 = new Phrase(chunk2);
//
//            Chunk chunk3 = new Chunk("\nReceipt No: ", normal);
//            Phrase ph3 = new Phrase(chunk3);
//
//            Chunk chunk4 = new Chunk(receiptNo, bold);
//            Phrase ph4 = new Phrase(chunk4);
//
//            Paragraph ph = new Paragraph();
//            ph.add(ph1);
//            ph.add(ph2);
//            ph.add(ph3);
//            ph.add(ph4);
//
//            table.addCell(ph);
//            tabletmp.addCell(table);
//            PdfContentByte canvas = writer.getDirectContent();
//            canvas.saveState();
//            canvas.setLineWidth((float) 10 / 10);
//            canvas.moveTo(40, 806 - (5 * 10));
//            canvas.lineTo(555, 806 - (5 * 10));
//            canvas.stroke();
//            doc.add(tabletmp);
//            canvas.restoreState();
//            PdfPTable tabletmp1 = new PdfPTable(1);
//            tabletmp1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//            tabletmp1.setWidthPercentage(100);
//            PdfPTable table1 = new PdfPTable(2);
//            table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//            table1.getDefaultCell().setVerticalAlignment(Element.ALIGN_JUSTIFIED);
//            float[] colWidths1 = { 60, 40 };
//            table1.setWidths(colWidths1);
//            String Patient = "abcddd";
//            String Email = "test@test.com";
//            String Phone = "89890099890890";
//            PdfPCell cell3 = new PdfPCell();
//            cell3.setBorder(Rectangle.NO_BORDER);
//            Chunk chunkPatientLabal = new Chunk("Patient: ", normal);
//            Phrase phPatientLabal = new Phrase(chunkPatientLabal);
//            Chunk chunkPatient = new Chunk(Patient, bold);
//            Phrase phPatient = new Phrase(chunkPatient);
//            Chunk chunkEmailLabal = new Chunk("\nEmail: ", normal);
//            Phrase phEmailLabal = new Phrase(chunkEmailLabal);
//            Chunk chunkEmail = new Chunk(Email, bold);
//            Phrase phEmail = new Phrase(chunkEmail);
//            Chunk chunkPhoneLabal = new Chunk("\nPhone: ", normal);
//            Phrase phPhoneLabal = new Phrase(chunkPhoneLabal);
//            Chunk chunkPhone = new Chunk(Phone, bold);
//            Phrase phPhone = new Phrase(chunkPhone);
//            Paragraph phN = new Paragraph();
//            phN.add(phPatientLabal);
//            phN.add(phPatient);
//            phN.add(phEmailLabal);
//            phN.add(phEmail);
//            phN.add(phPhoneLabal);
//            phN.add(phPhone);
//            cell3.addElement(phN);
//            table1.addCell(cell3);
//            PdfPCell cell4 = new PdfPCell();
//            cell4.getBorderWidthRight();
//            cell4.setBorder(Rectangle.NO_BORDER);
//            String ReferingPhysician = "phy Patient";
//            Chunk chunkRefPhyLabal = new Chunk("Refering Physician: ", normal);
//            Phrase phRefPhyLabal = new Phrase(chunkRefPhyLabal);
//            Chunk chunkRefPhy = new Chunk(ReferingPhysician, bold);
//            Phrase phRefPhy = new Phrase(chunkRefPhy);
//            Paragraph phN1 = new Paragraph();
//            phN1.add(phRefPhyLabal);
//            phN1.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
//            phN1.add(phRefPhy);
//            cell4.addElement(phN1);
//            table1.addCell(cell4);
//            tabletmp1.addCell(table1);
//            tabletmp1.setSpacingAfter(10);
//            doc.add(tabletmp1);
//            PdfPTable table7 = new PdfPTable(1);
//            table7.setWidthPercentage(100);
//            PdfPCell c7 = new PdfPCell(new Phrase("Payment Summry", new Font(ff,
//                    size, Font.BOLD)));
//            BaseColor headingColor7 = WebColors.getRGBColor("#989898");
//            c7.setBackgroundColor(headingColor7);
//            c7.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//            table7.addCell(c7);
//            table7.setSpacingAfter(2f);
//            doc.add(table7);
//            // ////////////////////////////////////////////////////////
//            PdfPTable tabletmp2 = new PdfPTable(1);
//            BaseColor headingColor8 = WebColors.getRGBColor("#F0F0F0");
//            tabletmp2.setWidthPercentage(100);
//            PdfPTable table8 = new PdfPTable(2);
//            table8.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//            table8.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//            table8.getDefaultCell().setVerticalAlignment(Element.ALIGN_JUSTIFIED);
//            float[] colWidths8 = { 50, 50 };
//            table8.setWidths(colWidths8);
//            PdfPCell cellPaymentSummry = new PdfPCell();
//            cellPaymentSummry.setBackgroundColor(headingColor8);
//            cellPaymentSummry.setBorder(Rectangle.NO_BORDER);
//
//            Chunk chunkPaymentInvoiceLabal = new Chunk("Invoice Id", normal);
//            Phrase phPaymentInvoiceLabal = new Phrase(chunkPaymentInvoiceLabal);
//
//            Chunk chunkPaymentModeLabal = new Chunk("\nPayment Mode", normal);
//            Phrase phPaymentModeLabal = new Phrase(chunkPaymentModeLabal);
//            Chunk chunkAmountReceivedLabal = new Chunk("\nAmount Received", normal);
//            Chunk chunkAmountDueLabal = new Chunk("\nAmount Due", normal);
//            Phrase phAmountDueLabal = new Phrase(chunkAmountDueLabal);
//
//            Phrase phAmountReceivedLabal = new Phrase(chunkAmountReceivedLabal);
//            Paragraph phPaymentSummry = new Paragraph();
//            phPaymentSummry.add(phPaymentInvoiceLabal);
//            phPaymentSummry.add(phPaymentModeLabal);
//            phPaymentSummry.add(phAmountReceivedLabal);
//            phPaymentSummry.add(phAmountDueLabal);
//            cellPaymentSummry.addElement(phPaymentSummry);
//            table8.addCell(cellPaymentSummry);
//
//            PdfPCell cellPaymentSummry1 = new PdfPCell();
//            cellPaymentSummry1.setBackgroundColor(headingColor8);
//            cellPaymentSummry1.getBorderWidthRight();
//            cellPaymentSummry1.setBorder(Rectangle.NO_BORDER);
//            String PaymentMode = "rannnnnnnnnnnnnnnnn";
//            String amountReceived = "2.33";
//            String amountDue = "28.00";
//            String invoice = "123";
//
//            Chunk chunkPaymentInvoicel = new Chunk(invoice, bold);
//            Phrase phPaymentInvoice = new Phrase(chunkPaymentInvoicel);
//
//            Chunk chunkPaymentMode = new Chunk("\n" + PaymentMode, bold);
//            Phrase phPaymentMode = new Phrase(chunkPaymentMode);
//
//            Chunk chunkAmountReceived = new Chunk("\n$" + amountReceived, bold);
//            Phrase phAmountReceived = new Phrase(chunkAmountReceived);
//
//            Chunk chunkAmountDue = new Chunk("\n$" + amountDue, bold);
//            Phrase phAmountDue = new Phrase(chunkAmountDue);
//
//            Paragraph phPaymentSummry1 = new Paragraph();
//            phPaymentSummry1.add(phPaymentInvoice);
//            phPaymentSummry1.add(phPaymentMode);
//            phPaymentSummry1.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
//
//            phPaymentSummry1.add(phAmountReceived);
//
//            phPaymentSummry1.add(phAmountDue);
//            cellPaymentSummry1.addElement(phPaymentSummry1);
//            table8.addCell(cellPaymentSummry1);
//            tabletmp2.addCell(table8);
//            doc.add(tabletmp2);




        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            doc.close();
        }

        return doc;
    }
    
    
}
