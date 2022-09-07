package com.ch2.rater.service;

import com.ch2.rater.security.SecurityUtils;
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
public record MatchService(Driver driver) {
    private static final String GET_MATCHES_QUERY = "Match (u1: User {id: $user_id})-[r1:Rated]->" +
                                                        "(l:Link)" +
                                                        "<-[r2:Rated]-(u2: User) " +
                                                        "Return u2.id " +
                                                        "Limit 10";

    public List<String> getMatches() {
        String userId = SecurityUtils.getCurrentUserId();
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            return session.readTransaction(getMatchesTransaction(userId));
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
