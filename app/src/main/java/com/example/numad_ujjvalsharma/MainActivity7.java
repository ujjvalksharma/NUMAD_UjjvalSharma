package com.example.numad_ujjvalsharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity7 extends AppCompatActivity implements MyActivity7RecycleViewAdaptor.NameClickListener {

    List<Product> products;
    Timer timer;
    ProgressBar progressBar;
    int selectedCategoryId=-1;
    HashMap<String,Integer> categoryToId=new HashMap<String,Integer>();
    EditText productIdEditText;
    RecyclerView recyclerView;
    MyActivity7RecycleViewAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        categoryToId.put("Any Category",-1);
        categoryToId.put("Clothes",1);
        categoryToId.put("Electronics",2);
        categoryToId.put("Furniture",3);
        categoryToId.put("Shoes",4);
        categoryToId.put("Others",5);
        Button searchBtn= (Button) findViewById(R.id.button10);
        Spinner precisionDropDown = (Spinner) findViewById(R.id.spinner2);
        String[] categories = new String[]{"Any Category","Clothes", "Electronics", "Furniture", "Shoes", "Others"};
        ArrayAdapter<String> precisionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        precisionDropDown.setAdapter(precisionAdapter);

        precisionDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCategory=parent.getItemAtPosition(position).toString();
                selectedCategoryId=categoryToId.get(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing here
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show progress bar
                progressBar= (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);

                searchBtnOnClickHelper();
            }
        });

        productIdEditText= (EditText) findViewById(R.id.editTextProductId);
    }


    private void searchBtnOnClickHelper() {

        StringBuilder urlStr=new StringBuilder("https://api.escuelajs.co/api/v1/products/");
        String productId=productIdEditText.getText().toString();
boolean isProductIdValid=false;
       if(productId!=null&&!"".equals(productId)){
           if(isNumeric(productId)){
               isProductIdValid=true;
               urlStr.append(productId);
           }else{
               new Handler(Looper.getMainLooper()).post(new Runnable() {
                   @Override
                   public void run() {
                       progressBar.setVisibility(View.INVISIBLE);
                       Toast toast = Toast.makeText(MainActivity7.this, "Product id should be integer or empty", Toast.LENGTH_SHORT);
                       toast.show();
                   }
               });
               return;
           }
       }

        StringBuilder finalUrlStr = urlStr;
        boolean finalIsProductIdValid = isProductIdValid;
        Runnable networkCallRunnable=()->{

            URL url = null;
            try {
                url = new URL(finalUrlStr.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                InputStream in = null;
                try {
                    in = new BufferedInputStream(urlConnection.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    String jsonResponseStr = readStream(in);
                    Thread.sleep(3000);
                    if(!finalIsProductIdValid){
                        parseProductJsonArray(jsonResponseStr);
                    }else{
                        parseProductJsonObject(jsonResponseStr);
                    }

                }catch(Exception e){

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(MainActivity7.this, "No products exist for this product Id", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                }
            } finally {
                urlConnection.disconnect();
            }

        };

        new Thread(networkCallRunnable).start();

    }

    private String readStream(InputStream in) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public void parseProductJsonArray(String jsonStr) {
        JSONArray jsonArry= null;
        this.products=new ArrayList<Product>();
        try {
            jsonArry = new JSONArray(jsonStr);
            final int n = jsonArry.length();

            for(int i=0;i<n;i++){
                JSONObject productJson = jsonArry.getJSONObject(i);
                JSONObject productCategoryJson= productJson.getJSONObject("category");
                Product product = null;
                if(selectedCategoryId==-1){
                     product=new Product(productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getInt("price"),
                            productJson.getString("description"),
                            productCategoryJson.getString("image"));
                    products.add(product);
                }else if(selectedCategoryId==productCategoryJson.getInt("id")){

                     product=new Product(productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getInt("price"),
                            productJson.getString("description"),
                            productCategoryJson.getString("image"));
                    products.add(product);
                }

                System.out.println("--------------------------------");
            }
            progressBar.setVisibility(View.INVISIBLE);
            if(this.products.size()!=0){
                displayResults(products);
            }else{

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(MainActivity7.this, "No new results to display", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseProductJsonObject(String jsonStr) {

        JSONObject productJson= null;
        this.products=new ArrayList<Product>();
        try {
            productJson = new JSONObject(jsonStr);
            JSONObject productCategoryJson= productJson.getJSONObject("category");
            if(selectedCategoryId==-1){
                Product product=new Product(productJson.getInt("id"),
                        productJson.getString("title"),
                        productJson.getInt("price"),
                        productJson.getString("description"),
                        productCategoryJson.getString("image"));
                products.add(product);
            } else if(selectedCategoryId==productCategoryJson.getInt("id")){

                Product product=new Product(productJson.getInt("id"),
                        productJson.getString("title"),
                        productJson.getInt("price"),
                        productJson.getString("description"),
                        productCategoryJson.getString("image"));
                products.add(product);
            }

            progressBar.setVisibility(View.INVISIBLE);
            if(this.products.size()!=0){
                displayResults(products);
            }else{

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(MainActivity7.this, "No new results to display", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void displayResults(List<Product> products) {
        System.out.println("products:"+products);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView = findViewById(R.id.recycle_view1);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity7.this));
                adapter = new MyActivity7RecycleViewAdaptor(MainActivity7.this, products);
                adapter.setClickListener(MainActivity7.this);
                recyclerView.setAdapter(adapter);
            }
        });


    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public void onClickName(View view, int position) {

    }
}