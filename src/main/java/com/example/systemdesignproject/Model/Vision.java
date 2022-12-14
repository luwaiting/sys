package com.example.systemdesignproject.Model;

import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Vision {
    public void analysisImage(List<String> imagePath) {
        try{
            List<String>textResults=new ArrayList<String>();
            int count=0;
            for(String image:imagePath){
                try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
                    Path path = Paths.get(image);
                    byte[] data = Files.readAllBytes(path);
                    ByteString imgBytes = ByteString.copyFrom(data);
                    List<AnnotateImageRequest> requests = new ArrayList<>();
                    Image imageR = Image.newBuilder().setContent(imgBytes).build();
                    Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
                    AnnotateImageRequest request =
                            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(imageR).build();
                    requests.add(request);
                    AnnotateImageResponse visionResponse;
                    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                        visionResponse = client.batchAnnotateImages(requests).getResponses(0);
                        if (visionResponse == null || !visionResponse.hasFullTextAnnotation()) {
                            System.out.print("error1");
                        }
                        if (visionResponse.hasError()) {
                            System.out.print("error2");
                        }
                        String textResult = visionResponse.getFullTextAnnotation().getText();
                        textResults.add(textResult);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
//          count++;
//          if(count==3){
//              break;
//          }
            }
            sortGoodsToSameEX(textResults);
        }catch (Exception e){
            e.printStackTrace();
        }

//        List<List<Goods>>sorted=sortGoodsToSameEX(textResults);
//        List<Goods>displayTotalGoods=showTotalGoodsWithOrder(sorted);
//        return displayTotalGoods;
    }
    public abstract List<Goods>showToManager();
    public abstract List<Goods>showTotalGoodsComparedTime();
    public abstract void sortGoodsToSameEX(List<String> textResult) throws ParseException;
    public abstract List<Goods>showTotalGoodsWithOrder(List<List<Goods>>sameExpList);
    public abstract List<List<Goods>>getResult();
}
