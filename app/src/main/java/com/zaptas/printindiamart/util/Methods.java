package com.zaptas.printindiamart.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by coderzlab on 16/7/15.
 */
public class Methods {







    public static boolean saveUSERID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("userid",cif).commit();
    }
    public static String getUSERID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("userid", null);
    }

    public static boolean savecin(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("cin",cif).commit();
    }
    public static String getcin(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("cin", null);
    }
    public static boolean savedghft(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("dghft",cif).commit();
    }
    public static String getdghft(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("dghft", null);
    }
    public static boolean savenemployee(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("nemployee",cif).commit();
    }
    public static String getnemployee(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("nemployee", null);
    }  public static boolean savebtype(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("btype",cif).commit();
    }
    public static String getbtype(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("btype", null);
    }public static boolean saveanualturn(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("anualturn",cif).commit();
    }
    public static String getanualturn(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("anualturn", null);
    }public static boolean saveownertype(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("ownertype",cif).commit();
    }
    public static String getownertype(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("ownertype", null);
    }public static boolean savegst(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("gst",cif).commit();
    }
    public static String getgst(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("gst", null);
    }
    public static String getturn(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("turn", null);
    }public static boolean saveturn(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("turn",cif).commit();
    }


    public static String getpan(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("pan", null);
    }public static boolean savepan(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("pan",cif).commit();
    }


public static boolean savetan(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("tan",cif).commit();
    }
    public static String gettan(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("tan", null);
    }public static boolean savenemp(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("nemp",cif).commit();
    }
    public static String getnemp(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("nemp", null);
    }


    public static boolean saveUSERNAME(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("USERNAME",cif).commit();
    }
    public static String getUSERNAME(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("USERNAME", null);
    }

    public static boolean saveLUSERNAME(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("LUSERNAME",cif).commit();
    }
    public static String getLUSERNAME(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString(":USERNAME", null);
    }
    public static boolean saveREGID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("REGID",cif).commit();
    }
    public static String getREGID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString(":REGID", null);
    }
    public static boolean savechatname(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("CHATNAME",cif).commit();
    }
    public static String getchatname(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("CHATNAME", null);
    }



    public static boolean savechatid(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("CHATNAMEID",cif).commit();
    }
    public static String getchatid(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("CHATNAMEID", null);
    }



    public static boolean saveSession(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("SESSION",cif).commit();
    }
    public static String getSession(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("SESSION", null);
    }


    public static boolean saveEmailID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("emailid",cif).commit();
    }
    public static String getEmailID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("emailid", null);
    }


    public static boolean saveUSERTYPE(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("usertype",cif).commit();
    }
    public static String getUSERTYPE(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("usertype", null);
    }





    public static boolean savepack(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("pack",cif).commit();
    }
    public static String getpack(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("pack", null);
    }

    public static boolean saveAdress(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("adress",cif).commit();
    }
    public static String getAdress(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("adress", null);
    }


    public static boolean saveCity(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("city",cif).commit();
    }
    public static String getCity(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("city", null);
    }

    public static boolean saveState(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("state",cif).commit();
    }
    public static String getState(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("state", null);
    }

    public static boolean saveCountry(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("country",cif).commit();
    }
    public static String getCountry(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("country", null);
    }

    public static boolean savePinCode(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("pincode",cif).commit();
    }
    public static String getPinCode(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("pincode", null);
    }

    public static boolean saveMobileNo(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("mobileno",cif).commit();
    }
    public static String getMobileNo(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("mobileno", null);
    }

    public static boolean saveUserIMG(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("UserIMG",cif).commit();
    }
    public static String getUserIMG(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("UserIMG", null);
    }
    public static boolean saveLogo(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("logo",cif).commit();
    }
    public static String getLogo(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("logo", null);
    }














    public static boolean saveDOB(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("DOB",cif).commit();
    }
    public static String getDOB(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("DOB", null);
    }

    public static boolean saveGENDER(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("GENDER",cif).commit();
    }
    public static String getGENDER(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("GENDER", null);
    }
    public static boolean savepack_zaptas(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("pack",cif).commit();
    }
    public static String getpack_zaptas(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("pack", null);
    }
    public static boolean savepack_startdate(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("start_p",cif).commit();
    }
    public static String getpack_startdate(Context context) {
        SharedPreferences sharedPreferences = getPreferances(context);
        return sharedPreferences.getString("start_p", null);
    }
    public static boolean savepack_enddate(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("end_p",cif).commit();
    }
    public static String getpack_enddate(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("end_p", null);
    }




    public static boolean saveCategoryIdd(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("CategoryIdd",cif).commit();
    }
    public static String getCategoryIdd(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("CategoryIdd", null);
    }



    public static boolean saveCategoryIdd2(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("CategoryIdd2",cif).commit();
    }
    public static String getCategoryIdd2(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("CategoryIdd2", null);
    }



    public static boolean saveCategoryNamee(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("CategoryNamee",cif).commit();
    }
    public static String getCategoryNamee(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("CategoryNamee", null);
    }



    public static boolean saveCategoryName2(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("CategoryName2",cif).commit();
    }
    public static String getCategoryName2(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("CategoryName2", null);
    }



    public static boolean saveSubCategoryName2(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("SubCategoryName2",cif).commit();
    }
    public static String getSubCategoryName2(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("SubCategoryName2", null);
    }




    public static boolean saveSearchedName(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("SearchedName",cif).commit();
    }
    public static String getSearchedName(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("SearchedName", null);
    }





    public static boolean saveSearchedEmail(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("SearchedEmail",cif).commit();
    }
    public static String getSearchedEmail(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("SearchedEmail", null);
    }





    public static boolean saveabout(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("about",cif).commit();
    }
    public static String getabout(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("about", null);
    }

    public static boolean saveComapny(Context context, String Comapny){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("Comapny",Comapny).commit();
    }
    public static String getComapny(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("Comapny", null);
    }


// HIERARCHY_SALES_USERNAME

    public  static SharedPreferences getPreferances(Context context){
       return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

    }
}
