package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.HistoryOrderRepository;
import com.gzpclass.supdem.domain.HistoryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/historyorder")
public class HistoryOrderController {

    @Autowired
    private HistoryOrderRepository repository;

    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<HistoryOrder> list(){return repository.findAll();}

    @CrossOrigin(origins = "*")
    @GetMapping("/cluster")
    public List<HistoryOrder> Clusterlist(@RequestParam(value = "Num",required = false,defaultValue = "2") Integer num){
        List<HistoryOrder> Ori = repository.findAll();
        MyCluster(Ori,num);
        return Ori;
    }

    public void MyCluster(List<HistoryOrder> Ori,Integer num){
        List<HistoryOrder> CluPoints = new ArrayList<>(num);
        for(int i=0;i<num;i++){
            CluPoints.add(Ori.get(i));
        }
        for(HistoryOrder Point:Ori){
            List<Double> CluDis=new ArrayList<>(num);
            for(int i=0;i<num;i++){
                HistoryOrder tPoint = CluPoints.get(i);
                double dis = distance(tPoint.getH_lat(),Point.getH_lat(),tPoint.getH_lng(),Point.getH_lng());
                CluDis.add(dis);
            }
            int tflag = SelectMin(CluDis);
            Point.setFlag(tflag);
        }
        List<Double> CluLat = new ArrayList<>(num);
        List<Double> CluLng = new ArrayList<>(num);
        for(int i=0;i<num;i++){
            CluLat.add(averageLat(Ori,i,0));
            CluLng.add(averageLat(Ori,i,1));
        }
        Boolean ttflag=false;
        outLoof:for(int i=0;i<num;i++){
            HistoryOrder tmpt_Point=CluPoints.get(i);
            if(tmpt_Point.getH_lat()!=CluLat.get(i)||tmpt_Point.getH_lng()!=CluLng.get(i)){
                ttflag=true;
                break outLoof;
            }
        }
        if(ttflag){
            Kmeans(Ori,CluLat,CluLng);
        }
    }

    public int SelectMin(List<Double> dis){
        Double mymin=dis.get(0);
        int myindex = 0;
        for(int i=1;i<dis.size();i++){
            if(dis.get(i)<mymin){
                mymin=dis.get(i);
                myindex = i;
            }
        }
        return myindex;
    }

    public double averageLat(List<HistoryOrder> Ori,Integer flag,Integer IsLat){
        double lat=0;
        int n=0;
        for(HistoryOrder Point:Ori){
            if(Point.getFlag()==flag){
                n++;
                if(IsLat==0){
                    lat+=Point.getH_lat();
                }
                else{
                    lat+=Point.getH_lng();
                }
            }
        }
        lat=lat/n;
        return lat;
    }

    public void Kmeans(List<HistoryOrder> Ori,List<Double> lat,List<Double> lng){
        int num = lat.size();
        for(HistoryOrder Point:Ori){
            List<Double> CluDis=new ArrayList<>(num);
            for(int i=0;i<num;i++){
                double dis = distance(lat.get(i),Point.getH_lat(),lng.get(i),Point.getH_lng());
                CluDis.add(dis);
            }
            Integer tflag = SelectMin(CluDis);
            Point.setFlag(tflag);
        }
        List<Double> tCluLat = new ArrayList<>(num);
        List<Double> tCluLng = new ArrayList<>(num);
        for(int i=0;i<num;i++){
            tCluLat.add(averageLat(Ori,i,0));
            tCluLng.add(averageLat(Ori,i,1));
        }
        if(tCluLat.equals(lat)&tCluLng.equals(lng)){

        }
        else{
            Kmeans(Ori,tCluLat,tCluLng);
        }

    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // 地球半径
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // 单位转换成米
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

}
