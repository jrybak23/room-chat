package com.example.room.chat.repositories;

import com.example.room.chat.config.MongoConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * Abstract MongoDB integration test class.
 *
 * @author Igor Rybak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoConfig.class)
public abstract class AbstractRepositoryTest {
    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * Import sample data from JSON file to MongoDB. JSON file should contain array with documents of collection.
     *
     * @param collection name of collection
     * @param fileName   path to json file
     */
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
