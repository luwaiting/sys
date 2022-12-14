package com.example.systemdesignproject.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GoodList extends Vision{
    public List<List<Goods>>sameExpireList;
//    public List<Goods>eachGood;
    public void sortGoodsToSameEX(List<String> textResult)throws ParseException {
        sameExpireList=new ArrayList<List<Goods>>();
        List<Goods>goodGroup;
        Map<String,String> initiate=new HashMap<String,String>();

        int count=0;
       for(String textR:textResult){
           textR+="\n";
            int indexStart=0;
            int indexClose=0;
           goodGroup=new ArrayList<Goods>();
            while((indexClose=textR.indexOf("\n",indexStart))!=-1&&indexStart<=textR.length()){
//                indexClose=textR.indexOf("\n",indexStart);
               String paragraph=textR.substring(indexStart,indexClose);//next is +1
                int firstValue=paragraph.indexOf(":");
//                System.out.print(firstValue);
                if(firstValue==-1){
                    firstValue=paragraph.indexOf(" ");
                }
                int finalValue=paragraph.length();
                String goodValue=paragraph.substring(firstValue+1,finalValue);
                String key=paragraph.substring(0,firstValue);
               initiate.put(key,goodValue);
               indexStart=indexClose+1;
            }
            String name=initiate.get("品名");
            String price= initiate.get("價格");
//           System.out.print(p);
            String expire=initiate.get("有效日期");
//            System.out.print(expire);
            Goods goods=new Goods(name,expire,price);
            if(sameExpireList.isEmpty()){
                goodGroup.add(goods);
                sameExpireList.add(goodGroup);
            }else{
                int j=0;
                for(int i=0;i<sameExpireList.size();i++){
                    if(sameExpireList.get(i).get(j).expiration.equals(expire)){
                        goodGroup.add(goods);
                        sameExpireList.get(i).add(goods);
                    }
                }
                if(goodGroup.isEmpty()){
                    goodGroup.add(goods);
                    sameExpireList.add(goodGroup);
//                    Date date=simpleDateFormat.parse(expire);
//                    for(int i=0;i<sameExpireList.size();i++){
//                        Date date1=simpleDateFormat.parse(sameExpireList.get(i).get(j).expiration);
//                        if(date.after(date1)){
//                            sameExpireList.add(goodGroup);
//                        }else if(date.before(date1)||date.equals(date1)){
//                            sameExpireList.add(i,goodGroup);
//                        }else{
//                            break;
//                        }
//                    }
                }
            }
       }
        List<List<Goods>>e=sortAmount(sameExpireList);
       sameExpireList=e;
                //按照時間先後,且貨物不重複

    }
    public List<List<Goods>>getResult(){
        return sameExpireList;
    }
    public List<List<Goods>>sortAmount(List<List<Goods>>sameExpList){
        List<List<Goods>>result=new ArrayList<List<Goods>>();
        List<Goods>newGoodgroup=new ArrayList<Goods>();
        Goods goods=null;
        for(int i=0;i<sameExpList.size();i++){
            int c=0;
            int count=0;
            for (int j=0;j<sameExpList.get(i).size();j++){
                String name=sameExpList.get(i).get(c).name;
                if(goods==null){
                    if(name.equals(sameExpList.get(i).get(j).name)){
                        goods=sameExpList.get(i).get(j);
                        sameExpList.get(i).remove(j);
                        count++;
                        j=-1;
                    }else{
                        continue;
                    }
                }else{
                    if(goods.name.equals(sameExpList.get(i).get(j).name)){
                        goods=sameExpList.get(i).get(j);
                        sameExpList.get(i).remove(j);
                        count++;
                        j=-1;
                    }else{
                        if(j==sameExpList.get(i).size()-1){
                            Goods setedAmountGood=new Goods(goods.name,goods.expiration, goods.price);
                            String coun=String.valueOf(count);
                            setedAmountGood.addAmount(coun);
                            newGoodgroup.add(setedAmountGood);
                            j=-1;
                            goods=null;
                            count=0;
                        }
                        continue;
                    }
                }
                if (j==sameExpList.get(i).size()-1){
                    Goods setedAmountGood=new Goods(goods.name,goods.expiration, goods.price);
                    String coun=String.valueOf(count);
                    setedAmountGood.addAmount(coun);
                    newGoodgroup.add(setedAmountGood);
                    j=-1;
                    goods=null;
                    count=0;
                }
            }
            result.add(newGoodgroup);
            newGoodgroup=new ArrayList<Goods>();
        }
        return result;
    }
    public List<Goods>showTotalGoodsComparedTime(){
        List<Goods> list=showTotalGoodsWithOrder(sameExpireList);
        List<Goods>sortedByTime=new ArrayList<Goods>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
        Date current=new Date();
        for(Goods g:list){
            try {
                Date date1=simpleDateFormat.parse(g.expiration);
                long diff=(date1.getTime()-current.getTime())/(1000 * 60 * 60 * 24);
                if(diff<=3&&diff>=0){
                    String originValue=g.price;
                    int changePrice=(int)(Integer.parseInt(g.price)*0.7);
                    String re=String.valueOf(changePrice);
                    g.setPrice(originValue+"-->"+re);
                    sortedByTime.add(g);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return sortedByTime;
    }
    public List<Goods>showToManager(){
        List<Goods>list=showTotalGoodsWithOrder(sameExpireList);
//        List<Goods>display=new ArrayList<Goods>();
        for(int i=0;i<list.size();i++){
            if(i%2==0){
                int amount=Integer.parseInt(list.get(i).amount);
                String afterA=String.valueOf((int)(amount*0.7));
                list.get(i).addAmount(afterA+" / "+amount);
            }else{
                int amount=Integer.parseInt(list.get(i).amount);
                String afterA=String.valueOf((int)(amount*0.4));
                list.get(i).addAmount(afterA+" / "+amount);
            }
        }
        return list;
    }

    public List<Goods>showTotalGoodsWithOrder(List<List<Goods>>sameExpList){
        List<Goods>display=new ArrayList<Goods>();
        for(int i=0;i<sameExpList.size();i++){
            for(int j=0;j<sameExpList.get(i).size();j++){
                display.add(sameExpList.get(i).get(j));
            }
        }
        return display;
    }
}
