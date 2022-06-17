package com.voting.constant;

public class ApiConstants {

    public static final String ENDPOINT_VOTES = "/votes";
    public static final String ENDPOINT_VOTES_PATH_COUNT_AGENDA_ID = "/count/{agendaId}";
    public static final String ENDPOINT_SESSIONS = "/sessions";
    public static final String ENDPOINT_AGENDA = "/agendas";
    public static final String NAO = "NAO";
    public static final String SIM = "SIM";
    public static final String ERROR_SESSION_NOT_FOUND = "Session Not Found";
    public static final String ERROR_SESSION_EXPIRED = "Session Expired";
    public static final String ERROR_DUPLICATED_VOTE = "Duplicated Vote";
    public static final String KAFKA_VOTE_GROUP = "vote";
    public static final String KAFKA_VOTE_TOPIC = "voting";
}
