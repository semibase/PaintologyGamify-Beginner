package com.paintology.lite.total.beginner.CameraPreview.ui.preview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.paintology.lite.total.beginner.CameraPreview.CustomCropper.CropImage;
import com.paintology.lite.total.beginner.CameraPreview.configuration.CameraConfiguration;
import com.paintology.lite.total.beginner.CameraPreview.ui.BaseSandriosActivity;
import com.paintology.lite.total.beginner.CameraPreview.ui.view.AspectFrameLayout;
import com.paintology.lite.total.beginner.CameraPreview.utils.Utils;
import com.paintology.lite.total.beginner.R;
import com.paintology.lite.total.beginner.util.KGlobal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/**
 * PreviewActivity to preview the image captured by sandrios camera
 * Created by Arpit Gandhi on 7/6/16.
 */
public class PreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PreviewActivity";

    private final static String SHOW_CROP = "show_crop";
    private final static String MEDIA_ACTION_ARG = "media_action_arg";
    private final static String FILE_PATH_ARG = "file_path_arg";
    private final static String RESPONSE_CODE_ARG = "response_code_arg";
    private final static String VIDEO_POSITION_ARG = "current_video_position";
    private final static String VIDEO_IS_PLAYED_ARG = "is_played";
    private final static String MIME_TYPE_VIDEO = "video";
    private final static String MIME_TYPE_IMAGE = "image";

    private int mediaAction;
    private String previewFilePath;
    private PreviewActivity mContext;
    private SurfaceView surfaceView;
    private FrameLayout photoPreviewContainer;
    private ImageView imagePreview;
    private ViewGroup buttonPanel;
    private AspectFrameLayout videoPreviewContainer;

    private MediaController mediaController;
    private MediaPlayer mediaPlayer;

    private int currentPlaybackPosition = 0;
    private boolean isVideoPlaying = true;
    private boolean showCrop = false;

    private MediaController.MediaPlayerControl MediaPlayerControlImpl = new MediaController.MediaPlayerControl() {
        @Override
        public void start() {
            mediaPlayer.start();
        }

        @Override
        public void pause() {
            mediaPlayer.pause();
        }

        @Override
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public void seekTo(int pos) {
            mediaPlayer.seekTo(pos);
        }

        @Override
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            return 0;
        }

        @Override
        public boolean canPause() {
            return true;
        }

        @Override
        public boolean canSeekBackward() {
            return true;
        }

        @Override
        public boolean canSeekForward() {
            return true;
        }

        @Override
        public int getAudioSessionId() {
            return mediaPlayer.getAudioSessionId();
        }
    };
    private SurfaceHolder.Callback surfaceCallbacks = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            showVideoPreview(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    public static Intent newIntent(Context context,
                                   @CameraConfiguration.MediaAction int mediaAction,
                                   String filePath, boolean showImageCrop) {

        return new Intent(context, PreviewActivity.class)
                .putExtra(MEDIA_ACTION_ARG, mediaAction)
                .putExtra(SHOW_CROP, showImageCrop)
                .putExtra(FILE_PATH_ARG, filePath);
    }

    public static boolean isResultConfirm(@NonNull Intent resultIntent) {
        return BaseSandriosActivity.ACTION_CONFIRM == resultIntent.getIntExtra(RESPONSE_CODE_ARG, -1);
    }

    public static String getMediaFilePatch(@NonNull Intent resultIntent) {
        return resultIntent.getStringExtra(FILE_PATH_ARG);
    }

    public static boolean isResultRetake(@NonNull Intent resultIntent) {
        return BaseSandriosActivity.ACTION_RETAKE == resultIntent.getIntExtra(RESPONSE_CODE_ARG, -1);
    }

    public static boolean isResultCancel(@NonNull Intent resultIntent) {
        return BaseSandriosActivity.ACTION_CANCEL == resultIntent.getIntExtra(RESPONSE_CODE_ARG, -1);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        mContext = this;
        surfaceView = (SurfaceView) findViewById(R.id.video_preview);
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mediaController == null) return false;
                if (mediaController.isShowing()) {
                    mediaController.hide();
                    showButtonPanel(true);
                } else {
                    showButtonPanel(false);
                    mediaController.show();
                }
                return false;
            }
        });

        videoPreviewContainer = (AspectFrameLayout) findViewById(R.id.previewAspectFrameLayout);
        photoPreviewContainer = (FrameLayout) findViewById(R.id.photo_preview_container);
        imagePreview = (ImageView) findViewById(R.id.image_view);
        buttonPanel = (ViewGroup) findViewById(R.id.preview_control_panel);
        View confirmMediaResult = findViewById(R.id.confirm_media_result);
        View reTakeMedia = findViewById(R.id.re_take_media);
        View cancelMediaAction = findViewById(R.id.cancel_media_action);

        findViewById(R.id.crop_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* UCrop.Options options = new UCrop.Options();
                options.setToolbarColor(ContextCompat.getColor(mContext, android.R.color.black));
                options.setStatusBarColor(ContextCompat.getColor(mContext, android.R.color.black));
                Uri uri = Uri.fromFile(new File(previewFilePath));
                UCrop.of(uri, uri)
                        .withOptions(options)
                        .start(mContext);*/
                File _latestFile = new File(previewFilePath);
                Uri contentUri = Uri.fromFile(_latestFile);
                CropImage.activity(contentUri)
                        .start(PreviewActivity.this);
            }
        });

        if (confirmMediaResult != null)
            confirmMediaResult.setOnClickListener(this);

        if (reTakeMedia != null)
            reTakeMedia.setOnClickListener(this);

        if (cancelMediaAction != null)
            cancelMediaAction.setOnClickListener(this);

        Bundle args = getIntent().getExtras();

        mediaAction = args.getInt(MEDIA_ACTION_ARG);
        previewFilePath = args.getString(FILE_PATH_ARG);
        Log.e("TAGGG", "displayImage Logic call Here arg> " + args.getBoolean(SHOW_CROP));
        showCrop = args.getBoolean(SHOW_CROP);

        if (mediaAction == CameraConfiguration.MEDIA_ACTION_VIDEO) {
            displayVideo(savedInstanceState);
        } else if (mediaAction == CameraConfiguration.MEDIA_ACTION_PHOTO) {
            displayImage();
        } else {
            String mimeType = Utils.getMimeType(previewFilePath);
            if (mimeType.contains(MIME_TYPE_VIDEO)) {
                displayVideo(savedInstanceState);
            } else if (mimeType.contains(MIME_TYPE_IMAGE)) {
                displayImage();
            } else finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveVideoParams(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.e("TAGGG", "CROP_IMAGE_ACTIVITY_REQUEST_CODE Result > " + resultUri + " Path " + KGlobal.getPath(resultUri, PreviewActivity.this));

                /*ArrayList<String> Sample = new ArrayList<>();
                Sample.add(Utilities.getPath(resultUri));

                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("result", Sample);
                returnIntent.putExtra("filetype", OpenGallery.isImageSelected);

                setResult(RESULT_OK, returnIntent);
                finish();*/
                previewFilePath = KGlobal.getPath(resultUri, PreviewActivity.this);
                showImagePreview();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (resultCode == RESULT_OK) {
            showImagePreview();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaController != null) {
            mediaController.hide();
            mediaController = null;
        }
    }

    private void displayImage() {
        Log.e("TAGGG", "displayImage Logic call Here > " + showCrop);
        if (showCrop)
            findViewById(R.id.crop_image).setVisibility(View.VISIBLE);
        else findViewById(R.id.crop_image).setVisibility(View.GONE);

        videoPreviewContainer.setVisibility(View.GONE);
        surfaceView.setVisibility(View.GONE);
        showImagePreview();
    }

    private void showImagePreview() {
        try {
            Uri uri = Uri.fromFile(new File(previewFilePath));
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);

            Glide.with(PreviewActivity.this)
                    .load(uri)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.paintology_logo)
                            .fitCenter())
                    .into(imagePreview);

            /*imagePreview.getCropImageView().setImageUri(uri, null);
            imagePreview.getOverlayView().setShowCropFrame(false);
            imagePreview.getOverlayView().setShowCropGrid(false);
            imagePreview.getCropImageView().setRotateEnabled(false);
            imagePreview.getOverlayView().setDimmedColor(Color.TRANSPARENT);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayVideo(Bundle savedInstanceState) {
        Log.e("TAGGG", "displayImage Logic call Here 2> " + showCrop);
        findViewById(R.id.crop_image).setVisibility(View.GONE);
        if (savedInstanceState != null) {
            loadVideoParams(savedInstanceState);
        }
        photoPreviewContainer.setVisibility(View.GONE);
        surfaceView.getHolder().addCallback(surfaceCallbacks);
    }

    private void showVideoPreview(SurfaceHolder holder) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(previewFilePath);
            mediaPlayer.setDisplay(holder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaController = new MediaController(mContext);
                    mediaController.setAnchorView(surfaceView);
                    mediaController.setMediaPlayer(MediaPlayerControlImpl);

                    int videoWidth = mp.getVideoWidth();
                    int videoHeight = mp.getVideoHeight();
                    Log.e("TAGGG", "View Height Width Print > " + videoWidth + " >H> " + videoHeight);
                    videoPreviewContainer.setAspectRatio((double) videoWidth / videoHeight);

                    mediaPlayer.start();
                    mediaPlayer.seekTo(currentPlaybackPosition);

                    if (!isVideoPlaying)
                        mediaPlayer.pause();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    finish();
                    return true;
                }
            });
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(TAG, "Error media player playing video.");
            finish();
        }
    }

    private void saveCroppedImage(Uri croppedFileUri) {
        try {
            File saveFile = new File(previewFilePath);
            FileInputStream inStream = new FileInputStream(new File(croppedFileUri.getPath()));
            FileOutputStream outStream = new FileOutputStream(saveFile);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveVideoParams(Bundle outState) {
        if (mediaPlayer != null) {
            outState.putInt(VIDEO_POSITION_ARG, mediaPlayer.getCurrentPosition());
            outState.putBoolean(VIDEO_IS_PLAYED_ARG, mediaPlayer.isPlaying());
        }
    }

    private void loadVideoParams(Bundle savedInstanceState) {
        currentPlaybackPosition = savedInstanceState.getInt(VIDEO_POSITION_ARG, 0);
        isVideoPlaying = savedInstanceState.getBoolean(VIDEO_IS_PLAYED_ARG, true);
    }

    private void showButtonPanel(boolean show) {
        if (show) {
            buttonPanel.setVisibility(View.VISIBLE);
        } else {
            buttonPanel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        Intent resultIntent = new Intent();
        if (view.getId() == R.id.confirm_media_result) {
            resultIntent.putExtra(RESPONSE_CODE_ARG, BaseSandriosActivity.ACTION_CONFIRM);
            resultIntent.putExtra(FILE_PATH_ARG, previewFilePath);
            try {
                rotateImageIfRequired(previewFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.re_take_media) {
            deleteMediaFile();
            resultIntent.putExtra(RESPONSE_CODE_ARG, BaseSandriosActivity.ACTION_RETAKE);
        } else if (view.getId() == R.id.cancel_media_action) {
            deleteMediaFile();
            resultIntent.putExtra(RESPONSE_CODE_ARG, BaseSandriosActivity.ACTION_CANCEL);
        }
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    /**
     * Rotate an image if required.
     *
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    private Bitmap rotateImageIfRequired(String selectedImage) throws IOException {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(selectedImage, bmOptions);

        InputStream input = getContentResolver().openInputStream(Uri.fromFile(new File(selectedImage)));
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage);

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(bitmap, 270);
            default:
                return bitmap;
        }
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteMediaFile();
    }

    private boolean deleteMediaFile() {
        File mediaFile = new File(previewFilePath);
        return mediaFile.delete();
    }
}
