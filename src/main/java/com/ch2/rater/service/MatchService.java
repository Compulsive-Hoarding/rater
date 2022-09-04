package com.ch2.rater.service;

import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchService {
    private static final String GET_MATCHES_QUERY = "Match (u1: User {id: $user_id})-[r1:Rated]->" +
            "(l:Link)" +
            "<-[r2:Rated]-(u2: User) " +
            "Return u2.id " +
            "Limit 10";
    private final Driver driver;

    public List<String> getMatches() {
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            return session.readTransaction(getMatchesTransaction("123123"));
        }
    }

    private static TransactionWork<List<String>> getMatchesTransaction(String userId) {
        return tx -> {
            Map<String, Object> params = Collections.singletonMap("user_id", userId);

            try {
                return tx.run(GET_MATCHES_QUERY, params).list(row -> row.get("u2.id").asString());
            } catch (Neo4jException ex) {
                System.out.println(GET_MATCHES_QUERY + " raised an exception");
                throw ex;
            }
        };
    }
}
