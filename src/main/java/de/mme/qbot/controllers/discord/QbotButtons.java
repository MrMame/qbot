package de.mme.qbot.controllers.discord;

import de.mme.qbot.helper.discord.actionbuttons.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QbotButtons {

    private AnonymAnswerButton anonymAnswerButton;
    private GetDareButton getDareButton;
    private GetQuestionButton getQuestionButton;
    private VoteAnswerAButton voteAnswerAButton;
    private VoteAnswerBButton voteAnswerBButton;
    private VoteAnswerCButton voteAnswerCButton;
    private VoteAnswerDButton voteAnswerDButton;
    private VoteAnswerEButton voteAnswerEButton;

    private List<IActionButton> buttons = new ArrayList<>();

    public QbotButtons(AnonymAnswerButton anonymAnswerButton, GetDareButton getDareButton, GetQuestionButton getQuestionButton, VoteAnswerAButton voteAnswerAButton, VoteAnswerBButton voteAnswerBButton, VoteAnswerCButton voteAnswerCButton, VoteAnswerDButton voteAnswerDButton, VoteAnswerEButton voteAnswerEButton) {
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

    public AnonymAnswerButton getAnonymAnswerButton() {
        return anonymAnswerButton;
    }

    public GetDareButton getGetDareButton() {
        return getDareButton;
    }

    public GetQuestionButton getGetQuestionButton() {
        return getQuestionButton;
    }

    public VoteAnswerAButton getVoteAnswerAButton() {
        return voteAnswerAButton;
    }

    public VoteAnswerBButton getVoteAnswerBButton() {
        return voteAnswerBButton;
    }

    public VoteAnswerCButton getVoteAnswerCButton() {
        return voteAnswerCButton;
    }

    public VoteAnswerDButton getVoteAnswerDButton() {
        return voteAnswerDButton;
    }

    public VoteAnswerEButton getVoteAnswerEButton() {
        return voteAnswerEButton;
    }

    public List<IActionButton> getAsList() {
        return buttons;
    }
}
