package com.paintology.lite.total.beginner.CameraPreview.controller.view;

import android.app.Activity;
import android.view.View;

import com.paintology.lite.total.beginner.CameraPreview.configuration.CameraConfiguration;
import com.paintology.lite.total.beginner.CameraPreview.utils.Size;


/**
 * Created by Arpit Gandhi on 7/6/16.
 */
public interface CameraView {

    Activity getActivity();

    void updateCameraPreview(Size size, View cameraPreview);

    void updateUiForMediaAction(@CameraConfiguration.MediaAction int mediaAction);

    void updateCameraSwitcher(int numberOfCameras);

    void onPhotoTaken();

    void onVideoRecordStart(int width, int height);

    void onVideoRecordStop();

    void releaseCameraPreview();

}
