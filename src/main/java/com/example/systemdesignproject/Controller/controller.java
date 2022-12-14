package com.example.systemdesignproject.Controller;

import com.example.systemdesignproject.Model.GoodList;
import com.example.systemdesignproject.Model.Goods;
import com.example.systemdesignproject.Model.Report;
import com.example.systemdesignproject.Model.Vision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/xd/v1")
public class controller {
    @GetMapping("/home")
    public String homePage(){
        return "homePage";
    }

    @GetMapping("/customerPage")
    public String customerPage(Model model){
        List<String> path=new ArrayList<String>();
        List<Goods>list;
        String imagePath1="/Users/waiting/imgs/water.png";
        String imagePath2="/Users/waiting/imgs/water1.png";
        String imagePath3="/Users/waiting/imgs/rice.png";
        String imagePath4="/Users/waiting/imgs/cha.png";
        String imagePath5="/Users/waiting/imgs/cha1.png";
        String imagePath6="/Users/waiting/imgs/cha2.png";
        String imagePath7="/Users/waiting/imgs/buding.png";
        String imagePath8="/Users/waiting/imgs/buding1.png";
        String imagePath9="/Users/waiting/imgs/bMilk.png";
        String imagePath10="/Users/waiting/imgs/bMilk2.png";
        path.add(imagePath1);
        path.add(imagePath2);
        path.add(imagePath3);path.add(imagePath4);path.add(imagePath5);path.add(imagePath6);path.add(imagePath7);path.add(imagePath8);path.add(imagePath9);path.add(imagePath10);
        Vision vision=new GoodList();
        vision.analysisImage(path);
//        list= vision.showTotalGoodsWithOrder(vision.getResult());
        list=vision.showTotalGoodsComparedTime();
//        if(!list.isEmpty()){
//            for(int i=0;i<list.size();i++){
//                System.out.println(list.get(i).name+" "+list.get(i).price);
//            }
//
//        }
        model.addAttribute("list",list);
        return "customer";
    }
    @GetMapping("/managerPage")
    public String managerPage(Model model){
        List<String> path=new ArrayList<String>();
        List<Goods>list;
        String imagePath1="/Users/waiting/imgs/water.png";
        String imagePath2="/Users/waiting/imgs/water1.png";
        String imagePath3="/Users/waiting/imgs/rice.png";
        String imagePath4="/Users/waiting/imgs/cha.png";
        String imagePath5="/Users/waiting/imgs/cha1.png";
        String imagePath6="/Users/waiting/imgs/cha2.png";
        String imagePath7="/Users/waiting/imgs/buding.png";
        String imagePath8="/Users/waiting/imgs/buding1.png";
        String imagePath9="/Users/waiting/imgs/bMilk.png";
        String imagePath10="/Users/waiting/imgs/bMilk2.png";
        path.add(imagePath1);
        path.add(imagePath2);
        path.add(imagePath3);path.add(imagePath4);path.add(imagePath5);path.add(imagePath6);path.add(imagePath7);path.add(imagePath8);path.add(imagePath9);path.add(imagePath10);
        Vision vision=new GoodList();
        vision.analysisImage(path);
        list= vision.showToManager();
        model.addAttribute("analysis",list);
        return "manager";
    }
//    @GetMapping("/report1")
//    public String report(@ModelAttribute("key")int key,@ModelAttribute("report")String report,Model model){
//        model.addAttribute("keycount",key);
//        model.addAttribute("content",report);
//        return "report";
//    }
    @GetMapping("/report")
    public String reportPage(){
        return "report";
    }
    @PostMapping("/report")
    public String report(@RequestParam("input")String input,
                         @RequestParam("inputname")String name
            ,Model model){//RedirectAttributes redirectAttributes
        Report report=new Report(name,input);
//        System.out.println(input);
        String judge=report.testContext();
//        System.out.println(judge);
        int key=report.id;
        String x=Integer.toString(key);
//        System.out.print(key);
//        redirectAttributes.addFlashAttribute("report",judge);
//        redirectAttributes.addFlashAttribute("key",key);
        model.addAttribute("report",judge);
        model.addAttribute("key",x);
        return "confirm";
        //"redirect:/report";
    }
    @GetMapping("/confirmPage")
    public String confirm(){
        return "confirm";
    }

}
