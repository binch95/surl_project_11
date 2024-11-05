package com.koreait.surl_project_11;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SurlController {

    private List<Surl> surls = new ArrayList<>();
    private long surlsLastId;

    @GetMapping("/add")
    @ResponseBody
    public Surl add(String body, String url) {
        Surl surl = Surl.builder()
                .id(++surlsLastId)
                .body(body)
                .url(url)
                .build();
        surls.add(surl);
        return surl;
    }

    @GetMapping("/s/{body}/**")
    @ResponseBody
    public String add(
            @PathVariable String body,
            HttpServletRequest req) {
       String url =  req.getRequestURI();

       if (req.getQueryString() != null){
           url = url + "?" + req.getQueryString();
       }
       String[] urlBilt = url.split("/",4);
        url = urlBilt[3];
        add(body,url);

       return url;
    }

    @GetMapping("/g/{id}")
    @ResponseBody
    public String go(
            @PathVariable long id) {
        Surl surl = surls.stream()
                .filter(_surl -> _surl.getId() == id)
                .findFirst()
                .orElse(null);
        if (surl == null) throw new RuntimeException("%번 데이터를 찾을 수 없어".formatted(id)) ;

        surl.increaseCount();

        return "redirect:" + surl.getUrl();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Surl> getAll(){
        return surls;
    }
}
