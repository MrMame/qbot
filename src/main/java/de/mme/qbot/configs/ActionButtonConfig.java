package de.mme.qbot.configs;

import de.mme.qbot.helper.discord.actionbuttons.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class ActionButtonConfig {

    // AnonymAnswerButton anonymAnswerButton, GetQuestionButton getQuestionButton, GetDareButton getDareButton, VoteAnswerAButton voteAnswerAButton, VoteAnswerBButton voteAnswerBButton, VoteAnswerCButton voteAnswerCButton, VoteAnswerDButton voteAnswerDButton, VoteAnswerEButton voteAnswerEButton

    @Bean
    public AnonymAnswerButton createAnonymAnswerButton(){
        return new AnonymAnswerButton();
    }
    @Bean
    public GetQuestionButton createGetQuestionButton(){
        return new GetQuestionButton();
    }
    @Bean
    public GetDareButton createGetDareButton(){
        return new GetDareButton();
    }
    @Bean
    public VoteAnswerAButton createVoteAnswerAButton(){
        return new VoteAnswerAButton();
    }
    @Bean
    public VoteAnswerBButton createVoteAnswerBButton(){
        return new VoteAnswerBButton();
    }
    @Bean
    public VoteAnswerCButton createVoteAnswerCButton(){
        return new VoteAnswerCButton();
    }
    @Bean
    public VoteAnswerDButton createVoteAnswerDButton(){
        return new VoteAnswerDButton();
    }
    @Bean
    public VoteAnswerEButton createVoteAnswerEButton(){
        return new VoteAnswerEButton();
    }

}
