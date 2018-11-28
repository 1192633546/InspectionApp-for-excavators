package com.inspectionsheetdemo.Drawer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import eu.thirdspaceauto.akka.hacksprint.R;
import eu.thirdspaceauto.akka.hacksprint.Utils.CameraPreview;
import eu.thirdspaceauto.akka.hacksprint.Utils.GIFView;

public class Preview extends Activity {
    private static final String TAG = "PreviewActivity";
    CameraPreview preview;
    Button buttonClick;
    Camera camera;
    Activity act;
    Context ctx;
    String component_str= "";
    GIFView gifView;
    int REQUEST_CODE;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        act = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        component_str = getIntent().getStringExtra("component");
        REQUEST_CODE = getIntent().getIntExtra("request_code",100);
        setContentView(R.layout.preview);

        gifView = (GIFView) findViewById(R.id.gif_view);
        gifView.loadGIFResource(this, R.drawable.triple_shoe_1);


        preview = new CameraPreview(this, (SurfaceView) findViewById(R.id.surfaceView));
        preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ((FrameLayout) findViewById(R.id.layout)).addView(preview);
        preview.setKeepScreenOn(true);

        preview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                camera.takePicture(shutterCallback, rawCallback, jpegCallback);
            }
        });


        buttonClick = (Button) findViewById(R.id.btnCapture);
        buttonClick.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(camera!=null) {
                    camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                camera = Camera.open(0);
                camera.startPreview();
                preview.setCamera(camera);
            } catch (RuntimeException ex) {
                Toast.makeText(ctx, "Camera not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    private void resetCam() {
        camera.startPreview();
        preview.setCamera(camera);
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
        }
    };

    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Toast.makeText(getApplicationContext(),"Picture taken",Toast.LENGTH_SHORT).show();
        }
    };

    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            new SaveImageTask().execute(data);
        }
    };

    private class SaveImageTask extends AsyncTask<byte[], String, String> {

        @Override
        protected String doInBackground(byte[]... data) {
            FileOutputStream outStream = null;
            String path = "";
            // Write to SD Card
            try {

                Bitmap realImage = BitmapFactory.decodeByteArray(data[0], 0, data[0].length);
                Bitmap final_bitmap = rotate(realImage, 90);

                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/volvo");
                dir.mkdirs();

                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);

                outStream = new FileOutputStream(outFile);
//                realImage.compress(Bitmap.CompressFormat.JPEG,90,outStream);
//                outStream.write(data[0]);
                byte[] bytes = getByte(final_bitmap);
                outStream.write(bytes);
                outStream.flush();
                outStream.close();

                path = outFile.getAbsolutePath();
                refreshGallery(outFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            return path;
        }

        @Override
        protected void onPostExecute(String path) {
            super.onPostExecute(path);
            Log.d(TAG,"onPost path= "+path);
            resetCam();
            Intent intent = new Intent();
            intent.putExtra("path",path);
            intent.putExtra("component",component_str);
            setResult(REQUEST_CODE,intent);
            act.finish();
        }
    }

    public byte[] getByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}

