package com.khahdihdz.tnm;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import java.text.NumberFormat;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, content, nav; SharedPreferences sp; int blue=Color.rgb(37,99,235);
    ArrayList<Product> products=new ArrayList<>(); ArrayList<Sale> sales=new ArrayList<>(); ArrayList<Customer> customers=new ArrayList<>();
    NumberFormat money=NumberFormat.getCurrencyInstance(new Locale("vi","VN"));

    public void onCreate(Bundle b){
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(246,248,252));
        getWindow().setNavigationBarColor(Color.WHITE);
        if(Build.VERSION.SDK_INT>=29) getWindow().setNavigationBarContrastEnforced(false);
        if(Build.VERSION.SDK_INT>=26) getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        else if(Build.VERSION.SDK_INT>=23) getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sp=getSharedPreferences("tnm",0); seed(); build("dashboard");
    }
    int dp(int v){return (int)(v*getResources().getDisplayMetrics().density+0.5f);}
    void applyInsets(LinearLayout v){
        v.setOnApplyWindowInsetsListener((view,insets)->{
            view.setPadding(0,insets.getSystemWindowInsetTop(),0,insets.getSystemWindowInsetBottom());
            return insets;
        });
        v.requestApplyInsets();
    }
    void seed(){if(sp.getBoolean("seed",false)){load();return;}
        products.add(new Product("SP001","Nước suối 500ml",6000,40)); products.add(new Product("SP002","Cà phê lon",12000,25)); products.add(new Product("SP003","Mì ly",15000,18));
        customers.add(new Customer("Khách lẻ","")); customers.add(new Customer("Nguyễn Văn An","0901234567")); save(); sp.edit().putBoolean("seed",true).apply();}
    void load(){String ps=sp.getString("products_json",""); if(!ps.isEmpty()) for(String x:ps.split("\\|\\|")){String[] a=x.split("\\|",-1);if(a.length==4)products.add(new Product(a[0],a[1],Long.parseLong(a[2]),Long.parseLong(a[3])));} String cs=sp.getString("customers_json",""); if(!cs.isEmpty()) for(String x:cs.split("\\|\\|")){String[] a=x.split("\\|",-1);if(a.length==2)customers.add(new Customer(a[0],a[1]));} String ss=sp.getString("sales_json",""); if(!ss.isEmpty()) for(String x:ss.split("\\|\\|")){String[] a=x.split("\\|",-1);if(a.length==3)sales.add(new Sale(a[0],Long.parseLong(a[1]),a[2]));}}
    void seedFromPrefs(){ }
    void save(){StringBuilder p=new StringBuilder(),c=new StringBuilder(),s=new StringBuilder(); for(Product x:products){if(p.length()>0)p.append("||");p.append(x.code).append("|").append(x.name).append("|").append(x.price).append("|").append(x.stock);} for(Customer x:customers){if(c.length()>0)c.append("||");c.append(x.name).append("|").append(x.phone);} for(Sale x:sales){if(s.length()>0)s.append("||");s.append(x.customer).append("|").append(x.total).append("|").append(x.payment);} sp.edit().putString("products_json",p.toString()).putString("customers_json",c.toString()).putString("sales_json",s.toString()).apply();}
    String V(long n){return money.format(n).replace("\u00a0"," ");}
    TextView tv(String s,int size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setIncludeFontPadding(false);return t;}
    GradientDrawable bg(int color,int radius){GradientDrawable g=new GradientDrawable();g.setColor(color);g.setCornerRadius(radius);return g;}
    Button btn(String text){Button b=new Button(this);b.setText(text);b.setTextSize(13);b.setAllCaps(false);return b;}
    void build(String page){
        root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(246,248,252));
        applyInsets(root);

        LinearLayout bar=new LinearLayout(this);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setPadding(dp(18),dp(7),dp(14),dp(7));

        String title=page.equals("dashboard")?"Tổng quan":page.equals("sales")?"Bán hàng":
            page.equals("products")?"Sản phẩm":page.equals("customers")?"Khách hàng":
            page.equals("reports")?"Báo cáo":"Cài đặt";
        TextView titleView=tv(title,24,Color.rgb(15,23,42));
        titleView.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        bar.addView(titleView,new LinearLayout.LayoutParams(0,58,1));

        String action=page.equals("dashboard")||page.equals("sales")?"Bán hàng":
            page.equals("products")||page.equals("customers")?"+ Thêm":"";
        if(!action.isEmpty()){
            TextView add=tv(action,14,Color.WHITE);
            add.setGravity(Gravity.CENTER);
            add.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
            add.setPadding(dp(16),0,dp(16),0);
            add.setBackground(bg(blue,16));
            add.setElevation(dp(2));
            add.setOnClickListener(v->{
                if(page.equals("products")) addProduct();
                else if(page.equals("customers")) addCustomer();
                else newSale();
            });
            bar.addView(add,new LinearLayout.LayoutParams(-2,dp(44)));
        }
        root.addView(bar,new LinearLayout.LayoutParams(-1,dp(66)));

        ScrollView sv=new ScrollView(this);
        sv.setFillViewport(true);
        content=new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16),dp(2),dp(16),dp(18));
        sv.addView(content);
        root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));

        nav=new LinearLayout(this);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(6),dp(7),dp(6),dp(7));
        nav.setBackgroundColor(Color.WHITE);
        nav.setElevation(dp(8));
        addNav(com.khahdihdz.tnm.R.drawable.ic_home,"Tổng quan","dashboard",page.equals("dashboard"));
        addNav(com.khahdihdz.tnm.R.drawable.ic_cart,"Bán hàng","sales",page.equals("sales"));
        addNav(com.khahdihdz.tnm.R.drawable.ic_inventory,"Sản phẩm","products",page.equals("products"));
        addNav(com.khahdihdz.tnm.R.drawable.ic_people,"Khách hàng","customers",page.equals("customers"));
        addNav(com.khahdihdz.tnm.R.drawable.ic_report,"Báo cáo","reports",page.equals("reports"));
        root.addView(nav,new LinearLayout.LayoutParams(-1,dp(72)));

        setContentView(root);
        render(page);
    }
    void addNav(int iconRes,String label,String page,boolean selected){
        LinearLayout item=new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);
        item.setPadding(dp(4),dp(4),dp(4),dp(3));
        item.setBackground(bg(selected?Color.rgb(239,246,255):Color.TRANSPARENT,18));

        ImageView icon=new ImageView(this);
        icon.setImageResource(iconRes);
        icon.setColorFilter(selected?blue:Color.rgb(100,116,139));
        item.addView(icon,new LinearLayout.LayoutParams(dp(24),dp(24)));

        TextView labelView=tv(label,11,selected?blue:Color.rgb(100,116,139));
        labelView.setGravity(Gravity.CENTER);
        labelView.setTypeface(Typeface.DEFAULT,selected?Typeface.BOLD:Typeface.NORMAL);
        item.addView(labelView,new LinearLayout.LayoutParams(-1,dp(20)));

        item.setOnClickListener(v->build(page));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,dp(58),1);
        lp.setMargins(dp(3),0,dp(3),0);
        nav.addView(item,lp);
    }
    TextView heading(String s){TextView t=tv(s,17,Color.rgb(20,32,50));t.setTypeface(null,1);t.setPadding(4,12,4,8);return t;}
    void card(LinearLayout p,String title,String value){
        LinearLayout c=new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setGravity(Gravity.CENTER_VERTICAL);
        c.setPadding(dp(16),dp(12),dp(16),dp(12));
        c.setBackground(bg(Color.WHITE,20));
        c.setElevation(dp(1));
        TextView a=tv(title,13,Color.rgb(100,116,139));
        TextView v=tv(value,22,Color.rgb(15,23,42));
        v.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        v.setMaxLines(1);
        c.addView(a);c.addView(v);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,dp(104),1);
        lp.setMargins(0,dp(4),dp(5),dp(4));
        p.addView(c,lp);
    }
    void render(String page){content.removeAllViews();
        if(page.equals("dashboard"))dashboard(); else if(page.equals("sales"))sales(); else if(page.equals("products"))products(); else if(page.equals("customers"))customers(); else if(page.equals("reports"))reports();
    }
    void dashboard(){LinearLayout row=new LinearLayout(this);row.setOrientation(LinearLayout.HORIZONTAL);long rev=0;for(Sale s:sales)rev+=s.total;card(row,"Doanh thu",V(rev));card(row,"Đơn hàng",""+sales.size());content.addView(row);LinearLayout row2=new LinearLayout(this);row2.setOrientation(LinearLayout.HORIZONTAL);long stock=0;for(Product p:products)stock+=(long)p.price*p.stock;card(row2,"Giá trị tồn kho",V(stock));card(row2,"Sản phẩm",""+products.size());content.addView(row2);
        content.addView(heading("Đơn hàng gần đây")); if(sales.size()==0)content.addView(tv("Chưa có đơn hàng. Bấm “Bán hàng” để tạo đơn.",15,Color.GRAY));for(int i=sales.size()-1;i>=0&&i>=sales.size()-5;i--)item(sales.get(i).customer,V(sales.get(i).total),"Hoàn thành");
        content.addView(heading("Tồn kho thấp"));for(Product p:products)if(p.stock<=5)item(p.name,"Còn "+p.stock+" sản phẩm","Cần nhập");
    }
    void sales(){content.addView(heading("Lịch sử bán hàng"));if(sales.size()==0)content.addView(tv("Chưa có đơn hàng.",15,Color.GRAY));for(int i=sales.size()-1;i>=0;i--)item("#"+(i+1)+" · "+sales.get(i).customer,V(sales.get(i).total),sales.get(i).payment);}
    void products(){content.addView(heading("Danh sách sản phẩm"));for(Product p:products)item(p.name,p.code+" · "+V(p.price),"Tồn: "+p.stock);}
    void customers(){content.addView(heading("Danh sách khách hàng"));for(Customer c:customers)item(c.name,c.phone,c.phone.isEmpty()?"Khách lẻ":"Khách hàng");}
    void reports(){long rev=0;for(Sale s:sales)rev+=s.total;content.addView(heading("Báo cáo kinh doanh"));card(content,"Tổng doanh thu",V(rev));card(content,"Số đơn hàng",""+sales.size());long avg=sales.size()==0?0:rev/sales.size();card(content,"Giá trị đơn trung bình",V(avg));content.addView(heading("Ghi chú"));content.addView(tv("Dữ liệu bán hàng được lưu cục bộ trên thiết bị. Hãy sao lưu thường xuyên.",14,Color.GRAY));}
    void item(String a,String b,String c){LinearLayout x=new LinearLayout(this);x.setOrientation(LinearLayout.VERTICAL);x.setPadding(16,13,16,13);x.setBackground(bg(Color.WHITE,18));x.addView(tv(a,16,Color.rgb(25,35,50)));x.addView(tv(b+"  •  "+c,13,Color.GRAY));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,ViewGroup.LayoutParams.WRAP_CONTENT);lp.setMargins(0,0,0,8);content.addView(x,lp);}
    void addProduct(){final LinearLayout f=form();EditText code=e("Mã sản phẩm"),name=e("Tên sản phẩm"),price=e("Giá bán"),stock=e("Tồn kho");f.addView(code);f.addView(name);f.addView(price);f.addView(stock);dialog("Thêm sản phẩm",f,()->{products.add(new Product(code.getText().toString(),name.getText().toString(),num(price),num(stock)));save();build("products");});}
    void addCustomer(){LinearLayout f=form();EditText name=e("Tên khách hàng"),phone=e("Số điện thoại");f.addView(name);f.addView(phone);dialog("Thêm khách hàng",f,()->{customers.add(new Customer(name.getText().toString(),phone.getText().toString()));save();build("customers");});}
    void newSale(){if(products.size()==0){toast("Chưa có sản phẩm");return;}LinearLayout f=form();Spinner spn=new Spinner(this);String[] names=new String[products.size()];for(int i=0;i<products.size();i++)names[i]=products.get(i).name+" — "+V(products.get(i).price);spn.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,names));EditText qty=e("Số lượng");f.addView(tv("Chọn sản phẩm",13,Color.DKGRAY));f.addView(spn);f.addView(qty);dialog("Tạo đơn hàng",f,()->{int q=(int)num(qty);if(q<1){toast("Số lượng không hợp lệ");return;}Product p=products.get(spn.getSelectedItemPosition());if(q>p.stock){toast("Không đủ tồn kho");return;}p.stock-=q;sales.add(new Sale("Khách lẻ",(long)p.price*q,"Tiền mặt"));save();build("sales");toast("Đã tạo đơn hàng");});}
    LinearLayout form(){LinearLayout f=new LinearLayout(this);f.setOrientation(LinearLayout.VERTICAL);f.setPadding(20,4,20,0);return f;}
    EditText e(String hint){EditText e=new EditText(this);e.setHint(hint);e.setSingleLine();e.setPadding(10,8,10,8);return e;}
    long num(EditText e){try{return Long.parseLong(e.getText().toString().trim());}catch(Exception x){return 0;}}
    void dialog(String title,View v,Runnable save){AlertDialog d=new AlertDialog.Builder(this).setTitle(title).setView(v).setNegativeButton("Hủy",null).setPositiveButton("Lưu",null).create();d.setOnShowListener(x->d.getButton(-1).setOnClickListener(y->{save.run();d.dismiss();}));d.show();}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    static class Product{String code,name;long price,stock;Product(String c,String n,long p,long s){code=c;name=n;price=p;stock=s;}}
    static class Sale{String customer,payment;long total;Sale(String c,long t,String p){customer=c;total=t;payment=p;}}
    static class Customer{String name,phone;Customer(String n,String p){name=n;phone=p;}}
}
