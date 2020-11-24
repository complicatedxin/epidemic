import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Test
{
    @org.junit.jupiter.api.Test
    public void t_gson() throws Exception
    {
        Gson gson=new Gson();
//        Map map=gson.fromJson("{\"hello\":\"world\"}", Map.class);
//        System.out.println(map);
        FileReader fr=new FileReader("temp.json");
        StringBuilder sb=new StringBuilder();
        char c[]=new char[1024];
        int count=fr.read(c);
        while(count!=-1)
        {
            sb.append(new String(c,0,count));
            count=fr.read(c);
        }
        fr.close();
        Map infoMap=gson.fromJson(sb.toString(), Map.class);
        Map chinaTotalMap= (Map) infoMap.get("chinaTotal");
        Map chinaAddMap= (Map) infoMap.get("chinaAdd");
        List areaTreeList= (List) infoMap.get("areaTree");
        Map areaTreeMap= (Map) areaTreeList.get(0);
        List provinceList= (List) areaTreeMap.get("children");
        Map provinceInfoMap=null;
        for(int i=0;i<provinceList.size();i++)
        {
            provinceInfoMap= (Map) provinceList.get(i);
            System.out.println(provinceInfoMap);
            List cityList= (List) provinceInfoMap.get("children");
            Map cityInfoMap=null;
            for(int l=0;l<cityList.size();l++)
            {
                cityInfoMap= (Map) cityList.get(l);
                System.out.println("——"+cityInfoMap);
            }
        }



    }
}
