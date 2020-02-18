package com.example.demo;

import com.example.demo.db.Tables;
import com.example.demo.db.tables.Clients;
import com.example.demo.db.tables.daos.ClientsDao;
import com.example.demo.db.tables.records.ClientsRecord;
import org.jooq.*;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

@RestController
@RequestMapping("client")
public class ClientController {
    private int counter = 4;
    private List<Map<String,String>> clients = new ArrayList<Map<String,String>>(){{
        add(new HashMap<String,String>(){{put("id","1");put("name", "Ivan");}});
        add(new HashMap<String,String>(){{put("id","2");put("name", "Petya");}});
        add(new HashMap<String,String>(){{put("id","3");put("name", "Semen");}});
    }};

    String user = "pethotel";
    String pass = "pethotel";
    String url = "jdbc:postgresql://localhost:5432/petHotel";

    // Create a JDBC Connection
    Connection conn = DriverManager.getConnection(url, user, pass);
    DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);

    Configuration configuration = new DefaultConfiguration().set(conn).set(SQLDialect.POSTGRES);

    // Initialise the DAO with the Configuration
    ClientsDao clientsDao = new ClientsDao(configuration);

    public ClientController() throws SQLException {
    }


    @GetMapping
    public List list() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List client = clientsDao.findAll();
        return client;
    }

    private Map<String,String> getClientById(@PathVariable String id){
        return clients.stream()
                .filter(client -> client.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("{id}")
    public Map<String,String> getClient(@PathVariable String id){
        return getClientById(id);
    }

    @PostMapping
    public Map<String,String> add(@RequestBody Map<String,String> client){
        client.put("id", String.valueOf(counter++));
        clients.add(client);
        return client;
    }

    @DeleteMapping("{id}")
    public Map<String,String> deleteClient(@PathVariable String id){
        Map<String,String> client = getClientById(id);
        clients.remove(client);
        return client;
    }

}
