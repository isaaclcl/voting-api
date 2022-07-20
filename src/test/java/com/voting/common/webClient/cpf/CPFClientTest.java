//package com.voting.common.webClient.cpf;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
//class CPFClientTest {
//
//    @InjectMocks
//    CPFClient cpfClient;
//
//    @Mock
//    CPFClientConfig cpfClientConfig;
//
//    @Test
//    void givenValidCPFWhenCallIsAbleToVoteThenReturnTrue() {
//        //given
//        String validCPF = "59161298077";
//        //when
//        boolean ableToVote = this.cpfClient.isAbleToVote(validCPF);
//
//        //then
//        assertTrue(ableToVote);
//    }
//}