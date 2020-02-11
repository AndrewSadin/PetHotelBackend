package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("client")
public class ClientController {
    private int counter = 4;
    private List<Map<String,String>> clients = new ArrayList<Map<String,String>>(){{
        add(new HashMap<String,String>(){{put("id","1");put("name", "Ivan");}});
        add(new HashMap<String,String>(){{put("id","2");put("name", "Petya");}});
        add(new HashMap<String,String>(){{put("id","3");put("name", "Semen");}});
    }};

    @GetMapping
    public HashMap<String, List> list(){
        return new HashMap<String, List>(){{put("clients",clients);}};
    }

    @GetMapping("{id}")
    public Map<String,String> getClient(@PathVariable String id){
        return clients.stream()
                .filter(client -> client.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String,String> add(@RequestBody Map<String,String> client){
        client.put("id", String.valueOf(counter++));
        clients.add(client);
        return client;
    }

}
