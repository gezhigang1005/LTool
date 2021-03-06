package xiaoliang.ltool.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import xiaoliang.ltool.bean.MeizhiBean;

/**
 * Created by liuj on 2016/10/3.
 * 妹纸图的数据解析类
 */

public class MeizhiUtil {

    public static ArrayList<MeizhiBean> getGankImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        if(json==null||json.length()<1) {
            return beans;
        }
        try{
            JSONObject jsonObject = new JSONObject(json);
            if(jsonObject.getBoolean("error")){
                return beans;
            }
            JSONArray results = jsonObject.getJSONArray("results");
            for(int i = 0;i<results.length();i++){
                MeizhiBean bean = new MeizhiBean(results.getJSONObject(i).getString("url"));
                bean.from = bean.url;
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getGankImgUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getDoubanImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        try{
            json = json.substring(json.indexOf("<body>"),json.indexOf("</body>"));
            String[] imgs = json.split("<div class=\"img_single\">");
            for(String img : imgs){
                if(!img.contains("bmiddle"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("title=\"")+7);
                bean.title = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("bmiddle/")+8);
                bean.url = "http://ww2.sinaimg.cn/large/"+img.substring(0,img.indexOf("\""));
                beans.add(bean);
//                Log.d("getDoubanImgUrl",bean.toString());
            }
        }catch (Exception e){
            Log.d("getDoubanImgUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getMeizhi51ImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMeizhi51ImgUrl","start");
        try{
            json = json.substring(json.indexOf("m-list-main"),json.indexOf("一页"));
            String[] imgs = json.split("<li");
            for(String img : imgs){
//                Log.d("getMeizhi51ImgUrl",img);
                if(!img.contains("u-img"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("title=\"")+7);
                bean.title = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("data-original=\"")+15);
                bean.url = img.substring(0,img.indexOf("\""));
                Log.d("getMeizhi51ImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMeizhi51ImgUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getMeizhi51PageImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMeizhi51PageImgUrl","start");
        try{
            json = json.substring(json.indexOf("gallery"),json.indexOf("标签"));
            json = json.replaceAll("84x125","236x354");
            String[] imgs = json.split("<li");
            for(String img : imgs){
//                Log.d("getMeizhi51PageImgUrl",img);
                if(!img.contains("swl-item"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("data-original=\"")+15);
                bean.url = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("<span>")+6);
                bean.title = img.substring(0,img.indexOf("</span>"));
                Log.d("getMeizhi51ImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMeizhi51ImgUrl",e.getMessage());
        }
        return beans;
    }

    public static String getMeizhi51DetailImgUrl(String json){
        String url = "";
        try{
            json = json.substring(json.indexOf("bigImg"));
            json = json.substring(json.indexOf("src=\"")+5);
            url = json.substring(0,json.indexOf("\""));
            Log.d("getMeizhi51DetailImgUrl",url);
        }catch (Exception e){
            Log.d("getMeizhi51DetailImgUrl",e.getMessage());
        }
        return url;
    }

    public static ArrayList<MeizhiBean> getMMHomeImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMMHomeImgUrl","start");
        try{
            json = json.substring(json.indexOf("<ul>"),json.indexOf("</ul>"));
            String[] imgs = json.split("<li");
            for(String img : imgs){
//                Log.d("getMMHomeImgUrl",img);
                if(!img.contains("_blank"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("alt=\"")+5);
                bean.title = img.substring(0,img.indexOf("\""));
                Log.d("getMMHomeImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMMHomeImgUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getMMHotImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMMHotImgUrl","start");
        try{
            String[] imgs = json.split("<li");
            for(String img : imgs){
//                Log.d("getMMHotImgUrl",img);
                if(!img.contains("<img"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                if(img.indexOf("title=\"")>0){
                    img = img.substring(img.indexOf("title=\"")+7);
                    bean.title = img.substring(0,img.indexOf("\""));
                }
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
                if(img.indexOf("alt=\"")>0){
                    img = img.substring(img.indexOf("alt=\"")+5);
                    bean.title = img.substring(0,img.indexOf("\""));
                }
                Log.d("getMMHotImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMMHotImgUrl",e.getMessage());
        }
        return beans;
    }
    public static ArrayList<MeizhiBean> getMMRecommendedImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMMRecommendedImgUrl","start");
        try{
            String[] imgs = json.split("<li");
            for(String img : imgs){
//                Log.d("getMMRecommendedImgUrl",img);
                if(!img.contains("_blank"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("alt=\"")+5);
                bean.title = img.substring(0,img.indexOf("\""));
                Log.d("getMMRecommendedImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMMRecommendedImgUrl",e.getMessage());
        }
        return beans;
    }
    public static ArrayList<MeizhiBean> getMMLabelImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMMLabelImgUrl","start");
        try{
            String[] imgs = json.split("<li");
            for(String img : imgs){
//                Log.d("getMMLabelImgUrl",img);
                if(!img.contains("_blank"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("alt=\"")+5);
                bean.title = img.substring(0,img.indexOf("\""))+"\n";
                img = img.substring(img.indexOf("<i>")+3);
                bean.title += img.substring(0,img.indexOf("</i>"));
                Log.d("getMMLabelImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMMLabelImgUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getMMImgListUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMMImgListUrl","start");
        try{
            json = json.substring(json.indexOf("picinfo"));
            json = json.substring(json.indexOf("[")+1,json.indexOf("]"));
            String[] imgs = json.split(",");
            int size = Integer.parseInt(imgs[2].trim());
            for(int i = 1;i<=size;i++){
                MeizhiBean bean = new MeizhiBean();
                bean.from = bean.url = "http://img.mmjpg.com/"+imgs[0]+"/"+imgs[1]+"/"+i+".jpg";
                bean.title = i+"/"+imgs[2];
                Log.d("getMMImgListUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMMImgListUrl",e.getMessage());
        }
        return beans;
    }
    public static ArrayList<MeizhiBean> getMMLableListUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMMLableListUrl","start");
        try{
            String msg = "";
            int page;
            String pageStr;
            if(json.contains("summary")){
                json = json.substring(json.indexOf("summary"));
                json = json.substring(json.indexOf("<span>")+6);
                msg = json.substring(0,json.indexOf("</span>"));
            }
            pageStr = json.substring(json.indexOf("page"));
            try{
                page = Integer.parseInt(pageStr.substring(pageStr.indexOf("共")+1,pageStr.indexOf("页")));
            }catch (Exception e){
                page = 1;
            }
            json = json.substring(json.indexOf("<ul>"),json.indexOf("</ul>"));
            String[] imgs = json.split("<li");
            for(String img:imgs){
                if(!img.contains("_blank"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                bean.pagination = page;
                bean.other = msg;
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("alt=\"")+5);
                bean.title = img.substring(0,img.indexOf("\""));
                Log.d("getMMLableListUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMMLableListUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getMeizhituImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMeizhituImgUrl","start");
        try{
            json = json.substring(json.indexOf("wp-list"),json.indexOf("navigation"));
            String[] imgs = json.split("<li");
            for(String img : imgs){
//                Log.d("getMeizhituImgUrl",img);
                if(!img.contains("wp-item"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
//                img = img.substring(img.indexOf("alt=\"")+5);
//                bean.title = img.substring(0,img.indexOf("\"")).replaceAll("<b>|</b>","");
//                bean.title = URLDecoder.decode(bean.title, "UTF-8");
//                bean.title = URLDecoder.decode(bean.title, "GB2312");
//                bean.title = new String(bean.title.getBytes(), "GB2312");
//                bean.title = new String(bean.title.getBytes(), "GB2312");
                Log.d("getMeizhituImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMeizhituImgUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getMeizhituImgListUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMeizhituImgListUrl","start");
        try{
            json = json.substring(json.indexOf("postContent"));
            json = json.substring(0,json.indexOf("</p>"));
            String[] imgs = json.split("<img");
            for(String img : imgs){
//                Log.d("getMeizhituImgListUrl",img);
                if(!img.contains("src"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
//                img = img.substring(img.indexOf("alt=\"")+5);
//                bean.title = img.substring(0,img.indexOf("\""));
//                bean.title = URLDecoder.decode(bean.title, "GB2312");
//                bean.title = new String(bean.title.getBytes(), "GB2312");
                img = img.substring(img.indexOf("src=\"")+5);
                bean.from = bean.url = img.substring(0,img.indexOf("\""));
                Log.d("getMeizhituImgListUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMeizhituImgListUrl",e.getMessage());
        }
        return beans;
    }

    public static ArrayList<MeizhiBean> getMeituluImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMeituluImgUrl","start");
        try{
            json = json.substring(json.indexOf("class=\"img\""));
            String[] imgs = json.split("<li>");
            for(String img : imgs){
//                Log.d("getMeituluImgUrl",img);
                if(!img.contains("src"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("alt=\"")+5);
                bean.title = img.substring(0,img.indexOf("\""));
                if(img.indexOf("图片：")>0){
                    img = img.substring(img.indexOf("图片：")+3);
                }else{
                    img = img.substring(img.indexOf("数量：")+3);
                }
                bean.title += "\n"+img.substring(0,img.indexOf("</"));
                Log.d("getMeituluImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMeituluImgUrl",e.getMessage());
        }
        return beans;
    }
    public static ArrayList<MeizhiBean> getMeituluHotImgUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMeituluHotImgUrl","start");
        try{
            json = json.substring(json.indexOf("boxs"));
            String[] imgs = json.split("<li>");
            for(String img : imgs){
//                Log.d("getMeituluHotImgUrl",img);
                if(!img.contains("src"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                img = img.substring(img.indexOf("href=\"")+6);
                bean.page = bean.from = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("src=\"")+5);
                bean.url = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("alt=\"")+5);
                bean.title = img.substring(0,img.indexOf("\""));
                img = img.substring(img.indexOf("数量：")+3);
                bean.title += "\n"+img.substring(0,img.indexOf("</"));
                Log.d("getMeituluHotImgUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMeituluHotImgUrl",e.getMessage());
        }
        return beans;
    }
    public static ArrayList<MeizhiBean> getMeituluImgListUrl(String json){
        ArrayList<MeizhiBean> beans = new ArrayList<>();
        Log.d("getMeituluImgListUrl","start");
        try{
            String msg = "";
            int page;
            String pageStr;
            if(json.contains("buchongshuoming")){
                json = json.substring(json.indexOf("buchongshuoming"));
                json = json.substring(json.indexOf("</span>")+7);
                msg = json.substring(0,json.indexOf("</p>"));
            }
            pageStr = json.substring(json.indexOf("pages"),json.lastIndexOf("a1"));
            try{
                page = Integer.parseInt(pageStr.substring(pageStr.lastIndexOf("\">")+2,pageStr.lastIndexOf("</a>")));
            }catch (Exception e){
                page = 1;
            }
            json = json.substring(json.indexOf("class=\"content\""),json.indexOf("pages"));
            String[] imgs = json.split("<img");
            for(String img:imgs){
                if(!img.contains("src"))
                    continue;
                MeizhiBean bean = new MeizhiBean();
                bean.pagination = page;
                bean.other = msg;
                img = img.substring(img.indexOf("src=")+4);
                bean.from = bean.url = img.substring(0,img.indexOf(" alt"));
//                bean.url = "http://img.buxidai.com/uploadfile/2015/0928/20150928045305932.jpg";
                img = img.substring(img.indexOf("alt=\"")+5);
                bean.title = img.substring(0,img.indexOf("\""));
                Log.d("getMeituluImgListUrl",bean.toString());
                beans.add(bean);
            }
        }catch (Exception e){
            Log.d("getMeituluImgListUrl",e.getMessage());
        }
        return beans;
    }
}
