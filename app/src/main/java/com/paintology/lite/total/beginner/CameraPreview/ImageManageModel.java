package com.paintology.lite.total.beginner.CameraPreview;

public class ImageManageModel {

    boolean isDone = false;
    String localImagePath = "";

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }
}
