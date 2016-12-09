package com.sorcererxw.matthiasheiderichphotography.util;

import android.util.Log;

import com.sorcererxw.matthiasheidericphotography.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/10/13
 */

public class WebCatcher {
    public static Observable<List<String>> catchImageLinks(String pageAddress) {
        return Observable.just(pageAddress)
//                .map(new Func1<String, List<String>>() {
//                    @Override
//                    public List<String> call(String pageAddress) {
//                        List<String> list = new ArrayList<>();
//                        try {
//                            Document document = Jsoup.connect(pageAddress)
//                                    .userAgent(
//                                            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)")
//                                    .get();
//                            Elements nodes = document.getElementsByClass("slideshow stacked")
//                                    .get(0).getElementsByClass("slide");
//                            for (int i = 0; i < nodes.size(); i++) {
//                                list.add(nodes.get(i).getElementsByClass("loading").get(0)
//                                        .select("data-src").val());
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return list;
//                    }
//                })
//                .subscribeOn(Schedulers.io());

                .map(new Function<String, String>() {
                    @Override
                    public String apply(String path) {
                        URL url;
                        String res = "";
                        try {
                            url = new URL(path);
                            URLConnection connection = url.openConnection();
                            connection.setRequestProperty("User-Agent",
                                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));

                            String inputLine;
                            while ((inputLine = br.readLine()) != null) {
                                res += inputLine;
                            }
                            br.close();
                        } catch (IOException e) {
                            if (BuildConfig.DEBUG) {
                                e.printStackTrace();
                            }
                        }
                        return res;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String s) {
                        Log.d("main", s);
                        Pattern p = Pattern.compile(
                                "data-image=\"https://static1.squarespace.com/static/[0-9a-z]*/[0-9a-z]*/[0-9a-z]*/[0-9a-z]*/[a-zA-Z0-9-_.]*\"");
                        Matcher m = p.matcher(s);
                        List<String> list = new ArrayList<>();
                        while (m.find()) {
                            String tmp = m.group(0).split("\"")[1];
                            if (!list.contains(tmp)) {
                                list.add(tmp);
                            }
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.computation());
    }
}
