package com.example.room.chat.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created by igorek2312 on 23.01.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AbstractRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    protected void importJSON(String collection, String fileName) {
        try {
            File file = new File(fileName);
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode root = (ArrayNode) mapper.readTree(file);
            root.elements().forEachRemaining(jsonNode -> mongoTemplate.save(jsonNode.toString(), collection));
        } catch (IOException e) {
            throw new RuntimeException("Could not import fileName: " + fileName, e);
        }
    }
}
