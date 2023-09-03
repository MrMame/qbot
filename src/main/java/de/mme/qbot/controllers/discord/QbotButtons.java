package de.mme.qbot.controllers.discord;

import de.mme.qbot.helper.discord.actionbuttons.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QbotButtons {

    private AnonymAnswerActionButton anonymAnswerButton;
    private GetDareActionButton getDareButton;
    private GetQuestionActionButton getQuestionButton;
    private VoteAnswerAActionButton voteAnswerAButton;
    private VoteAnswerBActionButton voteAnswerBButton;
    private VoteAnswerCActionButton voteAnswerCButton;
    private VoteAnswerDActionButton voteAnswerDButton;
    private VoteAnswerEActionButton voteAnswerEButton;

    private List<IActionButton> buttons = new ArrayList<>();

    public QbotButtons(AnonymAnswerActionButton anonymAnswerButton, GetDareActionButton getDareButton, GetQuestionActionButton getQuestionButton, VoteAnswerAActionButton voteAnswerAButton, VoteAnswerBActionButton voteAnswerBButton, VoteAnswerCActionButton voteAnswerCButton, VoteAnswerDActionButton voteAnswerDButton, VoteAnswerEActionButton voteAnswerEButton) {
        this.anonymAnswerButton = anonymAnswerButton;
        this.getDareButton = getDareButton;
        this.getQuestionButton = getQuestionButton;
        this.voteAnswerAButton = voteAnswerAButton;
        this.voteAnswerBButton = voteAnswerBButton;
        this.voteAnswerCButton = voteAnswerCButton;
        this.voteAnswerDButton = voteAnswerDButton;
        this.voteAnswerEButton = voteAnswerEButton;

        buttons.add(anonymAnswerButton);
        buttons.add(getDareButton);
        buttons.add(getQuestionButton);
        buttons.add(voteAnswerAButton);
        buttons.add(voteAnswerBButton);
        buttons.add(voteAnswerCButton);
        buttons.add(voteAnswerDButton);
        buttons.add(voteAnswerEButton);
    }

    public AnonymAnswerActionButton getAnonymAnswerButton() {
        return anonymAnswerButton;
    }

    public GetDareActionButton getGetDareButton() {
        return getDareButton;
    }

    public GetQuestionActionButton getGetQuestionButton() {
        return getQuestionButton;
    }

    public VoteAnswerAActionButton getVoteAnswerAButton() {
        return voteAnswerAButton;
    }

    public VoteAnswerBActionButton getVoteAnswerBButton() {
        return voteAnswerBButton;
    }

    public VoteAnswerCActionButton getVoteAnswerCButton() {
        return voteAnswerCButton;
    }

    public VoteAnswerDActionButton getVoteAnswerDButton() {
        return voteAnswerDButton;
    }

    public VoteAnswerEActionButton getVoteAnswerEButton() {
        return voteAnswerEButton;
    }

    public List<IActionButton> getAsList() {
        return buttons;
    }
}
