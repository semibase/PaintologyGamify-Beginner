package com.paintology.lite.total.beginner.CameraPreview.manager.listener;


import androidx.annotation.RestrictTo;

/**
 * Created by Arpit Gandhi on 8/14/16.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface CameraCloseListener<CameraId> {
    void onCameraClosed(CameraId closedCameraId);
}
