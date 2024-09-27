package com.pixelbattle.extender.events;

import com.pixelbattle.extender.General;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class ProcessEventBus {
    ProgressBar progressBar;
    Text progressText;
    Text parsingStatus;
    Text extendingStatus;
    Text integrityStatus;
    Text totalStatus;

    public int chunksCount;

    public void nextStepOfExtending() {
        this.chunksCount++;
        this.progressBar.setProgress((double) this.chunksCount / General.countOfChunks);
        this.progressText.setText(100 * this.chunksCount / General.countOfChunks + "%");
    }

    public void setParsingStatus(String status) {
        this.parsingStatus.setText("1. Parsing: " + status);
    }

    public void setExtendingStatus(String status) {
        this.extendingStatus.setText("2. Extending: " + status);
    }

    public void setIntegrityStatus(String status) {
        this.integrityStatus.setText("3. Integrity: " + status);
    }

    public void setTotalStatus(String status) {
        this.totalStatus.setText("Total: " + status);
    }

    public void clear() {
        this.setTotalStatus("None");
        this.setIntegrityStatus("None");
        this.setExtendingStatus("None");
        this.setParsingStatus("None");
        this.progressBar.setProgress(0);
        this.progressText.setText("0%");
        this.chunksCount = 0;
    }

    public void setChunksCount(int chunksCount) {
        this.chunksCount = chunksCount;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setProgressText(Text progressText) {
        this.progressText = progressText;
    }

    public void setParsingStatus(Text parsingStatus) {
        this.parsingStatus = parsingStatus;
    }

    public void setExtendingStatus(Text extendingStatus) {
        this.extendingStatus = extendingStatus;
    }

    public void setIntegrityStatus(Text integrityStatus) {
        this.integrityStatus = integrityStatus;
    }

    public void setTotalStatus(Text totalStatus) {
        this.totalStatus = totalStatus;
    }

}
