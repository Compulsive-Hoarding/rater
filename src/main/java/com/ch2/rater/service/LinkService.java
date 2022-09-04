package com.ch2.rater.service;

import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LinkService {
    private static final String ADD_LINK_QUERY = "Merge (l:Link {id: $link_id}) " +
            "With * " +
            "Merge (u:User {id: $user_id}) " +
            "With * " +
            "Merge (u)-[r:Rated]->(l)";

    private final Driver driver;

    public void addLink(String url, String userId) {
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            session.writeTransaction(addLinkTransaction(url, userId));
        }
    }

    private static TransactionWork<Result> addLinkTransaction(String url, String userId) {
        return tx -> {
            Map<String, Object> params = Map.of(
                    "link_id", url,
                    "user_id", userId
                    );

            try {
                return tx.run(ADD_LINK_QUERY, params);
            } catch (Neo4jException ex) {
                System.out.println(ADD_LINK_QUERY + " raised an exception");
                throw ex;
            }
        };
    }
}
